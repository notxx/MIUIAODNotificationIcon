package com.xiaomi.stat.p009d;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* renamed from: com.xiaomi.stat.d.a */
public class C0163a {

    /* renamed from: e */
    private static String f114e = "cfbsdfgsdfxccvd1";

    /* renamed from: f */
    private static KeyGenerator f115f;

    /* renamed from: g */
    private static Cipher f116g;

    static {
        try {
            f115f = KeyGenerator.getInstance("AES");
            f115f.init(128);
            f116g = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (Exception e) {
            C0177k.m320b("AES", "AesUtils e", e);
        }
    }

    /* renamed from: a */
    public static byte[] m223a() {
        return f115f.generateKey().getEncoded();
    }

    /* renamed from: a */
    public static byte[] m225a(byte[] bArr, String str) {
        try {
            f116g.init(1, new SecretKeySpec(m224a(str), "AES"), new IvParameterSpec(f114e.getBytes()));
            return f116g.doFinal(bArr);
        } catch (Exception e) {
            C0177k.m320b("AES", "encrypt exception:", e);
            return null;
        }
    }

    /* renamed from: a */
    public static String m222a(String str, byte[] bArr) {
        try {
            f116g.init(2, new SecretKeySpec(bArr, "AES"), new IvParameterSpec(f114e.getBytes()));
            return new String(f116g.doFinal(m224a(str)));
        } catch (Exception e) {
            C0177k.m320b("AES", "decrypt exception:", e);
            return null;
        }
    }

    /* renamed from: a */
    public static byte[] m224a(String str) {
        if (str == null || str.length() < 1) {
            return null;
        }
        byte[] bArr = new byte[(str.length() / 2)];
        for (int i = 0; i < str.length() / 2; i++) {
            int i2 = i * 2;
            int i3 = i2 + 1;
            bArr[i] = (byte) ((Integer.parseInt(str.substring(i2, i3), 16) * 16) + Integer.parseInt(str.substring(i3, i2 + 2), 16));
        }
        return bArr;
    }
}
