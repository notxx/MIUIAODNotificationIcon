package com.miui.aod.doze;

import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.Settings.Secure;
import com.miui.aod.AODApplication;
import com.miui.aod.Utils;
import com.miui.aod.doze.DozeMachine.Service;
import com.miui.aod.doze.DozeMachine.State;
import com.miui.aod.services.WrappedDreamService;
import com.miui.aod.utils.CommonUtils;
import com.miui.aod.widget.AODSettings;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class DozeService extends WrappedDreamService implements Service {
    private static final int AOD_START_DELAY = 300;
    static final boolean DEBUG = true;
    private static final String TAG = "DozeService";
    /* access modifiers changed from: private */
    public boolean mAcquire;
    private DozeMachine mDozeMachine;
    private boolean mDreamStart = false;
    private Handler mHandler;
    private PowerManager mPowerManager;
    private Runnable mRunnable = new Runnable() {
        public void run() {
            DozeService.this.start();
        }
    };
    /* access modifiers changed from: private */
    public WakeLock mWakeLock;

    public void fingerprintPressed(boolean z, Runnable runnable) {
    }

    public DozeService() {
        setDebug(DEBUG);
    }

    public void onCreate() {
        super.onCreate();
        setWindowless(DEBUG);
        if (AODApplication.getHost() == null) {
            finish();
        } else if (!Utils.SUPPORT_AOD) {
            finish();
        } else {
            if (Secure.getInt(getContentResolver(), AODSettings.AOD_MODE, 0) == 0) {
                boolean isInvertColorsEnable = Utils.isInvertColorsEnable(this);
                if ((!Utils.isGxzwSensor() || !Utils.isUnlockWithFingerprintPossible(this) || isInvertColorsEnable) && Utils.getKeyguardNotificationStatus(getContentResolver()) != 2) {
                    finish();
                    return;
                }
            }
            this.mDozeMachine = new DozeFactory().assembleMachine(this);
            this.mHandler = new Handler();
            this.mPowerManager = (PowerManager) getSystemService("power");
            this.mWakeLock = this.mPowerManager.newWakeLock(1, TAG);
        }
    }

    public void startDozing() {
        if (!this.mAcquire) {
            this.mWakeLock.acquire();
            this.mAcquire = DEBUG;
        }
        this.mHandler.removeCallbacks(this.mRunnable);
        this.mHandler.postDelayed(this.mRunnable, 300);
    }

    /* access modifiers changed from: private */
    public void start() {
        super.startDozing();
        if (this.mAcquire) {
            this.mHandler.postDelayed(new Runnable() {
                public void run() {
                    DozeService.this.mWakeLock.release();
                    DozeService.this.mAcquire = false;
                }
            }, 1000);
        }
    }

    public void onDreamingStarted() {
        super.onDreamingStarted();
        this.mDreamStart = DEBUG;
        this.mDozeMachine.requestState(State.INITIALIZED);
        startDozing();
    }

    public void setDozeScreenBrightness(int i) {
        if (this.mDreamStart) {
            super.setDozeScreenBrightness(i);
        }
    }

    public void onDreamingStopped() {
        super.onDreamingStopped();
        this.mDreamStart = false;
        this.mDozeMachine.requestState(State.FINISH);
    }

    /* access modifiers changed from: protected */
    public void dumpOnHandler(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mDozeMachine != null) {
            this.mDozeMachine.dump(printWriter);
        }
    }

    public void requestWakeUp() {
        CommonUtils.requestWakeUp(this);
    }

    public void requestState(State state) {
        this.mDozeMachine.requestState(state);
    }
}
