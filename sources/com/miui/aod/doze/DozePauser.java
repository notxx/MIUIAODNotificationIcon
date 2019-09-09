package com.miui.aod.doze;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Handler;
import android.os.PowerManager;
import com.miui.aod.doze.DozeMachine.Part;
import com.miui.aod.doze.DozeMachine.State;
import com.miui.aod.util.CancelableWakeLockTask;
import com.miui.aod.util.wakelock.WakeLock;

public class DozePauser implements Part {
    public static final String TAG = "DozePauser";
    private Handler mHandler;
    /* access modifiers changed from: private */
    public final DozeMachine mMachine;
    private CancelableWakeLockTask mPausedTask = new CancelableWakeLockTask(new Runnable() {
        public void run() {
            DozePauser.this.mMachine.requestState(State.DOZE_AOD_PAUSED);
        }
    }, this.mWakeLock, this.mHandler, TAG);
    private PowerManager mPowerManager;
    private WakeLock mWakeLock = WakeLock.wrap(this.mPowerManager.newWakeLock(1, TAG));

    /* renamed from: com.miui.aod.doze.DozePauser$2 */
    static /* synthetic */ class C00902 {
        static final /* synthetic */ int[] $SwitchMap$com$miui$aod$doze$DozeMachine$State = new int[State.values().length];

        static {
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[State.DOZE_AOD_PAUSING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public DozePauser(Context context, Handler handler, DozeMachine dozeMachine, AlarmManager alarmManager) {
        this.mMachine = dozeMachine;
        this.mHandler = handler;
        this.mPowerManager = (PowerManager) context.getSystemService("power");
    }

    public void transitionTo(State state, State state2) {
        if (C00902.$SwitchMap$com$miui$aod$doze$DozeMachine$State[state2.ordinal()] != 1) {
            this.mPausedTask.remove();
        } else {
            this.mPausedTask.execute(0);
        }
    }
}
