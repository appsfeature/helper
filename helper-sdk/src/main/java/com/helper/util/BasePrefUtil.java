package com.helper.util;

import android.content.Context;
import android.content.SharedPreferences;



public class BasePrefUtil {

    private static final String TAG = "BasePrefUtil";
    private static final String DOWNLOAD_DIRECTORY = "DownloadDirectory";
    private static SharedPreferences sharedPreferences;


    private static SharedPreferences getDefaultSharedPref(Context context) {
        if (sharedPreferences == null)
            sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

        return sharedPreferences;
    }

    public static void setString(Context context, String key, String value) {
        final SharedPreferences.Editor editor = getDefaultSharedPref(context).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(Context context, String key) {
        return getString(context, key, "");
    }

    public static String getString(Context context, String key, String defaultValue) {
        return getDefaultSharedPref(context).getString(key, defaultValue);
    }

    public static void setInt(Context context, String key, int value) {
        final SharedPreferences.Editor editor = getDefaultSharedPref(context).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getInt(Context context, String key) {
        return getInt(context, key, 0);
    }

    public static int getInt(Context context, String key, int defaultValue) {
        return getDefaultSharedPref(context).getInt(key, defaultValue);
    }

    public static void setFloat(Context context, String key, float value) {
        final SharedPreferences.Editor editor = getDefaultSharedPref(context).edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static float getFloat(Context context, String key) {
        return getFloat(context, key, 0);
    }

    public static float getFloat(Context context, String key, float defaultValue) {
        return getDefaultSharedPref(context).getFloat(key, defaultValue);
    }

    public static void setLong(Context context, String key, long value) {
        final SharedPreferences.Editor editor = getDefaultSharedPref(context).edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static long getLong(Context context, String key) {
        return getDefaultSharedPref(context).getLong(key, 0);
    }

    public static void setBoolean(Context context, String key, boolean value) {
        final SharedPreferences.Editor editor = getDefaultSharedPref(context).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        return getDefaultSharedPref(context).getBoolean(key, defaultValue);
    }

    public static void removeKey(Context context, String key) {
        getDefaultSharedPref(context).edit().remove(key).apply();
    }

    public static void clearPreferences(Context context) {
        final SharedPreferences.Editor editor = getDefaultSharedPref(context).edit();
        editor.clear();
        editor.apply();
    }

    public static String getDownloadDirectory(Context context) {
        return getString(context, DOWNLOAD_DIRECTORY, BaseConstants.DEFAULT_DIRECTORY);
    }

    public static void setDownloadDirectory(Context context, String value) {
        setString(context, DOWNLOAD_DIRECTORY, value);
    }
}