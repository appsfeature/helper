package com.helper;

import android.content.Context;

import com.helper.callback.ActivityLifecycleListener;
import com.helper.callback.Response;
import com.helper.util.BasePrefUtil;

import java.util.ArrayList;

public class Helper {

    public static boolean IS_ADS_ENABLE = true;
    private static volatile Helper helper;
    private boolean isEnableDebugMode = false;
    private Response.Helper mListener;

    private Helper() {
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


    public ArrayList<ActivityLifecycleListener> getActivityLifecycleListener() {
        return mActivityLifecycleListener;
    }

    public Helper addActivityLifecycleListener(ActivityLifecycleListener listener) {
        synchronized (mActivityLifecycleListener) {
            mActivityLifecycleListener.add(listener);
        }
        return this;
    }

    private final ArrayList<ActivityLifecycleListener> mActivityLifecycleListener = new ArrayList<>();

    public void removeActivityLifecycleListener(ActivityLifecycleListener callback) {
        synchronized (mActivityLifecycleListener) {
            mActivityLifecycleListener.remove(callback);
        }
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