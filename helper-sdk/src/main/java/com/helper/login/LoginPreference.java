package com.helper.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.helper.model.UserProfile;
import com.helper.util.BasePrefUtil;
import com.helper.util.GsonParser;

public class LoginPreference extends BasePrefUtil {
    
    private static final String IS_LOGIN_COMPLETE = "isLogComplete";
    private static final String IS_REGISTRATION_COMPLETE = "isRegComplete";
    private static final String AUTHENTICATION_COMPLETE = "authenticationComplete";

    private static final String USER_ID = "loginUserId";
    private static final String USER_NAME = "loginUserName";
    private static final String USER_EMAIL = "loginUserEmail";
    private static final String USER_MOBILE = "loginUserMobile";
    private static final String USER_PHOTO_URL = "loginPhotoUrl";
    private static final String LOGIN_TYPE = "loginType";
    private static final String LOGIN_MODE = "loginMode";
    private static final String LOGIN_EXTRAS = "loginExtras" ;
    private static final String LOGIN_PROFILE = "loginProfile";

    /**
     * Set String value for a particular key.
     */
    public static void setProfile(Context context, int loginType, UserProfile profile) {
        if (getDefaultSharedPref(context) != null && profile != null) {
            final SharedPreferences.Editor editor = getDefaultSharedPref(context).edit();
            if (editor != null) {
                editor.putString(USER_ID + loginType, getEmptyData(profile.getUserId()));
                editor.putString(USER_NAME + loginType, getEmptyData(profile.getUserName()));
                editor.putString(USER_PHOTO_URL + loginType, getEmptyData(profile.getUserImage()));
                editor.putString(USER_MOBILE + loginType, getEmptyData(profile.getMobileNo()));
                editor.putString(USER_EMAIL + loginType, getEmptyData(profile.getEmailId()));
                editor.putInt(LOGIN_TYPE + loginType, loginType);
                editor.putInt(LOGIN_MODE + loginType, profile.getMode());
                editor.putString(LOGIN_EXTRAS + loginType, getEmptyData(profile.getExtras()));

                editor.putString(LOGIN_PROFILE + loginType, profile.toJson());
                editor.commit();
            }
        }
    }

    public static String getProfileJson(Context context, int loginType) {
        return getString(context, LOGIN_PROFILE + loginType);
    }

    public static UserProfile getUserProfile(Context context, int loginType) {
        UserProfile userProfile = GsonParser.fromJsonAll(getProfileJson(context, loginType), UserProfile.class);
        return (userProfile == null) ? new UserProfile() : userProfile;
    }

    public static String getUserName(Context context, int loginType) {
        return getString(context, USER_NAME + loginType);
    }

    public static String getUserImage(Context context, int loginType) {
        return getString(context, USER_PHOTO_URL + loginType);
    }

    public static String getUserId(Context context, int loginType) {
        return getString(context, USER_ID + loginType);
    }

    public static String getUserMobile(Context context, int loginType) {
        return getString(context, USER_MOBILE + loginType);
    }

    public static String getEmailId(Context context, int loginType) {
        return getString(context, USER_EMAIL + loginType);
    }

    public static String getExtrasJson(Context context, int loginType) {
        return getString(context, LOGIN_EXTRAS + loginType);
    }

    public static <T> T getExtrasModel(Context context, int loginType, Class<T> classOfT) {
        return new Gson().fromJson(getExtrasJson(context, loginType), classOfT);
    }

    public static boolean isRegComplete(Context context, int loginType) {
        return getBoolean(context, IS_REGISTRATION_COMPLETE + loginType);
    }

    public static boolean isLoginComplete(Context context, int loginType) {
        return getBoolean(context, IS_LOGIN_COMPLETE + loginType);
    }

    public static boolean isAuthenticationComplete(Context context, int loginType) {
        return getBoolean(context, AUTHENTICATION_COMPLETE + loginType);
    }

    public static void setRegComplete(Context context, int loginType, boolean flag) {
        setBoolean(context, IS_REGISTRATION_COMPLETE + loginType, flag);
    }

    public static void setLoginComplete(Context context, int loginType, boolean flag) {
        setBoolean(context, IS_LOGIN_COMPLETE + loginType, flag);
    }

    public static void setAuthenticationComplete(Context context, int loginType, boolean flag) {
        setBoolean(context, AUTHENTICATION_COMPLETE + loginType, flag);
    }

    public static int getLoginType(Context context, int loginType) {
        return getInt(context, LOGIN_TYPE + loginType);
    }

    public static int getLoginMode(Context context, int loginType) {
        return getInt(context, LOGIN_MODE + loginType);
    }

    private static String getEmptyData(String data) {
        return TextUtils.isEmpty(data) ? "" : data;
    }

}