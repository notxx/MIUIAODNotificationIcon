package com.xiaomi.stat.p009d;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* renamed from: com.xiaomi.stat.d.g */
public class C0173g {

    /* renamed from: a */
    private static final char[] f155a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /* renamed from: b */
    private static final char[] f156b = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /* renamed from: a */
    static MessageDigest m290a(String str) {
        try {
            return MessageDigest.getInstance(str);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /* renamed from: a */
    private static MessageDigest m289a() {
        return m290a("MD5");
    }

    /* renamed from: a */
    public static byte[] m292a(byte[] bArr) {
        return m289a().digest(bArr);
    }

    /* renamed from: b */
    public static byte[] m295b(String str) {
        return m292a(m291a(str, "UTF-8"));
    }

    /* renamed from: c */
    public static String m296c(String str) {
        return m288a(m295b(str), true);
    }

    /* renamed from: b */
    private static MessageDigest m294b() {
        return m290a("SHA-256");
    }

    /* renamed from: d */
    public static String m298d(String str) {
        return m288a(m299e(str), true);
    }

    /* renamed from: e */
    public static byte[] m299e(String str) {
        return m297c(m291a(str, "UTF-8"));
    }

    /* renamed from: c */
    public static byte[] m297c(byte[] bArr) {
        return m294b().digest(bArr);
    }

    /* renamed from: a */
    public static String m288a(byte[] bArr, boolean z) {
        return new String(m293a(bArr, z ? f155a : f156b));
    }

    /* renamed from: a */
    private static char[] m293a(byte[] bArr, char[] cArr) {
        int length = bArr.length;
        char[] cArr2 = new char[(length << 1)];
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i + 1;
            cArr2[i] = cArr[(bArr[i2] & 240) >>> 4];
            i = i3 + 1;
            cArr2[i3] = cArr[bArr[i2] & 15];
        }
        return cArr2;
    }

    /* renamed from: a */
    private static byte[] m291a(String str, String str2) {
        if (str == null) {
            return null;
        }
        try {
            return str.getBytes(str2);
        } catch (UnsupportedEncodingException unused) {
            return null;
        }
    }
}
