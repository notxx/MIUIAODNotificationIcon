package com.xiaomi.stat;

import com.xiaomi.stat.p006a.C0142l;

/* renamed from: com.xiaomi.stat.o */
class C0192o implements Runnable {

    /* renamed from: a */
    final /* synthetic */ int f190a;

    /* renamed from: b */
    final /* synthetic */ int f191b;

    /* renamed from: c */
    final /* synthetic */ long f192c;

    /* renamed from: d */
    final /* synthetic */ long f193d;

    /* renamed from: e */
    final /* synthetic */ C0186e f194e;

    C0192o(C0186e eVar, int i, int i2, long j, long j2) {
        this.f194e = eVar;
        this.f190a = i;
        this.f191b = i2;
        this.f192c = j;
        this.f193d = j2;
    }

    public void run() {
        if (C0143b.m73a() && this.f194e.m413g()) {
            this.f194e.m391a(C0142l.m61a(this.f190a, this.f191b, this.f192c, this.f193d));
        }
    }
}
