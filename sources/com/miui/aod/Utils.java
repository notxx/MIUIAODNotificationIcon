package com.miui.aod;

import android.app.admin.DevicePolicyManager;
import android.content.ContentResolver;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.text.format.DateFormat;
import android.util.Log;
import com.miui.aod.util.BatteryController;
import com.miui.aod.util.ReflectUtil;
import com.miui.aod.utils.CommonUtils;
import com.miui.aod.utils.MiuiSettingsUtils;
import com.miui.aod.widget.AODSettings;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import miui.os.Build;
import miui.util.FeatureParser;

public class Utils {
    private static final boolean GXZW_SENSOR = MiuiSettingsUtils.getBooleanFromSystemProperties("ro.hardware.fp.fod", false);
    public static final boolean SUPPORT_AOD = FeatureParser.getBoolean("support_aod", false);
    public static final boolean SUPPORT_LOW_BRIGHTNESS_FOD = (!"equuleus".equals(Build.DEVICE) && !"ursa".equals(Build.DEVICE));
    private static String TAG = "MiuiAod.Utils";
    private static boolean sSystemBootCompleted;

    public static String getHourMinformat(Context context) {
        String str = ((SimpleDateFormat) DateFormat.getTimeFormat(context)).toPattern().toString();
        return str.contains("a") ? str.replace("a", "").trim() : str;
    }

    public static boolean isBootCompleted() {
        if (!sSystemBootCompleted) {
            sSystemBootCompleted = "1".equals(MiuiSettingsUtils.getStringFromSystemProperites("sys.boot_completed", "0"));
        }
        return sSystemBootCompleted;
    }

    public static boolean isPowerSaveMode(Context context) {
        boolean z = false;
        if (BatteryController.isCharging()) {
            return false;
        }
        boolean z2 = Secure.getInt(context.getContentResolver(), "EXTREME_POWER_MODE_ENABLE", 0) == 1;
        if ((System.getInt(context.getContentResolver(), "POWER_SAVE_MODE_OPEN", 0) == 1) || z2) {
            z = true;
        }
        return z;
    }

    public static int getShowStyle(Context context) {
        return Secure.getInt(context.getContentResolver(), "aod_show_style", AODSettings.AOD_SHOW_STYLE_DEF);
    }

    public static boolean isShowTemporary(Context context) {
        return isAodEnable(context) && getShowStyle(context) == 0;
    }

    public static boolean isAodClockDisable(Context context) {
        boolean isPowerSaveMode = isPowerSaveMode(context);
        if ((!isAodEnable(context)) || (!isShowTemporary(context) && isPowerSaveMode)) {
            return true;
        }
        boolean z = false;
        if (!isTimingStyle(context)) {
            return false;
        }
        long aodStartTime = getAodStartTime(context);
        long aodEndTime = getAodEndTime(context);
        Calendar instance = Calendar.getInstance();
        long j = (((long) ((instance.get(11) * 60) + instance.get(12))) * 60000) + 1;
        int i = (aodStartTime > aodEndTime ? 1 : (aodStartTime == aodEndTime ? 0 : -1));
        if ((i < 0 && (j < aodStartTime || j > aodEndTime)) || (i > 0 && j < aodStartTime && j > aodEndTime)) {
            z = true;
        }
        return z;
    }

    public static long getAodStartTime(Context context) {
        return ((long) Secure.getInt(context.getContentResolver(), "aod_start", 420)) * 60000;
    }

    public static long getAodEndTime(Context context) {
        return ((long) Secure.getInt(context.getContentResolver(), "aod_end", 1380)) * 60000;
    }

    public static boolean isAodEnable(Context context) {
        return Secure.getInt(context.getContentResolver(), AODSettings.AOD_MODE, 0) == 1;
    }

    public static boolean isTimingStyle(Context context) {
        return isAodEnable(context) && getShowStyle(context) == 1;
    }

    public static boolean isGxzwSensor() {
        return GXZW_SENSOR;
    }

    public static boolean isFodAodShowEnable(Context context) {
        return Secure.getInt(context.getContentResolver(), "gxzw_icon_aod_show_enable", 1) == 1;
    }

    public static boolean isInvertColorsEnable(Context context) {
        return Secure.getInt(context.getContentResolver(), "accessibility_display_inversion_enabled", 0) != 0;
    }

    public static boolean isUnlockWithFingerprintPossible(Context context) {
        FingerprintManager fingerprintManager = (FingerprintManager) context.getSystemService("fingerprint");
        return fingerprintManager != null && fingerprintManager.isHardwareDetected() && !isFingerprintDisabled(context) && CommonUtils.getEnrolledFingerprintsSize(fingerprintManager) > 0;
    }

    public static boolean isShowFingerprintIcon(Context context) {
        return isUnlockWithFingerprintPossible(context) && AODApplication.getHost().isGxzwIconShown();
    }

    private static boolean isFingerprintDisabled(Context context) {
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService("device_policy");
        return !(devicePolicyManager == null || (devicePolicyManager.getKeyguardDisabledFeatures(null) & 32) == 0) || AODApplication.getHost().isSimPinSecure();
    }

    public static int getKeyguardNotificationStatus(ContentResolver contentResolver) {
        return System.getInt(contentResolver, "wakeup_for_keyguard_notification", isSupportAodAnimateDevice() ? 2 : 1);
    }

    public static boolean isSupportAodAnimateDevice() {
        return "perseus".equals(android.os.Build.DEVICE);
    }

    public static boolean isAodAnimateEnable(Context context) {
        return getKeyguardNotificationStatus(context.getContentResolver()) == 2;
    }

    private static boolean setTouchMode(int i, int i2) {
        try {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("setTouchMode:");
            sb.append(i);
            sb.append(" value");
            sb.append(i2);
            Log.i(str, sb.toString());
            Object callStaticObjectMethod = ReflectUtil.callStaticObjectMethod(Class.forName("miui.util.ITouchFeature"), "getInstance", null, new Object[0]);
            if (callStaticObjectMethod != null) {
                return ((Boolean) ReflectUtil.callObjectMethod(callStaticObjectMethod, "setTouchMode", new Class[]{Integer.TYPE, Integer.TYPE}, Integer.valueOf(i), Integer.valueOf(i2))).booleanValue();
            }
        } catch (Exception e) {
            Log.i(TAG, e.toString());
        }
        return false;
    }

    public static void updateTouchMode(Context context) {
        setTouchMode(11, isShowTemporary(context) ? 1 : 0);
    }
}
