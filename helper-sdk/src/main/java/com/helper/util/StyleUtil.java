package com.helper.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;


public class StyleUtil {

    public static SpannableString spannableText(String sentence, int textColor, String... coloredTest) {
        return spannableText(sentence, textColor, 0, false, coloredTest);
    }
    public static SpannableString spannableText(String sentence, int textColor, boolean isBold, String... coloredTest) {
        return spannableText(sentence, textColor, 0, isBold, coloredTest);
    }
    public static SpannableString spannableText(String sentence, int textColor, int textSizeInDip, boolean isBold, String... coloredTest) {
        SpannableString s = new SpannableString(sentence);
        try {
            for (String item : coloredTest){
                int indexOf = sentence.indexOf(item);
                if(indexOf > 0 && indexOf < sentence.length()) {
                    s.setSpan(new ForegroundColorSpan(textColor), indexOf, indexOf + item.length(), 0);
                    if(textSizeInDip != 0)
                        s.setSpan(new AbsoluteSizeSpan(textSizeInDip, true), indexOf, indexOf + item.length(), 0);
                    if(isBold)
                        s.setSpan(new StyleSpan(Typeface.BOLD), indexOf, indexOf + item.length(), 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public static SpannableString spannableTextBold(String sentence, String... boldTest) {
        return spannableTextBold(new StyleSpan(Typeface.BOLD), sentence, boldTest);
    }
    public static SpannableString spannableTextBold(StyleSpan styleSpan, String sentence, String... boldTest) {
        SpannableString s = new SpannableString(sentence);
        try {
            for (String item : boldTest){
                int indexOf = sentence.indexOf(item);
                if(indexOf > 0 && indexOf < sentence.length())
                    s.setSpan(styleSpan, indexOf, indexOf + item.length(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

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

    public static void setTranslucentStatus(Window window) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
    //            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(Color.parseColor("#FAFAFA"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static float dpToPx(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
