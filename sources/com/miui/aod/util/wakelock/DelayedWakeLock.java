package com.miui.aod.util.wakelock;

import android.os.Handler;

public class DelayedWakeLock implements WakeLock {
    private final Handler mHandler;
    private final WakeLock mInner;
    private final Runnable mRelease;

    public DelayedWakeLock(Handler handler, WakeLock wakeLock) {
        this.mHandler = handler;
        this.mInner = wakeLock;
        WakeLock wakeLock2 = this.mInner;
        wakeLock2.getClass();
        this.mRelease = new Runnable() {
            public final void run() {
                WakeLock.this.release();
            }
        };
    }

    public void acquire() {
        this.mInner.acquire();
    }

    public void release() {
        this.mHandler.postDelayed(this.mRelease, 140);
    }

    public Runnable wrap(Runnable runnable) {
        return WakeLock.wrapImpl(this, runnable);
    }
}
