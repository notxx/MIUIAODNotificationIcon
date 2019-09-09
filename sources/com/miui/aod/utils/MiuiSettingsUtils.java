package com.miui.aod.utils;

import android.content.ContentResolver;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import miui.os.SystemProperties;
import miui.util.FeatureParser;

public class MiuiSettingsUtils {
    private MiuiSettingsUtils() {
    }

    public static boolean putIntToSecure(ContentResolver contentResolver, String str, int i, int i2) {
        return Secure.putIntForUser(contentResolver, str, i, i2);
    }

    public static int getIntFromSecure(ContentResolver contentResolver, String str, int i, int i2) {
        return Secure.getIntForUser(contentResolver, str, i, i2);
    }

    public static boolean putLongToSystem(ContentResolver contentResolver, String str, long j, int i) {
        return System.putLongForUser(contentResolver, str, j, i);
    }

    public static boolean putIntToSystem(ContentResolver contentResolver, String str, int i, int i2) {
        return System.putIntForUser(contentResolver, str, i, i2);
    }

    public static long getLongFromSystem(ContentResolver contentResolver, String str, long j, int i) {
        return System.getLongForUser(contentResolver, str, j, i);
    }

    public static int getIntFromSystem(ContentResolver contentResolver, String str, int i, int i2) {
        return System.getIntForUser(contentResolver, str, i, i2);
    }

    public static String getStringFromSystem(ContentResolver contentResolver, String str, int i) {
        return System.getStringForUser(contentResolver, str, i);
    }

    public static boolean getBooleanFromFeatureParse(String str, boolean z) {
        return FeatureParser.getBoolean(str, z);
    }

    public static float getFloatFromFeatureParse(String str, float f) {
        return FeatureParser.getFloat(str, f).floatValue();
    }

    public static boolean getBooleanFromSystemProperties(String str, boolean z) {
        return SystemProperties.getBoolean(str, z);
    }

    public static int getIntFromSystemProperties(String str, int i) {
        return SystemProperties.getInt(str, i);
    }

    public static String getStringFromSystemProperites(String str, String str2) {
        return SystemProperties.get(str, str2);
    }
}
