package com.xiaomi.stat;

import android.os.Looper;
import android.text.TextUtils;

/* renamed from: com.xiaomi.stat.b */
public class C0143b {

    /* renamed from: B */
    private static int f44B = 31;

    /* renamed from: C */
    private static int f45C = 15;

    /* renamed from: D */
    private static String[] f46D = null;

    /* renamed from: F */
    private static boolean f47F = false;

    /* renamed from: G */
    private static boolean f48G;

    /* renamed from: H */
    private static String f49H;

    /* renamed from: I */
    private static boolean f50I;

    /* renamed from: J */
    private static Object f51J = new Object();

    /* renamed from: K */
    private static boolean f52K;

    static {
        m102n();
    }

    /* renamed from: a */
    public static boolean m73a() {
        return C0125A.m8a().mo2350a("pref_statistic_enabled", true);
    }

    /* renamed from: b */
    public static boolean m75b() {
        return C0125A.m8a().mo2350a("pref_network_access_enabled", true);
    }

    /* renamed from: c */
    public static void m76c() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            long j = 5000;
            if (C0125A.m8a().mo2349a("pref_network_access_enabled")) {
                j = 1000;
            }
            try {
                Thread.sleep(j);
            } catch (InterruptedException unused) {
            }
        } else {
            throw new IllegalStateException("don't call this on main thread");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0029, code lost:
        return;
     */
    /* renamed from: d */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void m80d() {
        /*
            java.lang.Object r0 = f51J
            monitor-enter(r0)
            boolean r1 = f50I     // Catch:{ all -> 0x002a }
            if (r1 == 0) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x002a }
            return
        L_0x0009:
            r1 = 1
            f50I = r1     // Catch:{ all -> 0x002a }
            boolean r2 = com.xiaomi.stat.p009d.C0180m.m344i()     // Catch:{ all -> 0x002a }
            f48G = r2     // Catch:{ all -> 0x002a }
            java.lang.String r2 = com.xiaomi.stat.p009d.C0180m.m342g()     // Catch:{ all -> 0x002a }
            f49H = r2     // Catch:{ all -> 0x002a }
            boolean r2 = f48G     // Catch:{ all -> 0x002a }
            if (r2 != 0) goto L_0x0028
            java.lang.String r2 = f49H     // Catch:{ all -> 0x002a }
            java.lang.String r3 = "CN"
            boolean r2 = android.text.TextUtils.equals(r2, r3)     // Catch:{ all -> 0x002a }
            if (r2 != 0) goto L_0x0028
            f48G = r1     // Catch:{ all -> 0x002a }
        L_0x0028:
            monitor-exit(r0)     // Catch:{ all -> 0x002a }
            return
        L_0x002a:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x002a }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.stat.C0143b.m80d():void");
    }

    /* renamed from: e */
    public static boolean m87e() {
        if (!f50I) {
            m80d();
        }
        return f48G;
    }

    /* renamed from: c */
    public static void m79c(boolean z) {
        f48G = z;
    }

    /* renamed from: a */
    public static void m71a(String str) {
        f49H = str;
    }

    /* renamed from: f */
    public static String m88f() {
        return f49H;
    }

    /* renamed from: g */
    public static boolean m93g() {
        return f52K;
    }

    /* renamed from: d */
    public static void m82d(boolean z) {
        f52K = z;
    }

    /* renamed from: h */
    public static String m94h() {
        return C0125A.m8a().mo2348a("pref_user_id", (String) null);
    }

    /* renamed from: a */
    public static void m69a(int i) {
        f44B = i;
    }

    /* renamed from: i */
    public static int m96i() {
        return f44B;
    }

    /* renamed from: j */
    public static int m98j() {
        return f45C;
    }

    /* renamed from: c */
    public static void m78c(String str) {
        C0125A.m8a().mo2354b("configDelay", str);
    }

    /* renamed from: k */
    public static String m99k() {
        return C0125A.m8a().mo2348a("configDelay", "0-0");
    }

    /* renamed from: c */
    public static void m77c(int i) {
        C0125A.m8a().mo2352b("configNetwork", i);
    }

    /* renamed from: l */
    public static int m100l() {
        return C0125A.m8a().mo2346a("configNetwork", -1);
    }

    /* renamed from: m */
    public static int m101m() {
        return C0125A.m8a().mo2346a("uploadInterval", -1);
    }

    /* renamed from: d */
    public static void m81d(int i) {
        C0125A.m8a().mo2352b("uploadInterval", i);
    }

    /* renamed from: d */
    public static boolean m83d(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        C0125A a = C0125A.m8a();
        StringBuilder sb = new StringBuilder();
        sb.append("pref_using_custom_policy_");
        sb.append(str);
        return a.mo2350a(sb.toString(), false);
    }

    /* renamed from: a */
    public static void m72a(String str, boolean z) {
        if (!TextUtils.isEmpty(str)) {
            C0125A a = C0125A.m8a();
            StringBuilder sb = new StringBuilder();
            sb.append("pref_using_custom_policy_");
            sb.append(str);
            a.mo2355b(sb.toString(), z);
        }
    }

    /* renamed from: e */
    public static int m84e(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        C0125A a = C0125A.m8a();
        StringBuilder sb = new StringBuilder();
        sb.append("pref_custom_policy_state_");
        sb.append(str);
        return a.mo2346a(sb.toString(), 0);
    }

    /* renamed from: n */
    public static void m102n() {
        String a = C0125A.m8a().mo2348a("pref_all_sub_ids_data", (String) null);
        if (!TextUtils.isEmpty(a)) {
            f46D = a.split(",");
        }
    }

    /* renamed from: f */
    public static void m89f(String str) {
        if (f46D != null) {
            String[] strArr = f46D;
            int length = strArr.length;
            int i = 0;
            while (i < length) {
                if (!TextUtils.equals(str, strArr[i])) {
                    i++;
                } else {
                    return;
                }
            }
        }
        if (f46D == null) {
            C0125A.m8a().mo2354b("pref_all_sub_ids_data", str);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(f46D[0]);
            int length2 = f46D.length;
            for (int i2 = 1; i2 < length2; i2++) {
                sb.append(",");
                sb.append(f46D[i2]);
            }
            sb.append(",");
            sb.append(str);
            C0125A.m8a().mo2354b("pref_all_sub_ids_data", sb.toString());
        }
    }

    /* renamed from: o */
    public static String[] m103o() {
        return f46D;
    }

    /* renamed from: p */
    public static int m104p() {
        return C0125A.m8a().mo2346a("pref_app_previous_version", -1);
    }

    /* renamed from: e */
    public static void m85e(int i) {
        if (i > 0) {
            C0125A.m8a().mo2352b("pref_app_previous_version", i);
        }
    }

    /* renamed from: q */
    public static boolean m105q() {
        return C0125A.m8a().mo2350a("pref_is_first_usage", true);
    }

    /* renamed from: e */
    public static void m86e(boolean z) {
        C0125A.m8a().mo2355b("pref_is_first_usage", z);
    }

    /* renamed from: r */
    public static long m106r() {
        return C0125A.m8a().mo2347a("pref_last_dau_event_time", -1);
    }

    /* renamed from: a */
    public static void m70a(long j) {
        C0125A.m8a().mo2353b("pref_last_dau_event_time", j);
    }

    /* renamed from: s */
    public static String m107s() {
        return C0125A.m8a().mo2348a("pref_instance_id", (String) null);
    }

    /* renamed from: g */
    public static void m91g(String str) {
        C0125A.m8a().mo2354b("pref_instance_id", str);
    }

    /* renamed from: t */
    public static String m108t() {
        return C0125A.m8a().mo2348a("pref_main_app_channel", (String) null);
    }

    /* renamed from: h */
    public static void m95h(String str) {
        C0125A.m8a().mo2354b("pref_main_app_channel", str);
    }

    /* renamed from: u */
    public static boolean m109u() {
        return f47F;
    }

    /* renamed from: f */
    public static void m90f(boolean z) {
        f47F = z;
    }

    /* renamed from: v */
    public static long m110v() {
        return C0125A.m8a().mo2347a("pref_instance_id_last_use_time", 0);
    }

    /* renamed from: b */
    public static void m74b(long j) {
        C0125A.m8a().mo2353b("pref_instance_id_last_use_time", j);
    }

    /* renamed from: i */
    public static void m97i(String str) {
        C0125A.m8a().mo2354b("pref_random_uuid", str);
    }

    /* renamed from: w */
    public static String m111w() {
        return C0125A.m8a().mo2348a("pref_random_uuid", (String) null);
    }

    /* renamed from: g */
    public static void m92g(boolean z) {
        C0125A.m8a().mo2355b("pref_system_upload_intl_enabled", z);
    }

    /* renamed from: x */
    public static boolean m112x() {
        return C0125A.m8a().mo2350a("pref_system_upload_intl_enabled", false);
    }
}
