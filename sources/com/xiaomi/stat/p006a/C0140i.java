package com.xiaomi.stat.p006a;

import android.database.DatabaseUtils;
import java.util.concurrent.Callable;

/* renamed from: com.xiaomi.stat.a.i */
class C0140i implements Callable<Long> {

    /* renamed from: a */
    final /* synthetic */ C0135c f33a;

    C0140i(C0135c cVar) {
        this.f33a = cVar;
    }

    /* renamed from: a */
    public Long call() {
        return Long.valueOf(DatabaseUtils.queryNumEntries(this.f33a.f24l.getReadableDatabase(), "events"));
    }
}
