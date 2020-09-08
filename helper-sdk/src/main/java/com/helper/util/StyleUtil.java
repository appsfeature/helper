package com.helper.util;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;



public class StyleUtil {
    public static void setStatusBarDarkMode(Activity activity, boolean darkMode) {
        try {
            if (darkMode) {
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    }
                }else {
                    activity.getWindow().setDecorFitsSystemWindows(false);
                    WindowInsetsController controller = activity.getWindow().getInsetsController();
                    if(controller != null) {
                        controller.hide(WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
//                        controller.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
                        controller.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
