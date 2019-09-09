package com.xiaomi.stat.p008c;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.xiaomi.stat.p007b.C0148e;
import java.util.Map;

/* renamed from: com.xiaomi.stat.c.d */
final class C0156d implements ServiceConnection {

    /* renamed from: a */
    final /* synthetic */ String[] f90a;

    /* renamed from: b */
    final /* synthetic */ String f91b;

    /* renamed from: c */
    final /* synthetic */ Map f92c;

    C0156d(String[] strArr, String str, Map map) {
        this.f90a = strArr;
        this.f91b = str;
        this.f92c = map;
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        C0148e.m131a().mo2396b().execute(new C0157e(this, iBinder));
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:6:0x0011 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onServiceDisconnected(android.content.ComponentName r1) {
        /*
            r0 = this;
            java.lang.String r0 = "UploadMode error while perform IPC connection."
            r1 = 0
            com.xiaomi.stat.p009d.C0177k.m319b(r0, r1)
            java.lang.Class<com.xiaomi.stat.c.i> r0 = com.xiaomi.stat.p008c.C0161i.class
            monitor-enter(r0)
            java.lang.Class<com.xiaomi.stat.c.i> r1 = com.xiaomi.stat.p008c.C0161i.class
            r1.notify()     // Catch:{ Exception -> 0x0011 }
            goto L_0x0011
        L_0x000f:
            r1 = move-exception
            goto L_0x0013
        L_0x0011:
            monitor-exit(r0)     // Catch:{ all -> 0x000f }
            return
        L_0x0013:
            monitor-exit(r0)     // Catch:{ all -> 0x000f }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.stat.p008c.C0156d.onServiceDisconnected(android.content.ComponentName):void");
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:6:0x000b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onBindingDied(android.content.ComponentName r1) {
        /*
            r0 = this;
            java.lang.Class<com.xiaomi.stat.c.i> r0 = com.xiaomi.stat.p008c.C0161i.class
            monitor-enter(r0)
            java.lang.Class<com.xiaomi.stat.c.i> r1 = com.xiaomi.stat.p008c.C0161i.class
            r1.notify()     // Catch:{ Exception -> 0x000b }
            goto L_0x000b
        L_0x0009:
            r1 = move-exception
            goto L_0x000d
        L_0x000b:
            monitor-exit(r0)     // Catch:{ all -> 0x0009 }
            return
        L_0x000d:
            monitor-exit(r0)     // Catch:{ all -> 0x0009 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.stat.p008c.C0156d.onBindingDied(android.content.ComponentName):void");
    }
}
