package com.helper.stats;


import android.content.Context;
import android.text.TextUtils;

import com.helper.util.BasePrefUtil;

import java.util.ArrayList;

/**
 * @author Created by Abhijit on 11/02/2020.
 */
public class LastStats {

    //call from SplashActivity.java
    public static void clear(Context context) {
        BasePrefUtil.setLastStats(context, "");
    }

    public static StatisticsModel getLastStats(Context context) {
        StatisticsModel statsModel = StatsJsonCreator.fromJson(BasePrefUtil.getLastStats(context));
        return statsModel == null ? new StatisticsModel() : statsModel;
    }

    public static void setLastStats(Context context, StatisticsModel lastStats) {
        BasePrefUtil.setLastStats(context, StatsJsonCreator.toJson(lastStats));
    }
}
