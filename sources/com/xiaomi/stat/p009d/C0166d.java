package com.xiaomi.stat.p009d;

import java.io.UnsupportedEncodingException;

/* renamed from: com.xiaomi.stat.d.d */
public class C0166d {

    /* renamed from: b */
    private static char[] f120b = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};

    /* renamed from: c */
    private static byte[] f121c = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1};

    /* renamed from: a */
    public static String m234a(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        int length = bArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            int i2 = i + 1;
            byte b = bArr[i] & 255;
            if (i2 == length) {
                stringBuffer.append(f120b[b >>> 2]);
                stringBuffer.append(f120b[(b & 3) << 4]);
                stringBuffer.append("==");
                break;
            }
            int i3 = i2 + 1;
            byte b2 = bArr[i2] & 255;
            if (i3 == length) {
                stringBuffer.append(f120b[b >>> 2]);
                stringBuffer.append(f120b[((b & 3) << 4) | ((b2 & 240) >>> 4)]);
                stringBuffer.append(f120b[(b2 & 15) << 2]);
                stringBuffer.append("=");
                break;
            }
            int i4 = i3 + 1;
            byte b3 = bArr[i3] & 255;
            stringBuffer.append(f120b[b >>> 2]);
            stringBuffer.append(f120b[((b & 3) << 4) | ((b2 & 240) >>> 4)]);
            stringBuffer.append(f120b[((b2 & 15) << 2) | ((b3 & 192) >>> 6)]);
            stringBuffer.append(f120b[b3 & 63]);
            i = i4;
        }
        return stringBuffer.toString();
    }

    /* renamed from: a */
    public static byte[] m235a(String str) {
        try {
            return m236b(str);
        } catch (UnsupportedEncodingException e) {
            C0177k.m326e("Base64Utils", "decode e", e);
            return new byte[0];
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0062  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x008e A[LOOP:0: B:1:0x000d->B:32:0x008e, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x009a A[EDGE_INSN: B:36:0x009a->B:33:0x009a ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x009a A[EDGE_INSN: B:38:0x009a->B:33:0x009a ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x009a A[EDGE_INSN: B:39:0x009a->B:33:0x009a ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x009a A[EDGE_INSN: B:40:0x009a->B:33:0x009a ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:7:0x0023 A[LOOP:2: B:7:0x0023->B:10:0x0030, LOOP_START, PHI: r4 
      PHI: (r4v1 int) = (r4v0 int), (r4v9 int) binds: [B:6:0x001f, B:10:0x0030] A[DONT_GENERATE, DONT_INLINE]] */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static byte[] m236b(java.lang.String r8) throws java.io.UnsupportedEncodingException {
        /*
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            java.lang.String r1 = "US-ASCII"
            byte[] r8 = r8.getBytes(r1)
            int r1 = r8.length
            r2 = 0
        L_0x000d:
            if (r2 >= r1) goto L_0x009a
        L_0x000f:
            byte[] r3 = f121c
            int r4 = r2 + 1
            byte r2 = r8[r2]
            byte r2 = r3[r2]
            r3 = -1
            if (r4 >= r1) goto L_0x001f
            if (r2 == r3) goto L_0x001d
            goto L_0x001f
        L_0x001d:
            r2 = r4
            goto L_0x000f
        L_0x001f:
            if (r2 != r3) goto L_0x0023
            goto L_0x009a
        L_0x0023:
            byte[] r5 = f121c
            int r6 = r4 + 1
            byte r4 = r8[r4]
            byte r4 = r5[r4]
            if (r6 >= r1) goto L_0x0032
            if (r4 == r3) goto L_0x0030
            goto L_0x0032
        L_0x0030:
            r4 = r6
            goto L_0x0023
        L_0x0032:
            if (r4 != r3) goto L_0x0036
            goto L_0x009a
        L_0x0036:
            int r2 = r2 << 2
            r5 = r4 & 48
            int r5 = r5 >>> 4
            r2 = r2 | r5
            char r2 = (char) r2
            r0.append(r2)
        L_0x0041:
            int r2 = r6 + 1
            byte r5 = r8[r6]
            r6 = 61
            if (r5 != r6) goto L_0x0054
            java.lang.String r8 = r0.toString()
            java.lang.String r0 = "iso8859-1"
            byte[] r8 = r8.getBytes(r0)
            return r8
        L_0x0054:
            byte[] r7 = f121c
            byte r5 = r7[r5]
            if (r2 >= r1) goto L_0x005f
            if (r5 == r3) goto L_0x005d
            goto L_0x005f
        L_0x005d:
            r6 = r2
            goto L_0x0041
        L_0x005f:
            if (r5 != r3) goto L_0x0062
            goto L_0x009a
        L_0x0062:
            r4 = r4 & 15
            int r4 = r4 << 4
            r7 = r5 & 60
            int r7 = r7 >>> 2
            r4 = r4 | r7
            char r4 = (char) r4
            r0.append(r4)
        L_0x006f:
            int r4 = r2 + 1
            byte r2 = r8[r2]
            if (r2 != r6) goto L_0x0080
            java.lang.String r8 = r0.toString()
            java.lang.String r0 = "iso8859-1"
            byte[] r8 = r8.getBytes(r0)
            return r8
        L_0x0080:
            byte[] r7 = f121c
            byte r2 = r7[r2]
            if (r4 >= r1) goto L_0x008b
            if (r2 == r3) goto L_0x0089
            goto L_0x008b
        L_0x0089:
            r2 = r4
            goto L_0x006f
        L_0x008b:
            if (r2 != r3) goto L_0x008e
            goto L_0x009a
        L_0x008e:
            r3 = r5 & 3
            int r3 = r3 << 6
            r2 = r2 | r3
            char r2 = (char) r2
            r0.append(r2)
            r2 = r4
            goto L_0x000d
        L_0x009a:
            java.lang.String r8 = r0.toString()
            java.lang.String r0 = "iso8859-1"
            byte[] r8 = r8.getBytes(r0)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.stat.p009d.C0166d.m236b(java.lang.String):byte[]");
    }
}
