package com.helper;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.Nullable;

import com.helper.base.HelperClass;
import com.helper.callback.ActivityLifecycleListener;
import com.helper.callback.Response;
import com.helper.model.LoginModel;
import com.helper.util.GsonParser;

import java.util.HashMap;

public interface Helper {

    boolean isEnableDebugMode();

    static Helper getInstance() {
        return HelperClass.Builder();
    }
    /**
     * @param isDebug = BuildConfig.DEBUG
     * @return this
     */
    Helper setDebugMode(Boolean isDebug);

    Response.Helper getListener();

    Helper setListener(Response.Helper listener);

    HashMap<Integer, ActivityLifecycleListener> getActivityLifecycleListener();

    Helper addActivityLifecycleListener(ActivityLifecycleListener listener);

    Helper addActivityLifecycleListener(int hashCode, ActivityLifecycleListener listener);

    void removeActivityLifecycleListener(int hashCode);

    String getDownloadDirectory(Context context);

    Helper setDownloadDirectory(Context context, String directory);
    void setEnableCurrentActivityLifecycle(boolean enableCurrentActivityLifecycle);

    boolean isEnableCurrentActivityLifecycle();

    /**
     * @return Current Activity Created from Lifecycle
     * @apiNote : for enable Use {ActivityTrackingApplication.setEnableCurrentActivityLifecycle(boolean isEnableCurrentActivityLifecycle)} method
     * @apiNote : Initialize in onCreate and clear reference in onDestroy method
     */
    @Nullable
    Activity getCurrentActivity();

    void setCurrentActivity(Activity currentActivity);


    LoginModel getLoginDetail(Context context);

    void setLoginDetail(Context context, LoginModel loginDetail);
}