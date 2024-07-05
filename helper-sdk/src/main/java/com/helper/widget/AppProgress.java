package com.helper.widget;


import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.helper.R;
import com.helper.util.Logger;

public class AppProgress {

    private Context context;
    private String message;
    private boolean cancelable;
    private TextView tvMessage;

    private static AlertDialog dialog;


    public static void show(Context context) {
        show(context, null, false, true);
    }

    public static void show(Context context, String msg) {
        show(context, msg, false);
    }

    public static void show(Context context, String msg, boolean isCancelable) {
        show(context, msg, isCancelable, false);
    }

    public static void show(Context context, String msg, boolean isCancelable, boolean isVertical) {
        if (dialog == null && context != null) {
            try {
                dialog = AppProgress.newInstance(context, msg)
                        .setCancelable(isCancelable)
                        .showProgress(isVertical);
            } catch (Exception e) {
                Logger.e(e.toString());
            }
        }
    }
    public static void hide() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
        } catch (Exception e) {
            Logger.e(e.toString());
        }
    }

    private static AppProgress newInstance(Context context, String message) {
        AppProgress cDialog = new AppProgress();
        cDialog.context = context;
        cDialog.message = message;
        cDialog.cancelable = true;
        return cDialog;
    }

    private AppProgress setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return this;
    }

    private AlertDialog showProgress(boolean isVertical) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context, R.style.DialogThemeFullScreen);
        final View dialogView = LayoutInflater.from(context).inflate(isVertical ? R.layout.base_dialog_progress_vertical : R.layout.base_dialog_progress, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(cancelable);
        tvMessage = dialogView.findViewById(R.id.tv_message);
        if (tvMessage != null && !TextUtils.isEmpty(message)) {
            tvMessage.setText(message);
        }
        AlertDialog b = dialogBuilder.create();
        b.show();
        return b;
    }

    public void updateMessage(String message) {
        this.message = message;
        if(tvMessage!=null){
            tvMessage.setText(message);
        }
    }
}

