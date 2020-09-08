package com.helper.util;

import android.content.Context;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;


public class DayNightPreference extends BasePrefUtil{

    private static final String DAY_MODE = "dayMode";
    public static final String NIGHT_MODE = "NIGHT_MODE";


    public static boolean isNightModeEnabled(Context context) {
        return getBoolean(context, NIGHT_MODE, false);
    }

    private static void setNightModeEnabled(Context context, boolean isNightModeEnabled) {
        setBoolean(context, NIGHT_MODE, isNightModeEnabled);
    }

    public static void setNightMode(Context context) {
        if (context != null) {
            setNightMode(context, isNightModeEnabled(context));
        }
    }

    public static void setNightMode(Context context, boolean isNightMode) {
        setNightModeEnabled(context, isNightMode);

        if (context instanceof AppCompatActivity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                ((AppCompatActivity) context).getDelegate().setLocalNightMode(isNightMode
                        ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
            }
        }
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }


}