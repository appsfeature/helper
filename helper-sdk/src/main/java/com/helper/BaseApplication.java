package com.helper;

import android.app.Application;

import com.helper.util.DayNightPreference;


public class BaseApplication extends Application {

    private static BaseApplication _instance;

    public static BaseApplication getInstance() {
        return _instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
        initTheme();
    }

    private void initTheme() {
        DayNightPreference.setNightMode(this);
    }
}
