package com.helper.application;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.helper.Helper;

public class ActivityTrackerApplication extends Application implements Application.ActivityLifecycleCallbacks {

    private final String TAG = ActivityTrackerApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        if (Helper.getInstance().isEnableDebugMode()) {
            registerActivityLifecycleCallbacks(this);
        }
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }
}
