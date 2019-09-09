package com.xiaomi.stat.p007b;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.stat.C0125A;
import com.xiaomi.stat.C0131I;
import com.xiaomi.stat.C0143b;
import com.xiaomi.stat.p008c.C0155c;
import com.xiaomi.stat.p009d.C0177k;
import com.xiaomi.stat.p009d.C0184r;
import java.util.Map;
import java.util.TreeMap;
import org.json.JSONObject;

/* renamed from: com.xiaomi.stat.b.a */
public class C0144a {

    /* renamed from: c */
    private static int f53c = 0;

    /* renamed from: d */
    private static int f54d = 1;

    /* renamed from: e */
    private static int f55e = 2;

    /* renamed from: q */
    private static volatile C0144a f56q;
    /* access modifiers changed from: private */

    /* renamed from: r */
    public int f57r = 0;

    /* renamed from: s */
    private Context f58s = C0131I.m27a();

    /* renamed from: t */
    private String f59t;
    /* access modifiers changed from: private */

    /* renamed from: u */
    public BroadcastReceiver f60u = new C0145b(this);

    private C0144a() {
    }

    /* renamed from: a */
    public static C0144a m114a() {
        if (f56q == null) {
            synchronized (C0144a.class) {
                if (f56q == null) {
                    f56q = new C0144a();
                }
            }
        }
        return f56q;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00a1, code lost:
        return;
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void mo2389a(boolean r6, boolean r7) {
        /*
            r5 = this;
            monitor-enter(r5)
            boolean r0 = com.xiaomi.stat.C0143b.m73a()     // Catch:{ all -> 0x00ab }
            if (r0 == 0) goto L_0x00a2
            boolean r0 = com.xiaomi.stat.C0143b.m75b()     // Catch:{ all -> 0x00ab }
            if (r0 != 0) goto L_0x000f
            goto L_0x00a2
        L_0x000f:
            boolean r0 = com.xiaomi.stat.p009d.C0178l.m328a()     // Catch:{ all -> 0x00ab }
            r1 = 1
            if (r0 != 0) goto L_0x0032
            java.lang.String r6 = "ConfigManager"
            java.lang.String r7 = "network is not connected!"
            com.xiaomi.stat.p009d.C0177k.m319b(r6, r7)     // Catch:{ all -> 0x00ab }
            r5.f57r = r1     // Catch:{ all -> 0x00ab }
            android.content.IntentFilter r6 = new android.content.IntentFilter     // Catch:{ all -> 0x00ab }
            r6.<init>()     // Catch:{ all -> 0x00ab }
            java.lang.String r7 = "android.net.conn.CONNECTIVITY_CHANGE"
            r6.addAction(r7)     // Catch:{ all -> 0x00ab }
            android.content.Context r7 = r5.f58s     // Catch:{ all -> 0x00ab }
            android.content.BroadcastReceiver r0 = r5.f60u     // Catch:{ all -> 0x00ab }
            r7.registerReceiver(r0, r6)     // Catch:{ all -> 0x00ab }
            monitor-exit(r5)
            return
        L_0x0032:
            java.lang.String r0 = "ConfigManager"
            java.lang.String r2 = "updateConfig"
            com.xiaomi.stat.p009d.C0177k.m319b(r0, r2)     // Catch:{ all -> 0x00ab }
            if (r7 != 0) goto L_0x006c
            java.lang.String r0 = "MI_STAT_TEST"
            java.lang.String r2 = "updateConfig-InToday"
            com.xiaomi.stat.p009d.C0177k.m319b(r0, r2)     // Catch:{ all -> 0x00ab }
            com.xiaomi.stat.A r0 = com.xiaomi.stat.C0125A.m8a()     // Catch:{ all -> 0x00ab }
            java.lang.String r2 = "config_success_requested"
            r3 = 0
            long r2 = r0.mo2347a(r2, r3)     // Catch:{ all -> 0x00ab }
            boolean r0 = com.xiaomi.stat.p009d.C0184r.m386b(r2)     // Catch:{ all -> 0x00ab }
            if (r0 == 0) goto L_0x005d
            java.lang.String r6 = "ConfigManager"
            java.lang.String r7 = "Today has successfully requested key."
            com.xiaomi.stat.p009d.C0177k.m319b(r6, r7)     // Catch:{ all -> 0x00ab }
            monitor-exit(r5)
            return
        L_0x005d:
            boolean r0 = r5.m119c()     // Catch:{ all -> 0x00ab }
            if (r0 == 0) goto L_0x006c
            java.lang.String r6 = "ConfigManager"
            java.lang.String r7 = "config request to max count skip.."
            com.xiaomi.stat.p009d.C0177k.m323d(r6, r7)     // Catch:{ all -> 0x00ab }
            monitor-exit(r5)
            return
        L_0x006c:
            r0 = 0
            if (r6 == 0) goto L_0x009d
            if (r7 == 0) goto L_0x0072
            goto L_0x009d
        L_0x0072:
            java.lang.String r6 = com.xiaomi.stat.C0143b.m99k()     // Catch:{ all -> 0x00ab }
            java.lang.String r7 = "-"
            java.lang.String[] r6 = r6.split(r7)     // Catch:{ all -> 0x00ab }
            int r7 = r6.length     // Catch:{ all -> 0x00ab }
            if (r7 <= r1) goto L_0x0099
            r7 = r6[r0]     // Catch:{ all -> 0x00ab }
            int r7 = java.lang.Integer.parseInt(r7)     // Catch:{ all -> 0x00ab }
            r6 = r6[r1]     // Catch:{ all -> 0x00ab }
            int r6 = java.lang.Integer.parseInt(r6)     // Catch:{ all -> 0x00ab }
            if (r6 <= r7) goto L_0x0099
            java.util.Random r0 = new java.util.Random     // Catch:{ all -> 0x00ab }
            r0.<init>()     // Catch:{ all -> 0x00ab }
            int r6 = r6 - r7
            int r6 = r0.nextInt(r6)     // Catch:{ all -> 0x00ab }
            int r0 = r6 + r7
        L_0x0099:
            r5.m115a(r0)     // Catch:{ all -> 0x00ab }
            goto L_0x00a0
        L_0x009d:
            r5.m115a(r0)     // Catch:{ all -> 0x00ab }
        L_0x00a0:
            monitor-exit(r5)
            return
        L_0x00a2:
            java.lang.String r6 = "ConfigManager"
            java.lang.String r7 = "update abort: statistic or network is not enabled"
            com.xiaomi.stat.p009d.C0177k.m322c(r6, r7)     // Catch:{ all -> 0x00ab }
            monitor-exit(r5)
            return
        L_0x00ab:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.stat.p007b.C0144a.mo2389a(boolean, boolean):void");
    }

    /* renamed from: a */
    private void m115a(int i) {
        if (i > 0) {
            try {
                Thread.sleep((long) (i * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        m118b();
    }

    /* renamed from: b */
    private String m118b() {
        String str;
        C0177k.m319b("ConfigManager", "requestConfigInner");
        this.f59t = C0149f.m133a().mo2401b();
        try {
            TreeMap treeMap = new TreeMap();
            treeMap.put("t", String.valueOf(f55e));
            treeMap.put("ai", C0131I.m29b());
            str = C0155c.m172a(this.f59t, (Map<String, String>) treeMap, false);
            try {
                m116a(str);
            } catch (Exception e) {
                e = e;
            }
        } catch (Exception e2) {
            e = e2;
            str = null;
            C0177k.m320b("ConfigManager", "requestConfigInner exception ", e);
            return str;
        }
        return str;
    }

    /* renamed from: a */
    private void m116a(String str) {
        try {
            C0177k.m319b("ConfigManager", String.format("config result:%s", new Object[]{str}));
            m120d();
            if (!TextUtils.isEmpty(str)) {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.getInt("errorCode") == 0) {
                    long optLong = jSONObject.optLong("time", 0);
                    C0125A.m8a().mo2353b("config_success_requested", optLong);
                    C0143b.m78c(jSONObject.optString("configDelay", "0-0"));
                    C0143b.m77c(jSONObject.optInt("configNetwork", -1));
                    C0143b.m81d(jSONObject.optInt("uploadInterval", 0) / 1000);
                    C0184r.m383a(optLong);
                    C0149f.m133a().mo2399a(jSONObject);
                    if (this.f57r == 1) {
                        this.f58s.unregisterReceiver(this.f60u);
                    }
                    this.f57r = 2;
                }
            }
        } catch (Exception e) {
            C0177k.m326e("ConfigManager", "processResult exception", e);
        }
    }

    /* renamed from: c */
    private boolean m119c() {
        long b = C0184r.m385b();
        C0125A a = C0125A.m8a();
        try {
            if (!C0125A.m8a().mo2349a("config_request_time")) {
                a.mo2353b("config_request_time", b);
                a.mo2352b("config_request_count", 1);
                return false;
            } else if (!C0184r.m386b(a.mo2347a("config_request_time", 0))) {
                a.mo2353b("config_request_time", b);
                a.mo2352b("config_request_count", 0);
                return false;
            } else if (a.mo2346a("config_request_count", 0) >= 12) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            C0177k.m326e("ConfigManager", "isRequestCountReachMax exception", e);
            return false;
        }
    }

    /* renamed from: d */
    private void m120d() {
        try {
            C0125A a = C0125A.m8a();
            a.mo2352b("config_request_count", a.mo2346a("config_request_count", 0) + 1);
        } catch (Exception e) {
            C0177k.m326e("ConfigManager", "addRequestCount exception", e);
        }
    }
}
