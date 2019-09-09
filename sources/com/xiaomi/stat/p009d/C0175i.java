package com.xiaomi.stat.p009d;

import android.text.TextUtils;
import com.xiaomi.stat.C0131I;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

/* renamed from: com.xiaomi.stat.d.i */
public class C0175i {
    /* renamed from: b */
    public static String m305b(String str, Map<String, String> map, boolean z) throws IOException {
        return m302a("POST", str, map, z);
    }

    /* JADX WARNING: type inference failed for: r4v0 */
    /* JADX WARNING: type inference failed for: r5v0, types: [java.io.OutputStream] */
    /* JADX WARNING: type inference failed for: r4v1, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r6v0, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r5v1, types: [java.io.OutputStream] */
    /* JADX WARNING: type inference failed for: r4v2 */
    /* JADX WARNING: type inference failed for: r5v2 */
    /* JADX WARNING: type inference failed for: r6v1 */
    /* JADX WARNING: type inference failed for: r5v6 */
    /* JADX WARNING: type inference failed for: r5v10, types: [java.io.OutputStream] */
    /* JADX WARNING: type inference failed for: r5v11 */
    /* JADX WARNING: type inference failed for: r6v4 */
    /* JADX WARNING: type inference failed for: r5v20 */
    /* JADX WARNING: type inference failed for: r4v3 */
    /* JADX WARNING: type inference failed for: r4v4 */
    /* JADX WARNING: type inference failed for: r4v5 */
    /* JADX WARNING: type inference failed for: r5v21 */
    /* JADX WARNING: type inference failed for: r5v22 */
    /* JADX WARNING: type inference failed for: r5v23 */
    /* JADX WARNING: type inference failed for: r6v6 */
    /* JADX WARNING: type inference failed for: r5v24 */
    /* JADX WARNING: type inference failed for: r5v25 */
    /* JADX WARNING: type inference failed for: r5v26 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 7 */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String m302a(java.lang.String r10, java.lang.String r11, java.util.Map<java.lang.String, java.lang.String> r12, boolean r13) {
        /*
            r0 = 2
            r1 = 3
            r2 = 1
            r3 = 0
            r4 = 0
            if (r12 != 0) goto L_0x0009
            r12 = r4
            goto L_0x000d
        L_0x0009:
            java.lang.String r12 = m304a(r12, r13)     // Catch:{ IOException -> 0x00bf, all -> 0x00bb }
        L_0x000d:
            java.lang.String r13 = "GET"
            boolean r13 = r13.equals(r10)     // Catch:{ IOException -> 0x00bf, all -> 0x00bb }
            if (r13 == 0) goto L_0x002c
            if (r12 == 0) goto L_0x002c
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00bf, all -> 0x00bb }
            r13.<init>()     // Catch:{ IOException -> 0x00bf, all -> 0x00bb }
            r13.append(r11)     // Catch:{ IOException -> 0x00bf, all -> 0x00bb }
            java.lang.String r5 = "? "
            r13.append(r5)     // Catch:{ IOException -> 0x00bf, all -> 0x00bb }
            r13.append(r12)     // Catch:{ IOException -> 0x00bf, all -> 0x00bb }
            java.lang.String r13 = r13.toString()     // Catch:{ IOException -> 0x00bf, all -> 0x00bb }
            goto L_0x002d
        L_0x002c:
            r13 = r11
        L_0x002d:
            java.net.URL r5 = new java.net.URL     // Catch:{ IOException -> 0x00bf, all -> 0x00bb }
            r5.<init>(r13)     // Catch:{ IOException -> 0x00bf, all -> 0x00bb }
            java.net.URLConnection r13 = r5.openConnection()     // Catch:{ IOException -> 0x00bf, all -> 0x00bb }
            java.net.HttpURLConnection r13 = (java.net.HttpURLConnection) r13     // Catch:{ IOException -> 0x00bf, all -> 0x00bb }
            r5 = 10000(0x2710, float:1.4013E-41)
            r13.setConnectTimeout(r5)     // Catch:{ IOException -> 0x00b8, all -> 0x00b5 }
            r5 = 15000(0x3a98, float:2.102E-41)
            r13.setReadTimeout(r5)     // Catch:{ IOException -> 0x00b8, all -> 0x00b5 }
            java.lang.String r5 = "GET"
            boolean r5 = r5.equals(r10)     // Catch:{ IOException -> 0x00b8, all -> 0x00b5 }
            if (r5 == 0) goto L_0x0050
            java.lang.String r12 = "GET"
            r13.setRequestMethod(r12)     // Catch:{ IOException -> 0x00b8, all -> 0x00b5 }
            goto L_0x007b
        L_0x0050:
            java.lang.String r5 = "POST"
            boolean r5 = r5.equals(r10)     // Catch:{ IOException -> 0x00b8, all -> 0x00b5 }
            if (r5 == 0) goto L_0x007b
            if (r12 == 0) goto L_0x007b
            java.lang.String r5 = "POST"
            r13.setRequestMethod(r5)     // Catch:{ IOException -> 0x00b8, all -> 0x00b5 }
            java.lang.String r5 = "Content-Type"
            java.lang.String r6 = "application/x-www-form-urlencoded"
            r13.setRequestProperty(r5, r6)     // Catch:{ IOException -> 0x00b8, all -> 0x00b5 }
            r13.setDoOutput(r2)     // Catch:{ IOException -> 0x00b8, all -> 0x00b5 }
            java.lang.String r5 = "UTF-8"
            byte[] r12 = r12.getBytes(r5)     // Catch:{ IOException -> 0x00b8, all -> 0x00b5 }
            java.io.OutputStream r5 = r13.getOutputStream()     // Catch:{ IOException -> 0x00b8, all -> 0x00b5 }
            int r6 = r12.length     // Catch:{ IOException -> 0x00b2, all -> 0x00b0 }
            r5.write(r12, r3, r6)     // Catch:{ IOException -> 0x00b2, all -> 0x00b0 }
            r5.flush()     // Catch:{ IOException -> 0x00b2, all -> 0x00b0 }
            goto L_0x007c
        L_0x007b:
            r5 = r4
        L_0x007c:
            int r12 = r13.getResponseCode()     // Catch:{ IOException -> 0x00b2, all -> 0x00b0 }
            java.io.InputStream r6 = r13.getInputStream()     // Catch:{ IOException -> 0x00b2, all -> 0x00b0 }
            byte[] r7 = com.xiaomi.stat.p009d.C0176j.m313b(r6)     // Catch:{ IOException -> 0x00ae }
            java.lang.String r8 = "HttpUtil %s succeed url: %s, code: %s"
            java.lang.Object[] r9 = new java.lang.Object[r1]     // Catch:{ IOException -> 0x00ae }
            r9[r3] = r10     // Catch:{ IOException -> 0x00ae }
            r9[r2] = r11     // Catch:{ IOException -> 0x00ae }
            java.lang.Integer r12 = java.lang.Integer.valueOf(r12)     // Catch:{ IOException -> 0x00ae }
            r9[r0] = r12     // Catch:{ IOException -> 0x00ae }
            java.lang.String r12 = java.lang.String.format(r8, r9)     // Catch:{ IOException -> 0x00ae }
            com.xiaomi.stat.p009d.C0177k.m318b(r12)     // Catch:{ IOException -> 0x00ae }
            java.lang.String r12 = new java.lang.String     // Catch:{ IOException -> 0x00ae }
            java.lang.String r8 = "UTF-8"
            r12.<init>(r7, r8)     // Catch:{ IOException -> 0x00ae }
            com.xiaomi.stat.p009d.C0176j.m309a(r6)
            com.xiaomi.stat.p009d.C0176j.m310a(r5)
            com.xiaomi.stat.p009d.C0176j.m312a(r13)
            return r12
        L_0x00ae:
            r12 = move-exception
            goto L_0x00c3
        L_0x00b0:
            r10 = move-exception
            goto L_0x00e4
        L_0x00b2:
            r12 = move-exception
            r6 = r4
            goto L_0x00c3
        L_0x00b5:
            r10 = move-exception
            r5 = r4
            goto L_0x00e4
        L_0x00b8:
            r12 = move-exception
            r5 = r4
            goto L_0x00c2
        L_0x00bb:
            r10 = move-exception
            r13 = r4
            r5 = r13
            goto L_0x00e4
        L_0x00bf:
            r12 = move-exception
            r13 = r4
            r5 = r13
        L_0x00c2:
            r6 = r5
        L_0x00c3:
            java.lang.String r7 = "HttpUtil %s failed, url: %s, error: %s"
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x00e2 }
            r1[r3] = r10     // Catch:{ all -> 0x00e2 }
            r1[r2] = r11     // Catch:{ all -> 0x00e2 }
            java.lang.String r10 = r12.getMessage()     // Catch:{ all -> 0x00e2 }
            r1[r0] = r10     // Catch:{ all -> 0x00e2 }
            java.lang.String r10 = java.lang.String.format(r7, r1)     // Catch:{ all -> 0x00e2 }
            com.xiaomi.stat.p009d.C0177k.m324e(r10)     // Catch:{ all -> 0x00e2 }
            com.xiaomi.stat.p009d.C0176j.m309a(r6)
            com.xiaomi.stat.p009d.C0176j.m310a(r5)
            com.xiaomi.stat.p009d.C0176j.m312a(r13)
            return r4
        L_0x00e2:
            r10 = move-exception
            r4 = r6
        L_0x00e4:
            com.xiaomi.stat.p009d.C0176j.m309a(r4)
            com.xiaomi.stat.p009d.C0176j.m310a(r5)
            com.xiaomi.stat.p009d.C0176j.m312a(r13)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.stat.p009d.C0175i.m302a(java.lang.String, java.lang.String, java.util.Map, boolean):java.lang.String");
    }

    /* renamed from: a */
    private static String m304a(Map<String, String> map, boolean z) {
        StringBuilder sb = new StringBuilder();
        for (Entry entry : map.entrySet()) {
            try {
                if (!TextUtils.isEmpty((CharSequence) entry.getKey())) {
                    if (sb.length() > 0) {
                        sb.append("&");
                    }
                    sb.append(URLEncoder.encode((String) entry.getKey(), "UTF-8"));
                    sb.append("=");
                    sb.append(URLEncoder.encode(entry.getValue() == null ? "null" : (String) entry.getValue(), "UTF-8"));
                }
            } catch (UnsupportedEncodingException unused) {
                C0177k.m324e("format params failed");
            }
        }
        if (z) {
            String a = m303a(map);
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(URLEncoder.encode("sn", "UTF-8"));
            sb.append("=");
            sb.append(URLEncoder.encode(a, "UTF-8"));
        }
        return sb.toString();
    }

    /* renamed from: a */
    public static String m303a(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        if (map != null) {
            ArrayList<String> arrayList = new ArrayList<>(map.keySet());
            Collections.sort(arrayList);
            for (String str : arrayList) {
                if (!TextUtils.isEmpty(str)) {
                    sb.append(str);
                    sb.append((String) map.get(str));
                }
            }
        }
        sb.append(C0131I.m30c());
        return C0173g.m296c(sb.toString());
    }
}
