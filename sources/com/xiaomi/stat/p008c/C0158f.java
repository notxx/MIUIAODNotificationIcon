package com.xiaomi.stat.p008c;

import com.xiaomi.stat.C0131I;
import com.xiaomi.stat.C0143b;
import com.xiaomi.stat.p009d.C0177k;
import com.xiaomi.stat.p009d.C0178l;
import com.xiaomi.stat.p009d.C0180m;

/* renamed from: com.xiaomi.stat.c.f */
public class C0158f {

    /* renamed from: d */
    boolean f95d;

    /* renamed from: f */
    private String f96f;

    /* renamed from: a */
    private boolean m175a(int i) {
        return (i & -32) == 0;
    }

    public C0158f(boolean z) {
        this.f95d = z;
        this.f96f = C0131I.m29b();
    }

    public C0158f(String str, boolean z) {
        this.f95d = z;
        this.f96f = str;
    }

    /* renamed from: b */
    private int m176b() {
        boolean b = C0180m.m338b(C0131I.m27a());
        StringBuilder sb = new StringBuilder();
        sb.append("UploadPolicy getExperiencePlanPolicy: ");
        sb.append(b);
        sb.append(" isInternationalVersion= ");
        sb.append(C0143b.m87e());
        sb.append(" isAnonymous= ");
        sb.append(this.f95d);
        C0177k.m318b(sb.toString());
        if (b) {
            return 3;
        }
        if (!C0143b.m87e() || !this.f95d) {
            return 2;
        }
        return 3;
    }

    /* renamed from: c */
    private int m177c() {
        int e = C0143b.m84e(this.f96f);
        StringBuilder sb = new StringBuilder();
        sb.append("UploadPolicy getCustomPrivacyPolicy: state=");
        sb.append(e);
        C0177k.m318b(sb.toString());
        return e == 1 ? 3 : 1;
    }

    /* renamed from: d */
    private int m178d() {
        if (C0143b.m83d(this.f96f)) {
            return m177c();
        }
        return m176b();
    }

    /* renamed from: e */
    private int m179e() {
        int a = C0178l.m327a(C0131I.m27a());
        int l = m175a(C0143b.m100l()) ? C0143b.m100l() : C0143b.m96i();
        StringBuilder sb = new StringBuilder();
        sb.append("UploadPolicy getHttpServicePolicy: currentNet= ");
        sb.append(a);
        sb.append(" Config.getServerNetworkType= ");
        sb.append(C0143b.m100l());
        sb.append(" Config.getUserNetworkType()= ");
        sb.append(C0143b.m96i());
        sb.append(" (configNet & currentNet) == currentNet ");
        int i = l & a;
        sb.append(i == a);
        C0177k.m318b(sb.toString());
        if (i == a) {
            return 3;
        }
        return 1;
    }

    /* renamed from: f */
    private int m180f() {
        return C0178l.m327a(C0131I.m27a()) == 8 ? 3 : 1;
    }

    /* renamed from: g */
    private int m181g() {
        if (!C0143b.m109u() || !C0155c.m173a()) {
            return m179e();
        }
        return m180f();
    }

    /* renamed from: a */
    public int mo2416a() {
        return Math.min(m178d(), m181g());
    }
}
