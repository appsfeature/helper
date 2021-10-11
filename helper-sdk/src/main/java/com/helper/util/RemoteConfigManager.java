package com.helper.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;
import java.util.Map;


public class RemoteConfigManager {

    private static final long MINIMUM_FETCH_TIME_INTERVAL = 3600L;
    private static RemoteConfigManager instance;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private final HashMap<Integer, RemoteCallback> mRemoteCallbacks = new HashMap<>();
    private long lastCallTimeInMills = 0;

    public interface RemoteCallback {
        void onComplete(String status);
    }

    private RemoteConfigManager() {
        this.initialize();
    }

    public static RemoteConfigManager getInstance() {
        if (instance == null) {
            synchronized (RemoteConfigManager.class) {
                if (instance == null) instance = new RemoteConfigManager();
            }
        }
        return instance;
    }

    /**
     * @apiNote need this method to initialize remote config in application class.
     */
    public RemoteConfigManager init() {
        if (isValidRequest()) {
            requestOnServer();
        }
        return this;
    }

    public void fetch(int hashCode, RemoteCallback callback) {
        if(isValidRequest()){
            addCallback(hashCode, callback);
            requestOnServer();
        } else if(isRequestRunning){
            addCallback(hashCode, callback);
        } else if(isRequestAttempted() && isDataAvailable()){
            addCallback(hashCode, callback);
            dispatchStatusUpdated(BaseConstants.SUCCESS);
            removeCallback(hashCode);
        }
    }

    /**
     * @apiNote call this method when request to get new update is mandatory.
     */
    public void fetchAndActivate(int hashCode, RemoteCallback callback) {
        addCallback(hashCode, callback);
        requestOnServer();
    }

    private void initialize() {
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(MINIMUM_FETCH_TIME_INTERVAL)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);

        //set defaults
//        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);
    }

    private boolean isRequestRunning = false;

    public void requestOnServer() {
        if (!isRequestRunning) {
            isRequestRunning = true;
            lastCallTimeInMills = System.currentTimeMillis();
            mFirebaseRemoteConfig.fetchAndActivate()
                    .addOnCompleteListener(new OnCompleteListener<Boolean>() {
                        @Override
                        public void onComplete(@NonNull Task<Boolean> task) {
                            isRequestRunning = false;
                            dispatchStatusUpdated(task.isSuccessful() ? BaseConstants.SUCCESS : BaseConstants.FAILURE);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dispatchStatusUpdated(BaseConstants.FAILURE);
                        }
                    });
        }
    }

    private boolean isValidRequest() {
        if(lastCallTimeInMills == 0){
            return true;
        }
        return (System.currentTimeMillis() - lastCallTimeInMills) > MINIMUM_FETCH_TIME_INTERVAL;
    }

    private boolean isDataAvailable() {
        return mFirebaseRemoteConfig != null && mFirebaseRemoteConfig.getAll().size() > 0;
    }

    private boolean isRequestAttempted() {
        return getLastRequestTimeInMillis() > -1;
    }

    /**
     * @return -1 if no fetch attempt has been made yet. Otherwise, returns the timestamp of the last successful fetch operation.
     */
    private long getLastRequestTimeInMillis() {
        return mFirebaseRemoteConfig != null ? mFirebaseRemoteConfig.getInfo().getFetchTimeMillis() : -1;
    }

    @Nullable
    public FirebaseRemoteConfig getFirebaseRemoteConfig() {
        return mFirebaseRemoteConfig;
    }

    public boolean isLastFetchRequestCompleted() {
        return mFirebaseRemoteConfig != null
                && mFirebaseRemoteConfig.getInfo().getLastFetchStatus() == FirebaseRemoteConfig.LAST_FETCH_STATUS_SUCCESS;
    }

    @Nullable
    public String getJson(String key) {
        return mFirebaseRemoteConfig != null ? mFirebaseRemoteConfig.getString(key) : null;
    }


    public RemoteConfigManager addCallback(int hashCode, RemoteCallback callback) {
        if (callback != null) {
            synchronized (mRemoteCallbacks) {
                this.mRemoteCallbacks.put(hashCode, callback);
            }
        }
        return this;
    }

    public void removeCallback(int hashCode) {
        if (mRemoteCallbacks.get(hashCode) != null) {
            synchronized (mRemoteCallbacks) {
                this.mRemoteCallbacks.remove(hashCode);
            }
        }
    }

    public void dispatchStatusUpdated(String status) {
        try {
            if (mRemoteCallbacks.size() > 0) {
                for (Map.Entry<Integer, RemoteCallback> entry : mRemoteCallbacks.entrySet()) {
                    RemoteCallback callback = entry.getValue();
                    if (callback != null) {
                        callback.onComplete(status);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
