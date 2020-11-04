package com.helper.stats;


import java.util.ArrayList;

/**
 * @author Created by Abhijit on 11/02/2020.
 */
public class LastStats {
    private static ArrayList<StatisticsModel> lastStats;

    //call from SplashActivity.java
    public static void clear() {
        LastStats.lastStats = null;
    }

    public static ArrayList<StatisticsModel> getLastStats() {
        if(LastStats.lastStats == null){
            return new ArrayList<>();
        }else {
            return LastStats.lastStats;
        }
    }

    public static void setLastStats(ArrayList<StatisticsModel> lastStats) {
        LastStats.lastStats = lastStats;
    }
}
