package com.xiaomi.stat.p007b;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Looper;
import android.text.TextUtils;
import com.xiaomi.stat.C0125A;
import com.xiaomi.stat.C0131I;
import com.xiaomi.stat.C0143b;
import com.xiaomi.stat.p009d.C0174h;
import com.xiaomi.stat.p009d.C0177k;
import com.xiaomi.stat.p009d.C0180m;
import java.util.HashMap;
import org.json.JSONObject;

/* renamed from: com.xiaomi.stat.b.f */
public class C0149f {

    /* renamed from: b */
    private static final Object f70b = new Object();

    /* renamed from: l */
    private static HashMap<String, String> f71l = new HashMap<>();

    /* renamed from: r */
    private static C0149f f72r;

    /* renamed from: m */
    private String f73m = "CN";

    /* renamed from: n */
    private String f74n = "data.mistat.xiaomi.com";

    /* renamed from: o */
    private String f75o = null;

    /* renamed from: p */
    private String f76p;

    /* renamed from: q */
    private C0150g f77q;

    static {
        f71l.put("CN", "data.mistat.xiaomi.com");
        f71l.put("INTL", "data.mistat.intl.xiaomi.com");
        f71l.put("IN", "data.mistat.india.xiaomi.com");
    }

    private C0149f() {
        mo2397a(C0131I.m27a());
    }

    /* renamed from: a */
    public static C0149f m133a() {
        if (f72r == null) {
            synchronized (f70b) {
                if (f72r == null) {
                    f72r = new C0149f();
                }
            }
        }
        return f72r;
    }

    /* access modifiers changed from: protected */
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Cannot clone instance of this class");
    }

    /* renamed from: e */
    private static void m137e() {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            C0177k.m326e("RDM", "can not init in main thread!", null);
        }
    }

    /* renamed from: a */
    public void mo2397a(Context context) {
        m137e();
        this.f77q = new C0150g();
        this.f76p = context.getFilesDir().getPath();
        if (!C0143b.m87e()) {
            this.f73m = "CN";
            this.f74n = "data.mistat.xiaomi.com";
        } else {
            String g = C0180m.m342g();
            StringBuilder sb = new StringBuilder();
            sb.append("[SystemRegion]:");
            sb.append(g);
            C0177k.m319b("RDM", sb.toString());
            String a = C0125A.m8a().mo2348a("region", (String) null);
            if (!TextUtils.isEmpty(g)) {
                this.f73m = g;
            }
            if (!TextUtils.isEmpty(a)) {
                this.f73m = a;
            }
            m138f();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("[file-dir]:");
        sb2.append(this.f76p);
        sb2.append("\n[CurrentRegion]:");
        sb2.append(this.f73m);
        sb2.append("\n[domain]:");
        sb2.append(this.f74n);
        C0177k.m319b("RDM", sb2.toString());
    }

    /* renamed from: f */
    private void m138f() {
        f71l = this.f77q.mo2406a(f71l, (HashMap) C0174h.m300a(this.f76p.concat("/map_domain")));
        String str = (String) f71l.get(this.f73m);
        if (!TextUtils.isEmpty(str)) {
            this.f74n = str;
        } else if (C0143b.m87e()) {
            this.f73m = "INTL";
            this.f74n = (String) f71l.get(this.f73m);
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: b */
    public String mo2401b() {
        return m134b("get_all_config");
    }

    /* renamed from: c */
    public String mo2402c() {
        return m134b("mistats/v3");
    }

    /* renamed from: d */
    public String mo2404d() {
        return m134b("key_get");
    }

    /* renamed from: b */
    private String m134b(String str) {
        String str2 = C0143b.m87e() ? "https://" : (VERSION.SDK_INT < 28 || C0131I.m27a().getApplicationInfo().targetSdkVersion < 28) ? "http://" : "https://";
        return str2.concat(this.f74n).concat("/").concat(str);
    }

    /* renamed from: a */
    public void mo2398a(String str) {
        m136d(str);
        m135c(str);
    }

    /* renamed from: a */
    public void mo2400a(boolean z) {
        if (z) {
            this.f73m = "INTL";
            this.f74n = "data.mistat.intl.xiaomi.com";
            String str = TextUtils.isEmpty(this.f75o) ? this.f73m : this.f75o;
            if (!TextUtils.isEmpty(str)) {
                String str2 = (String) f71l.get(str);
                if (!TextUtils.isEmpty(str2)) {
                    this.f73m = str;
                    this.f74n = str2;
                    return;
                }
                return;
            }
            return;
        }
        this.f73m = "CN";
        this.f74n = "data.mistat.xiaomi.com";
    }

    /* renamed from: c */
    private boolean m135c(String str) {
        boolean z;
        if (f71l.keySet().contains(str)) {
            this.f73m = str;
            this.f74n = (String) f71l.get(this.f73m);
            z = true;
        } else {
            this.f73m = "INTL";
            this.f74n = (String) f71l.get(this.f73m);
            C0177k.m323d("RDM", "unknown region,set to unknown(singapore)'s domain");
            z = false;
        }
        C0125A.m8a().mo2354b("region", str);
        return z;
    }

    /* renamed from: d */
    private void m136d(String str) {
        this.f75o = str;
    }

    /* renamed from: a */
    public void mo2399a(JSONObject jSONObject) {
        HashMap a = this.f77q.mo2405a("region-url", jSONObject);
        String str = TextUtils.isEmpty(this.f75o) ? this.f73m : this.f75o;
        if (a != null) {
            f71l = this.f77q.mo2406a(f71l, a);
            if (!TextUtils.isEmpty(str)) {
                String str2 = (String) f71l.get(str);
                if (!TextUtils.isEmpty(str2)) {
                    this.f73m = str;
                    this.f74n = str2;
                }
            } else if (C0143b.m87e()) {
                this.f73m = "INTL";
                this.f74n = (String) f71l.get(this.f73m);
            }
            C0174h.m301a(f71l, this.f76p.concat("/map_domain"));
        }
    }
}
