package com.xiaomi.stat;

import java.lang.Thread.UncaughtExceptionHandler;

/* renamed from: com.xiaomi.stat.J */
public class C0132J implements UncaughtExceptionHandler {

    /* renamed from: a */
    private C0186e f14a;

    /* renamed from: b */
    private UncaughtExceptionHandler f15b;

    /* renamed from: c */
    private boolean f16c = true;

    public C0132J(C0186e eVar) {
        this.f14a = eVar;
    }

    /* renamed from: a */
    public void mo2365a() {
        UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (!(defaultUncaughtExceptionHandler instanceof C0132J)) {
            this.f15b = defaultUncaughtExceptionHandler;
            Thread.setDefaultUncaughtExceptionHandler(this);
        }
    }

    /* renamed from: a */
    public void mo2366a(boolean z) {
        this.f16c = z;
    }

    public void uncaughtException(Thread thread, Throwable th) {
        if (this.f16c) {
            this.f14a.mo2437a(th, (String) null, false);
        }
        if (this.f15b != null) {
            this.f15b.uncaughtException(thread, th);
        }
    }
}
