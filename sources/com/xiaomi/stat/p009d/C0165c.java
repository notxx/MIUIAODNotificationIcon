package com.xiaomi.stat.p009d;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import com.xiaomi.stat.C0131I;

/* renamed from: com.xiaomi.stat.d.c */
public class C0165c {

    /* renamed from: a */
    private static boolean f117a;

    /* renamed from: b */
    private static int f118b;

    /* renamed from: c */
    private static String f119c;

    /* renamed from: a */
    public static int m231a() {
        if (!f117a) {
            m233c();
        }
        return f118b;
    }

    /* renamed from: b */
    public static String m232b() {
        if (!f117a) {
            m233c();
        }
        return f119c;
    }

    /* renamed from: c */
    private static void m233c() {
        if (!f117a) {
            f117a = true;
            Context a = C0131I.m27a();
            try {
                PackageInfo packageInfo = a.getPackageManager().getPackageInfo(a.getPackageName(), 0);
                f118b = packageInfo.versionCode;
                f119c = packageInfo.versionName;
            } catch (NameNotFoundException unused) {
            }
        }
    }
}
