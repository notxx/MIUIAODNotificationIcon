package com.xiaomi.stat;

import android.content.Context;

public class MiStat {

    /* renamed from: a */
    private static C0186e f17a;

    public static void initialize(Context context, String str, String str2, boolean z, String str3) {
        if (f17a == null) {
            C0186e eVar = new C0186e(context, str, str2, z, str3);
            f17a = eVar;
            return;
        }
        throw new IllegalStateException("duplicate sdk init!");
    }

    /* renamed from: a */
    private static void m33a() {
        if (f17a == null) {
            throw new IllegalStateException("must init sdk before use!");
        }
    }

    public static void setExceptionCatcherEnabled(boolean z) {
        m33a();
        f17a.mo2440c(z);
    }

    public static void trackEvent(String str, String str2, MiStatParams miStatParams) {
        m33a();
        f17a.mo2436a(str, str2, miStatParams);
    }

    public static void setUploadNetworkType(int i) {
        m33a();
        f17a.mo2435a(i);
    }

    public static void setInternationalRegion(boolean z, String str) {
        m33a();
        f17a.mo2438a(z, str);
    }

    public static boolean setUseSystemUploadingService(boolean z) {
        m33a();
        return f17a.mo2441e(z);
    }

    public static void setDebugModeEnabled(boolean z) {
        m33a();
        f17a.mo2442f(z);
    }
}
