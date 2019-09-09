package com.miui.aod.doze;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.p000v4.util.Preconditions;
import android.util.Log;
import com.miui.aod.Utils;
import com.miui.aod.util.Assert;
import com.miui.aod.util.wakelock.WakeLock;
import com.miui.aod.utils.CommonUtils;
import java.io.PrintWriter;
import java.util.ArrayList;

@SuppressLint({"RestrictedApi"})
public class DozeMachine {
    private int mAodReason;
    private Context mContext;
    private final Service mDozeService;
    private Part[] mParts;
    private int mPulseReason;
    private final ArrayList<State> mQueuedRequests = new ArrayList<>();
    private State mState = State.UNINITIALIZED;
    private final WakeLock mWakeLock;
    private boolean mWakeLockHeldForCurrentState = false;

    public interface Part {
        void dump(PrintWriter printWriter) {
        }

        void transitionTo(State state, State state2);
    }

    public interface Service {

        public static class Delegate implements Service {
            private final Service mDelegate;

            public Delegate(Service service) {
                this.mDelegate = service;
            }

            public void finish() {
                this.mDelegate.finish();
            }

            public void setDozeScreenState(int i) {
                this.mDelegate.setDozeScreenState(i);
            }

            public void setDozeScreenBrightness(int i) {
                this.mDelegate.setDozeScreenBrightness(i);
            }

            public void fingerprintPressed(boolean z, Runnable runnable) {
                this.mDelegate.fingerprintPressed(z, runnable);
            }

            public void requestState(State state) {
                this.mDelegate.requestState(state);
            }
        }

        void fingerprintPressed(boolean z, Runnable runnable);

        void finish();

        void requestState(State state);

        void setDozeScreenBrightness(int i);

        void setDozeScreenState(int i);
    }

    public enum State {
        UNINITIALIZED,
        INITIALIZED,
        DOZE,
        DOZE_AOD,
        DOZE_REQUEST_PULSE,
        DOZE_PULSING,
        DOZE_PULSE_DONE,
        FINISH,
        DOZE_AOD_PAUSED,
        DOZE_AOD_PAUSING;

