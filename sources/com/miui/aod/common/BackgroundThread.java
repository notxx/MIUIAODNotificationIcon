package com.miui.aod.common;

import android.os.Handler;
import android.os.HandlerThread;

public final class BackgroundThread extends HandlerThread {
    private static Handler sHandler;

    private BackgroundThread() {
        super("Background", 0);
    }

    public static Handler getHandler() {
        Handler handler;
        synchronized (BackgroundThread.class) {
            if (sHandler == null) {
                BackgroundThread backgroundThread = new BackgroundThread();
                backgroundThread.start();
                sHandler = new Handler(backgroundThread.getLooper());
            }
            handler = sHandler;
        }
        return handler;
    }

    public static void post(Runnable runnable) {
        getHandler().post(runnable);
    }

    public static void postAtFrontOfQueue(Runnable runnable) {
        getHandler().postAtFrontOfQueue(runnable);
    }

    public static void postDelayed(Runnable runnable, long j) {
        getHandler().postDelayed(runnable, j);
    }

    public static void removeCallbacks(Runnable runnable) {
        getHandler().removeCallbacks(runnable);
    }
}
