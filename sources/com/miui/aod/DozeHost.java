package com.miui.aod;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Interpolator;
import com.miui.aod.doze.DozeLog;
import com.miui.aod.util.wakelock.WakeLock;
import com.miui.aod.utils.CommonUtils;
import com.miui.aod.utils.MiuiSettingsUtils;
import com.miui.aod.widget.AODSettings;
import com.miui.aod.widget.FrameAnimationManager;
import java.util.ArrayList;
import miui.os.Build;

public class DozeHost {
    private AODView mAODView;
    private boolean mAnimateWakeup;
    private final ArrayList<Callback> mCallbacks = new ArrayList<>();
    /* access modifiers changed from: private */
    public ViewGroup mContainer;
    private Context mContext;
    private boolean mDozing;
    private boolean mDozingRequested;
    private Runnable mFingerUpRunnable = new Runnable() {
        public void run() {
            if (DozeHost.this.mContainer != null) {
                DozeHost.this.mContainer.setAlpha(1.0f);
            }
        }
    };
    private boolean mGxzwIconTransparent = true;
    private Handler mHandler = new Handler();
    private PowerManager mPowerManager;
    private boolean mShowAodAnimate;
    private boolean mSimPinSecure = false;
    private Runnable mStopAodRunnable = new Runnable() {
        public void run() {
            DozeHost.this.onStopDoze();
        }
    };
    private WindowManager mViewManager;
    private WakeLock mWakeLock;
    private boolean mWindowAdded = false;
    private Runnable removeAODViewRunnable = new Runnable() {
        public void run() {
            DozeHost.this.removeAODView();
        }
    };

    public interface Callback {
        void fireAodState(boolean z, String str);

        void notifyKeycodeGoto();

        void onAodAnimate(boolean z);

        void onDozingRequested(boolean z);

        void onExtendPulse();

        void onFingerprintPressed(boolean z);

        void onPowerSaveChanged(boolean z);
    }

    public class SineEaseOutInterpolator implements Interpolator {
        public SineEaseOutInterpolator() {
        }

        public float getInterpolation(float f) {
            return (float) Math.sin(((double) f) * 1.5707963267948966d);
        }
    }

    public boolean isPulsingBlocked() {
        return false;
    }

    public void onKeyguardTransparent() {
        this.mHandler.post(this.mWakeLock.wrap(this.removeAODViewRunnable));
    }

    private AODView showSecurityIdentityViewAt() {
        removeAODView();
        AODView aODView = (AODView) LayoutInflater.from(this.mContext).inflate(R.layout.aod_mode_layout, null);
        aODView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        LayoutParams layoutParams = new LayoutParams(-1, -1, 2026, 66816, Utils.isGxzwSensor() ? -2 : -1);
        layoutParams.flags &= -9;
        layoutParams.flags |= 131072;
        layoutParams.flags |= 32;
        CommonUtils.setLayoutInDisplayCutoutMode(layoutParams, 1);
        layoutParams.setTitle("AOD");
        aODView.setSystemUiVisibility(4868);
        layoutParams.screenOrientation = 1;
        this.mViewManager.addView(aODView, layoutParams);
        this.mWindowAdded = true;
        this.mContainer = (ViewGroup) aODView.findViewById(R.id.clock_container);
        if (!this.mDozingRequested) {
            aODView.setVisibility(8);
            this.mContainer.setAlpha(0.0f);
        }
        return aODView;
    }

    public DozeHost(Context context) {
        this.mContext = context;
        this.mPowerManager = (PowerManager) context.getSystemService("power");
        this.mWakeLock = WakeLock.wrap(this.mPowerManager.newWakeLock(1, "DozeHost"));
        upgrade();
    }

