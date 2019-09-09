package com.xiaomi.stat.p009d;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Process;
import android.os.RemoteException;
import android.provider.Settings.Global;
import android.provider.Settings.System;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.xiaomi.stat.C0131I;
import com.xiaomi.stat.C0143b;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/* renamed from: com.xiaomi.stat.d.e */
public class C0167e {

    /* renamed from: A */
    private static String f122A = null;

    /* renamed from: B */
    private static String f123B = null;

    /* renamed from: C */
    private static String f124C = null;

    /* renamed from: D */
    private static String f125D = null;

    /* renamed from: E */
    private static String f126E = null;

    /* renamed from: F */
    private static Boolean f127F = null;

    /* renamed from: G */
    private static String f128G = null;

    /* renamed from: H */
    private static String f129H = null;

    /* renamed from: I */
    private static String f130I = null;

    /* renamed from: J */
    private static boolean f131J = false;

    /* renamed from: i */
    private static Method f132i;

    /* renamed from: j */
    private static Method f133j;

    /* renamed from: k */
    private static Method f134k;

    /* renamed from: l */
    private static Object f135l;

    /* renamed from: m */
    private static Method f136m;

    /* renamed from: n */
    private static String f137n;

    /* renamed from: o */
    private static String f138o;

    /* renamed from: p */
    private static String f139p;

    /* renamed from: q */
    private static String f140q;

    /* renamed from: r */
    private static String f141r;

    /* renamed from: s */
    private static String f142s;

    /* renamed from: t */
    private static String f143t;

    /* renamed from: u */
    private static String f144u;

    /* renamed from: v */
    private static String f145v;

    /* renamed from: w */
    private static String f146w;

    /* renamed from: x */
    private static String f147x;

    /* renamed from: y */
    private static String f148y;

    /* renamed from: z */
    private static String f149z;

    /* renamed from: com.xiaomi.stat.d.e$a */
    private static class C0168a {

        /* renamed from: com.xiaomi.stat.d.e$a$a */
        private static final class C0169a implements ServiceConnection {

            /* renamed from: b */
            private boolean f150b;

            /* renamed from: c */
            private IBinder f151c;

            private C0169a() {
                this.f150b = false;
            }

            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                synchronized (this) {
                    this.f151c = iBinder;
                    notifyAll();
                }
            }

            public void onServiceDisconnected(ComponentName componentName) {
                this.f150b = true;
                this.f151c = null;
            }

            /* renamed from: a */
            public IBinder mo2429a() throws InterruptedException {
                if (this.f151c != null) {
                    return this.f151c;
                }
                if (this.f151c == null && !this.f150b) {
                    synchronized (this) {
                        wait(30000);
                        if (this.f151c == null) {
                            throw new InterruptedException("Not connect or connect timeout to google play service");
                        }
                    }
                }
                return this.f151c;
            }
        }

        /* renamed from: com.xiaomi.stat.d.e$a$b */
        private static final class C0170b implements IInterface {

            /* renamed from: a */
            private IBinder f152a;

            public C0170b(IBinder iBinder) {
                this.f152a = iBinder;
            }

            public IBinder asBinder() {
                return this.f152a;
            }

            /* renamed from: a */
            public String mo2432a() throws RemoteException {
                if (this.f152a == null) {
                    return "";
                }
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    this.f152a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        /* renamed from: a */
        static String m277a(Context context) {
            if (!m278b(context)) {
                C0177k.m319b("GAIDClient", "Google play service is not available");
                return "";
            }
            C0169a aVar = new C0169a();
            Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
            intent.setPackage("com.google.android.gms");
            if (context.bindService(intent, aVar, 1)) {
                try {
                    return new C0170b(aVar.mo2429a()).mo2432a();
                } catch (Exception e) {
                    C0177k.m320b("GAIDClient", "Query Google ADID failed ", e);
                } finally {
                    context.unbindService(aVar);
                }
            }
            return "";
        }

        /* renamed from: b */
        private static boolean m278b(Context context) {
            try {
                context.getPackageManager().getPackageInfo("com.android.vending", 0);
                return true;
            } catch (NameNotFoundException unused) {
                return false;
            }
        }
    }

    /* renamed from: com.xiaomi.stat.d.e$b */
    private static class C0171b {

        /* renamed from: f */
        private static Signature[] f153f;

