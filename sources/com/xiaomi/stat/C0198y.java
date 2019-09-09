package com.xiaomi.stat;

import com.xiaomi.stat.p006a.C0142l;

/* renamed from: com.xiaomi.stat.y */
class C0198y implements Runnable {

    /* renamed from: a */
    final /* synthetic */ Throwable f208a;

    /* renamed from: b */
    final /* synthetic */ String f209b;

    /* renamed from: c */
    final /* synthetic */ boolean f210c;

    /* renamed from: d */
    final /* synthetic */ C0186e f211d;

    C0198y(C0186e eVar, Throwable th, String str, boolean z) {
        this.f211d = eVar;
        this.f208a = th;
        this.f209b = str;
        this.f210c = z;
    }

    public void run() {
        if (C0143b.m73a() && this.f211d.m414g(false)) {
            this.f211d.m391a(C0142l.m66a(this.f208a, this.f209b, this.f210c, this.f211d.f169b));
        }
    }
}
