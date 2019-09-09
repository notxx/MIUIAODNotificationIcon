package com.xiaomi.stat.p007b;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* renamed from: com.xiaomi.stat.b.e */
public class C0148e {

    /* renamed from: a */
    private static final Object f67a = new Object();

    /* renamed from: b */
    private static volatile C0148e f68b;

    /* renamed from: c */
    private static volatile ExecutorService f69c;

    private C0148e() {
    }

    /* renamed from: a */
    public static C0148e m131a() {
        if (f68b == null) {
            synchronized (f67a) {
                if (f68b == null) {
                    f68b = new C0148e();
                }
            }
        }
        return f68b;
    }

    /* renamed from: b */
    public synchronized ExecutorService mo2396b() {
        if (f69c == null) {
            f69c = Executors.newCachedThreadPool();
        }
        return f69c;
    }
}
