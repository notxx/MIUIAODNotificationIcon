package com.xiaomi.stat.p009d;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;

/* renamed from: com.xiaomi.stat.d.j */
public class C0176j {
    /* renamed from: a */
    public static void m312a(HttpURLConnection httpURLConnection) {
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
    }

    /* renamed from: a */
    public static void m311a(Reader reader) {
        m308a((Closeable) reader);
    }

    /* renamed from: a */
    public static void m309a(InputStream inputStream) {
        m308a((Closeable) inputStream);
    }

    /* renamed from: a */
    public static void m310a(OutputStream outputStream) {
        m308a((Closeable) outputStream);
    }

    /* renamed from: a */
    public static void m308a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }

    /* renamed from: b */
    public static byte[] m313b(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        m306a(inputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    /* renamed from: a */
    public static long m306a(InputStream inputStream, OutputStream outputStream) throws IOException {
        return m307a(inputStream, outputStream, 4096);
    }

    /* renamed from: a */
    public static long m307a(InputStream inputStream, OutputStream outputStream, int i) throws IOException {
        byte[] bArr = new byte[i];
        long j = 0;
        while (true) {
            int read = inputStream.read(bArr);
            if (-1 == read) {
                return j;
            }
            outputStream.write(bArr, 0, read);
            j += (long) read;
        }
    }
}
