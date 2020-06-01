package com.helper.callback;

import android.animation.Animator;
import android.app.Activity;
import android.view.View;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public interface Response {

    //Useage : function(@Response.Visibility int visibility)
    @IntDef({VISIBLE, INVISIBLE, GONE})
    @Retention(RetentionPolicy.SOURCE)
    @interface Visibility {
    }

    interface Callback<T> {
        void onSuccess(T response);

        void onFailure(Exception e);
    }

    interface CallbackImage<T> {
        void onSuccess(T response, String imagePath, String pdfPath);

        void onFailure(Exception e);
    }

    interface Status<T> {
        void onSuccess(T response);
    }

    interface NetworkCallback<T> {
        void onCompleted();

        void onDataLoaded();

        void onSuccess(T response);

        void onFailure(Exception e);
    }

    interface OnClickListener<T> {
        void onItemClicked(View view, T item);
    }

    interface Progress {
        void onStartProgressBar();

        void onStopProgressBar();
    }

    interface Helper {
        void onOpenPdf(Activity activity, int id, String title, String url);
    }

    interface AnimatorListener {
        void onAnimationStart(Animator animation);

        void onAnimationEnd(Animator animation);
    }

    interface SlideListener {
        void onSlideUp();
        void onSlideDown();
    }
}