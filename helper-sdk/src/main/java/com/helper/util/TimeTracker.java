package com.helper.util;

import android.util.Log;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class TimeTracker {
    private static final String TAG = "TimeTracker";
    public static TimeFormat showTimeFormat = TimeFormat.SHOW_IN_SECONDS;

    public static void startTracking(String processName) {
        process.put(processName, currentTimeMillis());
    }

    public static void endTracking(String processName) {
        if (process != null && process.containsKey(processName) && processName != null) {
            long netStopTime = System.currentTimeMillis();
            Long startTrackingTime = process.get(processName);
            if (startTrackingTime != null) {
                long netDiffInMill = netStopTime - startTrackingTime;
                logTime(processName, netDiffInMill);
            } else {
                Log.d(TAG, processName + " does'nt start, Please start first");
            }
        } else {
            Log.d(TAG, processName + " does'nt start, Please start first");
        }
    }

    private static void logTime(String processName, long netDiffInMill) {
        long seconds;
        String timeFormat;
        switch (showTimeFormat){
            case SHOW_IN_HOURS:
                seconds = TimeUnit.MILLISECONDS.toHours(netDiffInMill);
                timeFormat = " hours";
                break;
            case SHOW_IN_MINUTE:
                seconds = TimeUnit.MILLISECONDS.toMinutes(netDiffInMill);
                timeFormat = " minutes";
                break;
            case SHOW_IN_SECONDS:
                seconds = TimeUnit.MILLISECONDS.toSeconds(netDiffInMill);
                timeFormat = " seconds";
                break;
            case SHOW_IN_MICROS:
                seconds = TimeUnit.MILLISECONDS.toMicros(netDiffInMill);
                timeFormat = " micros";
                break;
            case HOW_IN_NANOS:
                seconds = TimeUnit.MILLISECONDS.toNanos(netDiffInMill);
                timeFormat = " nanos";
                break;
            case SHOW_IN_MILLIS:
            default:
                timeFormat = " millis";
                seconds = TimeUnit.MILLISECONDS.toMillis(netDiffInMill);
        }
        Log.d(TAG, processName + " time taken: " + seconds + timeFormat);
    }

    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }


    private static HashMap<String, Long> process = new HashMap<>();

    public enum TimeFormat{
        SHOW_IN_HOURS, SHOW_IN_MINUTE, SHOW_IN_SECONDS, SHOW_IN_MILLIS, SHOW_IN_MICROS, HOW_IN_NANOS,
    }
}
