package com.helper.task;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

public class HandlerBackground extends Handler {

    private HandlerBackground(Looper looper) {
        super(looper);
    }

    public static HandlerBackground getInstance() {
        return getInstance("ServiceStartArguments");
    }

    public static HandlerBackground getInstance(String name) {
        HandlerThread thread = new HandlerThread(name,
                android.os.Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        return new HandlerBackground(thread.getLooper());
    }

    @Override
    public void handleMessage(Message msg) {
        // Normally we would do some work here, like download a file.
        // For our sample, we just sleep for 5 seconds.
    }
}