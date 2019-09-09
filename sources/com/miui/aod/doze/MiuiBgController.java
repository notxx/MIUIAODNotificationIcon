package com.miui.aod.doze;

import android.app.AlarmManager;
import android.app.AlarmManager.OnAlarmListener;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import com.miui.aod.AODApplication;
import com.miui.aod.doze.DozeMachine.Part;
import com.miui.aod.doze.DozeMachine.State;
import com.miui.aod.util.AlarmTimeout;
import com.miui.aod.widget.AODSettings;
import com.miui.aod.widget.SunSelector;
import java.util.Calendar;

public class MiuiBgController implements Part {
    public static final String TAG = "MiuiBgController";
    private static final HandlerThread sWorkerThread = new HandlerThread("bg_looper");
    private Handler mBgHandler;
    private final AlarmTimeout mChangeBgTimeout;
    /* access modifiers changed from: private */
    public Context mContext;
    private boolean mIsSunRiseOpen;
    private long mScheduleTime;

    static {
        sWorkerThread.start();
    }

    public MiuiBgController(Context context, Handler handler, AlarmManager alarmManager) {
        this.mChangeBgTimeout = new AlarmTimeout(alarmManager, new OnAlarmListener() {
            public final void onAlarm() {
                MiuiBgController.this.changeBg();
            }
        }, "SunImageTimeout", handler);
        this.mContext = context;
        if (AODSettings.isSunStyle(this.mContext)) {
            this.mIsSunRiseOpen = true;
            this.mBgHandler = new Handler(sWorkerThread.getLooper());
            this.mBgHandler.post(new Runnable() {
                public void run() {
                    SunSelector.updateSunRiseTime(MiuiBgController.this.mContext);
                }
            });
            return;
        }
        this.mIsSunRiseOpen = false;
    }

    public void transitionTo(State state, State state2) {
        switch (state2) {
            case INITIALIZED:
                changeBg();
                return;
            case FINISH:
                this.mChangeBgTimeout.cancel();
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: private */
    public void changeBg() {
        if (this.mIsSunRiseOpen) {
            Calendar instance = Calendar.getInstance();
            int i = instance.get(11);
            int i2 = instance.get(12);
            int i3 = i * 60;
            int i4 = i3 + i2;
            int chooseBg = chooseBg(i4);
            AODApplication.getHost().setSunImage(chooseBg);
            int changePoint = SunSelector.getChangePoint((chooseBg + 1) % SunSelector.getChangePointLength());
            if (changePoint > i4) {
                this.mScheduleTime = ((long) ((changePoint - i3) - i2)) * 60000;
            } else if (changePoint >= 0) {
                this.mScheduleTime = ((long) (((changePoint + 1440) - i3) - i2)) * 60000;
            } else {
                return;
            }
            this.mChangeBgTimeout.schedule(this.mScheduleTime, 1);
        }
    }

    private int chooseBg(int i) {
        return SunSelector.getDrawableIndex(i);
    }
}
