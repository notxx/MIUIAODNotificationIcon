package com.xiaomi.stat.p009d;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.TimeZone;

/* renamed from: com.xiaomi.stat.d.m */
public class C0180m {

    /* renamed from: d */
    private static Method f158d;

    /* renamed from: e */
    private static Class f159e;

    /* renamed from: f */
    private static Method f160f;

    /* renamed from: g */
    private static Boolean f161g;

    /* renamed from: b */
    public static String m337b() {
        return "Android";
    }

    static {
        try {
            f158d = Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class});
        } catch (Exception unused) {
        }
        try {
            f159e = Class.forName("miui.os.Build");
        } catch (Exception unused2) {
        }
        try {
            f160f = Class.forName("android.provider.MiuiSettings$Secure").getDeclaredMethod("isUserExperienceProgramEnable", new Class[]{ContentResolver.class});
            f160f.setAccessible(true);
        } catch (Exception unused3) {
        }
    }

    /* renamed from: a */
    public static boolean m336a() {
        if (f161g != null) {
            return f161g.booleanValue();
        }
        if (!TextUtils.isEmpty(m335a("ro.miui.ui.version.code"))) {
            f161g = Boolean.valueOf(true);
        } else {
            f161g = Boolean.valueOf(false);
        }
        return f161g.booleanValue();
    }

    /* renamed from: c */
    public static String m339c() {
        return VERSION.RELEASE;
    }

    /* renamed from: d */
    public static String m340d() {
        return VERSION.INCREMENTAL;
    }

    /* renamed from: e */
    public static String m341e() {
        try {
            return TimeZone.getDefault().getDisplayName(false, 0);
        } catch (Exception unused) {
            return null;
        }
    }

    /* renamed from: a */
    public static String m334a(Context context) {
        String[] split;
        String a = m335a("gsm.operator.numeric");
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(a)) {
            for (String str : a.split(",")) {
                if (!TextUtils.isEmpty(str) && !"00000".equals(str)) {
                    if (sb.length() > 0) {
                        sb.append(",");
                    }
                    sb.append(str);
                }
            }
        }
        String sb2 = sb.toString();
        if (TextUtils.isEmpty(sb2)) {
            sb2 = ((TelephonyManager) context.getSystemService("phone")).getNetworkOperator();
        }
        return sb2 == null ? "" : sb2;
    }

    /* renamed from: g */
    public static String m342g() {
        String a = m335a("ro.miui.region");
        if (TextUtils.isEmpty(a)) {
            a = Locale.getDefault().getCountry();
        }
        return a == null ? "" : a;
    }

    /* renamed from: h */
    public static String m343h() {
        if (f159e != null) {
            try {
                if (((Boolean) f159e.getField("IS_ALPHA_BUILD").get(null)).booleanValue()) {
                    return "A";
                }
                if (((Boolean) f159e.getField("IS_DEVELOPMENT_VERSION").get(null)).booleanValue()) {
                    return "D";
                }
                if (((Boolean) f159e.getField("IS_STABLE_VERSION").get(null)).booleanValue()) {
                    return "S";
                }
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder();
                sb.append("getRomBuildCode failed: ");
                sb.append(e.toString());
                Log.e("OSUtil", sb.toString());
            }
        }
        return "";
    }

    /* renamed from: a */
    private static String m335a(String str) {
        try {
            if (f158d != null) {
                return String.valueOf(f158d.invoke(null, new Object[]{str}));
            }
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("getProp failed ex: ");
            sb.append(e.getMessage());
            C0177k.m319b("OSUtil", sb.toString());
        }
        return null;
    }

    /* renamed from: b */
    public static boolean m338b(Context context) {
        if (f160f == null) {
            return true;
        }
        try {
            return ((Boolean) f160f.invoke(null, new Object[]{context.getContentResolver()})).booleanValue();
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("isUserExperiencePlanEnabled failed: ");
            sb.append(e.toString());
            Log.e("OSUtil", sb.toString());
            return true;
        }
    }

    /* renamed from: i */
    public static boolean m344i() {
        if (f159e != null) {
            try {
                return ((Boolean) f159e.getField("IS_INTERNATIONAL_BUILD").get(null)).booleanValue();
            } catch (Exception unused) {
            }
        }
        return false;
    }
}
