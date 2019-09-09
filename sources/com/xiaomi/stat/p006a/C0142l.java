package com.xiaomi.stat.p006a;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.stat.C0130H;
import com.xiaomi.stat.C0131I;
import com.xiaomi.stat.C0143b;
import com.xiaomi.stat.MiStatParams;
import com.xiaomi.stat.p009d.C0167e;
import com.xiaomi.stat.p009d.C0184r;
import java.io.PrintWriter;
import java.io.StringWriter;

/* renamed from: com.xiaomi.stat.a.l */
public class C0142l {

    /* renamed from: a */
    public String f37a;

    /* renamed from: b */
    public String f38b;

    /* renamed from: c */
    public String f39c;

    /* renamed from: d */
    public C0130H f40d;

    /* renamed from: e */
    public long f41e;

    /* renamed from: f */
    public String f42f;

    /* renamed from: g */
    public boolean f43g;

    private C0142l() {
    }

    /* renamed from: a */
    public static C0142l m59a() {
        C0142l lVar = new C0142l();
        lVar.f37a = "mistat_dau";
        lVar.f38b = "mistat_basic";
        lVar.f39c = "track";
        lVar.f41e = C0184r.m385b();
        C0130H h = new C0130H();
        boolean q = C0143b.m105q();
        if (q) {
            C0143b.m86e(false);
        }
        h.putInt("fo", q ? 1 : 0);
        Context a = C0131I.m27a();
        h.putString("ia", C0167e.m244b(a));
        h.putString("i1", C0167e.m247c(a));
        h.putString("ib", C0167e.m253e(a));
        h.putString("i2", C0167e.m254f(a));
        h.putString("md", C0167e.m258h(a));
        h.putString("ms", C0167e.m260i(a));
        h.putString("ii", C0167e.m249d());
        h.putString("mcm", C0167e.m263k(a));
        h.putString("mcs", C0167e.m264l(a));
        h.putString("bm", C0167e.m266n(a));
        h.putString("bs", C0167e.m267o(a));
        h.putString("aa", C0167e.m269q(a));
        h.putString("ai", C0167e.m268p(a));
        h.putString("od", C0167e.m274x(a));
        lVar.f40d = h;
        m67a(lVar);
        return lVar;
    }

    /* renamed from: a */
    private static void m67a(C0142l lVar) {
        if (C0143b.m87e()) {
            lVar.f43g = true;
        } else {
            lVar.f43g = false;
        }
    }

    /* renamed from: a */
    private static void m68a(C0142l lVar, String str) {
        if (!TextUtils.isEmpty(str)) {
            lVar.f42f = str;
            lVar.f40d.putString("mi_sai", str);
        }
    }

    /* renamed from: a */
    public static C0142l m63a(String str, long j, long j2) {
        return m64a(str, j, j2, true, null, null);
    }

    /* renamed from: a */
    private static C0142l m64a(String str, long j, long j2, boolean z, MiStatParams miStatParams, String str2) {
        C0142l lVar = new C0142l();
        lVar.f37a = "mistat_pa";
        lVar.f38b = z ? "mistat_basic" : "mistat_user_page";
        lVar.f39c = "track";
        lVar.f41e = C0184r.m385b();
        C0130H h = new C0130H(miStatParams);
        h.putString("pg", str);
        h.putLong("bt", j);
        h.putLong("et", j2);
        lVar.f40d = h;
        m67a(lVar);
        if (!z) {
            m68a(lVar, str2);
        }
        return lVar;
    }

    /* renamed from: a */
    public static C0142l m65a(String str, String str2, MiStatParams miStatParams, String str3, boolean z) {
        C0142l lVar = new C0142l();
        lVar.f37a = str;
        lVar.f38b = str2;
        lVar.f39c = "track";
        lVar.f41e = C0184r.m385b();
        lVar.f40d = new C0130H(miStatParams);
        if (C0143b.m87e()) {
            lVar.f43g = !z;
        } else {
            lVar.f43g = false;
        }
        m68a(lVar, str3);
        return lVar;
    }

    /* renamed from: a */
    public static C0142l m66a(Throwable th, String str, boolean z, String str2) {
        C0142l lVar = new C0142l();
        lVar.f37a = "mistat_app_exception";
        lVar.f38b = "mistat_crash";
        lVar.f39c = "track";
        lVar.f41e = C0184r.m385b();
        C0130H h = new C0130H();
        lVar.f40d = h;
        h.putString("ek", str);
        h.putInt("et", z ? 1 : 0);
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        h.putString("sk", stringWriter.toString());
        h.putString("em", th.getMessage());
        m67a(lVar);
        m68a(lVar, str2);
        return lVar;
    }

    /* renamed from: a */
    public static C0142l m60a(int i) {
        C0142l lVar = new C0142l();
        lVar.f37a = "mistat_app_update";
        lVar.f38b = "mistat_basic";
        lVar.f39c = "track";
        lVar.f41e = C0184r.m385b();
        C0130H h = new C0130H();
        h.putInt("pvr", i);
        lVar.f40d = h;
        m67a(lVar);
        return lVar;
    }

    /* renamed from: a */
    public static C0142l m62a(C0130H h) {
        C0142l lVar = new C0142l();
        lVar.f37a = "mistat_delete_event";
        lVar.f38b = "mistat_basic";
        lVar.f39c = "track";
        lVar.f41e = C0184r.m385b();
        lVar.f40d = h;
        m67a(lVar);
        return lVar;
    }

    /* renamed from: a */
    public static C0142l m61a(int i, int i2, long j, long j2) {
        C0142l lVar = new C0142l();
        lVar.f37a = "mistat_page_monitor";
        lVar.f38b = "mistat_basic";
        lVar.f39c = "track";
        lVar.f41e = C0184r.m385b();
        C0130H h = new C0130H();
        h.putInt("rc", i);
        h.putInt("pc", i2);
        h.putLong("sts", j);
        h.putLong("ets", j2);
        lVar.f40d = h;
        m67a(lVar);
        return lVar;
    }
}
