package com.helper.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.helper.R;


/**
 * Usage Instruction
 */
/*private static AlertDialog dialog;

        public static void hideDialog() {
            try {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                    dialog = null;
                }
            } catch (Exception e) {
            }
        }

        public static void showDialog(Context context, String msg) {
            if (dialog == null) {
                try {
                    dialog = PopupProgress.newInstance(context, msg).show();
                } catch (Exception e) {
                    Logger.d(Logger.getClassPath(MCQUtil.class, "showDialog"), e.toString());
                }
            }
}*/

/**
 * @author Created by Abhijit on 19-Dec-16.
 */
public class PopupProgress {

    private Context context;
    private String message;
    private boolean cancelable;
    private TextView tvMessage;


    public static PopupProgress newInstance(Context context) {
        PopupProgress cDialog = new PopupProgress();
        cDialog.context = context;
        cDialog.cancelable = true;
        return cDialog;
    }

    public static PopupProgress newInstance(Context context, String message) {
        PopupProgress cDialog = new PopupProgress();
        cDialog.context = context;
        cDialog.message = message;
        cDialog.cancelable = true;
        return cDialog;
    }

    public PopupProgress setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return this;
    }

    public AlertDialog show() {
        return show(false);
    }

    public AlertDialog show(boolean isVertical) {
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

