package com.xiaomi.stat.p008c;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import com.xiaomi.stat.C0131I;
import com.xiaomi.stat.C0143b;
import com.xiaomi.stat.p009d.C0175i;
import java.io.IOException;
import java.util.Map;

/* renamed from: com.xiaomi.stat.c.c */
public class C0155c {
    /* renamed from: a */
    public static String m172a(String str, Map<String, String> map, boolean z) throws IOException {
        if (!C0143b.m109u() || !m173a()) {
            return C0175i.m305b(str, map, z);
        }
        return m174b(str, map, z);
    }

    /* renamed from: b */
    public static String m174b(String str, Map<String, String> map, boolean z) {
        if (z) {
            map.put("sn", C0175i.m303a(map));
        }
        Intent intent = new Intent();
        intent.setClassName("com.xiaomi.xmsf", "com.xiaomi.xmsf.push.service.HttpService");
        Context a = C0131I.m27a();
        String[] strArr = new String[1];
        if (a == null) {
            return strArr[0];
        }
        ServiceConnection a2 = m171a(str, map, strArr);
        boolean bindService = a.bindService(intent, a2, 1);
        synchronized (C0161i.class) {
            try {
                C0161i.class.wait(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!bindService) {
            strArr[0] = null;
        }
        if (bindService) {
            a.unbindService(a2);
        }
        return strArr[0];
    }

    /* renamed from: a */
    private static ServiceConnection m171a(String str, Map<String, String> map, String[] strArr) {
        return new C0156d(strArr, str, map);
    }

    /* renamed from: a */
    public static boolean m173a() {
        Context a = C0131I.m27a();
        if (a == null) {
            return false;
        }
        boolean z = true;
        if ((a.getApplicationInfo().flags & 1) == 0) {
            return false;
        }
        PackageManager packageManager = a.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(a.getPackageName(), 64);
            PackageInfo packageInfo2 = packageManager.getPackageInfo("android", 64);
            if (packageInfo != null) {
                if (((packageInfo.signatures != null) && (packageInfo.signatures.length > 0)) && packageInfo2 != null) {
                    boolean z2 = packageInfo2.signatures != null;
                    if (packageInfo2.signatures.length <= 0) {
                        z = false;
                    }
                    if (z && z2) {
                        return packageInfo2.signatures[0].equals(packageInfo.signatures[0]);
                    }
                }
            }
        } catch (NameNotFoundException unused) {
        }
        return false;
    }
}
