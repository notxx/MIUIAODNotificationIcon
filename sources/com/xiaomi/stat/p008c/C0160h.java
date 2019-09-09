package com.xiaomi.stat.p008c;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.xiaomi.stat.p009d.C0178l;
import com.xiaomi.stat.p009d.C0184r;

/* renamed from: com.xiaomi.stat.c.h */
class C0160h extends BroadcastReceiver {

    /* renamed from: a */
    final /* synthetic */ C0159g f106a;

    C0160h(C0159g gVar) {
        this.f106a = gVar;
    }

    public void onReceive(Context context, Intent intent) {
        boolean z = C0184r.m385b() - this.f106a.f104n > ((long) this.f106a.f101k);
        if (C0178l.m328a() && this.f106a.f102l && z) {
            this.f106a.mo2419b();
            this.f106a.f104n = C0184r.m385b();
        }
    }
}
