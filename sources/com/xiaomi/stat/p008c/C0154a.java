package com.xiaomi.stat.p008c;

import android.content.Context;
import com.xiaomi.stat.C0131I;
import com.xiaomi.stat.C0143b;
import com.xiaomi.stat.p009d.C0177k;
import java.util.HashMap;
import java.util.Map.Entry;

/* renamed from: com.xiaomi.stat.c.a */
public class C0154a {

    /* renamed from: g */
    private static Context f88g = C0131I.m27a();

    /* renamed from: h */
    private static HashMap<Integer, Integer> f89h = new HashMap<>();

    static {
        f89h.put(Integer.valueOf(1), Integer.valueOf(1));
        f89h.put(Integer.valueOf(2), Integer.valueOf(2));
        f89h.put(Integer.valueOf(3), Integer.valueOf(4));
        f89h.put(Integer.valueOf(4), Integer.valueOf(8));
        f89h.put(Integer.valueOf(5), Integer.valueOf(16));
    }

    /* renamed from: a */
    public static int m169a(String str) {
        int i = 0;
        try {
            for (Entry entry : f89h.entrySet()) {
                int intValue = ((Integer) entry.getKey()).intValue();
                int intValue2 = ((Integer) entry.getValue()).intValue();
                if (m170a(intValue, str)) {
                    i |= intValue2;
                }
            }
        } catch (Exception e) {
            C0177k.m326e("ClientConfigMoniter", "getClientConfiguration exception", e);
        }
        return i;
    }

    /* renamed from: a */
    private static boolean m170a(int i, String str) {
        boolean z;
        switch (i) {
            case 1:
                z = C0143b.m109u();
                break;
            case 2:
                z = C0143b.m83d(str);
                break;
            case 3:
                z = C0177k.m316a();
                break;
            case 5:
                try {
                    z = C0143b.m93g();
                    break;
                } catch (Exception e) {
                    C0177k.m326e("ClientConfigMoniter", "checkSetting exception", e);
                    return false;
                }
            default:
                return false;
        }
        return z;
    }
}
