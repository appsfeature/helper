package com.helper.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.helper.R;
import com.helper.callback.Response;

public class AppDialog {

    public static void show(Context context, String message) {
        show(context, message, null, context.getString(R.string.ok), context.getString(R.string.cancel), null);
    }

    public static void show(Context context, String title, String message) {
        show(context, title, message, context.getString(R.string.ok), context.getString(R.string.cancel), null);
    }

    public static void show(Context context, String message, Response.Dialog callback) {
        show(context, message, null, context.getString(R.string.ok), context.getString(R.string.cancel), callback);
    }

    public static void show(Context context, String title, String message, Response.Dialog callback) {
        show(context, title, message, context.getString(R.string.ok), context.getString(R.string.cancel), callback);
    }

    public static void show(Context context, String title, String message, String buttonPositive, Response.Dialog callback) {
        show(context, title, message, buttonPositive, context.getString(R.string.cancel), callback);
    }

    public static void showAlert(Context context, String message) {
        show(context, message, null, context.getString(R.string.ok), null, null);
    }

    public static void showAlert(Context context, String title, String message) {
        show(context, title, message, context.getString(R.string.ok), null, null);
    }

    public static void showAlert(Context context, String title, String message, String buttonPositive) {
        show(context, title, message, buttonPositive, null, null);
    }

    public static void show(Context context, String title, @Nullable String subTitle, String buttonPositive, String buttonNegative, Response.Dialog callback) {
        try {
            if(context == null) return;
            Dialog dialog = new Dialog(context, R.style.DialogThemeFullScreen);
            dialog.setCancelable(false);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            dialog.setContentView(R.layout.base_dialog);
            TextView tvTitle = dialog.findViewById(R.id.tv_title);
            TextView tvSubTitle = dialog.findViewById(R.id.tv_sub_title);
            TextView btnAction = dialog.findViewById(R.id.btn_action);
            TextView btnCancel = dialog.findViewById(R.id.btn_cancel);
            tvTitle.setText(!TextUtils.isEmpty(title) ? title : "");
            if(!TextUtils.isEmpty(subTitle)) {
                tvSubTitle.setText(subTitle);
            }else {
                tvSubTitle.setVisibility(View.GONE);
            }
            if (buttonPositive != null) {
                btnAction.setText(buttonPositive);
            }
            if(!TextUtils.isEmpty(buttonNegative)) {
                btnCancel.setText(buttonNegative);
            }else {
                btnCancel.setVisibility(View.GONE);
            }
            btnAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    if (callback != null) {
                        callback.onDoneClicked();
                    }
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    if (callback != null) {
                        callback.onCancel();
                    }
                }
            });
            (dialog.findViewById(R.id.iv_close)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    if (callback != null) {
                        callback.onCancel();
                    }
                }
            });
            dialog.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
