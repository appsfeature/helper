package com.helper.util;

import android.app.Activity;
import android.os.Build;
import android.view.View;

public class StyleUtil {
    public static void setStatusBarDarkMode(Activity activity, boolean darkMode) {
        if(darkMode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }
}
