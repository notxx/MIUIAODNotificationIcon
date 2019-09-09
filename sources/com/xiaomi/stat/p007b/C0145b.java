package com.xiaomi.stat.p007b;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.xiaomi.stat.p009d.C0177k;
import com.xiaomi.stat.p009d.C0178l;

/* renamed from: com.xiaomi.stat.b.b */
class C0145b extends BroadcastReceiver {

    /* renamed from: a */
    final /* synthetic */ C0144a f61a;

    C0145b(C0144a aVar) {
        this.f61a = aVar;
    }

    public void onReceive(Context context, Intent intent) {
        try {
            if (this.f61a.f57r != 1) {
                context.unregisterReceiver(this.f61a.f60u);
                return;
            }
            if (C0178l.m328a()) {
                C0148e.m131a().mo2396b().execute(new C0146c(this));
            }
        } catch (Exception e) {
            C0177k.m326e("ConfigManager", "mNetReceiver exception", e);
        }
    }
}
