package com.helper.callback;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public interface ActivityLifecycleListener {

    default void onActivityPreCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
    }

    void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState);

    default void onActivityPostCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
    }

    default void onActivityStarted(@NonNull Activity activity) {
    }

    default void onActivityResumed(@NonNull Activity activity) {
    }

    default void onActivityPaused(@NonNull Activity activity) {
    }

    default void onActivityStopped(@NonNull Activity activity) {
    }

    default void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
    }

    default void onActivityDestroyed(@NonNull Activity activity) {
    }
}
