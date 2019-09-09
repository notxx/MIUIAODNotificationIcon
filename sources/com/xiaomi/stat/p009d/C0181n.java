package com.xiaomi.stat.p009d;

import android.text.TextUtils;
import java.util.regex.Pattern;

/* renamed from: com.xiaomi.stat.d.n */
public class C0181n {

    /* renamed from: h */
    private static Pattern f162h = Pattern.compile("^[a-zA-Z_][a-zA-Z0-9_]*");

    /* renamed from: c */
    public static String m348c(String str) {
        return str == null ? "null" : str;
    }

    /* renamed from: a */
    public static boolean m346a(String str) {
        if (TextUtils.isEmpty(str) || str.length() > 64 || str.startsWith("mistat_") || str.startsWith("mi_") || str.startsWith("abtest_")) {
            return false;
        }
        return f162h.matcher(str).matches();
    }

    /* renamed from: b */
    public static boolean m347b(String str) {
        return str == null || str.length() <= 256;
    }

    /* renamed from: e */
    public static void m349e(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("invalid parameter name: ");
        sb.append(str);
        C0177k.m324e(sb.toString());
    }

    /* renamed from: f */
    public static void m350f(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("parameter value is too long: ");
        sb.append(str);
        C0177k.m324e(sb.toString());
    }

    /* renamed from: a */
    public static void m345a() {
        C0177k.m324e("parameter number exceed limits");
    }
}