        /* access modifiers changed from: 0000 */
        public boolean canPulse() {
            switch (this) {
                case DOZE:
                case DOZE_AOD:
                case DOZE_AOD_PAUSED:
                case DOZE_AOD_PAUSING:
                    return true;
                default:
                    return false;
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean staysAwake() {
            switch (this) {
                case DOZE_REQUEST_PULSE:
                case DOZE_PULSING:
                    return true;
                default:
                    return false;
            }
        }

        /* access modifiers changed from: 0000 */
        public int screenState() {
            switch (this) {
                case DOZE:
                case DOZE_AOD_PAUSED:
                case UNINITIALIZED:
                case INITIALIZED:
                    return 1;
                case DOZE_AOD:
                case DOZE_AOD_PAUSING:
                    return 4;
                case DOZE_REQUEST_PULSE:
                case DOZE_PULSING:
                    return 2;
                default:
                    return 0;
            }
        }
    }

    public DozeMachine(Service service, WakeLock wakeLock, Context context) {
        this.mDozeService = service;
        this.mWakeLock = wakeLock;
        this.mContext = context;
    }

    @SuppressLint({"RestrictedApi"})
    public void setParts(Part[] partArr) {
        Preconditions.checkState(this.mParts == null);
        this.mParts = partArr;
    }

    @SuppressLint({"RestrictedApi"})
    public void requestState(State state) {
        Preconditions.checkArgument(state != State.DOZE_REQUEST_PULSE);
        requestState(state, -1);
    }

    public void requestPulse(int i) {
        Preconditions.checkState(!isExecutingTransition());
        requestState(State.DOZE_REQUEST_PULSE, i);
    }

    public void requestAod(int i) {
        Preconditions.checkState(!isExecutingTransition());
        requestState(State.DOZE_AOD, i);
    }

    private void requestState(State state, int i) {
        Assert.isMainThread();
        StringBuilder sb = new StringBuilder();
        sb.append("request: current=");
        sb.append(this.mState);
        sb.append(" req=");
        sb.append(state);
        Log.i("DozeMachine", sb.toString(), new Throwable("here"));
        boolean z = !isExecutingTransition();
        this.mQueuedRequests.add(state);
        if (z) {
            this.mWakeLock.acquire();
            for (int i2 = 0; i2 < this.mQueuedRequests.size(); i2++) {
                transitionTo((State) this.mQueuedRequests.get(i2), i);
            }
            this.mQueuedRequests.clear();
            this.mWakeLock.release();
        }
    }

    public State getState() {
        Assert.isMainThread();
        Preconditions.checkState(!isExecutingTransition());
        return this.mState;
    }

    public int getPulseReason() {
        Assert.isMainThread();
        boolean z = this.mState == State.DOZE_REQUEST_PULSE || this.mState == State.DOZE_PULSING || this.mState == State.DOZE_PULSE_DONE;
        StringBuilder sb = new StringBuilder();
        sb.append("must be in pulsing state, but is ");
        sb.append(this.mState);
        Preconditions.checkState(z, sb.toString());
        return this.mPulseReason;
    }

    public int getAodReason() {
        return this.mAodReason;
    }

    private boolean isExecutingTransition() {
        return !this.mQueuedRequests.isEmpty();
    }

    private void transitionTo(State state, int i) {
        State transitionPolicy = transitionPolicy(state);
        StringBuilder sb = new StringBuilder();
        sb.append("transition: old=");
        sb.append(this.mState);
        sb.append(" req=");
        sb.append(state);
        sb.append(" new=");
        sb.append(transitionPolicy);
        Log.i("DozeMachine", sb.toString());
        if (transitionPolicy != this.mState) {
            validateTransition(transitionPolicy);
            State state2 = this.mState;
            this.mState = transitionPolicy;
            DozeLog.traceState(transitionPolicy);
            CommonUtils.traceAppCounter("doze_machine_state", transitionPolicy.ordinal());
            updateStateReason(transitionPolicy, state2, i);
            performTransitionOnComponents(state2, transitionPolicy);
            updateWakeLockState(transitionPolicy);
            resolveIntermediateState(transitionPolicy);
        }
    }

    private void updateStateReason(State state, State state2, int i) {
        if (state == State.DOZE_REQUEST_PULSE) {
            this.mPulseReason = i;
        } else if (state2 == State.DOZE_PULSE_DONE) {
            this.mPulseReason = -1;
        }
        if (state == State.DOZE_AOD) {
            this.mAodReason = i;
        } else if (state2 == State.DOZE_AOD) {
            this.mAodReason = -1;
        }
    }

    private void performTransitionOnComponents(State state, State state2) {
        for (Part transitionTo : this.mParts) {
            transitionTo.transitionTo(state, state2);
        }
        if (C00881.$SwitchMap$com$miui$aod$doze$DozeMachine$State[state2.ordinal()] == 9) {
            this.mDozeService.finish();
        }
    }

    private void validateTransition(State state) {
        try {
            int i = C00881.$SwitchMap$com$miui$aod$doze$DozeMachine$State[this.mState.ordinal()];
            boolean z = false;
            if (i == 7) {
                Preconditions.checkState(state == State.INITIALIZED);
            } else if (i == 9) {
                Preconditions.checkState(state == State.FINISH);
            }
            switch (state) {
                case DOZE_PULSING:
                    if (this.mState == State.DOZE_REQUEST_PULSE) {
                        z = true;
                    }
                    Preconditions.checkState(z);
                    return;
                case UNINITIALIZED:
                    throw new IllegalArgumentException("can't transition to UNINITIALIZED");
                case INITIALIZED:
                    if (this.mState == State.UNINITIALIZED) {
                        z = true;
                    }
                    Preconditions.checkState(z);
                    return;
                case DOZE_PULSE_DONE:
                    if (this.mState == State.DOZE_REQUEST_PULSE || this.mState == State.DOZE_PULSING) {
                        z = true;
                    }
                    Preconditions.checkState(z);
                    return;
                default:
                    return;
            }
        } catch (RuntimeException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Illegal Transition: ");
            sb.append(this.mState);
            sb.append(" -> ");
            sb.append(state);
            throw new IllegalStateException(sb.toString(), e);
        }
    }

    private State transitionPolicy(State state) {
        if (this.mState == State.FINISH) {
            return State.FINISH;
        }
        if ((this.mState == State.DOZE_AOD_PAUSED || this.mState == State.DOZE_AOD_PAUSING || this.mState == State.DOZE_AOD || this.mState == State.DOZE) && state == State.DOZE_PULSE_DONE) {
            StringBuilder sb = new StringBuilder();
            sb.append("Dropping pulse done because current state is already done: ");
            sb.append(this.mState);
            Log.i("DozeMachine", sb.toString());
            return this.mState;
        } else if (state != State.DOZE_REQUEST_PULSE || this.mState.canPulse()) {
            return state;
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Dropping pulse request because current state can't pulse: ");
            sb2.append(this.mState);
            Log.i("DozeMachine", sb2.toString());
            return this.mState;
        }
    }

    private void updateWakeLockState(State state) {
        boolean staysAwake = state.staysAwake();
        if (this.mWakeLockHeldForCurrentState && !staysAwake) {
            this.mWakeLock.release();
            this.mWakeLockHeldForCurrentState = false;
        } else if (!this.mWakeLockHeldForCurrentState && staysAwake) {
            this.mWakeLock.acquire();
            this.mWakeLockHeldForCurrentState = true;
        }
    }

    private void resolveIntermediateState(State state) {
        if (C00881.$SwitchMap$com$miui$aod$doze$DozeMachine$State[state.ordinal()] == 8) {
            if (!Utils.isGxzwSensor() || !Utils.isShowFingerprintIcon(this.mContext) || !Utils.isFodAodShowEnable(this.mContext)) {
                transitionTo(Utils.isAodClockDisable(this.mContext) ? State.DOZE : State.DOZE_AOD, -1);
            } else {
                transitionTo(State.DOZE_AOD, -1);
            }
        }
    }

    public void dump(PrintWriter printWriter) {
        printWriter.print(" state=");
        printWriter.println(this.mState);
        printWriter.print(" wakeLockHeldForCurrentState=");
        printWriter.println(this.mWakeLockHeldForCurrentState);
        printWriter.println("Parts:");
        for (Part dump : this.mParts) {
            dump.dump(printWriter);
        }
    }
}