        /* renamed from: g */
        private static final Signature f154g = new Signature("3082033b30820223a003020102020900a07a328482f70d2a300d06092a864886f70d01010505003035310b30090603550406130255533113301106035504080c0a43616c69666f726e69613111300f06035504070c084d6f756e7461696e301e170d3133303430313033303831325a170d3430303831373033303831325a3035310b30090603550406130255533113301106035504080c0a43616c69666f726e69613111300f06035504070c084d6f756e7461696e30820120300d06092a864886f70d01010105000382010d00308201080282010100ac678c9234a0226edbeb75a43e8e18f632d8c8a094c087fffbbb0b5e4429d845e36bffbe2d7098e320855258aa777368c18c538f968063d5d61663dc946ab03acbb31d00a27d452e12e6d42865e27d6d0ad2d8b12cf3b3096a7ec66a21db2a6a697857fd4d29fb4cdf294b3371d7601f2e3f190c0164efa543897026c719b334808e4f612fe3a3da589115fc30f9ca89862feefdf31a9164ecb295dcf7767e673be2192dda64f88189fd6e6ebd62e572c7997c2385a0ea9292ec799dee8f87596fc73aa123fb6f577d09ac0c123179c3bdbc978c2fe6194eb9fa4ab3658bfe8b44040bb13fe7809409e622189379fbc63966ab36521793547b01673ecb5f15cf020103a350304e301d0603551d0e0416041447203684e562385ada79108c4c94c5055037592f301f0603551d2304183016801447203684e562385ada79108c4c94c5055037592f300c0603551d13040530030101ff300d06092a864886f70d010105050003820101008d530fe05c6fe694c7559ddb5dd2c556528dd3cad4f7580f439f9a90a4681d37ce246b9a6681bdd5a5437f0b8bba903e39bac309fc0e9ee5553681612e723e9ec4f6abab6b643b33013f09246a9b5db7703b96f838fb27b00612f5fcd431bea32f68350ae51a4a1d012c520c401db7cccc15a7b19c4310b0c3bfc625ce5744744d0b9eeb02b0a4e7d51ed59849ce580b9f7c3062c84b9a0b13cc211e1c916c289820266a610801e3316c915649804571b147beadbf88d3b517ee04121d40630853f2f2a506bb788620de9648faeacff568e5033a666316bc2046526674ed3de25ceefdc4ad3628f1a230fd41bf9ca9f6a078173850dba555768fe1c191483ad9");

        /* renamed from: a */
        static boolean m285a(Context context) {
            if (f153f == null) {
                f153f = new Signature[]{m287c(context)};
            }
            if (f153f[0] == null || !f153f[0].equals(f154g)) {
                return false;
            }
            return true;
        }

        /* renamed from: a */
        private static Signature m281a(PackageInfo packageInfo) {
            if (packageInfo == null || packageInfo.signatures == null || packageInfo.signatures.length <= 0) {
                return null;
            }
            return packageInfo.signatures[0];
        }

        /* renamed from: c */
        private static Signature m287c(Context context) {
            try {
                return m281a(context.getPackageManager().getPackageInfo("android", 64));
            } catch (Exception unused) {
                return null;
            }
        }

        /* renamed from: b */
        public static String m286b(Context context) {
            String str;
            if (VERSION.SDK_INT >= 17) {
                try {
                    String string = Global.getString(context.getContentResolver(), "mi_device_mac");
                    if (!TextUtils.isEmpty(string)) {
                        return string;
                    }
                } catch (Exception unused) {
                }
            }
            try {
                String str2 = Build.PRODUCT;
                String a = C0167e.m245b("ro.product.model");
                boolean z = true;
                if (!TextUtils.equals("tv", m283a()) || "batman".equals(str2) || "conan".equals(str2)) {
                    if (!"augustrush".equals(str2)) {
                        if (!"casablanca".equals(str2)) {
                            z = false;
                        }
                    }
                }
                if (TextUtils.equals("me2", str2)) {
                    str = C0167e.m245b("persist.service.bdroid.bdaddr");
                } else if ((TextUtils.equals("transformers", str2) && TextUtils.equals("MiBOX4C", a)) || TextUtils.equals("dolphin-zorro", str2)) {
                    str = m284a("/sys/class/net/wlan0/address");
                } else if (z) {
                    str = m284a("/sys/class/net/eth0/address");
                } else {
                    str = m284a("ro.boot.btmac");
                }
                return !TextUtils.isEmpty(str) ? str.trim() : "";
            } catch (Exception e) {
                C0177k.m320b("DeviceUtil", "getMiTvMac exception", e);
                return "";
            }
        }

