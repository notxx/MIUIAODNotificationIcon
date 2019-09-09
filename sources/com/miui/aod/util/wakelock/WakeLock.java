package com.miui.aod.util.wakelock;

import android.content.Context;
import android.os.PowerManager;

public interface WakeLock {
    void acquire();

    void release();

    Runnable wrap(Runnable runnable);

    static WakeLock createPartial(Context context, String str) {
        return wrap(createPartialInner(context, str));
    }

    static android.os.PowerManager.WakeLock createPartialInner(Context context, String str) {
        return ((PowerManager) context.getSystemService(PowerManager.class)).newWakeLock(1, str);
    }

    static Runnable wrapImpl(WakeLock wakeLock, Runnable runnable) {
        wakeLock.acquire();
        return new Runnable(runnable, wakeLock) {
            private final /* synthetic */ Runnable f$0;
            private final /* synthetic */ WakeLock f$1;

            {
                this.f$0 = r1;
                this.f$1 = r2;
            }

            public final void run() {
                WakeLock.lambda$wrapImpl$2(this.f$0, this.f$1);
            }
        };
    }

    static /* synthetic */ void lambda$wrapImpl$2(Runnable runnable, WakeLock wakeLock) {
        try {
            runnable.run();
        } finally {
            wakeLock.release();
        }
    }

    static WakeLock wrap(final android.os.PowerManager.WakeLock wakeLock) {
        return new WakeLock() {
            public void acquire() {
                wakeLock.acquire();
            }

            public void release() {
                wakeLock.release();
            }

            public Runnable wrap(Runnable runnable) {
                return WakeLock.wrapImpl(this, runnable);
            }
        };
    }
}
