package com.xiaomi.stat;

import com.xiaomi.stat.p009d.C0177k;
import com.xiaomi.stat.p009d.C0181n;
import org.json.JSONException;
import org.json.JSONObject;

public class MiStatParams {

    /* renamed from: b */
    private JSONObject f18b;

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public boolean mo2362a() {
        return true;
    }

    public MiStatParams() {
        this.f18b = new JSONObject();
    }

    MiStatParams(MiStatParams miStatParams) {
        if (miStatParams == null || miStatParams.f18b == null) {
            this.f18b = new JSONObject();
        } else {
            this.f18b = miStatParams.f18b;
        }
    }

    public void putInt(String str, int i) {
        if (!mo2363a(str)) {
            C0181n.m349e(str);
        } else if (m34c(str)) {
            C0181n.m345a();
        } else {
            try {
                this.f18b.put(str, i);
            } catch (JSONException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("put value error ");
                sb.append(e);
                C0177k.m322c("MiStatParams", sb.toString());
            }
        }
    }

    public void putLong(String str, long j) {
        if (!mo2363a(str)) {
            C0181n.m349e(str);
        } else if (m34c(str)) {
            C0181n.m345a();
        } else {
            try {
                this.f18b.put(str, j);
            } catch (JSONException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("put value error ");
                sb.append(e);
                C0177k.m322c("MiStatParams", sb.toString());
            }
        }
    }

    public void putString(String str, String str2) {
        if (!mo2363a(str)) {
            C0181n.m349e(str);
        } else if (!mo2364b(str2)) {
            C0181n.m350f(str2);
        } else if (m34c(str)) {
            C0181n.m345a();
        } else {
            try {
                this.f18b.put(str, C0181n.m348c(str2));
            } catch (JSONException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("put value error ");
                sb.append(e);
                C0177k.m322c("MiStatParams", sb.toString());
            }
        }
    }

    public String toJsonString() {
        return this.f18b.toString();
    }

    public int getParamsNumber() {
        return this.f18b.length();
    }

    /* renamed from: c */
    private boolean m34c(String str) {
        return mo2362a() && !this.f18b.has(str) && this.f18b.length() == 30;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public boolean mo2363a(String str) {
        return C0181n.m346a(str);
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: b */
    public boolean mo2364b(String str) {
        return C0181n.m347b(str);
    }
}
