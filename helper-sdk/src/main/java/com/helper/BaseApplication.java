package com.helper;

import android.app.Application;

import com.helper.util.DayNightPreference;


public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initTheme();
    }

    private void initTheme() {
        DayNightPreference.setNightMode(this);
    }
}
