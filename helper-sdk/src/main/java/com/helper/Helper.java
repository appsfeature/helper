package com.helper;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.Nullable;

import com.helper.callback.ActivityLifecycleListener;
import com.helper.callback.Response;
import com.helper.util.BasePrefUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Helper {

    public static boolean IS_ADS_ENABLE = true;
    private static volatile Helper helper;
    private boolean isEnableDebugMode = false;
    private Response.Helper mListener;
    private boolean isEnableCurrentActivityLifecycle = false;
    @Nullable
    private Activity mCurrentActivity;

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

    public HashMap<Integer, ActivityLifecycleListener> getActivityLifecycleListener() {
        return mActivityLifecycleListener;
    }

    public Helper addActivityLifecycleListener(ActivityLifecycleListener listener) {
        addActivityLifecycleListener(this.hashCode(), listener);
        return this;
    }

    public Helper addActivityLifecycleListener(int hashCode, ActivityLifecycleListener listener) {
        synchronized (mActivityLifecycleListener) {
            mActivityLifecycleListener.put(hashCode, listener);
        }
        return this;
    }

    private final HashMap<Integer, ActivityLifecycleListener> mActivityLifecycleListener = new HashMap<>();

    public void removeActivityLifecycleListener(int hashCode) {
        if (mActivityLifecycleListener.get(hashCode) != null) {
            synchronized (mActivityLifecycleListener) {
                this.mActivityLifecycleListener.remove(hashCode);
            }
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

    public boolean isEnableCurrentActivityLifecycle() {
        return isEnableCurrentActivityLifecycle;
    }

    public Helper setEnableCurrentActivityLifecycle(boolean isEnableCurrentActivityLifecycle) {
        this.isEnableCurrentActivityLifecycle = isEnableCurrentActivityLifecycle;
        return this;
    }

    /**
     * @apiNote : for enable Use {@link #setEnableCurrentActivityLifecycle(boolean isEnableCurrentActivityLifecycle)} method
     * @apiNote : Initialize in onCreate and clear reference in onDestroy method
     * @return Current Activity Created from Lifecycle
     */
    @Nullable
    public Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    public void setCurrentActivity(Activity currentActivity) {
        this.mCurrentActivity = currentActivity;
    }
}