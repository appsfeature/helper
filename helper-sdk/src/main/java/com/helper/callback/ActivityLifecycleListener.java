package com.helper.callback;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface ActivityLifecycleListener {

    default void onActivityPreCreated(@NonNull Activity activity,
                                      @Nullable Bundle savedInstanceState) {
    }

    default void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState){
    }

    default void onActivityPostCreated(@NonNull Activity activity,
                                       @Nullable Bundle savedInstanceState) {
    }

    default void onActivityPreStarted(@NonNull Activity activity) {
    }

    default void onActivityStarted(@NonNull Activity activity){
    }

    default void onActivityPostStarted(@NonNull Activity activity) {
    }

    default void onActivityPreResumed(@NonNull Activity activity) {
    }

    default void onActivityResumed(@NonNull Activity activity){
    }

    default void onActivityPostResumed(@NonNull Activity activity) {
    }

    default void onActivityPrePaused(@NonNull Activity activity) {
    }

    default void onActivityPaused(@NonNull Activity activity){
    }

    default void onActivityPostPaused(@NonNull Activity activity) {
    }

    default void onActivityPreStopped(@NonNull Activity activity) {
    }

    default void onActivityStopped(@NonNull Activity activity){
    }

    default void onActivityPostStopped(@NonNull Activity activity) {
    }

    default void onActivityPreSaveInstanceState(@NonNull Activity activity,
                                                @NonNull Bundle outState) {
    }

    default void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState){
    }

    default void onActivityPostSaveInstanceState(@NonNull Activity activity,
                                                 @NonNull Bundle outState) {
    }

    default void onActivityPreDestroyed(@NonNull Activity activity) {
    }

    default void onActivityDestroyed(@NonNull Activity activity){
    }

    default void onActivityPostDestroyed(@NonNull Activity activity) {
    }
}
