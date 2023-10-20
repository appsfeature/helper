package com.helper.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class TimerTaskHandler {

    /**
     * @param dateTime : the Date and time at which you want to execute
     *                 : e.g "2023-10-19 16:15:00"
     */
    public static void start(String dateTime, Callback callback) {
        try {
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            Date date = dateFormatter.parse(dateTime);

            //Now create the time and schedule it
            Timer timer = new Timer();

            //Use this if you want to execute it once
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    callback.onRun();
                }
            }, date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public interface Callback{
        void onRun();
    }
}