        /* renamed from: a */
        private static String m284a(String str) {
            String str2;
            String str3 = "";
            BufferedReader bufferedReader = null;
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(new FileInputStream(str)), 512);
                try {
                    String readLine = bufferedReader2.readLine();
                    if (readLine != null) {
                        if (str3.length() > 0) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(str3);
                            sb.append("\n");
                            str3 = sb.toString();
                        }
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(str3);
                        sb2.append(readLine);
                        str2 = sb2.toString();
                    } else {
                        str2 = str3;
                    }
                    C0176j.m311a((Reader) bufferedReader2);
                    return str2;
                } catch (Exception e) {
                    e = e;
                    bufferedReader = bufferedReader2;
                    try {
                        C0177k.m326e("DeviceUtil", "catEntry exception", e);
                        C0176j.m311a((Reader) bufferedReader);
                        return str3;
                    } catch (Throwable th) {
                        th = th;
                        bufferedReader2 = bufferedReader;
                        C0176j.m311a((Reader) bufferedReader2);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    C0176j.m311a((Reader) bufferedReader2);
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
                C0177k.m326e("DeviceUtil", "catEntry exception", e);
                C0176j.m311a((Reader) bufferedReader);
                return str3;
            }
        }

        /* renamed from: a */
        private static String m283a() {
            String str;
            String str2 = "";
            try {
                Class cls = Class.forName("mitv.common.ConfigurationManager");
                int parseInt = Integer.parseInt(String.valueOf(cls.getMethod("getProductCategory", new Class[0]).invoke(cls.getMethod("getInstance", new Class[0]).invoke(cls, new Object[0]), new Object[0])));
                Class cls2 = Class.forName("mitv.tv.TvContext");
                if (parseInt == Integer.parseInt(String.valueOf(m282a(cls2, "PRODUCT_CATEGORY_MITV")))) {
                    str = "tv";
                } else if (parseInt == Integer.parseInt(String.valueOf(m282a(cls2, "PRODUCT_CATEGORY_MIBOX")))) {
                    str = "box";
                } else if (parseInt == Integer.parseInt(String.valueOf(m282a(cls2, "PRODUCT_CATEGORY_MITVBOX")))) {
                    str = "tvbox";
                } else if (parseInt != Integer.parseInt(String.valueOf(m282a(cls2, "PRODUCT_CATEGORY_MIPROJECTOR")))) {
                    return str2;
                } else {
                    str = "projector";
                }
                return str;
            } catch (Exception e) {
                C0177k.m320b("DeviceUtil", "getMiTvProductCategory exception", e);
                return str2;
            }
        }

