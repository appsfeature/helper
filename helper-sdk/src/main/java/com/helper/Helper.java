package com.helper;

import android.app.Application;
import android.content.Context;

import com.helper.callback.Response;
import com.helper.util.BasePrefUtil;

public class Helper {

    public static boolean IS_ADS_ENABLE = true;
    private static volatile Helper helper;
    private boolean isEnableDebugMode = false;
    private Response.Helper mListener;
    private Application.ActivityLifecycleCallbacks mActivityLifecycleCallbacks;

    private Helper() {
        if (helper != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static Helper getInstance() {
        if (helper == null) {
            synchronized (Helper.class) {
                if (helper == null) helper = new Helper();
            }
        }
        return helper;
    }

    public boolean isEnableDebugMode() {
        return isEnableDebugMode;
    }

    /**
     * @param isDebug = BuildConfig.DEBUG
     * @return this
     */
    public Helper setDebugMode(Boolean isDebug) {
        isEnableDebugMode = isDebug;
        return this;
    }

    public Response.Helper getListener() {
        return mListener;
    }

    public Helper setListener(Response.Helper listener) {
        this.mListener = listener;
        return this;
    }

    public Application.ActivityLifecycleCallbacks getActivityLifecycleCallbacks() {
        return mActivityLifecycleCallbacks;
    }

    public Helper setActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks mActivityLifecycleCallbacks) {
        this.mActivityLifecycleCallbacks = mActivityLifecycleCallbacks;
        return this;
    }

    public String getDownloadDirectory(Context context) {
        return BasePrefUtil.getDownloadDirectory(context);
    }

    public Helper setDownloadDirectory(Context context, String directory) {
        BasePrefUtil.setDownloadDirectory(context, directory);
        return this;
    }

    public Helper setAdsEnable(Boolean isAdsEnable) {
        IS_ADS_ENABLE = isAdsEnable;
        return this;
    }
}