package com.miui.aod.doze;

import android.app.AlarmManager;
import android.app.AlarmManager.OnAlarmListener;
import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import com.miui.aod.AODApplication;
import com.miui.aod.DozeHost;
import com.miui.aod.doze.DozeMachine.Part;
import com.miui.aod.doze.DozeMachine.State;
import com.miui.aod.util.AlarmTimeout;
import com.miui.aod.util.wakelock.WakeLock;
import com.miui.aod.utils.CommonUtils;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DozeUi implements Part {
    private final Context mContext;
    private final Handler mHandler;
    private long mLastTimeTickElapsed = 0;
    private final DozeMachine mMachine;
    private final AlarmTimeout mTimeTicker;
    private final WakeLock mWakeLock;

    static /* synthetic */ void lambda$onTimeTick$4() {
    }

    public DozeUi(Context context, AlarmManager alarmManager, DozeMachine dozeMachine, WakeLock wakeLock, Handler handler) {
        this.mContext = context;
        this.mMachine = dozeMachine;
        this.mWakeLock = wakeLock;
        this.mHandler = handler;
        this.mTimeTicker = new AlarmTimeout(alarmManager, new OnAlarmListener() {
            public final void onAlarm() {
                DozeUi.this.onTimeTick();
            }
        }, "doze_time_tick", handler);
    }

    private void pulseWhileDozing(int i) {
        if (i != 15) {
            this.mMachine.requestState(State.DOZE_PULSING);
            this.mMachine.requestState(State.DOZE_PULSE_DONE);
        }
    }

    public void transitionTo(State state, State state2) {
        switch (state2) {
            case DOZE_AOD:
                if (!(state == State.DOZE_AOD_PAUSING || state == State.DOZE_PULSING)) {
                    AODApplication.getHost().dozeTimeTick();
                    if (state != State.INITIALIZED) {
                        Handler handler = this.mHandler;
                        WakeLock wakeLock = this.mWakeLock;
                        DozeHost host = AODApplication.getHost();
                        host.getClass();
                        handler.postDelayed(wakeLock.wrap((Runnable) new Runnable() {
                            public final void run() {
                                DozeHost.this.dozeTimeTick();
                            }
                        }), 500);
                    }
                }
                scheduleTimeTick();
                break;
            case DOZE_AOD_PAUSING:
                scheduleTimeTick();
                break;
            case DOZE:
            case DOZE_AOD_PAUSED:
                unscheduleTimeTick();
                break;
            case DOZE_REQUEST_PULSE:
                pulseWhileDozing(this.mMachine.getPulseReason());
                break;
            case INITIALIZED:
                AODApplication.getHost().startDozing();
                break;
            case FINISH:
                AODApplication.getHost().stopDozing();
                unscheduleTimeTick();
                break;
        }
        updateAnimateWakeup(state2);
    }

    private void updateAnimateWakeup(State state) {
        int i = C00971.$SwitchMap$com$miui$aod$doze$DozeMachine$State[state.ordinal()];
        if (i != 5) {
            switch (i) {
                case 7:
                    return;
                case 8:
                case 9:
                    break;
                default:
                    AODApplication.getHost().setAnimateWakeup(false);
                    return;
            }
        }
        AODApplication.getHost().setAnimateWakeup(true);
    }

    private void scheduleTimeTick() {
        if (!this.mTimeTicker.isScheduled()) {
            this.mTimeTicker.schedule(roundToNextMinute(System.currentTimeMillis()) - System.currentTimeMillis(), 1);
            this.mLastTimeTickElapsed = SystemClock.elapsedRealtime();
        }
    }

    private void unscheduleTimeTick() {
        if (this.mTimeTicker.isScheduled()) {
            verifyLastTimeTick();
            this.mTimeTicker.cancel();
        }
    }

    private void verifyLastTimeTick() {
        long elapsedRealtime = SystemClock.elapsedRealtime() - this.mLastTimeTickElapsed;
        if (elapsedRealtime > 90000) {
            String formatShortElapsedTime = CommonUtils.formatShortElapsedTime(this.mContext, elapsedRealtime);
            DozeLog.traceMissedTick(formatShortElapsedTime);
            StringBuilder sb = new StringBuilder();
            sb.append("Missed AOD time tick by ");
            sb.append(formatShortElapsedTime);
            Log.e("DozeMachine", sb.toString());
        }
    }

    private long roundToNextMinute(long j) {
        Calendar instance = GregorianCalendar.getInstance();
        instance.setTimeInMillis(j);
        instance.set(14, 0);
        instance.set(13, 0);
        instance.add(12, 1);
        return instance.getTimeInMillis();
    }

    /* access modifiers changed from: private */
    public void onTimeTick() {
        verifyLastTimeTick();
        AODApplication.getHost().dozeTimeTick();
        this.mHandler.post(this.mWakeLock.wrap((Runnable) $$Lambda$DozeUi$zQhiaw8wVJT6R3zcKg0M3ZPIVgY.INSTANCE));
        scheduleTimeTick();
    }
}
