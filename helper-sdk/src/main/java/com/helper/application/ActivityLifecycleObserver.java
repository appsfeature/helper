package com.helper.application;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ActivityLifecycleObserver implements Application.ActivityLifecycleCallbacks {

    private Activity currentActivity;
    private static ActivityLifecycleObserver instance;

    public Activity getCurrentActivity() {
        return currentActivity;
    }

    private ActivityLifecycleObserver() {
    }

    public static ActivityLifecycleObserver getInstance() {
        if (instance == null) {
            instance = new ActivityLifecycleObserver();
        }
        return instance;
    }

    public void register(Application application) {
        if (application != null) {
            application.registerActivityLifecycleCallbacks(this);
        }
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        currentActivity = activity;
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        currentActivity = activity;

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
