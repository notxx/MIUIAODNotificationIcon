package com.xiaomi.stat;

import com.xiaomi.stat.p006a.C0135c;
import com.xiaomi.stat.p007b.C0148e;
import com.xiaomi.stat.p007b.C0149f;
import com.xiaomi.stat.p009d.C0167e;

/* renamed from: com.xiaomi.stat.f */
class C0187f implements Runnable {

    /* renamed from: a */
    final /* synthetic */ String f179a;

    /* renamed from: b */
    final /* synthetic */ boolean f180b;

    /* renamed from: c */
    final /* synthetic */ C0186e f181c;

    C0187f(C0186e eVar, String str, boolean z) {
        this.f181c = eVar;
        this.f179a = str;
        this.f180b = z;
    }

    public void run() {
        C0167e.m241a();
        if (this.f181c.f168a) {
            C0143b.m95h(this.f179a);
        }
        C0143b.m80d();
        C0149f.m133a().mo2398a(C0143b.m88f());
        C0143b.m72a(this.f181c.f170c, this.f180b);
        if (!this.f181c.f168a) {
            C0143b.m89f(this.f181c.f169b);
        }
        this.f181c.m411f();
        C0135c.m41a().mo2380b();
        C0148e.m131a().mo2396b().execute(new C0188g(this));
    }
}
