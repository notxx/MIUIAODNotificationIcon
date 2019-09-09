package com.xiaomi.stat.p007b;

import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.xiaomi.stat.C0125A;
import com.xiaomi.stat.C0131I;
import com.xiaomi.stat.p008c.C0155c;
import com.xiaomi.stat.p009d.C0163a;
import com.xiaomi.stat.p009d.C0164b;
import com.xiaomi.stat.p009d.C0166d;
import com.xiaomi.stat.p009d.C0173g;
import com.xiaomi.stat.p009d.C0177k;
import com.xiaomi.stat.p009d.C0182o;
import com.xiaomi.stat.p009d.C0184r;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.xiaomi.stat.b.h */
public class C0151h {

    /* renamed from: r */
    private static volatile C0151h f78r;

    /* renamed from: s */
    private Context f79s = C0131I.m27a();

    /* renamed from: t */
    private byte[] f80t;

    /* renamed from: u */
    private byte[] f81u;

    /* renamed from: v */
    private String f82v;

    private C0151h() {
        m150d();
    }

    /* renamed from: a */
    public static C0151h m148a() {
        if (f78r == null) {
            synchronized (C0151h.class) {
                if (f78r == null) {
                    f78r = new C0151h();
                }
            }
        }
        return f78r;
    }

    /* renamed from: d */
    private void m150d() {
        this.f81u = C0163a.m223a();
        if (this.f81u == null || this.f81u.length <= 0) {
            this.f81u = C0163a.m224a("050f03d86eeafeb29cf38986462d957c");
        }
        String concat = C0173g.m288a(this.f81u, true).concat("_").concat(String.valueOf(C0184r.m385b()));
        try {
            concat = C0173g.m288a(concat.getBytes("utf-8"), true);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        this.f80t = C0163a.m225a(C0163a.m224a(concat), "4ef8b4ac42dbc3f95320b73ae0edbd43");
    }

    /* renamed from: a */
    public synchronized byte[] mo2408a(byte[] bArr) {
        if (bArr == null) {
            C0177k.m319b("SecretKeyManager", "encrypt content is empty");
            return null;
        }
        return C0163a.m225a(bArr, m151e());
    }

    /* renamed from: e */
    private String m151e() {
        CharSequence charSequence;
        String str = null;
        if (VERSION.SDK_INT >= 18) {
            JSONObject g = m153g();
            if (g != null) {
                str = g.optString("sk");
                charSequence = g.optString("sid");
                return (!TextUtils.isEmpty(str) || TextUtils.isEmpty(charSequence)) ? C0173g.m288a(this.f81u, true) : str;
            }
        }
        charSequence = null;
        if (!TextUtils.isEmpty(str)) {
        }
    }

    /* renamed from: b */
    public synchronized String mo2409b() {
        String str;
        CharSequence charSequence;
        str = null;
        if (VERSION.SDK_INT >= 18) {
            JSONObject g = m153g();
            if (g != null) {
                str = g.optString("sid");
                charSequence = g.optString("sk");
                if (TextUtils.isEmpty(str) || TextUtils.isEmpty(charSequence)) {
                    str = C0173g.m288a(this.f80t, true);
                }
            }
        }
        charSequence = null;
        str = C0173g.m288a(this.f80t, true);
        return str;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0036, code lost:
        return;
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void mo2407a(boolean r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            boolean r0 = com.xiaomi.stat.C0143b.m73a()     // Catch:{ all -> 0x0040 }
            if (r0 == 0) goto L_0x0037
            boolean r0 = com.xiaomi.stat.C0143b.m75b()     // Catch:{ all -> 0x0040 }
            if (r0 != 0) goto L_0x000e
            goto L_0x0037
        L_0x000e:
            boolean r0 = com.xiaomi.stat.p009d.C0178l.m328a()     // Catch:{ all -> 0x0040 }
            if (r0 == 0) goto L_0x002e
            r0 = 3
            r1 = 1
        L_0x0016:
            if (r1 > r0) goto L_0x0035
            boolean r2 = r4.m149b(r5)     // Catch:{ all -> 0x0040 }
            if (r2 == 0) goto L_0x0035
            if (r1 != r0) goto L_0x0021
            goto L_0x0035
        L_0x0021:
            r2 = 10000(0x2710, double:4.9407E-320)
            java.lang.Thread.sleep(r2)     // Catch:{ InterruptedException -> 0x0027 }
            goto L_0x002b
        L_0x0027:
            r2 = move-exception
            r2.printStackTrace()     // Catch:{ all -> 0x0040 }
        L_0x002b:
            int r1 = r1 + 1
            goto L_0x0016
        L_0x002e:
            java.lang.String r5 = "SecretKeyManager"
            java.lang.String r0 = "network not connected!"
            com.xiaomi.stat.p009d.C0177k.m319b(r5, r0)     // Catch:{ all -> 0x0040 }
        L_0x0035:
            monitor-exit(r4)
            return
        L_0x0037:
            java.lang.String r5 = "SecretKeyManager"
            java.lang.String r0 = "update abort: statistic or network is not enabled"
            com.xiaomi.stat.p009d.C0177k.m322c(r5, r0)     // Catch:{ all -> 0x0040 }
            monitor-exit(r4)
            return
        L_0x0040:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.stat.p007b.C0151h.mo2407a(boolean):void");
    }

    /* renamed from: b */
    private boolean m149b(boolean z) {
        if (VERSION.SDK_INT < 18) {
            C0177k.m319b("SecretKeyManager", "under 4.3,use randomly generated key");
            return false;
        }
        if (m156j()) {
            m157k();
        }
        JSONObject g = m153g();
        if (g != null) {
            String optString = g.optString("sid");
            if (!TextUtils.isEmpty(g.optString("sk")) && !TextUtils.isEmpty(optString) && !z) {
                C0177k.m319b("SecretKeyManager", "key and sid already requested successfully in recent 7 days!");
                return false;
            }
        }
        JSONObject h = m154h();
        long optLong = h.optLong("rt");
        int optInt = h.optInt("rc");
        if (!C0184r.m386b(optLong) || optInt < 15 || z) {
            return m152f();
        }
        C0177k.m319b("SecretKeyManager", "request count > max count today, skip...");
        return false;
    }

    /* renamed from: f */
    private boolean m152f() {
        boolean z = false;
        try {
            byte[] a = C0163a.m223a();
            String a2 = C0166d.m234a(C0182o.m352a(C0166d.m235a("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCA1ynlvPE46RxIPx6qrb8f20DU\n\rkAJgwHtD3zCEkgOjcvFY2mLl0UGnK1F0Vsh4LvImSCa8o8qYYfBguROgIXRdJGZ+\n\rk9stSV7vWmcsxphMfHEE9R4q+QWqgPBSzwyWmwmAQ7PZmHifOrEYl9t/l0YtmjnW\n\r8Zs3aL7Ap9CGse2kWwIDAQAB\r"), a));
            m155i();
            HashMap hashMap = new HashMap();
            hashMap.put("skey_rsa", a2);
            String a3 = C0155c.m172a(C0149f.m133a().mo2404d(), (Map<String, String>) hashMap, false);
            if (!TextUtils.isEmpty(a3)) {
                StringBuilder sb = new StringBuilder();
                sb.append("result:");
                sb.append(a3);
                C0177k.m319b("SecretKeyManager", sb.toString());
                JSONObject jSONObject = new JSONObject(a3);
                String optString = jSONObject.optString("msg");
                int optInt = jSONObject.optInt("code");
                long optLong = jSONObject.optLong("curTime");
                JSONObject optJSONObject = jSONObject.optJSONObject("result");
                if (optInt == 1 && optJSONObject != null) {
                    try {
                        String optString2 = optJSONObject.optString("sid");
                        String a4 = C0163a.m222a(optJSONObject.optString("key"), a);
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("sk", a4);
                        jSONObject2.put("sid", optString2);
                        this.f82v = jSONObject2.toString();
                        C0125A.m8a().mo2354b("last_aes_content", C0164b.m226a(this.f79s, jSONObject2.toString()));
                        C0125A.m8a().mo2353b("last_success_time", optLong);
                        C0184r.m383a(optLong);
                        return false;
                    } catch (Exception e) {
                        e = e;
                    }
                } else if (optInt == 2) {
                    String str = "SecretKeyManager";
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("update secret-key failed: ");
                    sb2.append(optString);
                    C0177k.m319b(str, sb2.toString());
                }
            }
            return true;
        } catch (Exception e2) {
            e = e2;
            z = true;
            C0177k.m326e("SecretKeyManager", "updateSecretKey e", e);
            return z;
        }
    }

    /* renamed from: g */
    private JSONObject m153g() {
        String str;
        String a = C0125A.m8a().mo2348a("last_aes_content", "");
        try {
            if (!TextUtils.isEmpty(a)) {
                if (!TextUtils.isEmpty(this.f82v)) {
                    str = this.f82v;
                } else {
                    String b = C0164b.m230b(this.f79s, a);
                    this.f82v = b;
                    str = b;
                }
                return new JSONObject(str);
            }
        } catch (Exception e) {
            C0177k.m326e("SecretKeyManager", "decodeFromAndroidKeyStore e", e);
        }
        return null;
    }

    /* renamed from: h */
    private JSONObject m154h() {
        try {
            String a = C0125A.m8a().mo2348a("request_history", "");
            if (!TextUtils.isEmpty(a)) {
                return new JSONObject(a);
            }
        } catch (Exception e) {
            C0177k.m326e("SecretKeyManager", "getRequestHistory e", e);
        }
        return new JSONObject();
    }

    /* renamed from: i */
    private void m155i() {
        try {
            JSONObject h = m154h();
            long optLong = h.optLong("rt");
            int optInt = h.optInt("rc");
            if (C0184r.m386b(optLong)) {
                h.put("rc", optInt + 1);
            } else {
                h.put("rc", 1);
            }
            h.put("rt", C0184r.m385b());
            C0125A.m8a().mo2354b("request_history", h.toString());
        } catch (JSONException e) {
            C0177k.m320b("SecretKeyManager", "updateSecretKey e", e);
        }
    }

    /* renamed from: j */
    private boolean m156j() {
        long a = C0125A.m8a().mo2347a("last_success_time", 0);
        return a != 0 && C0184r.m384a(a, 604800000);
    }

    /* renamed from: k */
    private void m157k() {
        C0125A a = C0125A.m8a();
        this.f82v = null;
        a.mo2351b("last_aes_content");
        a.mo2351b("last_success_time");
    }

    /* renamed from: l */
    private synchronized boolean m158l() {
        boolean z;
        JSONObject g = m153g();
        z = true;
        if (g != null) {
            String optString = g.optString("sk");
            String optString2 = g.optString("sid");
            if (!TextUtils.isEmpty(optString) && !TextUtils.isEmpty(optString2)) {
                z = false;
            }
        }
        return z;
    }

    /* renamed from: c */
    public String mo2410c() {
        return (VERSION.SDK_INT < 18 || m158l()) ? "1" : "0";
    }
}
