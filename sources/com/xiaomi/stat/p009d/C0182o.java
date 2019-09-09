package com.xiaomi.stat.p009d;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/* renamed from: com.xiaomi.stat.d.o */
public class C0182o {
    /* renamed from: a */
    public static byte[] m352a(byte[] bArr, byte[] bArr2) {
        try {
            RSAPublicKey a = m351a(bArr);
            Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            instance.init(1, a);
            return instance.doFinal(bArr2);
        } catch (Exception e) {
            C0177k.m326e("RsaUtils", "RsaUtils encrypt exception:", e);
            return null;
        }
    }

    /* renamed from: a */
    private static RSAPublicKey m351a(byte[] bArr) throws Exception {
        return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(bArr));
    }
}
