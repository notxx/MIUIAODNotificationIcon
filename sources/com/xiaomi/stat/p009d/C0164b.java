package com.xiaomi.stat.p009d;

import android.content.Context;
import android.os.Build.VERSION;
import android.security.KeyPairGeneratorSpec;
import android.security.KeyPairGeneratorSpec.Builder;
import android.util.Base64;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.util.GregorianCalendar;
import javax.crypto.Cipher;
import javax.security.auth.x500.X500Principal;

/* renamed from: com.xiaomi.stat.d.b */
public class C0164b {
    /* renamed from: a */
    public static synchronized String m226a(Context context, String str) throws Exception {
        synchronized (C0164b.class) {
            Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            KeyStore instance2 = KeyStore.getInstance("AndroidKeyStore");
            instance2.load(null);
            m229a(context, instance2);
            Certificate certificate = instance2.getCertificate("RSA_KEY");
            if (certificate == null) {
                return null;
            }
            instance.init(1, certificate.getPublicKey());
            String encodeToString = Base64.encodeToString(instance.doFinal(str.getBytes("UTF-8")), 0);
            return encodeToString;
        }
    }

    /* renamed from: b */
    public static synchronized String m230b(Context context, String str) throws Exception {
        String str2;
        synchronized (C0164b.class) {
            Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            KeyStore instance2 = KeyStore.getInstance("AndroidKeyStore");
            instance2.load(null);
            m229a(context, instance2);
            instance.init(2, (PrivateKey) instance2.getKey("RSA_KEY", null));
            str2 = new String(instance.doFinal(Base64.decode(str, 0)), "UTF-8");
        }
        return str2;
    }

    /* renamed from: a */
    private static void m229a(Context context, KeyStore keyStore) {
        try {
            if (!keyStore.containsAlias("RSA_KEY") && VERSION.SDK_INT >= 18) {
                if (VERSION.SDK_INT < 23) {
                    m228a(context);
                } else {
                    m227a();
                }
            }
        } catch (Exception e) {
            C0177k.m326e("AndroidKeyStoreUtils", "createKey e", e);
        }
    }

    /* renamed from: a */
    private static void m228a(Context context) throws KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        GregorianCalendar gregorianCalendar2 = new GregorianCalendar();
        gregorianCalendar2.add(1, 1);
        KeyPairGeneratorSpec build = new Builder(context).setAlias("RSA_KEY").setSubject(new X500Principal("CN=RSA_KEY")).setSerialNumber(BigInteger.valueOf(1337)).setStartDate(gregorianCalendar.getTime()).setEndDate(gregorianCalendar2.getTime()).build();
        KeyPairGenerator instance = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore");
        instance.initialize(build);
        instance.generateKeyPair();
    }

    /* renamed from: a */
    private static void m227a() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException, InvalidAlgorithmParameterException, NoSuchProviderException, NoSuchAlgorithmException {
        Class cls = Class.forName("android.security.keystore.KeyGenParameterSpec$Builder");
        if (cls != null) {
            Constructor constructor = cls.getConstructor(new Class[]{String.class, Integer.TYPE});
            Class cls2 = Class.forName("android.security.keystore.KeyProperties");
            Object newInstance = constructor.newInstance(new Object[]{"RSA_KEY", Integer.valueOf(cls2.getDeclaredField("PURPOSE_ENCRYPT").getInt(null) | cls2.getDeclaredField("PURPOSE_DECRYPT").getInt(null))});
            cls.getMethod("setDigests", new Class[]{String[].class}).invoke(newInstance, new Object[]{new String[]{(String) cls2.getDeclaredField("DIGEST_SHA256").get(null), (String) cls2.getDeclaredField("DIGEST_SHA512").get(null)}});
            cls.getMethod("setEncryptionPaddings", new Class[]{String[].class}).invoke(newInstance, new Object[]{new String[]{(String) cls2.getDeclaredField("ENCRYPTION_PADDING_RSA_PKCS1").get(null)}});
            Object invoke = cls.getMethod("build", new Class[0]).invoke(newInstance, new Object[0]);
            Class cls3 = Class.forName("java.security.KeyPairGenerator");
            if (cls3 != null) {
                KeyPairGenerator keyPairGenerator = (KeyPairGenerator) cls3.getMethod("getInstance", new Class[]{String.class, String.class}).invoke(null, new Object[]{"RSA", "AndroidKeyStore"});
                cls3.getMethod("initialize", new Class[]{AlgorithmParameterSpec.class}).invoke(keyPairGenerator, new Object[]{invoke});
                keyPairGenerator.generateKeyPair();
            }
        }
    }
}
