package com.helper.util;

import android.text.TextUtils;
import android.util.Log;

import com.helper.Helper;


/**
 * Created by Amit on 7/1/2017.
 */

public class Logger {
    public static final String SDK_NAME = "Helper";
    public static final String TAG = SDK_NAME + "-log";

    public static void e(String s) {
        if (Helper.getInstance().isEnableDebugMode()) {
            Log.d(TAG, LINE_BREAK_START);
            Log.d(TAG, s);
            Log.d(TAG, LINE_BREAK_END);
        }
    }

    public static void i(String s) {
        if (Helper.getInstance().isEnableDebugMode()) {
            Log.d(TAG, s);
        }
    }

    public static void e(String q ,String s ) {
        if (Helper.getInstance().isEnableDebugMode()) {
            Log.d(TAG, LINE_BREAK_START);
            Log.d(TAG, q + " : " + s);
            Log.d(TAG, LINE_BREAK_END);
        }
    }

    public static void d(String s) {
        if (Helper.getInstance().isEnableDebugMode()) {
            Log.d(TAG, LINE_BREAK_START);
            Log.d(TAG, s);
            Log.d(TAG, LINE_BREAK_END);
        }
    }

    public static void d(String... s) {
        if (Helper.getInstance().isEnableDebugMode()) {
            Log.d(TAG, LINE_BREAK_START);
            for (String m : s){
                Log.d(TAG, m);
            }
            Log.d(TAG, LINE_BREAK_END);
        }
    }

    public static void e(String... s) {
        if (Helper.getInstance().isEnableDebugMode()) {
            Log.e(TAG, LINE_BREAK_START);
            for (String m : s){
                Log.e(TAG, m);
            }
            Log.e(TAG, LINE_BREAK_END);
        }
    }

    /**
     * @apiNote throw new IllegalArgumentException();
     */
    public static void logIntegration(String tag, String message){
        Log.e(tag, ".     |  |");
        Log.e(tag, ".     |  |");
        Log.e(tag, ".     |  |");
        Log.e(tag, ".   \\ |  | /");
        Log.e(tag, ".    \\    /");
        Log.e(tag, ".     \\  /");
        Log.e(tag, ".      \\/");
        Log.e(tag, ".");
        Log.e(tag, message);
        Log.e(tag, ".");
        Log.e(tag, ".      /\\");
        Log.e(tag, ".     /  \\");
        Log.e(tag, ".    /    \\");
        Log.e(tag, ".   / |  | \\");
        Log.e(tag, ".     |  |");
        Log.e(tag, ".     |  |");
        Log.e(tag, ".");
    }

//    public static void e(String q, String s) {
//        if (Helper.getInstance().isEnableDebugMode()) {
//            Log.d(TAG, LINE_BREAK_START);
//            Log.d(TAG, q + " : " + s);
//            Log.d(TAG, LINE_BREAK_END);
//        }
//    }

    /**
     * @param currentThread = Thread.currentThread().getStackTrace()
     * @return Getting the name of the currently executing method
     */
    public static String getClassPath(StackTraceElement[] currentThread) {
        try {
            if(currentThread!=null && currentThread.length>=3){
                if(currentThread[2]!=null){
                    return currentThread[2].toString()+" [Line Number = "+currentThread[2].getLineNumber()+"]";
                }
            }
        } catch (Exception e) {
            return "";
        }
        return "";
    }

    public static String getClassPath(Class<?> classReference, String methodName) {
        if(methodName == null){
            methodName = "";
        }
        return classReference.getName() + "->" + methodName + "";
    }


    public static String leak(String... errors) {
        String errorList = TextUtils.join(",", errors);
        return "Might be null (" + errorList + ")";
    }

    public static final String LINE_BREAK_START = "-----------------------------"+SDK_NAME+"----------------------------->";
    public static final String LINE_BREAK_END = "<-----------------------------"+SDK_NAME+"------------------------------";

    public static String getMethodName() {
        return Thread.currentThread().getStackTrace()[2].toString();
    }

}
