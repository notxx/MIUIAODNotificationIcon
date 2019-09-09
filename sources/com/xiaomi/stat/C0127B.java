package com.xiaomi.stat;

import android.database.Cursor;
import java.util.concurrent.Callable;

/* renamed from: com.xiaomi.stat.B */
class C0127B implements Callable<Cursor> {

    /* renamed from: a */
    final /* synthetic */ C0125A f6a;

    C0127B(C0125A a) {
        this.f6a = a;
    }

    /* renamed from: a */
    public Cursor call() throws Exception {
        try {
            return this.f6a.f5f.getWritableDatabase().query("pref", null, null, null, null, null, null);
        } catch (Exception unused) {
            return null;
        }
    }
}
