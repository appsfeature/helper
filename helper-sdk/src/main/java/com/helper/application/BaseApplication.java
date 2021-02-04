package com.helper.application;

import com.helper.util.DayNightPreference;


public abstract class BaseApplication extends ActivityTrackerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initTheme();
    }

    private void initTheme() {
        DayNightPreference.setNightMode(this);
    }
}
