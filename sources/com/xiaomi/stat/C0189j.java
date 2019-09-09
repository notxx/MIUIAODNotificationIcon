package com.xiaomi.stat;

import com.xiaomi.stat.p006a.C0142l;

/* renamed from: com.xiaomi.stat.j */
class C0189j implements Runnable {

    /* renamed from: a */
    final /* synthetic */ int f183a;

    /* renamed from: b */
    final /* synthetic */ int f184b;

    /* renamed from: c */
    final /* synthetic */ C0186e f185c;

    C0189j(C0186e eVar, int i, int i2) {
        this.f185c = eVar;
        this.f183a = i;
        this.f184b = i2;
    }

    public void run() {
        if (C0143b.m73a() && this.f185c.m413g()) {
            C0143b.m85e(this.f183a);
            this.f185c.m391a(C0142l.m60a(this.f184b));
        }
    }
}
