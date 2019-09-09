package com.miui.aod.util;

import android.app.AlarmManager;
import android.app.AlarmManager.OnAlarmListener;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;

public class AlarmTimeout implements OnAlarmListener {
    private final AlarmManager mAlarmManager;
    private final Handler mHandler;
    private final OnAlarmListener mListener;
    private boolean mScheduled;
    private final String mTag;

    public AlarmTimeout(AlarmManager alarmManager, OnAlarmListener onAlarmListener, String str, Handler handler) {
        this.mAlarmManager = alarmManager;
        this.mListener = onAlarmListener;
        this.mTag = str;
        this.mHandler = handler;
    }

    public void schedule(long j, int i) {
        switch (i) {
            case 0:
                if (this.mScheduled) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(this.mTag);
                    sb.append(" timeout is already scheduled");
                    throw new IllegalStateException(sb.toString());
                }
                break;
            case 1:
                if (this.mScheduled) {
                    return;
                }
                break;
            case 2:
                if (this.mScheduled) {
                    cancel();
                    break;
                }
                break;
            default:
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Illegal mode: ");
                sb2.append(i);
                throw new IllegalArgumentException(sb2.toString());
        }
        this.mAlarmManager.setExact(2, SystemClock.elapsedRealtime() + j, this.mTag, this, this.mHandler);
        StringBuilder sb3 = new StringBuilder();
        sb3.append("AlarmTimeout schedule ");
        sb3.append(this.mTag);
        sb3.append(" in ");
        sb3.append(j);
        Log.d("AlarmTimeout", sb3.toString());
        this.mScheduled = true;
    }

    public boolean isScheduled() {
        return this.mScheduled;
    }

    public void cancel() {
        if (this.mScheduled) {
            this.mAlarmManager.cancel(this);
            StringBuilder sb = new StringBuilder();
            sb.append("AlarmTimeout cancel ");
            sb.append(this.mTag);
            Log.d("AlarmTimeout", sb.toString());
            this.mScheduled = false;
        }
    }

    public void onAlarm() {
        if (this.mScheduled) {
            this.mScheduled = false;
            this.mListener.onAlarm();
        }
    }
}
