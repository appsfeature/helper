package com.helper.base;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.Nullable;

import com.helper.Helper;
import com.helper.callback.ActivityLifecycleListener;
import com.helper.callback.Response;
import com.helper.model.LoginModel;
import com.helper.util.BasePrefUtil;
import com.helper.util.GsonParser;

import java.util.HashMap;

public class HelperClass implements Helper {

    private static volatile HelperClass helper;
    private boolean isEnableDebugMode = false;
    private Response.Helper mListener;
    public boolean isEnableCurrentActivityLifecycle = false;
    @Nullable
    private Activity mCurrentActivity;

    private HelperClass() {
    }

    public static HelperClass Builder() {
        if (helper == null) {
            synchronized (HelperClass.class) {
                if (helper == null) helper = new HelperClass();
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
    public HelperClass setDebugMode(Boolean isDebug) {
        isEnableDebugMode = isDebug;
        return this;
    }

    public Response.Helper getListener() {
        return mListener;
    }

    public HelperClass setListener(Response.Helper listener) {
        this.mListener = listener;
        return this;
    }

    public HashMap<Integer, ActivityLifecycleListener> getActivityLifecycleListener() {
        return mActivityLifecycleListener;
    }

    public HelperClass addActivityLifecycleListener(ActivityLifecycleListener listener) {
        addActivityLifecycleListener(this.hashCode(), listener);
        return this;
    }

    public HelperClass addActivityLifecycleListener(int hashCode, ActivityLifecycleListener listener) {
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

    public HelperClass setDownloadDirectory(Context context, String directory) {
        BasePrefUtil.setDownloadDirectory(context, directory);
        return this;
    }

    public void setEnableCurrentActivityLifecycle(boolean enableCurrentActivityLifecycle) {
        isEnableCurrentActivityLifecycle = enableCurrentActivityLifecycle;
    }

    public boolean isEnableCurrentActivityLifecycle() {
        return isEnableCurrentActivityLifecycle;
    }

    /**
     * @apiNote : for enable Use {ActivityTrackingApplication.setEnableCurrentActivityLifecycle(boolean isEnableCurrentActivityLifecycle)} method
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

    @Override
    public LoginModel getLoginDetail(Context context) {
        return BasePrefUtil.getLoginDetail(context);
    }

    @Override
    public void setLoginDetail(Context context, LoginModel loginDetail) {
        BasePrefUtil.setLoginDetail(context, loginDetail);
    }
}