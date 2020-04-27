package com.helper.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;


public class DayNightPreference {

    private static final String DAY_MODE = "dayMode";
    public static final String NIGHT_MODE = "NIGHT_MODE";


    public static boolean isNightModeEnabled(Context context) {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getBoolean(NIGHT_MODE, false);
    }

    private static void setNightModeEnabled(Context context, boolean isNightModeEnabled) {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(NIGHT_MODE, isNightModeEnabled);
        editor.apply();
    }

    public static void setNightMode(Context context) {
        if (context != null) {
            setNightMode(context, isNightModeEnabled(context));
        }
    }

    public static void setNightMode(Context context, boolean isNightMode) {
        setNightModeEnabled(context, isNightMode);

        if (context instanceof AppCompatActivity) {
            ((AppCompatActivity) context).getDelegate().setLocalNightMode(isNightMode
                    ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

        }
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }


}