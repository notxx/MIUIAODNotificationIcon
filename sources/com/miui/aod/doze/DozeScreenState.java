package com.miui.aod.doze;

import android.os.Handler;
import com.miui.aod.doze.DozeMachine.Part;
import com.miui.aod.doze.DozeMachine.Service;
import com.miui.aod.doze.DozeMachine.State;
import com.miui.aod.utils.CommonUtils;

public class DozeScreenState implements Part {
    private final Runnable mApplyPendingScreenState = new Runnable() {
        public final void run() {
            DozeScreenState.this.applyPendingScreenState();
        }
    };
    private final Service mDozeService;
    private final Handler mHandler;
    private int mPendingScreenState = 0;

    public DozeScreenState(Service service, Handler handler) {
        this.mDozeService = service;
        this.mHandler = handler;
    }

    public void transitionTo(State state, State state2) {
        int screenState = state2.screenState();
        if (state2 == State.FINISH) {
            this.mPendingScreenState = 0;
            this.mHandler.removeCallbacks(this.mApplyPendingScreenState);
            applyScreenState(screenState);
        } else if (screenState != 0) {
            boolean hasCallbacks = CommonUtils.hasCallbacks(this.mHandler, this.mApplyPendingScreenState);
            if (hasCallbacks || state == State.INITIALIZED) {
                this.mPendingScreenState = screenState;
                if (!hasCallbacks) {
                    this.mHandler.post(this.mApplyPendingScreenState);
                }
                return;
            }
            applyScreenState(screenState);
        }
    }

    /* access modifiers changed from: private */
    public void applyPendingScreenState() {
        applyScreenState(this.mPendingScreenState);
        this.mPendingScreenState = 0;
    }

    private void applyScreenState(int i) {
        if (i != 0) {
            this.mDozeService.setDozeScreenState(i);
            this.mPendingScreenState = 0;
        }
    }
}
