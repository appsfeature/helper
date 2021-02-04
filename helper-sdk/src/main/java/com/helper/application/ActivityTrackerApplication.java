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

public abstract class ActivityTrackerApplication extends Application implements Application.ActivityLifecycleCallbacks {

    private final String TAG = ActivityTrackerApplication.class.getSimpleName();

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
        if (Helper.getInstance().getActivityLifecycleCallbacks() != null) {
            Helper.getInstance().getActivityLifecycleCallbacks().onActivityCreated(activity, savedInstanceState);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onActivityPreCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        if (Helper.getInstance().getActivityLifecycleCallbacks() != null) {
            Helper.getInstance().getActivityLifecycleCallbacks().onActivityPreCreated(activity, savedInstanceState);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onActivityPostCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        if (Helper.getInstance().getActivityLifecycleCallbacks() != null) {
            Helper.getInstance().getActivityLifecycleCallbacks().onActivityPostCreated(activity, savedInstanceState);
        }
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        if (Helper.getInstance().getActivityLifecycleCallbacks() != null) {
            Helper.getInstance().getActivityLifecycleCallbacks().onActivityStarted(activity);
        }
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        if (Helper.getInstance().getActivityLifecycleCallbacks() != null) {
            Helper.getInstance().getActivityLifecycleCallbacks().onActivityResumed(activity);
        }
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        if (Helper.getInstance().getActivityLifecycleCallbacks() != null) {
            Helper.getInstance().getActivityLifecycleCallbacks().onActivityPaused(activity);
        }
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        if (Helper.getInstance().getActivityLifecycleCallbacks() != null) {
            Helper.getInstance().getActivityLifecycleCallbacks().onActivityStopped(activity);
        }
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
        if (Helper.getInstance().getActivityLifecycleCallbacks() != null) {
            Helper.getInstance().getActivityLifecycleCallbacks().onActivitySaveInstanceState(activity, outState);
        }
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        if (Helper.getInstance().getActivityLifecycleCallbacks() != null) {
            Helper.getInstance().getActivityLifecycleCallbacks().onActivityDestroyed(activity);
        }
    }

}
