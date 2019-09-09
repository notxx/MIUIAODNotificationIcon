package com.xiaomi.stat.p009d;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/* renamed from: com.xiaomi.stat.d.p */
public class C0183p {

    /* renamed from: b */
    private static SharedPreferences f163b;

    /* renamed from: c */
    private static Editor f164c;

    /* renamed from: l */
    private static void m381l(Context context) {
        if (f164c == null) {
            synchronized (C0183p.class) {
                if (f164c == null) {
                    f163b = context.getSharedPreferences("mi_stat_pref", 0);
                    f164c = f163b.edit();
                }
            }
        }
    }

    /* renamed from: a */
    private static String m355a(Context context, String str, String str2) {
        m381l(context);
        return f163b.getString(str, str2);
    }

    /* renamed from: b */
    private static void m364b(Context context, String str, String str2) {
        m381l(context);
        f164c.putString(str, str2).apply();
    }

    /* renamed from: a */
    private static long m353a(Context context, String str, long j) {
        m381l(context);
        return f163b.getLong(str, j);
    }

    /* renamed from: b */
    private static void m363b(Context context, String str, long j) {
        m381l(context);
        f164c.putLong(str, j).apply();
    }

    /* renamed from: a */
    private static boolean m359a(Context context, String str, boolean z) {
        m381l(context);
        return f163b.getBoolean(str, z);
    }

    /* renamed from: b */
    private static void m365b(Context context, String str, boolean z) {
        m381l(context);
        f164c.putBoolean(str, z).apply();
    }

    /* renamed from: a */
    public static String m354a(Context context) {
        return m355a(context, "imei1", "");
    }

    /* renamed from: a */
    public static void m357a(Context context, String str) {
        m364b(context, "imei1", str);
    }

    /* renamed from: b */
    public static String m360b(Context context) {
        return m355a(context, "imei2", "");
    }

    /* renamed from: b */
    public static void m362b(Context context, String str) {
        m364b(context, "imei2", str);
    }

    /* renamed from: c */
    public static String m367c(Context context) {
        return m355a(context, "meid", "");
    }

    /* renamed from: c */
    public static void m369c(Context context, String str) {
        m364b(context, "meid", str);
    }

    /* renamed from: d */
    public static String m370d(Context context) {
        return m355a(context, "mac", "");
    }

    /* renamed from: d */
    public static void m371d(Context context, String str) {
        m364b(context, "mac", str);
    }

    /* renamed from: e */
    public static String m372e(Context context) {
        return m355a(context, "serial", "");
    }

    /* renamed from: e */
    public static void m373e(Context context, String str) {
        m364b(context, "serial", str);
    }

    /* renamed from: f */
    public static long m374f(Context context) {
        return m353a(context, "s_t", 0);
    }

    /* renamed from: a */
    public static void m356a(Context context, long j) {
        m363b(context, "s_t", j);
    }

    /* renamed from: g */
    public static long m376g(Context context) {
        return m353a(context, "l_t", 0);
    }

    /* renamed from: b */
    public static void m361b(Context context, long j) {
        m363b(context, "l_t", j);
    }

    /* renamed from: h */
    public static long m377h(Context context) {
        return m353a(context, "e_t", 0);
    }

    /* renamed from: c */
    public static void m368c(Context context, long j) {
        m363b(context, "e_t", j);
    }

    /* renamed from: i */
    public static boolean m378i(Context context) {
        return m359a(context, "od_checked", false);
    }

    /* renamed from: a */
    public static void m358a(Context context, boolean z) {
        m365b(context, "od_checked", z);
    }

    /* renamed from: j */
    public static String m379j(Context context) {
        return m355a(context, "od_v", (String) null);
    }

    /* renamed from: f */
    public static void m375f(Context context, String str) {
        m364b(context, "od_v", str);
    }

    /* renamed from: k */
    public static boolean m380k(Context context) {
        return m359a(context, "resued_old_instanced_id", false);
    }

    /* renamed from: b */
    public static void m366b(Context context, boolean z) {
        m365b(context, "resued_old_instanced_id", z);
    }
}
