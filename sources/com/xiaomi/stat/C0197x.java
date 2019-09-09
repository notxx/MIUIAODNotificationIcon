package com.xiaomi.stat;

import com.xiaomi.stat.p006a.C0142l;

/* renamed from: com.xiaomi.stat.x */
class C0197x implements Runnable {

    /* renamed from: a */
    final /* synthetic */ boolean f203a;

    /* renamed from: b */
    final /* synthetic */ String f204b;

    /* renamed from: c */
    final /* synthetic */ String f205c;

    /* renamed from: d */
    final /* synthetic */ MiStatParams f206d;

    /* renamed from: e */
    final /* synthetic */ C0186e f207e;

    C0197x(C0186e eVar, boolean z, String str, String str2, MiStatParams miStatParams) {
        this.f207e = eVar;
        this.f203a = z;
        this.f204b = str;
        this.f205c = str2;
        this.f206d = miStatParams;
    }

    public void run() {
        if (C0143b.m73a() && this.f207e.m414g(this.f203a)) {
            this.f207e.m391a(C0142l.m65a(this.f204b, this.f205c, this.f206d, this.f207e.f169b, this.f203a));
        }
    }
}
