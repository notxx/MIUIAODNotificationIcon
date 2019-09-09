package com.xiaomi.stat.p009d;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.xiaomi.stat.C0131I;
import java.util.Iterator;

/* renamed from: com.xiaomi.stat.d.l */
public class C0178l {

    /* renamed from: com.xiaomi.stat.d.l$a */
    private static class C0179a {
        /* renamed from: a */
        private static boolean m331a(int i) {
            return i > 4900 && i < 5900;
        }

        /* renamed from: b */
        private static boolean m333b(int i) {
            return i > 2400 && i < 2500;
        }

        /* renamed from: a */
        public static String m330a(Context context) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return "NOT_CONNECTED";
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
                return "NOT_CONNECTED";
            }
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(1);
            NetworkInfo networkInfo2 = connectivityManager.getNetworkInfo(9);
            if (networkInfo == null || !networkInfo.isConnected()) {
                return (networkInfo2 == null || !networkInfo2.isConnected()) ? "UNKNOWN" : "ETHERNET";
            }
            StringBuilder sb = new StringBuilder();
            sb.append("WIFI");
            sb.append(m332b(context));
            return sb.toString();
        }

        /* renamed from: b */
        private static String m332b(Context context) {
            String str;
            String str2 = "";
            try {
                if (VERSION.SDK_INT >= 22) {
                    int frequency = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getFrequency();
                    if (m331a(frequency)) {
                        str = "5G";
                    } else if (!m333b(frequency)) {
                        return str2;
                    } else {
                        str = "2G";
                    }
                } else {
                    char c = 65535;
                    WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
                    String ssid = wifiManager.getConnectionInfo().getSSID();
                    String substring = ssid.substring(1, ssid.length() - 1);
                    if (ssid != null && ssid.length() > 2) {
                        Iterator it = wifiManager.getScanResults().iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            ScanResult scanResult = (ScanResult) it.next();
                            if (scanResult.SSID.equals(substring)) {
                                if (m331a(scanResult.frequency)) {
                                    c = 2;
                                } else if (m333b(scanResult.frequency)) {
                                    c = 1;
                                }
                            }
                        }
                    }
                    if (c == 2) {
                        str = "5G";
                    } else if (c != 1) {
                        return str2;
                    } else {
                        str = "2G";
                    }
                }
                return str;
            } catch (Exception e) {
                C0177k.m326e("NetWorkStateUtil", "getWifiFreeBand error", e);
                return str2;
            }
        }
    }

    /* renamed from: a */
    public static int m327a(Context context) {
        String b = m329b(context);
        if (TextUtils.isEmpty(b) || b.equals("NOT_CONNECTED")) {
            return 0;
        }
        if (b.equals("2G")) {
            return 1;
        }
        if (b.equals("3G")) {
            return 2;
        }
        if (b.equals("4G")) {
            return 4;
        }
        if (b.startsWith("WIFI")) {
            return 8;
        }
        if (b.equals("ETHERNET")) {
            return 16;
        }
        return 0;
    }

    /* renamed from: a */
    public static boolean m328a() {
        Context a = C0131I.m27a();
        if (a != null) {
            try {
                NetworkInfo activeNetworkInfo = ((ConnectivityManager) a.getSystemService("connectivity")).getActiveNetworkInfo();
                if (activeNetworkInfo != null) {
                    return activeNetworkInfo.isConnectedOrConnecting();
                }
            } catch (Exception unused) {
                C0177k.m318b("isNetworkConnected exception");
            }
        }
        return false;
    }

    /* renamed from: b */
    public static String m329b(Context context) {
        try {
            if (C0167e.m273w(context)) {
                return C0179a.m330a(context);
            }
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                if (activeNetworkInfo.isConnected()) {
                    if (activeNetworkInfo.getType() == 1) {
                        return "WIFI";
                    }
                    if (activeNetworkInfo.getType() == 0) {
                        switch (activeNetworkInfo.getSubtype()) {
                            case 1:
                            case 2:
                            case 4:
                            case 7:
                            case 11:
                            case 16:
                                return "2G";
                            case 3:
                            case 5:
                            case 6:
                            case 8:
                            case 9:
                            case 10:
                            case 12:
                            case 14:
                            case 15:
                            case 17:
                                return "3G";
                            case 13:
                            case 18:
                            case 19:
                                return "4G";
                            default:
                                return "UNKNOWN";
                        }
                    }
                    return "UNKNOWN";
                }
            }
            return "NOT_CONNECTED";
        } catch (Exception e) {
            C0177k.m326e("NetWorkStateUtil", "getNetworkState error", e);
        }
    }
}
