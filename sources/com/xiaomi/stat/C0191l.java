package com.xiaomi.stat;

import android.text.TextUtils;
import com.xiaomi.stat.p007b.C0149f;
import com.xiaomi.stat.p009d.C0180m;

/* renamed from: com.xiaomi.stat.l */
class C0191l implements Runnable {

    /* renamed from: a */
    final /* synthetic */ boolean f187a;

    /* renamed from: b */
    final /* synthetic */ String f188b;

    /* renamed from: c */
    final /* synthetic */ C0186e f189c;

    C0191l(C0186e eVar, boolean z, String str) {
        this.f189c = eVar;
        this.f187a = z;
        this.f188b = str;
    }

    public void run() {
        if (!C0180m.m336a()) {
            C0143b.m79c(this.f187a);
            C0149f.m133a().mo2400a(this.f187a);
        }
        if (C0143b.m87e() && !TextUtils.isEmpty(this.f188b)) {
            C0143b.m71a(this.f188b);
            C0149f.m133a().mo2398a(this.f188b);
        }
    }
}
