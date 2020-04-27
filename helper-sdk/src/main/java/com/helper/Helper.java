package com.helper;

public class Helper {
    
    private static volatile Helper helper;
    private boolean isEnableDebugMode = false;

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
}