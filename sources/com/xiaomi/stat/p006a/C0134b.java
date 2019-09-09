package com.xiaomi.stat.p006a;

import android.text.TextUtils;

/* renamed from: com.xiaomi.stat.a.b */
public class C0134b {

    /* renamed from: c */
    private String f19c;

    /* renamed from: d */
    private int f20d;

    /* renamed from: e */
    private boolean f21e;

    /* renamed from: f */
    private boolean f22f;

    public C0134b(String str, int i, boolean z) {
        this.f19c = str;
        this.f20d = i;
        this.f21e = z;
        this.f22f = TextUtils.isEmpty(str);
    }

    /* renamed from: a */
    public boolean mo2376a(String str, String str2, boolean z) {
        if (!TextUtils.equals(str, this.f19c) || this.f21e != z) {
            return false;
        }
        if (this.f20d == 0) {
            return true;
        }
        if (!this.f22f || !TextUtils.equals(str2, "mistat_basic")) {
            return false;
        }
        return true;
    }

    /* renamed from: a */
    public String mo2375a() {
        StringBuilder sb = new StringBuilder();
        sb.append("sub");
        if (this.f22f) {
            sb.append(" is null");
        } else {
            sb.append(" = \"");
            sb.append(this.f19c);
            sb.append("\"");
        }
        if (this.f20d != 0) {
            sb.append(" and ");
            sb.append("eg");
            sb.append(" = \"");
            sb.append("mistat_basic");
            sb.append("\"");
        }
        sb.append(" and ");
        sb.append("is_am");
        sb.append(" = ");
        sb.append(this.f21e ? 1 : 0);
        return sb.toString();
    }
}
