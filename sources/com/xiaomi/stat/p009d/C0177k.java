package com.xiaomi.stat.p009d;

import android.text.TextUtils;
import android.util.Log;

/* renamed from: com.xiaomi.stat.d.k */
public class C0177k {

    /* renamed from: b */
    private static boolean f157b = false;

    /* renamed from: a */
    private static void m314a(int i, String str, String str2, Throwable th) {
        if (TextUtils.isEmpty(str)) {
            str = "MI_STAT";
        }
        if (i == 0) {
            Log.v(str, str2, th);
        } else if (i == 1) {
            Log.i(str, str2, th);
        } else if (i == 2) {
            Log.d(str, str2, th);
        } else if (i == 3) {
            Log.w(str, str2, th);
        } else if (i == 4) {
            Log.e(str, str2, th);
        }
    }

    /* renamed from: b */
    private static void m317b(int i, String str, String str2, Throwable th) {
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        if (str2.length() > 4000) {
            m314a(i, str, str2.substring(0, 4000), null);
            m317b(i, str, str2.substring(4000, str2.length()), null);
            return;
        }
        m314a(i, str, str2, th);
    }

    /* renamed from: a */
    public static void m315a(boolean z) {
        f157b = z;
    }

    /* renamed from: a */
    public static boolean m316a() {
        return f157b;
    }

    /* renamed from: b */
    public static void m318b(String str) {
        m319b("MI_STAT", str);
    }

    /* renamed from: b */
    public static void m319b(String str, String str2) {
        if (f157b) {
            m317b(2, str, str2, null);
        }
    }

    /* renamed from: b */
    public static void m320b(String str, String str2, Throwable th) {
        if (f157b) {
            m317b(2, str, str2, th);
        }
    }

    /* renamed from: c */
    public static void m321c(String str) {
        m322c("MI_STAT", str);
    }

    /* renamed from: c */
    public static void m322c(String str, String str2) {
        if (f157b) {
            m317b(1, str, str2, null);
        }
    }

    /* renamed from: d */
    public static void m323d(String str, String str2) {
        if (f157b) {
            m317b(3, str, str2, null);
        }
    }

    /* renamed from: e */
    public static void m324e(String str) {
        m325e("MI_STAT", str);
    }

    /* renamed from: e */
    public static void m325e(String str, String str2) {
        m317b(4, str, str2, null);
    }

    /* renamed from: e */
    public static void m326e(String str, String str2, Throwable th) {
        m317b(4, str, str2, th);
    }
}
