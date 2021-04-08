package com.helper.application;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.helper.Helper;
import com.helper.callback.ActivityLifecycleListener;

public abstract class ActivityTrackingApplication extends Application implements Application.ActivityLifecycleCallbacks {

    private final String TAG = ActivityTrackingApplication.class.getSimpleName();

    public abstract boolean isDebugMode();

    @Override
    public void onCreate() {
        super.onCreate();
        if (isDebugMode()) {
            Helper.getInstance().setDebugMode(isDebugMode());
            registerActivityLifecycleCallbacks(this);
        }
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, activity.getClass().getSimpleName());
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

    public void addActivityLifecycleListener(ActivityLifecycleListener listener) {
        Helper.getInstance().addActivityLifecycleListener(listener);
    }
}
