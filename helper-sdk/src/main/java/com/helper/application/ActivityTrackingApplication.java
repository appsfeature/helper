package com.helper.application;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.helper.Helper;
import com.helper.callback.ActivityLifecycleListener;

import java.util.List;
import java.util.Map;

public abstract class ActivityTrackingApplication extends Application implements Application.ActivityLifecycleCallbacks {

    private final String TAG = "ActivityTracking";
    private long waitingTimeActivity = 800;
    private long waitingTimeFragment = 1500;

    public abstract boolean isDebugMode();

    private Handler handler;

    /**
     * @apiNote : Call from Application class according to need
     */
    public ActivityTrackingApplication addActivityLifecycleListener(int hashCode, ActivityLifecycleListener listener) {
        Helper.getInstance().addActivityLifecycleListener(hashCode, listener);
        registerActivityLifecycleCallbacksSingleton();
        return this;
    }

    public ActivityTrackingApplication setEnableCurrentActivityLifecycle(boolean isEnableCurrentActivityLifecycle) {
        Helper.getInstance().isEnableCurrentActivityLifecycle = isEnableCurrentActivityLifecycle;
        registerActivityLifecycleCallbacksSingleton();
        return this;
    }

    private boolean isRegisteredActivityLifecycle = false;

    private void registerActivityLifecycleCallbacksSingleton() {
        if (!isRegisteredActivityLifecycle) {
            Log.i(ActivityTrackingApplication.class.getSimpleName(), "registerActivityLifecycleCallbacksSingleton");
            isRegisteredActivityLifecycle = true;
            registerActivityLifecycleCallbacks(this);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (isDebugMode()) {
            Helper.getInstance().setDebugMode(isDebugMode());
            registerActivityLifecycleCallbacksSingleton();
            handler = new Handler();
        }
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        dispatchAllListeners(activity, "onActivityCreated", savedInstanceState);
        Helper.getInstance().setCurrentActivity(activity);
        if (isDebugMode()) {
            try {
                //Log Activity
                Log.d(TAG, "-------------------------------------------------");
                Log.d(TAG, activity.getClass().getSimpleName());
                if (handler != null) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (activity instanceof AppCompatActivity) {
                                FragmentManager fm = ((AppCompatActivity) activity).getSupportFragmentManager();
                                List<Fragment> fragments = fm.getFragments();
                                int count = 1;
                                for (Fragment fragment : fragments) {
                                    //Log Fragment attached on Activity
                                    if (count == 1) {
                                        Log.d(TAG, activity.getClass().getSimpleName() + " -> " + count + ". Attached (" + fragment.getClass().getSimpleName() + ")");
                                    }else {
                                        Log.d(TAG, getSpace(activity.getClass().getSimpleName() + " -> ") + count+ ". Attached (" + fragment.getClass().getSimpleName() + ")");
                                    }
                                    addChildFragmentTracking(fragment);
                                    count += 1;
                                }
                            }
                        }

                        private void addChildFragmentTracking(Fragment fragment) {
                            fragment.getLifecycle().addObserver(new DefaultLifecycleObserver() {
                                @Override
                                public void onResume(@NonNull LifecycleOwner owner) {
                                    owner.getLifecycle().removeObserver(this);
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                if(!fragment.isAdded()) return;
                                                FragmentManager cfm = fragment.getChildFragmentManager();
                                                List<Fragment> childFragments = cfm.getFragments();
                                                int mCount = 1;
                                                for (Fragment child : childFragments) {
                                                    //Log Child fragment attached on Fragment
                                                    if (mCount == 1) {
                                                        Log.d(TAG, " ");
                                                        Log.d(TAG, getSpace(activity.getClass().getSimpleName() + " -> ") + "<" + fragment.getClass().getSimpleName() + "--> " + mCount + ". Attached (" + child.getClass().getSimpleName() + ")");
                                                    } else {
                                                        Log.d(TAG, getSpace(activity.getClass().getSimpleName() + " -> " + "<" + fragment.getClass().getSimpleName() + "--> ") + mCount + ". Attached (" + child.getClass().getSimpleName() + ")");
                                                    }
                                                    addChildFragmentTracking(child);
                                                    mCount += 1;
                                                }
                                            } catch (IllegalStateException e) {
                                                e.printStackTrace();
                                                Log.d(TAG, e.toString());
                                            }
                                        }
                                    }, waitingTimeFragment);
                                }
                            });
                        }
                    }, waitingTimeActivity);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getSpace(String simpleName) {
        StringBuilder space = new StringBuilder(simpleName.length());
        for (int i = 0; i < simpleName.length(); i++) {
            space.append(" ");
        }
        return space.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onActivityPreCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        Helper.getInstance().setCurrentActivity(activity);
        dispatchAllListeners(activity, "onActivityPreCreated", savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onActivityPostCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        dispatchAllListeners(activity, "onActivityPostCreated", savedInstanceState);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        dispatchAllListeners(activity, "onActivityStarted");
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        dispatchAllListeners(activity, "onActivityResumed");
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        dispatchAllListeners(activity, "onActivityPaused");
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        dispatchAllListeners(activity, "onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
        dispatchAllListeners(activity, "onActivitySaveInstanceState", outState);
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        Helper.getInstance().setCurrentActivity(null);
        dispatchAllListeners(activity, "onActivityDestroyed");
    }

    public ActivityTrackingApplication setWaitingTime(long waitingTime) {
        this.waitingTimeActivity = waitingTime;
        return this;
    }

    public ActivityTrackingApplication setWaitingTimeFragment(long waitingTime) {
        this.waitingTimeFragment = waitingTime;
        return this;
    }

    public void dispatchAllListeners(Activity activity, String lifecycle) {
        dispatchAllListeners(activity, lifecycle, null);
    }

    public void dispatchAllListeners(Activity activity, String lifecycle, Bundle bundle) {
        try {
            if (Helper.getInstance().getActivityLifecycleListener() != null && Helper.getInstance().getActivityLifecycleListener().size() > 0) {
                for (Map.Entry<Integer, ActivityLifecycleListener> entry : Helper.getInstance().getActivityLifecycleListener().entrySet()) {
                    ActivityLifecycleListener callback = entry.getValue();
                    if (callback != null) {
                        switch (lifecycle) {
                            case "onActivityCreated":
                                callback.onActivityCreated(activity, bundle);
                                break;
                            case "onActivityPreCreated":
                                callback.onActivityPreCreated(activity, bundle);
                                break;
                            case "onActivityPostCreated":
                                callback.onActivityPostCreated(activity, bundle);
                                break;
                            case "onActivityStarted":
                                callback.onActivityStarted(activity);
                                break;
                            case "onActivityResumed":
                                callback.onActivityResumed(activity);
                                break;
                            case "onActivityPaused":
                                callback.onActivityPaused(activity);
                                break;
                            case "onActivityStopped":
                                callback.onActivityStopped(activity);
                                break;
                            case "onActivitySaveInstanceState":
                                callback.onActivitySaveInstanceState(activity, bundle);
                                break;
                            case "onActivityDestroyed":
                                callback.onActivityDestroyed(activity);
                                break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
