package com.miui.aod.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import com.miui.aod.AODApplication;
import java.util.ArrayList;

public class BatteryController extends BroadcastReceiver {
    private static boolean mCharging;
    private static BatteryController sInstance;
    ContentObserver mBatteryModeChangeObserver = new ContentObserver(new Handler()) {
        public void onChange(boolean z) {
            AODApplication.getHost().firePowerSaveChanged();
        }
    };
    private final ArrayList<BatteryStateChangeCallback> mChangeCallbacks = new ArrayList<>();
    protected boolean mCharged;
    private boolean mHasReceivedBattery = false;
    private int mLevel;
    protected boolean mPluggedIn;

    public interface BatteryStateChangeCallback {
        void onBatteryLevelChanged(int i, boolean z, boolean z2);
    }

    public static synchronized BatteryController getInstance(Context context) {
        BatteryController batteryController;
        synchronized (BatteryController.class) {
            if (sInstance == null && context != null) {
                sInstance = new BatteryController(context);
            }
            batteryController = sInstance;
        }
        return batteryController;
    }

    private BatteryController(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        context.registerReceiver(this, intentFilter);
        context.getContentResolver().registerContentObserver(System.getUriFor("POWER_SAVE_MODE_OPEN"), false, this.mBatteryModeChangeObserver);
        context.getContentResolver().registerContentObserver(Secure.getUriFor("EXTREME_POWER_MODE_ENABLE"), false, this.mBatteryModeChangeObserver);
        this.mBatteryModeChangeObserver.onChange(false);
    }

    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BATTERY_CHANGED")) {
            boolean z = true;
            this.mHasReceivedBattery = true;
            this.mLevel = intent.getIntExtra("level", 0);
            this.mPluggedIn = intent.getIntExtra("plugged", 0) != 0;
            int intExtra = intent.getIntExtra("status", 1);
            this.mCharged = intExtra == 5;
            if ((!this.mCharged && intExtra != 2) || !this.mPluggedIn) {
                z = false;
            }
            mCharging = z;
            fireBatteryLevelChanged();
        }
    }

    public static boolean isCharging() {
        return mCharging;
    }

    /* access modifiers changed from: protected */
    public void fireBatteryLevelChanged() {
        synchronized (this.mChangeCallbacks) {
            int size = this.mChangeCallbacks.size();
            for (int i = 0; i < size; i++) {
                ((BatteryStateChangeCallback) this.mChangeCallbacks.get(i)).onBatteryLevelChanged(this.mLevel, this.mPluggedIn, mCharging);
            }
        }
    }

    public void addCallback(BatteryStateChangeCallback batteryStateChangeCallback) {
        synchronized (this.mChangeCallbacks) {
            this.mChangeCallbacks.add(batteryStateChangeCallback);
        }
        if (this.mHasReceivedBattery) {
            batteryStateChangeCallback.onBatteryLevelChanged(this.mLevel, this.mPluggedIn, mCharging);
        }
    }

    public void removeCallback(BatteryStateChangeCallback batteryStateChangeCallback) {
        synchronized (this.mChangeCallbacks) {
            this.mChangeCallbacks.remove(batteryStateChangeCallback);
        }
    }
}
