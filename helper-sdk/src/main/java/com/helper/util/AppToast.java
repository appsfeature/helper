package com.helper.util;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.helper.R;

public class AppToast {
    public static void success(Context context, String message) {
        if(context instanceof Activity){
            Activity activity = (Activity) context;
            View layout = activity.getLayoutInflater().inflate(R.layout.lib_toast_success, (ViewGroup) activity.findViewById(R.id.lib_toast_success_layout));
            LottieAnimationView lottieView = layout.findViewById(R.id.lottie_view);
            TextView tvTitle = layout.findViewById(R.id.tv_title);
            lottieView.setAnimation(R.raw.gif_tick_success);
            lottieView.setSpeed(2);
            tvTitle.setText(message);
            Toast toast = new Toast(activity);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.setView(layout);//setting the view of custom toast layout
            toast.show();
        }else {
            BaseUtil.showToastCentre(context, message);
        }
    }

    public static void failure(Context context, String message) {
        if(context instanceof Activity) {
            Activity activity = (Activity) context;
            View layout = activity.getLayoutInflater().inflate(R.layout.lib_toast_success, (ViewGroup) activity.findViewById(R.id.lib_toast_success_layout));
            LottieAnimationView lottieView = layout.findViewById(R.id.lottie_view);
            TextView tvTitle = layout.findViewById(R.id.tv_title);
            lottieView.setAnimation(R.raw.gif_failure);
            tvTitle.setText(message);
            Toast toast = new Toast(activity);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.setView(layout);//setting the view of custom toast layout
            toast.show();
        }else {
            BaseUtil.showToastCentre(context, message);
        }
    }
}