    private void upgrade() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        if (VERSION.SDK_INT >= 28 && MiuiSettingsUtils.getIntFromSecure(contentResolver, "doze_always_on", -1, -2) == -1) {
            int intFromSecure = MiuiSettingsUtils.getIntFromSecure(contentResolver, "aod_mode", -1, -2);
            if (intFromSecure != -1) {
                MiuiSettingsUtils.putIntToSecure(contentResolver, "doze_always_on", intFromSecure, -2);
            }
            MiuiSettingsUtils.putIntToSecure(contentResolver, "doze_enabled", 0, -2);
        }
        boolean z = MiuiSettingsUtils.getIntFromSecure(contentResolver, AODSettings.AOD_MODE, 0, -2) == 1;
        boolean z2 = MiuiSettingsUtils.getIntFromSecure(contentResolver, "aod_mode_time", 0, -2) == 1;
        if (Build.DEVICE.equals("cepheus") && Build.IS_STABLE_VERSION && MiuiSettingsUtils.getIntFromSecure(contentResolver, "aod_index_update", 0, -2) == 0) {
            int intFromSecure2 = MiuiSettingsUtils.getIntFromSecure(contentResolver, "aod_style_index", -1, -2);
            if (intFromSecure2 <= 15 && intFromSecure2 >= 0) {
                MiuiSettingsUtils.putIntToSecure(contentResolver, "aod_style_index", new int[]{3, 2, 6, 5, 1, 4, 7, 8, 9, 10, 0, 11, 12, 13, 14, 15}[intFromSecure2], -2);
            }
            MiuiSettingsUtils.putIntToSecure(contentResolver, "aod_index_update", 1, -2);
        }
        if (MiuiSettingsUtils.getIntFromSecure(contentResolver, "aod_time_update", 0, -2) == 0) {
            if (z) {
                MiuiSettingsUtils.putIntToSecure(contentResolver, "need_reset_aod_time", 1, -2);
                if (z2) {
                    int intFromSecure3 = MiuiSettingsUtils.getIntFromSecure(contentResolver, "aod_start", 360, -2);
                    int intFromSecure4 = MiuiSettingsUtils.getIntFromSecure(contentResolver, "aod_end", 1440, -2);
                    MiuiSettingsUtils.putIntToSecure(contentResolver, "aod_start", intFromSecure3, -2);
                    MiuiSettingsUtils.putIntToSecure(contentResolver, "aod_end", intFromSecure4, -2);
                }
            } else {
                MiuiSettingsUtils.putIntToSecure(contentResolver, "aod_mode_time", 1, -2);
            }
            MiuiSettingsUtils.putIntToSecure(contentResolver, "aod_time_update", 1, -2);
        }
        if (MiuiSettingsUtils.getIntFromSecure(contentResolver, "aod_show_style_update", 0, -2) == 0) {
            if (z) {
                MiuiSettingsUtils.putIntToSecure(contentResolver, "aod_show_style", z2 ? 1 : 2, -2);
            }
            MiuiSettingsUtils.putIntToSecure(contentResolver, "aod_show_style_update", 1, -2);
        }
        AODSettings.upgradeIndex2Name(contentResolver, z);
    }

    public boolean isDozing() {
        return this.mDozingRequested;
    }

    public boolean isAodClockVisible() {
        return this.mContainer != null && this.mContainer.getVisibility() == 0;
    }

    public void setAodClockVisibility(final boolean z) {
        this.mHandler.post(this.mWakeLock.wrap((Runnable) new Runnable() {
            public void run() {
                DozeHost.this.mContainer.setVisibility(z ? 0 : 4);
            }
        }));
    }

    public void onGxzwIconChanged(boolean z) {
        this.mGxzwIconTransparent = z;
    }

    public void fireAodState(final boolean z, final String str) {
        final ArrayList arrayList = new ArrayList(this.mCallbacks);
        this.mHandler.post(this.mWakeLock.wrap((Runnable) new Runnable() {
            public void run() {
                for (Callback fireAodState : arrayList) {
                    fireAodState.fireAodState(z, str);
                }
            }
        }));
    }

    public boolean isGxzwIconShown() {
        long runtimeSharedValue = CommonUtils.getRuntimeSharedValue("KEYGUARD_GXZW_ICON_SHOWN");
        if (runtimeSharedValue == -1) {
            if (this.mGxzwIconTransparent) {
                return false;
            }
        } else if (runtimeSharedValue != 1) {
            return false;
        }
        return true;
    }

    public void fireFingerprintPressed(final boolean z) {
        this.mHandler.post(this.mWakeLock.wrap((Runnable) new Runnable() {
            public void run() {
                DozeHost.this.onFingerprintPressed(z);
            }
        }));
    }

    /* access modifiers changed from: private */
    public void onFingerprintPressed(boolean z) {
        if (this.mDozing && Utils.SUPPORT_LOW_BRIGHTNESS_FOD) {
            for (Callback onFingerprintPressed : new ArrayList(this.mCallbacks)) {
                onFingerprintPressed.onFingerprintPressed(z);
            }
        }
        this.mHandler.removeCallbacks(this.mFingerUpRunnable);
        if (!z) {
            this.mHandler.postDelayed(this.mFingerUpRunnable, 30);
        } else if (this.mContainer != null) {
            this.mContainer.animate().cancel();
            this.mContainer.setAlpha(0.0f);
        }
    }

    public boolean isAnimateShowing() {
        return this.mShowAodAnimate;
    }

    public boolean isSimPinSecure() {
        return this.mSimPinSecure;
    }

    public void setSimPinSecure(boolean z) {
        this.mSimPinSecure = z;
    }

    public void setNotificationAnimate(boolean z) {
        this.mShowAodAnimate = z;
    }

    public void startDozing() {
        if (!this.mDozingRequested) {
            this.mDozingRequested = true;
            for (Callback onDozingRequested : new ArrayList(this.mCallbacks)) {
                onDozingRequested.onDozingRequested(this.mDozingRequested);
            }
            DozeLog.traceDozing(this.mContext, this.mDozing);
            this.mDozing = this.mDozingRequested;
            this.mAODView = showSecurityIdentityViewAt();
            this.mAODView.setAlpha(1.0f);
            this.mAODView.setVisibility(0);
            this.mAODView.onStartDoze();
            this.mAODView.handleUpdateView();
            if (Utils.isGxzwSensor() && Utils.isAodClockDisable(this.mContext)) {
                this.mContainer.setVisibility(4);
            } else if (!Utils.isAodAnimateEnable(this.mContext) || !Utils.isAodClockDisable(this.mContext)) {
                this.mContainer.setAlpha(0.0f);
                if (!FrameAnimationManager.needFrameAnimation(AODSettings.getAodStyleName(this.mContext))) {
                    this.mContainer.animate().alpha(1.0f).setDuration(800).setInterpolator(new SineEaseOutInterpolator()).setStartDelay(800).start();
                    return;
                }
                this.mAODView.clearClockPanelAnimation();
                this.mContainer.postDelayed(new Runnable() {
                    public final void run() {
                        DozeHost.lambda$startDozing$1(DozeHost.this);
                    }
                }, 800);
            } else {
                this.mContainer.setVisibility(4);
            }
        }
    }

    public static /* synthetic */ void lambda$startDozing$1(DozeHost dozeHost) {
        dozeHost.mContainer.setAlpha(1.0f);
        dozeHost.mAODView.startClockPanelAnimation();
    }

    public void stopDozing() {
        if (Looper.myLooper() == this.mHandler.getLooper()) {
            onStopDoze();
        } else {
            this.mHandler.postAtFrontOfQueue(this.mStopAodRunnable);
        }
    }

    /* access modifiers changed from: private */
    public void onStopDoze() {
        if (this.mDozingRequested) {
            this.mDozingRequested = false;
            for (Callback onDozingRequested : new ArrayList(this.mCallbacks)) {
                onDozingRequested.onDozingRequested(this.mDozingRequested);
            }
            this.mAODView.onStopDoze();
            DozeLog.traceDozing(this.mContext, this.mDozing);
            this.mDozing = this.mDozingRequested;
            if (CommonUtils.getRuntimeSharedValue("KEYGUARD_TURN_ON_DELAYED") == 1) {
                this.mHandler.postDelayed(this.removeAODViewRunnable, 550);
            } else {
                removeAODView();
            }
        }
    }

    public void removeAODView() {
        this.mViewManager = (WindowManager) this.mContext.getSystemService("window");
        if (this.mAODView != null && this.mWindowAdded) {
            this.mAODView.setVisibility(8);
            this.mViewManager.removeView(this.mAODView);
            this.mWindowAdded = false;
        }
    }

    public void firePowerSaveChanged() {
        boolean isPowerSaveMode = Utils.isPowerSaveMode(this.mContext);
        for (Callback onPowerSaveChanged : new ArrayList(this.mCallbacks)) {
            onPowerSaveChanged.onPowerSaveChanged(isPowerSaveMode);
        }
    }

    public void dozeTimeTick() {
        this.mAODView.handleUpdateView();
    }

    public void setSunImage(int i) {
        this.mAODView.setSunImage(i);
    }

    public void setAnimateWakeup(boolean z) {
        this.mAnimateWakeup = z;
    }

    public void extendPulse() {
        for (Callback onExtendPulse : new ArrayList(this.mCallbacks)) {
            onExtendPulse.onExtendPulse();
        }
    }

    public void notifyKeycodeGoto() {
        for (Callback notifyKeycodeGoto : new ArrayList(this.mCallbacks)) {
            notifyKeycodeGoto.notifyKeycodeGoto();
        }
    }

    public void addCallback(Callback callback) {
        this.mCallbacks.add(callback);
    }

    public void removeCallback(Callback callback) {
        this.mCallbacks.remove(callback);
    }

    public void fireAnimateState() {
        for (Callback onAodAnimate : new ArrayList(this.mCallbacks)) {
            onAodAnimate.onAodAnimate(this.mShowAodAnimate);
        }
    }
}
