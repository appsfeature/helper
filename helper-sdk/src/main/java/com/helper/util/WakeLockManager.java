package com.helper.util;

import android.content.Context;
import android.os.PowerManager;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class WakeLockManager {

    private static WakeLockManager instance;
    private final PowerManager.WakeLock wakeLock;

    public static WakeLockManager get(Context context, @WakeMode int mode, String tag) {
        if (instance == null) {
            synchronized (WakeLockManager.class) {
                if (instance == null) instance = new WakeLockManager(context, mode, tag);
            }
        }
        return instance;
    }

    public WakeLockManager(Context context, @WakeMode int mode, String tag) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        this.wakeLock = pm.newWakeLock(mode, tag);
    }

    public WakeLockManager start() {
        if (wakeLock != null) {
            wakeLock.acquire();
        }
        return this;
    }
    public WakeLockManager resume() {
        if (wakeLock != null) {
            if(!wakeLock.isHeld()) {
                wakeLock.acquire();
            }
        }
        return this;
    }
    public WakeLockManager stop() {
        if (wakeLock != null) {
            if(wakeLock.isHeld()){
                wakeLock.release();
            }
        }
        return this;
    }

    @IntDef({WakeMode.SCREEN_BRIGHT_WAKE_LOCK, WakeMode.PARTIAL_WAKE_LOCK, WakeMode.FULL_WAKE_LOCK})
    @Retention(RetentionPolicy.SOURCE)
    public @interface WakeMode {
        int SCREEN_BRIGHT_WAKE_LOCK = PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP;
        int PARTIAL_WAKE_LOCK = PowerManager.PARTIAL_WAKE_LOCK;
        int FULL_WAKE_LOCK = PowerManager.FULL_WAKE_LOCK;
        int OFF_WAKE_LOCK = PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK;
    }
}

