package com.xiaomi.stat;

import com.xiaomi.stat.p006a.C0142l;
import com.xiaomi.stat.p009d.C0184r;

/* renamed from: com.xiaomi.stat.k */
class C0190k implements Runnable {

    /* renamed from: a */
    final /* synthetic */ C0186e f186a;

    C0190k(C0186e eVar) {
        this.f186a = eVar;
    }

    public void run() {
        if (C0143b.m73a() && this.f186a.m413g()) {
            long b = C0184r.m385b();
            if (this.f186a.m397a(C0143b.m106r(), b)) {
                C0143b.m70a(b);
                this.f186a.m391a(C0142l.m59a());
            }
        }
    }
}
