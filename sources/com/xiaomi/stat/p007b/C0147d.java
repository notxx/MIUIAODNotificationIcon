package com.xiaomi.stat.p007b;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.stat.C0125A;
import com.xiaomi.stat.C0131I;
import com.xiaomi.stat.C0143b;
import com.xiaomi.stat.p008c.C0155c;
import com.xiaomi.stat.p009d.C0166d;
import com.xiaomi.stat.p009d.C0167e;
import com.xiaomi.stat.p009d.C0173g;
import com.xiaomi.stat.p009d.C0177k;
import com.xiaomi.stat.p009d.C0178l;
import com.xiaomi.stat.p009d.C0184r;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.xiaomi.stat.b.d */
public class C0147d {

    /* renamed from: a */
    private static final Object f63a = new Object();

    /* renamed from: u */
    private static C0147d f64u;

    /* renamed from: v */
    private String f65v = C0125A.m8a().mo2348a("pref_key_device_id", "");

    /* renamed from: w */
    private Context f66w = C0131I.m27a();

    private C0147d() {
    }

    /* renamed from: a */
    public static C0147d m122a() {
        if (f64u == null) {
            synchronized (f63a) {
                if (f64u == null) {
                    f64u = new C0147d();
                }
            }
        }
        return f64u;
    }

    /* renamed from: b */
    public synchronized String mo2393b() {
        if (C0143b.m87e()) {
            this.f65v = C0167e.m249d();
        } else if (!mo2395d()) {
            m123e();
        }
        return this.f65v;
    }

    /* renamed from: c */
    public String mo2394c() {
        return this.f65v;
    }

    /* renamed from: a */
    public String mo2392a(boolean z) {
        if (z) {
            return C0167e.m249d();
        }
        String r = C0167e.m270r(C0131I.m27a());
        return !TextUtils.isEmpty(r) ? r : C0167e.m249d();
    }

    /* renamed from: e */
    private void m123e() {
        if (!C0143b.m73a() || !C0143b.m75b()) {
            C0177k.m322c("DeviceIdManager", "request abort: statistic or network is not enabled");
            return;
        }
        if (C0178l.m328a()) {
            int i = 1;
            while (i <= 3 && m124f() && i != 3) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }
        } else {
            C0177k.m319b("DeviceIdManager", "network is not connected!");
        }
    }

    /* renamed from: f */
    private boolean m124f() {
        try {
            String a = C0155c.m172a(" http://data.mistat.xiaomi.com/idservice/deviceid_get", (Map<String, String>) m126h(), true);
            C0177k.m319b("DeviceIdManager", a);
            if (!TextUtils.isEmpty(a)) {
                JSONObject jSONObject = new JSONObject(a);
                long optLong = jSONObject.optLong("timestamp");
                int optInt = jSONObject.optInt("code");
                String optString = jSONObject.optString("device_id");
                if (optInt == 1) {
                    this.f65v = optString;
                    C0125A a2 = C0125A.m8a();
                    if (!TextUtils.isEmpty(this.f65v)) {
                        a2.mo2354b("pref_key_device_id", optString);
                        a2.mo2353b("pref_key_restore_ts", optLong);
                    }
                    C0184r.m383a(optLong);
                    return false;
                }
            }
        } catch (IOException e) {
            C0177k.m320b("DeviceIdManager", "[getDeviceIdLocal IOException]:", e);
        } catch (JSONException e2) {
            C0177k.m320b("DeviceIdManager", "[getDeviceIdLocal JSONException]:", e2);
        }
        return true;
    }

    /* renamed from: d */
    public boolean mo2395d() {
        String a = C0125A.m8a().mo2348a("pref_key_device_id", (String) null);
        return !TextUtils.isEmpty(a) && !TextUtils.isEmpty(this.f65v) && this.f65v.equals(a);
    }

    /* renamed from: g */
    private String[] m125g() {
        return new String[]{C0167e.m244b(this.f66w), C0167e.m253e(this.f66w), C0167e.m258h(this.f66w), C0167e.m263k(this.f66w), C0167e.m266n(this.f66w), C0167e.m269q(this.f66w), C0167e.m268p(this.f66w)};
    }

    /* renamed from: h */
    private HashMap<String, String> m126h() {
        HashMap<String, String> hashMap = new HashMap<>();
        String[] g = m125g();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("ia", g[0]);
            jSONObject.put("ib", g[1]);
            jSONObject.put("md", g[2]);
            jSONObject.put("mm", g[3]);
            jSONObject.put("bm", g[4]);
            jSONObject.put("aa", g[5]);
            jSONObject.put("ai", g[6]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[pay-load]:");
        sb.append(jSONObject.toString());
        C0177k.m319b("DeviceIdManager", sb.toString());
        byte[] bArr = new byte[0];
        try {
            bArr = C0151h.m148a().mo2408a(jSONObject.toString().getBytes("utf-8"));
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
        String str = null;
        if (bArr != null) {
            str = C0166d.m234a(C0173g.m288a(bArr, true).getBytes());
        }
        hashMap.put("sv", "3.0.8");
        String str2 = "p";
        if (str == null) {
            str = "";
        }
        hashMap.put(str2, str);
        hashMap.put("ai", C0131I.m29b());
        hashMap.put("gzip", "0");
        hashMap.put("fc", C0151h.m148a().mo2410c());
        hashMap.put("sid", C0151h.m148a().mo2409b());
        return hashMap;
    }
}