        /* renamed from: a */
        private static <T> T m282a(Class<?> cls, String str) {
            try {
                Field declaredField = cls.getDeclaredField(str);
                declaredField.setAccessible(true);
                return declaredField.get(null);
            } catch (Exception e) {
                C0177k.m326e("DeviceUtil", "getStaticVariableValue exception", e);
                return null;
            }
        }
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0041 */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0047 A[Catch:{ Exception -> 0x005b }] */
    /* JADX WARNING: Removed duplicated region for block: B:14:? A[RETURN, SYNTHETIC] */
    static {
        /*
            r0 = 1
            r1 = 0
            java.lang.String r2 = "android.os.SystemProperties"
            java.lang.Class r2 = java.lang.Class.forName(r2)     // Catch:{ Exception -> 0x0016 }
            java.lang.String r3 = "get"
            java.lang.Class[] r4 = new java.lang.Class[r0]     // Catch:{ Exception -> 0x0016 }
            java.lang.Class<java.lang.String> r5 = java.lang.String.class
            r4[r1] = r5     // Catch:{ Exception -> 0x0016 }
            java.lang.reflect.Method r2 = r2.getMethod(r3, r4)     // Catch:{ Exception -> 0x0016 }
            f132i = r2     // Catch:{ Exception -> 0x0016 }
        L_0x0016:
            java.lang.String r2 = "miui.telephony.TelephonyManagerEx"
            java.lang.Class r2 = java.lang.Class.forName(r2)     // Catch:{ Exception -> 0x0041 }
            java.lang.String r3 = "getDefault"
            java.lang.Class[] r4 = new java.lang.Class[r1]     // Catch:{ Exception -> 0x0041 }
            java.lang.reflect.Method r3 = r2.getMethod(r3, r4)     // Catch:{ Exception -> 0x0041 }
            r4 = 0
            java.lang.Object[] r5 = new java.lang.Object[r1]     // Catch:{ Exception -> 0x0041 }
            java.lang.Object r3 = r3.invoke(r4, r5)     // Catch:{ Exception -> 0x0041 }
            f135l = r3     // Catch:{ Exception -> 0x0041 }
            java.lang.String r3 = "getImeiList"
            java.lang.Class[] r4 = new java.lang.Class[r1]     // Catch:{ Exception -> 0x0041 }
            java.lang.reflect.Method r3 = r2.getMethod(r3, r4)     // Catch:{ Exception -> 0x0041 }
            f133j = r3     // Catch:{ Exception -> 0x0041 }
            java.lang.String r3 = "getMeidList"
            java.lang.Class[] r4 = new java.lang.Class[r1]     // Catch:{ Exception -> 0x0041 }
            java.lang.reflect.Method r2 = r2.getMethod(r3, r4)     // Catch:{ Exception -> 0x0041 }
            f134k = r2     // Catch:{ Exception -> 0x0041 }
        L_0x0041:
            int r2 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x005b }
            r3 = 21
            if (r2 < r3) goto L_0x005b
            java.lang.String r2 = "android.telephony.TelephonyManager"
            java.lang.Class r2 = java.lang.Class.forName(r2)     // Catch:{ Exception -> 0x005b }
            java.lang.String r3 = "getImei"
            java.lang.Class[] r0 = new java.lang.Class[r0]     // Catch:{ Exception -> 0x005b }
            java.lang.Class r4 = java.lang.Integer.TYPE     // Catch:{ Exception -> 0x005b }
            r0[r1] = r4     // Catch:{ Exception -> 0x005b }
            java.lang.reflect.Method r0 = r2.getMethod(r3, r0)     // Catch:{ Exception -> 0x005b }
            f136m = r0     // Catch:{ Exception -> 0x005b }
        L_0x005b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.stat.p009d.C0167e.<clinit>():void");
    }

    /* renamed from: a */
    public static void m241a() {
        boolean z = C0184r.m385b() - C0143b.m110v() > 7776000000L;
        if (TextUtils.isEmpty(C0143b.m111w()) || z) {
            C0143b.m97i(UUID.randomUUID().toString());
        }
    }

    /* renamed from: e */
    private static String m252e() {
        String w = C0143b.m111w();
        if (!TextUtils.isEmpty(w)) {
            return w;
        }
        String uuid = UUID.randomUUID().toString();
        C0143b.m97i(uuid);
        return uuid;
    }

    /* renamed from: a */
    public static String m239a(Context context) {
        if (C0143b.m87e()) {
            return "";
        }
        if (!TextUtils.isEmpty(f137n)) {
            return f137n;
        }
        String a = C0183p.m354a(context);
        if (!TextUtils.isEmpty(a)) {
            f137n = a;
            return f137n;
        }
        m275y(context);
        if (TextUtils.isEmpty(f137n)) {
            return "";
        }
        C0183p.m357a(context, f137n);
        return f137n;
    }

    /* renamed from: b */
    public static String m244b(Context context) {
        if (!TextUtils.isEmpty(f145v)) {
            return f145v;
        }
        String a = m239a(context);
        if (TextUtils.isEmpty(a)) {
            return "";
        }
        f145v = C0173g.m296c(a);
        return f145v;
    }

    /* renamed from: c */
    public static String m247c(Context context) {
        if (!TextUtils.isEmpty(f122A)) {
            return f122A;
        }
        String a = m239a(context);
        if (TextUtils.isEmpty(a)) {
            return "";
        }
        f122A = C0173g.m298d(a);
        return f122A;
    }

    /* renamed from: d */
    public static String m250d(Context context) {
        if (C0143b.m87e()) {
            return "";
        }
        if (!TextUtils.isEmpty(f138o)) {
            return f138o;
        }
        String b = C0183p.m360b(context);
        if (!TextUtils.isEmpty(b)) {
            f138o = b;
            return f138o;
        }
        m275y(context);
        if (TextUtils.isEmpty(f138o)) {
            return "";
        }
        C0183p.m362b(context, f138o);
        return f138o;
    }

    /* renamed from: e */
    public static String m253e(Context context) {
        if (!TextUtils.isEmpty(f146w)) {
            return f146w;
        }
        String d = m250d(context);
        if (TextUtils.isEmpty(d)) {
            return "";
        }
        f146w = C0173g.m296c(d);
        return f146w;
    }

    /* renamed from: f */
    public static String m254f(Context context) {
        if (!TextUtils.isEmpty(f123B)) {
            return f123B;
        }
        String d = m250d(context);
        if (TextUtils.isEmpty(d)) {
            return "";
        }
        f123B = C0173g.m298d(d);
        return f123B;
    }

    /* renamed from: g */
    public static String m256g(Context context) {
        if (C0143b.m87e()) {
            return "";
        }
        if (!TextUtils.isEmpty(f139p)) {
            return f139p;
        }
        String c = C0183p.m367c(context);
        if (!TextUtils.isEmpty(c)) {
            f139p = c;
            return f139p;
        }
        String s = m271s(context);
        if (TextUtils.isEmpty(s)) {
            return "";
        }
        f139p = s;
        C0183p.m369c(context, f139p);
        return f139p;
    }

    /* renamed from: h */
    public static String m258h(Context context) {
        if (!TextUtils.isEmpty(f147x)) {
            return f147x;
        }
        String g = m256g(context);
        if (TextUtils.isEmpty(g)) {
            return "";
        }
        f147x = C0173g.m296c(g);
        return f147x;
    }

    /* renamed from: i */
    public static String m260i(Context context) {
        if (!TextUtils.isEmpty(f124C)) {
            return f124C;
        }
        String g = m256g(context);
        if (TextUtils.isEmpty(g)) {
            return "";
        }
        f124C = C0173g.m298d(g);
        return f124C;
    }

    /* renamed from: j */
    public static String m262j(Context context) {
        String str;
        if (C0143b.m87e()) {
            return "";
        }
        if (!TextUtils.isEmpty(f140q)) {
            return f140q;
        }
        String d = C0183p.m370d(context);
        if (!TextUtils.isEmpty(d)) {
            f140q = d;
            return f140q;
        }
        if (m273w(context)) {
            str = C0171b.m286b(context);
        } else {
            str = m238B(context);
        }
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        f140q = str;
        C0183p.m371d(context, f140q);
        return f140q;
    }

    /* renamed from: k */
    public static String m263k(Context context) {
        if (!TextUtils.isEmpty(f148y)) {
            return f148y;
        }
        String j = m262j(context);
        if (TextUtils.isEmpty(j)) {
            return "";
        }
        f148y = C0173g.m296c(j);
        return f148y;
    }

    /* renamed from: l */
    public static String m264l(Context context) {
        if (!TextUtils.isEmpty(f125D)) {
            return f125D;
        }
        String j = m262j(context);
        if (TextUtils.isEmpty(j)) {
            return "";
        }
        f125D = C0173g.m298d(j);
        return f125D;
    }

    /* renamed from: m */
    public static String m265m(Context context) {
        if (C0143b.m87e()) {
            return "";
        }
        if (!TextUtils.isEmpty(f141r)) {
            return f141r;
        }
        String e = C0183p.m372e(context);
        if (!TextUtils.isEmpty(e)) {
            f141r = e;
            return f141r;
        }
        String t = m272t(context);
        if (TextUtils.isEmpty(t)) {
            return "";
        }
        f141r = t;
        C0183p.m373e(context, f141r);
        return f141r;
    }

    /* renamed from: n */
    public static String m266n(Context context) {
        if (!TextUtils.isEmpty(f149z)) {
            return f149z;
        }
        String m = m265m(context);
        if (TextUtils.isEmpty(m)) {
            return "";
        }
        f149z = C0173g.m296c(m);
        return f149z;
    }

    /* renamed from: o */
    public static String m267o(Context context) {
        if (!TextUtils.isEmpty(f126E)) {
            return f126E;
        }
        String m = m265m(context);
        if (TextUtils.isEmpty(m)) {
            return "";
        }
        f126E = C0173g.m298d(m);
        return f126E;
    }

    /* renamed from: p */
    public static String m268p(Context context) {
        if (!TextUtils.isEmpty(f142s)) {
            return f142s;
        }
        String string = System.getString(context.getContentResolver(), "android_id");
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        f142s = string;
        return f142s;
    }

    /* renamed from: q */
    public static String m269q(Context context) {
        if (!TextUtils.isEmpty(f143t)) {
            return f143t;
        }
        try {
            String type = context.getContentResolver().getType(Uri.parse("content://com.miui.analytics.server.AnalyticsProvider/aaid"));
            if (!TextUtils.isEmpty(type)) {
                f143t = type;
                return type;
            }
            Object invoke = Class.forName("android.provider.MiuiSettings$Ad").getDeclaredMethod("getAaid", new Class[]{ContentResolver.class}).invoke(null, new Object[]{context.getContentResolver()});
            if ((invoke instanceof String) && !TextUtils.isEmpty((String) invoke)) {
                f143t = (String) invoke;
                return f143t;
            }
            return "";
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("getAaid failed ex: ");
            sb.append(e.getMessage());
            C0177k.m319b("DeviceUtil", sb.toString());
        }
    }

    /* renamed from: r */
    public static String m270r(Context context) {
        if (!TextUtils.isEmpty(f144u)) {
            return f144u;
        }
        String a = C0168a.m277a(context);
        if (TextUtils.isEmpty(a)) {
            return "";
        }
        f144u = a;
        return f144u;
    }

    /* renamed from: y */
    private static List<String> m275y(Context context) {
        List<String> list;
        if (m242a(context, "android.permission.READ_PHONE_STATE")) {
            list = m255f();
            if (list == null || list.isEmpty()) {
                list = VERSION.SDK_INT >= 21 ? m276z(context) : m237A(context);
            }
        } else {
            list = null;
        }
        if (list == null || list.isEmpty()) {
            list = m257g();
        }
        if (list != null && !list.isEmpty()) {
            Collections.sort(list);
            f137n = (String) list.get(0);
            if (list.size() >= 2) {
                f138o = (String) list.get(1);
            }
        }
        return list;
    }

    /* renamed from: f */
    private static List<String> m255f() {
        if (f133j != null && !m261i()) {
            try {
                List<String> list = (List) f133j.invoke(f135l, new Object[0]);
                if (list != null && list.size() > 0 && !m243a(list)) {
                    return list;
                }
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder();
                sb.append("getImeiListFromMiui failed ex: ");
                sb.append(e.getMessage());
                C0177k.m319b("DeviceUtil", sb.toString());
            }
        }
        return null;
    }

    /* renamed from: z */
    private static List<String> m276z(Context context) {
        if (f136m != null) {
            try {
                ArrayList arrayList = new ArrayList();
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                String str = (String) f136m.invoke(telephonyManager, new Object[]{Integer.valueOf(0)});
                if (m248c(str)) {
                    arrayList.add(str);
                }
                if (m259h()) {
                    String str2 = (String) f136m.invoke(telephonyManager, new Object[]{Integer.valueOf(1)});
                    if (m248c(str2)) {
                        arrayList.add(str2);
                    }
                }
                return arrayList;
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder();
                sb.append("getImeiListAboveLollipop failed ex: ");
                sb.append(e.getMessage());
                C0177k.m319b("DeviceUtil", sb.toString());
            }
        }
        return null;
    }

    /* renamed from: A */
    private static List<String> m237A(Context context) {
        try {
            ArrayList arrayList = new ArrayList();
            Class cls = Class.forName("android.telephony.TelephonyManager");
            if (!m259h()) {
                String deviceId = ((TelephonyManager) cls.getMethod("getDefault", new Class[0]).invoke(null, new Object[0])).getDeviceId();
                if (m248c(deviceId)) {
                    arrayList.add(deviceId);
                }
                return arrayList;
            }
            String deviceId2 = ((TelephonyManager) cls.getMethod("getDefault", new Class[]{Integer.TYPE}).invoke(null, new Object[]{Integer.valueOf(0)})).getDeviceId();
            String deviceId3 = ((TelephonyManager) cls.getMethod("getDefault", new Class[]{Integer.TYPE}).invoke(null, new Object[]{Integer.valueOf(1)})).getDeviceId();
            if (m248c(deviceId2)) {
                arrayList.add(deviceId2);
            }
            if (m248c(deviceId3)) {
                arrayList.add(deviceId3);
            }
            return arrayList;
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("getImeiListBelowLollipop failed ex: ");
            sb.append(e.getMessage());
            C0177k.m319b("DeviceUtil", sb.toString());
            return null;
        }
    }

    /* renamed from: g */
    private static List<String> m257g() {
        ArrayList arrayList = new ArrayList();
        String b = m245b("ro.ril.miui.imei0");
        if (TextUtils.isEmpty(b)) {
            b = m245b("ro.ril.oem.imei");
        }
        if (TextUtils.isEmpty(b)) {
            b = m245b("persist.radio.imei");
        }
        if (m248c(b)) {
            arrayList.add(b);
        }
        if (m259h()) {
            String b2 = m245b("ro.ril.miui.imei1");
            if (TextUtils.isEmpty(b2)) {
                b2 = m245b("ro.ril.oem.imei2");
            }
            if (TextUtils.isEmpty(b2)) {
                b2 = m245b("persist.radio.imei2");
            }
            if (m248c(b2)) {
                arrayList.add(b2);
            }
        }
        return arrayList;
    }

    /* renamed from: s */
    public static String m271s(Context context) {
        if (m242a(context, "android.permission.READ_PHONE_STATE")) {
            if (f134k != null) {
                try {
                    List list = (List) f134k.invoke(f135l, new Object[0]);
                    if (list != null && list.size() > 0 && !m246b(list)) {
                        Collections.sort(list);
                        return (String) list.get(0);
                    }
                } catch (Exception e) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("queryMeid failed ex: ");
                    sb.append(e.getMessage());
                    C0177k.m319b("DeviceUtil", sb.toString());
                }
            }
            try {
                Class cls = Class.forName("android.telephony.TelephonyManager");
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                String str = null;
                if (cls != null) {
                    if (VERSION.SDK_INT >= 26) {
                        Method method = cls.getMethod("getMeid", new Class[0]);
                        if (method != null) {
                            str = (String) method.invoke(telephonyManager, new Object[0]);
                        }
                    } else {
                        Method method2 = cls.getMethod("getDeviceId", new Class[0]);
                        if (method2 != null) {
                            str = (String) method2.invoke(telephonyManager, new Object[0]);
                        }
                    }
                }
                if (m251d(str)) {
                    return str;
                }
            } catch (Exception e2) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("queryMeid->getMeid failed ex: ");
                sb2.append(e2.getMessage());
                C0177k.m319b("DeviceUtil", sb2.toString());
            }
        }
        String b = m245b("persist.radio.meid");
        if (m251d(b)) {
            return b;
        }
        String b2 = m245b("ro.ril.oem.meid");
        return m251d(b2) ? b2 : "";
    }

