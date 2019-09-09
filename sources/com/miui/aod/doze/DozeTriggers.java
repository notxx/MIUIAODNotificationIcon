package com.miui.aod.doze;

import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import com.miui.aod.AODApplication;
import com.miui.aod.DozeHost.Callback;
import com.miui.aod.Utils;
import com.miui.aod.doze.DozeMachine.Part;
import com.miui.aod.doze.DozeMachine.Service;
import com.miui.aod.doze.DozeMachine.State;
import com.miui.aod.util.Assert;
import java.io.PrintWriter;

public class DozeTriggers implements Part {
    private final boolean mAllowPulseTriggers;
    private final TriggerReceiver mBroadcastReceiver = new TriggerReceiver();
    /* access modifiers changed from: private */
    public final Context mContext;
    private Callback mHostCallback = new Callback() {
        public void notifyKeycodeGoto() {
        }

        public void onDozingRequested(boolean z) {
        }

        public void onExtendPulse() {
        }

        public void fireAodState(boolean z, String str) {
            State state = DozeTriggers.this.mMachine.getState();
            boolean z2 = false;
            boolean z3 = state == State.DOZE_AOD_PAUSED;
            boolean z4 = state == State.DOZE_AOD_PAUSING;
            boolean z5 = state == State.DOZE_AOD;
            if (state == State.DOZE) {
                z2 = true;
            }
            if (z) {
                if (z3 || z4 || z2) {
                    DozeTriggers.this.mMachine.requestAod(DozeLog.aodReasonToInt(str));
                } else {
                    DozeTriggers.this.mTimeController.onAodFire(DozeLog.aodReasonToInt(str));
                }
            } else if (!z5 && !z3 && !z4) {
            } else {
                if (Utils.isAodClockDisable(DozeTriggers.this.mContext) || !AODApplication.getHost().isAodClockVisible()) {
                    DozeTriggers.this.mMachine.requestState(State.DOZE);
                }
            }
        }

        public void onFingerprintPressed(boolean z) {
            if (z) {
                DozeTriggers.this.mService.fingerprintPressed(z, new Runnable() {
                    public void run() {
                        DozeTriggers.this.mMachine.requestPulse(15);
                        DozeTriggers.this.mMachine.requestState(State.DOZE_PULSING);
                        Log.i("DozeTriggers", "change display to on");
                    }
                });
            } else {
                DozeTriggers.this.mService.fingerprintPressed(z, new Runnable() {
                    public void run() {
                        DozeTriggers.this.mMachine.requestState(State.DOZE_AOD);
                        Log.i("DozeTriggers", "change display to aod");
                    }
                });
            }
        }

        public void onPowerSaveChanged(boolean z) {
            if (z) {
                DozeTriggers.this.mService.requestState(State.FINISH);
            }
        }

        public void onAodAnimate(boolean z) {
            State state = DozeTriggers.this.mMachine.getState();
            boolean z2 = false;
            boolean z3 = state == State.DOZE_AOD_PAUSED;
            boolean z4 = state == State.DOZE_AOD_PAUSING;
            boolean z5 = state == State.DOZE_AOD;
            if (state == State.DOZE) {
                z2 = true;
            }
            if (z) {
                if (z3 || z4 || z2) {
                    DozeTriggers.this.mMachine.requestState(State.DOZE_AOD);
                }
            } else if (!Utils.isAodClockDisable(DozeTriggers.this.mContext)) {
            } else {
                if (z5 || z3 || z4) {
                    DozeTriggers.this.mMachine.requestState(State.DOZE);
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public final DozeMachine mMachine;
    private boolean mPulsePending;
    /* access modifiers changed from: private */
    public Service mService;
    /* access modifiers changed from: private */
    public MiuiDozeTimeController mTimeController;
    private final UiModeManager mUiModeManager;

    private class TriggerReceiver extends BroadcastReceiver {
        private boolean mRegistered;

        private TriggerReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if ("com.android.systemui.doze.pulse".equals(intent.getAction())) {
                Log.d("DozeTriggers", "Received pulse intent");
                DozeTriggers.this.requestPulse(0);
            }
            if (UiModeManager.ACTION_ENTER_CAR_MODE.equals(intent.getAction())) {
                DozeTriggers.this.mMachine.requestState(State.FINISH);
            }
        }

        public void register(Context context) {
            if (!this.mRegistered) {
                IntentFilter intentFilter = new IntentFilter("com.android.systemui.doze.pulse");
                intentFilter.addAction(UiModeManager.ACTION_ENTER_CAR_MODE);
                context.registerReceiver(this, intentFilter);
                this.mRegistered = true;
            }
        }

        public void unregister(Context context) {
            if (this.mRegistered) {
                context.unregisterReceiver(this);
                this.mRegistered = false;
            }
        }
    }

    public DozeTriggers(Context context, DozeMachine dozeMachine, boolean z, Service service, MiuiDozeTimeController miuiDozeTimeController) {
        this.mContext = context;
        this.mMachine = dozeMachine;
        this.mAllowPulseTriggers = z;
        this.mUiModeManager = (UiModeManager) this.mContext.getSystemService(UiModeManager.class);
        this.mService = service;
        this.mTimeController = miuiDozeTimeController;
    }

    public void transitionTo(State state, State state2) {
        switch (state2) {
            case INITIALIZED:
                this.mBroadcastReceiver.register(this.mContext);
                AODApplication.getHost().addCallback(this.mHostCallback);
                checkTriggersAtInit();
                return;
            case FINISH:
                this.mBroadcastReceiver.unregister(this.mContext);
                AODApplication.getHost().removeCallback(this.mHostCallback);
                return;
            default:
                return;
        }
    }

    private void checkTriggersAtInit() {
        if (this.mUiModeManager.getCurrentModeType() == 3 || Utils.isPowerSaveMode(this.mContext)) {
            this.mService.requestState(State.FINISH);
        }
    }

    /* access modifiers changed from: private */
    public void requestPulse(int i) {
        Assert.isMainThread();
        AODApplication.getHost().extendPulse();
        if (this.mPulsePending || !this.mAllowPulseTriggers || !canPulse()) {
            if (this.mAllowPulseTriggers) {
                DozeLog.tracePulseDropped(this.mContext, this.mPulsePending, this.mMachine.getState(), AODApplication.getHost().isPulsingBlocked());
            }
            return;
        }
        continuePulseRequest(i);
    }

    private boolean canPulse() {
        return this.mMachine.getState() == State.DOZE || this.mMachine.getState() == State.DOZE_AOD;
    }

    private void continuePulseRequest(int i) {
        this.mPulsePending = false;
        if (AODApplication.getHost().isPulsingBlocked() || !canPulse()) {
            DozeLog.tracePulseDropped(this.mContext, this.mPulsePending, this.mMachine.getState(), AODApplication.getHost().isPulsingBlocked());
        } else {
            this.mMachine.requestPulse(i);
        }
    }

    public void dump(PrintWriter printWriter) {
        printWriter.print(" pulsePending=");
        printWriter.println(this.mPulsePending);
        printWriter.println("DozeSensors:");
    }
}
