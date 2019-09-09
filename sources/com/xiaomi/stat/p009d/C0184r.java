package com.xiaomi.stat.p009d;

import android.content.Context;
import android.os.SystemClock;
import com.xiaomi.stat.C0131I;
import com.xiaomi.stat.p007b.C0148e;
import java.util.Calendar;

/* renamed from: com.xiaomi.stat.d.r */
public class C0184r {

    /* renamed from: d */
    private static long f165d;

    /* renamed from: e */
    private static long f166e;

    /* renamed from: f */
    private static long f167f;

    /* renamed from: a */
    public static void m382a() {
        boolean z;
        Context a = C0131I.m27a();
        long f = C0183p.m374f(a);
        long g = C0183p.m376g(a);
        long h = C0183p.m377h(a);
        if (f == 0 || g == 0 || h == 0 || Math.abs((System.currentTimeMillis() - g) - (SystemClock.elapsedRealtime() - h)) > 300000) {
            z = true;
        } else {
            f165d = f;
            f167f = g;
            f166e = h;
            z = false;
        }
        if (z) {
            C0148e.m131a().mo2396b().execute(new C0185s());
        }
        StringBuilder sb = new StringBuilder();
        sb.append("syncTimeIfNeeded sync: ");
        sb.append(z);
        C0177k.m319b("TimeUtil", sb.toString());
    }

    /* renamed from: b */
    public static long m385b() {
        if (f165d == 0 || f166e == 0) {
            return System.currentTimeMillis();
        }
        return (f165d + SystemClock.elapsedRealtime()) - f166e;
    }

    /* renamed from: a */
    public static void m383a(long j) {
        if (j > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("update server time:");
            sb.append(j);
            C0177k.m319b("MI_STAT_TEST", sb.toString());
            f165d = j;
            f166e = SystemClock.elapsedRealtime();
            f167f = System.currentTimeMillis();
            Context a = C0131I.m27a();
            C0183p.m356a(a, f165d);
            C0183p.m361b(a, f167f);
            C0183p.m368c(a, f166e);
        }
    }

    /* renamed from: a */
    public static boolean m384a(long j, long j2) {
        return Math.abs(m385b() - j) >= j2;
    }

    /* renamed from: b */
    public static boolean m386b(long j) {
        StringBuilder sb = new StringBuilder();
        sb.append("inToday,current ts :");
        sb.append(m385b());
        C0177k.m319b("MI_STAT_TEST", sb.toString());
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(m385b());
        instance.set(11, 0);
        instance.set(12, 0);
        instance.set(13, 0);
        instance.set(14, 0);
        long timeInMillis = instance.getTimeInMillis();
        long j2 = timeInMillis + 86400000;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("[start]:");
        sb2.append(timeInMillis);
        sb2.append("\n[end]:");
        sb2.append(j2);
        sb2.append("duration");
        sb2.append((j2 - timeInMillis) - 86400000);
        C0177k.m319b("MI_STAT_TEST", sb2.toString());
        String str = "MI_STAT_TEST";
        StringBuilder sb3 = new StringBuilder();
        sb3.append("is in today:");
        int i = (timeInMillis > j ? 1 : (timeInMillis == j ? 0 : -1));
        sb3.append(i <= 0 && j < j2);
        C0177k.m319b(str, sb3.toString());
        if (i > 0 || j >= j2) {
            return false;
        }
        return true;
    }
}
