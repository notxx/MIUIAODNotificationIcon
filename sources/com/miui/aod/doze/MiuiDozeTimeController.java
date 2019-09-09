package com.miui.aod.doze;

import android.app.AlarmManager;
import android.app.AlarmManager.OnAlarmListener;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import com.miui.aod.AODApplication;
import com.miui.aod.Utils;
import com.miui.aod.doze.DozeMachine.Part;
import com.miui.aod.doze.DozeMachine.Service;
import com.miui.aod.doze.DozeMachine.State;
import com.miui.aod.util.AlarmTimeout;
import java.util.Calendar;

public class MiuiDozeTimeController implements Part {
    public static final String TAG = "MiuiDozeTimeController";
    private Context mContext;
    private final AlarmTimeout mHideDozeTimeout;
    private long mHideTime;
    private final DozeMachine mMachine;
    private Service mService;
    private final AlarmTimeout mShowDozeTimeout;
    private int mShowStyle = Utils.getShowStyle(this.mContext);
    private long mShowTime;

    public MiuiDozeTimeController(Context context, Handler handler, DozeMachine dozeMachine, AlarmManager alarmManager, Service service) {
        this.mContext = context;
        this.mMachine = dozeMachine;
        this.mService = service;
        this.mShowDozeTimeout = new AlarmTimeout(alarmManager, new OnAlarmListener() {
            public final void onAlarm() {
                MiuiDozeTimeController.this.showDoze();
            }
        }, "DarkenAlarmTimeout", handler);
        this.mHideDozeTimeout = new AlarmTimeout(alarmManager, new OnAlarmListener() {
            public final void onAlarm() {
                MiuiDozeTimeController.this.hideDoze();
            }
        }, "OffAlarmTimeout", handler);
        if (Utils.isTimingStyle(this.mContext)) {
            checkTime();
        }
    }

    private void checkTime() {
        this.mShowTime = 0;
        this.mHideTime = 0;
        long aodStartTime = Utils.getAodStartTime(this.mContext);
        long aodEndTime = Utils.getAodEndTime(this.mContext);
        Calendar instance = Calendar.getInstance();
        long j = (((long) ((instance.get(11) * 60) + instance.get(12))) * 60000) + 1;
        int i = (aodStartTime > aodEndTime ? 1 : (aodStartTime == aodEndTime ? 0 : -1));
        if (i <= 0) {
            if (j < aodStartTime || j > aodEndTime) {
                this.mShowTime = aodStartTime > j ? aodStartTime - j : (aodStartTime - j) + 86400000;
            } else {
                this.mHideTime = aodEndTime - j;
            }
        } else if (i <= 0) {
        } else {
            if (j >= aodStartTime || j <= aodEndTime) {
                if (aodEndTime <= j) {
                    aodEndTime += 86400000;
                }
                this.mHideTime = aodEndTime - j;
                return;
            }
            this.mShowTime = aodStartTime > j ? aodStartTime - j : (aodStartTime - j) + 86400000;
        }
    }

    public void transitionTo(State state, State state2) {
        switch (state2) {
            case INITIALIZED:
                if (Utils.isTimingStyle(this.mContext)) {
                    if (this.mShowTime > 0) {
                        this.mShowDozeTimeout.schedule(this.mShowTime, 1);
                        String str = TAG;
                        StringBuilder sb = new StringBuilder();
                        sb.append("transitionTo: mShowDozeTimeout schedule ");
                        sb.append(this.mShowTime);
                        Log.i(str, sb.toString());
                    } else if (this.mHideTime > 0) {
                        this.mHideDozeTimeout.schedule(this.mHideTime, 1);
                        String str2 = TAG;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("transitionTo: mHideDozeTimeout schedule ");
                        sb2.append(this.mHideTime);
                        Log.i(str2, sb2.toString());
                    }
                    if (this.mHideTime == 0) {
                        AODApplication.getHost().setAodClockVisibility(false);
                        return;
                    }
                    return;
                }
                return;
            case DOZE_AOD:
                onAodFire(this.mMachine.getAodReason());
                return;
            case DOZE:
                if (this.mShowStyle == 0) {
                    AODApplication.getHost().setAodClockVisibility(false);
                    this.mHideDozeTimeout.cancel();
                    return;
                }
                return;
            case FINISH:
                this.mShowDozeTimeout.cancel();
                this.mHideDozeTimeout.cancel();
                return;
            default:
                return;
        }
    }

    public void onAodFire(int i) {
        if (Utils.isShowTemporary(this.mContext) && i != 6) {
            AODApplication.getHost().setAodClockVisibility(true);
            this.mHideDozeTimeout.cancel();
            this.mHideDozeTimeout.schedule(10000, 1);
        }
    }

    /* access modifiers changed from: private */
    public void showDoze() {
        onShowDoze(true);
        if (Utils.isTimingStyle(this.mContext)) {
            checkTime();
            this.mHideDozeTimeout.schedule(this.mHideTime, 1);
        }
    }

    /* access modifiers changed from: private */
    public void hideDoze() {
        onShowDoze(false);
        if (Utils.isTimingStyle(this.mContext)) {
            checkTime();
            this.mShowDozeTimeout.schedule(this.mShowTime, 1);
        }
    }

    private void onShowDoze(boolean z) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("onShowDoze:");
        sb.append(z);
        Log.i(str, sb.toString());
        State state = this.mMachine.getState();
        boolean z2 = false;
        boolean z3 = state == State.DOZE_AOD_PAUSED;
        boolean z4 = state == State.DOZE_AOD_PAUSING;
        boolean z5 = state == State.DOZE_AOD;
        if (state == State.DOZE) {
            z2 = true;
        }
        if (z) {
            if (z2) {
                Log.i(TAG, "Show, unpausing AOD");
                this.mService.requestState(State.DOZE_AOD);
            }
        } else if (z5 || z3 || z3 || z4) {
            Log.i(TAG, "Hide, pausing AOD");
            this.mService.requestState(State.DOZE);
        }
        AODApplication.getHost().setAodClockVisibility(z);
    }
}
