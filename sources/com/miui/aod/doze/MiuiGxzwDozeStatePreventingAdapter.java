package com.miui.aod.doze;

import android.content.Context;
import com.miui.aod.Utils;
import com.miui.aod.doze.DozeMachine.Service;
import com.miui.aod.doze.DozeMachine.Service.Delegate;
import com.miui.aod.doze.DozeMachine.State;

public class MiuiGxzwDozeStatePreventingAdapter extends Delegate {
    private boolean mAod2On = false;
    private int mAodBrightness = 17;
    private final Context mContext;
    private boolean mFingerprintPress;

    private MiuiGxzwDozeStatePreventingAdapter(Service service, Context context) {
        super(service);
        this.mContext = context;
    }

    public void requestState(State state) {
        if (state == State.DOZE_AOD) {
            super.requestState(state);
        } else if (state == State.DOZE || state == State.DOZE_AOD_PAUSING || state == State.DOZE_AOD_PAUSED) {
            if (!Utils.isShowFingerprintIcon(this.mContext)) {
                super.requestState(state);
            }
        } else if (state == State.FINISH && !Utils.isUnlockWithFingerprintPossible(this.mContext)) {
            super.requestState(state);
        }
    }

    public void finish() {
        super.finish();
        this.mAodBrightness = 17;
        this.mFingerprintPress = false;
        this.mAod2On = false;
    }

    public void setDozeScreenBrightness(int i) {
        this.mAodBrightness = i;
        if (!Utils.SUPPORT_LOW_BRIGHTNESS_FOD || !this.mFingerprintPress) {
            super.setDozeScreenBrightness(i);
        }
    }

    public void fingerprintPressed(boolean z, Runnable runnable) {
        super.fingerprintPressed(z, runnable);
        if (this.mFingerprintPress != z) {
            this.mFingerprintPress = z;
            if (Utils.SUPPORT_LOW_BRIGHTNESS_FOD) {
                if (z && this.mAodBrightness < 17) {
                    super.setDozeScreenBrightness(-1);
                    runnable.run();
                    this.mAod2On = true;
                } else if (!z && this.mAod2On) {
                    runnable.run();
                    this.mAod2On = false;
                    super.setDozeScreenBrightness(this.mAodBrightness);
                }
            }
        }
    }

    public static Service wrapIfNeeded(Service service, Context context) {
        return Utils.isGxzwSensor() ? new MiuiGxzwDozeStatePreventingAdapter(service, context) : service;
    }
}
