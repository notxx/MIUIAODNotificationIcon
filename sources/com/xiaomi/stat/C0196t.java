package com.xiaomi.stat;

import com.xiaomi.stat.p006a.C0142l;

/* renamed from: com.xiaomi.stat.t */
class C0196t implements Runnable {

    /* renamed from: a */
    final /* synthetic */ String f199a;

    /* renamed from: b */
    final /* synthetic */ long f200b;

    /* renamed from: c */
    final /* synthetic */ long f201c;

    /* renamed from: d */
    final /* synthetic */ C0186e f202d;

    C0196t(C0186e eVar, String str, long j, long j2) {
        this.f202d = eVar;
        this.f199a = str;
        this.f200b = j;
        this.f201c = j2;
    }

    public void run() {
        if (C0143b.m73a() && this.f202d.m413g()) {
            this.f202d.m391a(C0142l.m63a(this.f199a, this.f200b, this.f201c));
        }
    }
}
