package com.helper.callback;

import android.animation.Animator;
import android.app.Activity;
import android.view.View;


import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import com.helper.model.HistoryModelResponse;

public interface Response {

    //Useage : function(@Response.Visibility int visibility)
    @IntDef({VISIBLE, INVISIBLE, GONE})
    @Retention(RetentionPolicy.SOURCE)
    @interface Visibility {
    }

    interface Callback<T> {
        void onSuccess(T response);
        void onFailure(Exception e);
        default void onRetry(NetworkListener.Retry retryCallback){}
    }

    interface CallbackImage<T> {
        void onSuccess(T response, String imagePath, String pdfPath);
        void onFailure(Exception e);
        default void onRetry(NetworkListener.Retry retryCallback){}
    }

    interface Status<T> {
        void onSuccess(T response);
        default void onRetry(NetworkListener.Retry retryCallback){}
    }

    interface NetworkCallback<T> {
        void onCompleted();
        void onDataLoaded();
        void onSuccess(T response);
        void onFailure(Exception e);
        default void onRetry(NetworkListener.Retry retryCallback){}
    }

    interface OnCustomResponse {
        void onCustomResponse(boolean status, String data);
        default void onRetry(NetworkListener.Retry retryCallback){}
    }

    interface OnClickListener<T> {
        void onItemClicked(View view, T item);
    }

    interface OnListClickListener<T> {
        void onItemClicked(View view, T item);
        default void onUpdateListItem(View view, int position, T item) { }
        void onDeleteClicked(View view, int position, T item);
    }

    interface OnListUpdateListener<T> {
        void onUpdateListItem(View view, int position, T item);
        void onRemovedListItem(View view, int position, T item);
    }

    interface OnDeleteListener{
        void onRemoveItemFromList(int position);
    }

    interface OnCustomClick {
        void onCustomItemClick(int position);
    }

    interface Progress {
        void onStartProgressBar();
        void onStopProgressBar();
    }

    interface Helper {
        void onOpenPdf(Activity activity, int id, String title, String url);
    }

    interface AnimatorListener {
        default void onAnimationStart(Animator animation) {}
        void onAnimationEnd(Animator animation);
    }

    interface SlideListener {
        void onSlideUp();
        void onSlideDown();
    }

    interface StatsListener {
        void onStatsUpdated();
    }
}