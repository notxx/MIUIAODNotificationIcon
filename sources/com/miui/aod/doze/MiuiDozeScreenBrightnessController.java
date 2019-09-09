package com.miui.aod.doze;

import android.app.AlarmManager;
import android.app.AlarmManager.OnAlarmListener;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import com.miui.aod.AODApplication;
import com.miui.aod.DozeHost.Callback;
import com.miui.aod.Utils;
import com.miui.aod.doze.DozeMachine.Part;
import com.miui.aod.doze.DozeMachine.Service;
import com.miui.aod.doze.DozeMachine.State;
import com.miui.aod.util.AlarmTimeout;
import com.miui.aod.util.CancelableWakeLockTask;
import com.miui.aod.util.wakelock.WakeLock;
import com.miui.aod.utils.CommonUtils;

public class MiuiDozeScreenBrightnessController implements Part {
    public static final String TAG = "MiuiDozeScreenBrightnessController";
    /* access modifiers changed from: private */
    public final Context mContext;
    private DozeTriggers mDozeTriggers;
    /* access modifiers changed from: private */
    public Handler mHandler;
    private Callback mHostCallback = new Callback() {
        public void notifyKeycodeGoto() {
        }

        public void onAodAnimate(boolean z) {
        }

        public void onDozingRequested(boolean z) {
        }

        public void onExtendPulse() {
        }

        public void onFingerprintPressed(boolean z) {
        }

        public void onPowerSaveChanged(boolean z) {
        }

        public void fireAodState(boolean z, String str) {
            if (!z && !Utils.isAodClockDisable(MiuiDozeScreenBrightnessController.this.mContext)) {
                MiuiDozeScreenBrightnessController.this.checkToScreenOff(MiuiDozeScreenBrightnessController.this.mLight);
            }
        }
    };
    /* access modifiers changed from: private */
    public boolean mLight = true;
    /* access modifiers changed from: private */
    public Sensor mLightSensor;
    /* access modifiers changed from: private */
    public final DozeMachine mMachine;
    /* access modifiers changed from: private */
    public final AlarmTimeout mOffTimeout;
    private PowerManager mPowerManager;
    private CancelableWakeLockTask mRegisterTask;
    /* access modifiers changed from: private */
    public boolean mRegistered;
    /* access modifiers changed from: private */
    public final SensorEventListener mSensorEventListener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int i) {
        }

        public void onSensorChanged(SensorEvent sensorEvent) {
            String str = MiuiDozeScreenBrightnessController.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("sensor event:");
            sb.append(sensorEvent.sensor.getType());
            sb.append(", value:");
            sb.append(sensorEvent.values[0]);
            Log.i(str, sb.toString());
            if (33171029 == sensorEvent.sensor.getType()) {
                int i = (int) sensorEvent.values[0];
                State state = MiuiDozeScreenBrightnessController.this.mMachine.getState();
                boolean z = state == State.DOZE_AOD_PAUSED;
                boolean z2 = state == State.DOZE_AOD_PAUSING;
                boolean z3 = state == State.DOZE_AOD;
                if (i == 2 || i == 1) {
                    MiuiDozeScreenBrightnessController.this.mLight = false;
                    if (z3) {
                        Log.i(MiuiDozeScreenBrightnessController.TAG, "Prox NEAR, pausing AOD   ");
                        MiuiDozeScreenBrightnessController.this.mService.requestState(State.DOZE_AOD_PAUSING);
                    }
                } else if (z || z2) {
                    Log.i(MiuiDozeScreenBrightnessController.TAG, "Prox FAR, unpausing AOD");
                    MiuiDozeScreenBrightnessController.this.mService.requestState(State.DOZE_AOD);
                }
                if (i == 3) {
                    MiuiDozeScreenBrightnessController.this.mLight = false;
                    MiuiDozeScreenBrightnessController.this.mService.setDozeScreenBrightness(1);
                    MiuiDozeScreenBrightnessController.this.mOffTimeout.schedule(300000, 1);
                } else {
                    MiuiDozeScreenBrightnessController.this.mOffTimeout.cancel();
                }
                if (i == 5) {
                    MiuiDozeScreenBrightnessController.this.mLight = true;
                    MiuiDozeScreenBrightnessController.this.checkToScreenOff(MiuiDozeScreenBrightnessController.this.mLight);
                    MiuiDozeScreenBrightnessController.this.mService.setDozeScreenBrightness(1);
                } else if (i == 4) {
                    MiuiDozeScreenBrightnessController.this.mLight = true;
                    MiuiDozeScreenBrightnessController.this.checkToScreenOff(MiuiDozeScreenBrightnessController.this.mLight);
                    MiuiDozeScreenBrightnessController.this.mService.setDozeScreenBrightness(CommonUtils.BRIGHTNESS_ON);
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public SensorManager mSensorManager;
    /* access modifiers changed from: private */
    public Service mService;
    private WakeLock mWakeLock;

    public MiuiDozeScreenBrightnessController(Handler handler, DozeMachine dozeMachine, AlarmManager alarmManager, Service service, SensorManager sensorManager, DozeTriggers dozeTriggers, Context context) {
        this.mContext = context;
        this.mHandler = handler;
        this.mMachine = dozeMachine;
        this.mService = service;
        this.mSensorManager = sensorManager;
        this.mPowerManager = (PowerManager) context.getSystemService("power");
        this.mWakeLock = WakeLock.wrap(this.mPowerManager.newWakeLock(1, TAG));
        this.mDozeTriggers = dozeTriggers;
        this.mOffTimeout = new AlarmTimeout(alarmManager, new OnAlarmListener() {
            public final void onAlarm() {
                MiuiDozeScreenBrightnessController.this.onOffTimeout();
            }
        }, "OffAlarmTimeout", handler);
        this.mLightSensor = this.mSensorManager.getDefaultSensor(33171029, true);
        this.mRegisterTask = new CancelableWakeLockTask(new Runnable() {
            public void run() {
                if (MiuiDozeScreenBrightnessController.this.mLightSensor != null && !MiuiDozeScreenBrightnessController.this.mRegistered) {
                    Log.i(MiuiDozeScreenBrightnessController.TAG, "register sensor listener");
                    MiuiDozeScreenBrightnessController.this.mRegistered = MiuiDozeScreenBrightnessController.this.mSensorManager.registerListener(MiuiDozeScreenBrightnessController.this.mSensorEventListener, MiuiDozeScreenBrightnessController.this.mLightSensor, 3, 0, MiuiDozeScreenBrightnessController.this.mHandler);
                }
            }
        }, this.mWakeLock, this.mHandler, TAG);
    }

    public void transitionTo(State state, State state2) {
        switch (state2) {
            case INITIALIZED:
                AODApplication.getHost().addCallback(this.mHostCallback);
                return;
            case DOZE_AOD:
                this.mRegisterTask.execute(400);
                return;
            case DOZE:
            case FINISH:
                this.mOffTimeout.cancel();
                this.mRegisterTask.remove();
                AODApplication.getHost().removeCallback(this.mHostCallback);
                if (this.mLightSensor != null && this.mRegistered) {
                    Log.i(TAG, "unregister sensor listener");
                    this.mSensorManager.unregisterListener(this.mSensorEventListener);
                    this.mRegistered = false;
                    return;
                }
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: private */
    public void onOffTimeout() {
        checkToScreenOff(this.mLight);
    }

    /* access modifiers changed from: private */
    public void checkToScreenOff(boolean z) {
        if (!Utils.isShowTemporary(this.mContext)) {
            boolean z2 = !z;
            State state = this.mMachine.getState();
            boolean z3 = false;
            boolean z4 = state == State.DOZE_AOD_PAUSED;
            boolean z5 = state == State.DOZE_AOD_PAUSING;
            if (state == State.DOZE_AOD) {
                z3 = true;
            }
            if (z && (z4 || z5)) {
                Log.i(TAG, "Brightness Light, unpausing AOD");
                this.mService.requestState(State.DOZE_AOD);
            } else if (z2 && z3) {
                Log.i(TAG, "Brightness Dark, pausing AOD");
                this.mService.requestState(State.DOZE_AOD_PAUSING);
            }
        }
    }
}