    /* renamed from: h */
    private static boolean m259h() {
        if ("dsds".equals(m245b("persist.radio.multisim.config"))) {
            return true;
        }
        String str = Build.DEVICE;
        if ("lcsh92_wet_jb9".equals(str) || "lcsh92_wet_tdd".equals(str) || "HM2013022".equals(str) || "HM2013023".equals(str) || "armani".equals(str) || "HM2014011".equals(str) || "HM2014012".equals(str)) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public static String m245b(String str) {
        try {
            if (f132i != null) {
                return String.valueOf(f132i.invoke(null, new Object[]{str}));
            }
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("getProp failed ex: ");
            sb.append(e.getMessage());
            C0177k.m319b("DeviceUtil", sb.toString());
        }
        return null;
    }

    /* renamed from: i */
    private static boolean m261i() {
        if (VERSION.SDK_INT >= 21) {
            return false;
        }
        String str = Build.DEVICE;
        String b = m245b("persist.radio.modem");
        if ("HM2014812".equals(str) || "HM2014821".equals(str) || (("gucci".equals(str) && "ct".equals(m245b("persist.sys.modem"))) || "CDMA".equals(b) || "HM1AC".equals(b) || "LTE-X5-ALL".equals(b) || "LTE-CT".equals(b) || "MI 3C".equals(Build.MODEL))) {
            return true;
        }
        return false;
    }

    /* renamed from: a */
    private static boolean m243a(List<String> list) {
        for (String c : list) {
            if (!m248c(c)) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: b */
    private static boolean m246b(List<String> list) {
        for (String d : list) {
            if (!m251d(d)) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: c */
    private static boolean m248c(String str) {
        return str != null && str.length() == 15 && !str.matches("^0*$");
    }

    /* renamed from: d */
    private static boolean m251d(String str) {
        return str != null && str.length() == 14 && !str.matches("^0*$");
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0041 A[Catch:{ Exception -> 0x0091 }] */
    /* renamed from: B */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String m238B(android.content.Context r10) {
        /*
            java.lang.String r0 = "android.permission.ACCESS_WIFI_STATE"
            boolean r0 = m242a(r10, r0)
            r1 = 0
            if (r0 == 0) goto L_0x00ac
            int r0 = android.os.Build.VERSION.SDK_INT
            r2 = 23
            if (r0 >= r2) goto L_0x0028
            java.lang.String r0 = "wifi"
            java.lang.Object r10 = r10.getSystemService(r0)     // Catch:{ Exception -> 0x0020 }
            android.net.wifi.WifiManager r10 = (android.net.wifi.WifiManager) r10     // Catch:{ Exception -> 0x0020 }
            android.net.wifi.WifiInfo r10 = r10.getConnectionInfo()     // Catch:{ Exception -> 0x0020 }
            java.lang.String r10 = r10.getMacAddress()     // Catch:{ Exception -> 0x0020 }
            goto L_0x0029
        L_0x0020:
            r10 = move-exception
            java.lang.String r0 = "DeviceUtil"
            java.lang.String r2 = "getMAC exception: "
            com.xiaomi.stat.p009d.C0177k.m326e(r0, r2, r10)
        L_0x0028:
            r10 = r1
        L_0x0029:
            boolean r0 = android.text.TextUtils.isEmpty(r10)
            if (r0 != 0) goto L_0x0037
            java.lang.String r0 = "02:00:00:00:00:00"
            boolean r10 = r0.equals(r10)
            if (r10 == 0) goto L_0x00ac
        L_0x0037:
            java.util.Enumeration r10 = java.net.NetworkInterface.getNetworkInterfaces()     // Catch:{ Exception -> 0x0091 }
        L_0x003b:
            boolean r0 = r10.hasMoreElements()     // Catch:{ Exception -> 0x0091 }
            if (r0 == 0) goto L_0x00ac
            java.lang.Object r0 = r10.nextElement()     // Catch:{ Exception -> 0x0091 }
            java.net.NetworkInterface r0 = (java.net.NetworkInterface) r0     // Catch:{ Exception -> 0x0091 }
            byte[] r2 = r0.getHardwareAddress()     // Catch:{ Exception -> 0x0091 }
            if (r2 == 0) goto L_0x003b
            int r3 = r2.length     // Catch:{ Exception -> 0x0091 }
            if (r3 != 0) goto L_0x0051
            goto L_0x003b
        L_0x0051:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0091 }
            r3.<init>()     // Catch:{ Exception -> 0x0091 }
            int r4 = r2.length     // Catch:{ Exception -> 0x0091 }
            r5 = 0
            r6 = r5
        L_0x0059:
            r7 = 1
            if (r6 >= r4) goto L_0x0072
            byte r8 = r2[r6]     // Catch:{ Exception -> 0x0091 }
            java.lang.String r9 = "%02x:"
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ Exception -> 0x0091 }
            java.lang.Byte r8 = java.lang.Byte.valueOf(r8)     // Catch:{ Exception -> 0x0091 }
            r7[r5] = r8     // Catch:{ Exception -> 0x0091 }
            java.lang.String r7 = java.lang.String.format(r9, r7)     // Catch:{ Exception -> 0x0091 }
            r3.append(r7)     // Catch:{ Exception -> 0x0091 }
            int r6 = r6 + 1
            goto L_0x0059
        L_0x0072:
            int r2 = r3.length()     // Catch:{ Exception -> 0x0091 }
            if (r2 <= 0) goto L_0x0080
            int r2 = r3.length()     // Catch:{ Exception -> 0x0091 }
            int r2 = r2 - r7
            r3.deleteCharAt(r2)     // Catch:{ Exception -> 0x0091 }
        L_0x0080:
            java.lang.String r2 = "wlan0"
            java.lang.String r0 = r0.getName()     // Catch:{ Exception -> 0x0091 }
            boolean r0 = r2.equals(r0)     // Catch:{ Exception -> 0x0091 }
            if (r0 == 0) goto L_0x003b
            java.lang.String r10 = r3.toString()     // Catch:{ Exception -> 0x0091 }
            return r10
        L_0x0091:
            r10 = move-exception
            java.lang.String r0 = "DeviceUtil"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "queryMac failed ex: "
            r2.append(r3)
            java.lang.String r10 = r10.getMessage()
            r2.append(r10)
            java.lang.String r10 = r2.toString()
            com.xiaomi.stat.p009d.C0177k.m319b(r0, r10)
        L_0x00ac:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.stat.p009d.C0167e.m238B(android.content.Context):java.lang.String");
    }

    /* renamed from: t */
    public static String m272t(Context context) {
        String str = null;
        if (VERSION.SDK_INT < 26) {
            str = Build.SERIAL;
        } else if (m242a(context, "android.permission.READ_PHONE_STATE")) {
            try {
                Method method = Class.forName("android.os.Build").getMethod("getSerial", new Class[0]);
                if (method != null) {
                    str = (String) method.invoke(null, new Object[0]);
                }
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder();
                sb.append("querySerial failed ex: ");
                sb.append(e.getMessage());
                C0177k.m319b("DeviceUtil", sb.toString());
            }
        }
        if (TextUtils.isEmpty(str) || "unknown".equals(str)) {
            return "";
        }
        f141r = str;
        return str;
    }

    /* renamed from: a */
    private static boolean m242a(Context context, String str) {
        return context.checkPermission(str, Process.myPid(), Process.myUid()) == 0;
    }

    /* renamed from: w */
    public static boolean m273w(Context context) {
        if (f127F == null) {
            f127F = Boolean.valueOf(C0171b.m285a(context));
        }
        return f127F.booleanValue();
    }

    /* renamed from: d */
    public static String m249d() {
        if (!TextUtils.isEmpty(f129H)) {
            return f129H;
        }
        boolean e = C0143b.m87e();
        String s = C0143b.m107s();
        if (!TextUtils.isEmpty(s)) {
            if (!e) {
                f129H = s;
                return f129H;
            }
            long b = C0184r.m385b();
            if (b - C0143b.m110v() <= 7776000000L) {
                f129H = s;
                C0143b.m74b(b);
                return f129H;
            }
        }
        if (e && !C0183p.m380k(C0131I.m27a())) {
            Context a = C0131I.m27a();
            C0183p.m366b(a, true);
            String string = a.getSharedPreferences("mistat", 0).getString("anonymous_id", null);
            StringBuilder sb = new StringBuilder();
            sb.append("last version instance id: ");
            sb.append(string);
            C0177k.m322c("DeviceUtil", sb.toString());
            f129H = string;
        }
        if (TextUtils.isEmpty(f129H)) {
            f129H = m252e();
        }
        C0143b.m91g(f129H);
        if (e) {
            C0143b.m74b(C0184r.m385b());
        }
        return f129H;
    }

    /* renamed from: x */
    public static String m274x(Context context) {
        if (!f131J) {
            f131J = true;
            if (!C0183p.m378i(context)) {
                C0183p.m358a(context, true);
                C0183p.m375f(context, context.getSharedPreferences("mistat", 0).getString("device_id", null));
            }
            f130I = C0183p.m379j(context);
        }
        return f130I;
    }
}
