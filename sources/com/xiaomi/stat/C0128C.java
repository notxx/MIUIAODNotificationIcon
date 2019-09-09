package com.xiaomi.stat;

import android.os.FileObserver;

/* renamed from: com.xiaomi.stat.C */
class C0128C extends FileObserver {

    /* renamed from: a */
    final /* synthetic */ C0125A f7a;

    C0128C(C0125A a, String str) {
        this.f7a = a;
        super(str);
    }

    public void onEvent(int i, String str) {
        if (i == 2) {
            synchronized (this.f7a) {
                this.f7a.m9b();
            }
            C0143b.m102n();
        }
    }
}
