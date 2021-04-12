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

import com.helper.Helper;
import com.helper.callback.ActivityLifecycleListener;

import java.util.List;

public abstract class ActivityTrackingApplication extends Application implements Application.ActivityLifecycleCallbacks {

    private final String TAG = ActivityTrackingApplication.class.getSimpleName();
    private long waitingTime = 800;

    public abstract boolean isDebugMode();

    private Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        if (isDebugMode()) {
            Helper.getInstance().setDebugMode(isDebugMode());
            registerActivityLifecycleCallbacks(this);
            handler = new Handler();
        }
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        try {
            Log.d(TAG, activity.getClass().getSimpleName());
            if (handler != null) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (activity instanceof AppCompatActivity) {
                            FragmentManager fm = ((AppCompatActivity) activity).getSupportFragmentManager();
                            List<Fragment> fragments = fm.getFragments();
                            for (Fragment fragment : fragments) {
                                Log.d(TAG, activity.getClass().getSimpleName() + " -> Attached (" +fragment.getClass().getSimpleName() + ")");
                            }
                        }
                    }
                }, waitingTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Helper.getInstance().getActivityLifecycleListener() != null) {
            for (ActivityLifecycleListener listener : Helper.getInstance().getActivityLifecycleListener()) {
                listener.onActivityCreated(activity, savedInstanceState);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onActivityPreCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        if (Helper.getInstance().getActivityLifecycleListener() != null) {
            for (ActivityLifecycleListener listener : Helper.getInstance().getActivityLifecycleListener()) {
                listener.onActivityPreCreated(activity, savedInstanceState);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onActivityPostCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        if (Helper.getInstance().getActivityLifecycleListener() != null) {
            for (ActivityLifecycleListener listener : Helper.getInstance().getActivityLifecycleListener()) {
                listener.onActivityPostCreated(activity, savedInstanceState);
            }
        }
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        if (Helper.getInstance().getActivityLifecycleListener() != null) {
            for (ActivityLifecycleListener listener : Helper.getInstance().getActivityLifecycleListener()) {
                listener.onActivityStarted(activity);
            }
        }
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        if (Helper.getInstance().getActivityLifecycleListener() != null) {
            for (ActivityLifecycleListener listener : Helper.getInstance().getActivityLifecycleListener()) {
                listener.onActivityResumed(activity);
            }
        }
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        if (Helper.getInstance().getActivityLifecycleListener() != null) {
            for (ActivityLifecycleListener listener : Helper.getInstance().getActivityLifecycleListener()) {
                listener.onActivityPaused(activity);
            }
        }
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        if (Helper.getInstance().getActivityLifecycleListener() != null) {
            for (ActivityLifecycleListener listener : Helper.getInstance().getActivityLifecycleListener()) {
                listener.onActivityStopped(activity);
            }
        }
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
        if (Helper.getInstance().getActivityLifecycleListener() != null) {
            for (ActivityLifecycleListener listener : Helper.getInstance().getActivityLifecycleListener()) {
                listener.onActivitySaveInstanceState(activity, outState);
            }
        }
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        if (Helper.getInstance().getActivityLifecycleListener() != null) {
            for (ActivityLifecycleListener listener : Helper.getInstance().getActivityLifecycleListener()) {
                listener.onActivityDestroyed(activity);
            }
        }
    }

    public ActivityTrackingApplication addActivityLifecycleListener(ActivityLifecycleListener listener) {
        Helper.getInstance().addActivityLifecycleListener(listener);
        return this;
    }

    public ActivityTrackingApplication setWaitingTime(long waitingTime) {
        this.waitingTime = waitingTime;
        return this;
    }
}
