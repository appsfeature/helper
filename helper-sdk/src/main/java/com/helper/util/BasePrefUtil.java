package com.helper.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.helper.model.LoginModel;


public class BasePrefUtil {

    private static final String TAG = "BasePrefUtil";
    private static final String DOWNLOAD_DIRECTORY = "DownloadDirectory";
    private static final String RECENT_FEATURE_DATA = "recent_feature_data";
    private static final String STORAGE_MIGRATION_COMPLETED = "helper_storage_migration_completed";
    private static final String LOGIN_DETAIL = "login_detail";
    private static final String USER_ID = "user_id";
    private static SharedPreferences sharedPreferences;


    public static SharedPreferences getDefaultSharedPref(Context context) {
        if (sharedPreferences == null && context != null)
            sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

        return sharedPreferences;
    }

    public static void setString(Context context, String key, String value) {
        if(context != null) {
            final SharedPreferences.Editor editor = getDefaultSharedPref(context).edit();
            editor.putString(key, value);
            editor.commit();
        }
    }

    public static String getString(Context context, String key) {
        return getString(context, key, "");
    }

    public static String getString(Context context, String key, String defaultValue) {
        if(context == null) {
            return defaultValue;
        }
        return getDefaultSharedPref(context).getString(key, defaultValue);
    }

    public static void setInt(Context context, String key, int value) {
        if(context != null) {
            final SharedPreferences.Editor editor = getDefaultSharedPref(context).edit();
            editor.putInt(key, value);
            editor.apply();
        }
    }

    public static int getInt(Context context, String key) {
        return getInt(context, key, 0);
    }

    public static int getInt(Context context, String key, int defaultValue) {
        if(context == null) {
            return defaultValue;
        }
        return getDefaultSharedPref(context).getInt(key, defaultValue);
    }

    public static void setFloat(Context context, String key, float value) {
        if(context != null) {
            final SharedPreferences.Editor editor = getDefaultSharedPref(context).edit();
            editor.putFloat(key, value);
            editor.apply();
        }
    }

    public static float getFloat(Context context, String key) {
        return getFloat(context, key, 0);
    }

    public static float getFloat(Context context, String key, float defaultValue) {
        if(context == null) {
            return defaultValue;
        }
        return getDefaultSharedPref(context).getFloat(key, defaultValue);
    }

    public static void setLong(Context context, String key, long value) {
        if(context != null) {
            final SharedPreferences.Editor editor = getDefaultSharedPref(context).edit();
            editor.putLong(key, value);
            editor.apply();
        }
    }

    public static long getLong(Context context, String key) {
        if(context == null) {
            return 0;
        }
        return getDefaultSharedPref(context).getLong(key, 0);
    }

    public static void setBoolean(Context context, String key, boolean value) {
        if(context != null) {
            final SharedPreferences.Editor editor = getDefaultSharedPref(context).edit();
            editor.putBoolean(key, value);
            editor.commit();
        }
    }

    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        if(context == null) {
            return defaultValue;
        }
        return getDefaultSharedPref(context).getBoolean(key, defaultValue);
    }

    public static void removeKey(Context context, String key) {
        if(context != null) {
            getDefaultSharedPref(context).edit().remove(key).apply();
        }
    }

    public static void clearPreferences(Context context) {
        if(context != null) {
            final SharedPreferences.Editor editor = getDefaultSharedPref(context).edit();
            editor.clear();
            editor.apply();
        }
    }

    public static String getDownloadDirectory(Context context) {
        return getString(context, DOWNLOAD_DIRECTORY, BaseConstants.DEFAULT_DIRECTORY);
    }

    public static void setDownloadDirectory(Context context, String value) {
        setString(context, DOWNLOAD_DIRECTORY, value);
    }

    public static String getRecentFeatureData(Context context, String key) {
        return getString(context, RECENT_FEATURE_DATA + key, "");
    }

    public static void setRecentFeatureData(Context context, String key, String value) {
        setString(context, RECENT_FEATURE_DATA + key, value);
    }

    public static void setStorageMigrationCompleted(Context context, boolean value) {
        setBoolean(context, STORAGE_MIGRATION_COMPLETED, value);
    }

    public static boolean isStorageMigrationCompleted(Context context) {
        return getBoolean(context, STORAGE_MIGRATION_COMPLETED,false);
    }

    public static String getLoginDetailJson(Context context) {
        return getString(context, LOGIN_DETAIL, "");
    }

    public static void setLoginDetailJson(Context context, String value) {
        setString(context, LOGIN_DETAIL, value);
    }

    public static String getUserId(Context context) {
        return getString(context, USER_ID, "");
    }

    private static void setUserId(Context context, String value) {
        setString(context, USER_ID, value);
    }

    public static LoginModel getLoginDetail(Context context) {
        LoginModel loginDetail = GsonParser.fromJsonAll(getLoginDetailJson(context), LoginModel.class);
        return (loginDetail == null) ? new LoginModel() : loginDetail;
    }

    public static void setLoginDetail(Context context, LoginModel loginDetail) {
        if(loginDetail != null) {
            setUserId(context, loginDetail.getUserId());
            setLoginDetailJson(context, GsonParser.toJsonAll(loginDetail, LoginModel.class));
        }
    }
}