package com.helper.stats;


import java.util.ArrayList;

/**
 * @author Created by Abhijit on 11/02/2020.
 */
public enum LastStats {
    instance;
    private ArrayList<StatisticsModel> lastStats;

    //call from SplashActivity.java
    public static void clear() {
        instance.lastStats = null;
    }

    public static ArrayList<StatisticsModel> getLastStats() {
        if(instance.lastStats == null){
            return new ArrayList<>();
        }else {
            return instance.lastStats;
        }
    }

    public static void setLastStats(ArrayList<StatisticsModel> lastStats) {
        instance.lastStats = lastStats;
    }
}
