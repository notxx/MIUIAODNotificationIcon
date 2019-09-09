package com.miui.aod.doze;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Handler;
import android.os.PowerManager;
import com.miui.aod.doze.DozeMachine.Service;
import com.miui.aod.doze.DozeMachine.Service.Delegate;
import com.miui.aod.util.CancelableWakeLockTask;
import com.miui.aod.util.wakelock.WakeLock;
import com.miui.aod.utils.CommonUtils;

public class MiuiDozeBrightnessTimeoutAdapter extends Delegate {
    public static final String TAG = "MiuiDozeBrightnessTimeoutAdapter";
    CancelableWakeLockTask mBrightnessTask;
    private Handler mHandler;
    private int mLastBrightness = -1;
    private PowerManager mPowerManager;
    private WakeLock mWakeLock;

    public MiuiDozeBrightnessTimeoutAdapter(Context context, Service service, AlarmManager alarmManager, Handler handler) {
        super(service);
        this.mHandler = handler;
        this.mPowerManager = (PowerManager) context.getSystemService("power");
        this.mWakeLock = WakeLock.wrap(this.mPowerManager.newWakeLock(1, TAG));
        this.mBrightnessTask = new CancelableWakeLockTask(new Runnable() {
            public void run() {
                MiuiDozeBrightnessTimeoutAdapter.this.onSetDozeScreenBrightnessTimeout();
            }
        }, this.mWakeLock, this.mHandler, TAG);
    }

    /* access modifiers changed from: private */
    public void onSetDozeScreenBrightnessTimeout() {
        super.setDozeScreenBrightness(this.mLastBrightness);
    }

    public void finish() {
        super.finish();
        this.mBrightnessTask.remove();
        this.mLastBrightness = -1;
    }

    public void setDozeScreenBrightness(int i) {
        if (this.mLastBrightness == -1) {
            this.mBrightnessTask.remove();
            this.mLastBrightness = i;
            onSetDozeScreenBrightnessTimeout();
        } else if (i != this.mLastBrightness) {
            this.mBrightnessTask.execute(this.mLastBrightness == CommonUtils.BRIGHTNESS_ON ? 10000 : 2000);
            this.mLastBrightness = i;
        }
    }
}
