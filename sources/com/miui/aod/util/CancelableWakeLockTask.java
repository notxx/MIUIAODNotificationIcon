package com.miui.aod.util;

import android.os.Handler;
import com.miui.aod.util.wakelock.WakeLock;

public class CancelableWakeLockTask {
    private Handler mHandler;
    private Runnable mLastWrappedTask;
    /* access modifiers changed from: private */
    public int mLockCount;
    private String mTag;
    /* access modifiers changed from: private */
    public Runnable mTask;
    private WakeLock mWakeLock;

    public CancelableWakeLockTask(Runnable runnable, WakeLock wakeLock, Handler handler, String str) {
        this.mTask = runnable;
        this.mWakeLock = wakeLock;
        this.mHandler = handler;
        this.mTag = str;
    }

    public void remove() {
        if (this.mLockCount == 1) {
            this.mWakeLock.release();
            this.mLockCount = 0;
        }
        if (this.mLockCount <= 1) {
            this.mHandler.removeCallbacks(this.mLastWrappedTask);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.mTag);
        sb.append(" wake count ");
        sb.append(this.mLockCount);
        throw new RuntimeException(sb.toString());
    }

    public void execute(long j) {
        remove();
        this.mLastWrappedTask = this.mWakeLock.wrap((Runnable) new Runnable() {
            public void run() {
                CancelableWakeLockTask.this.mLockCount = CancelableWakeLockTask.this.mLockCount - 1;
                CancelableWakeLockTask.this.mTask.run();
            }
        });
        this.mLockCount++;
        this.mHandler.postDelayed(this.mLastWrappedTask, j);
    }
}
