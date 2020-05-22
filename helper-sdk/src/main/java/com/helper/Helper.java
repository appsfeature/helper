package com.helper;

import com.helper.callback.Response;
import com.helper.util.BaseConstants;

public class Helper {

    public static final boolean IS_ADS_ENABLE = true;
    public static String downloadDirectory = BaseConstants.DEFAULT_DIRECTORY;
    private static volatile Helper helper;
    private boolean isEnableDebugMode = false;
    private Response.Helper mListener;

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

    public String getDownloadDirectory() {
        return downloadDirectory;
    }

    public Helper setListener(String directory) {
        downloadDirectory = directory;
        return this;
    }
}