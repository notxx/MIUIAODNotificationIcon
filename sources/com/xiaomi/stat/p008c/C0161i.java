package com.xiaomi.stat.p008c;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.xiaomi.stat.C0131I;
import com.xiaomi.stat.C0143b;
import com.xiaomi.stat.p006a.C0134b;
import com.xiaomi.stat.p007b.C0147d;
import com.xiaomi.stat.p007b.C0151h;
import com.xiaomi.stat.p009d.C0165c;
import com.xiaomi.stat.p009d.C0166d;
import com.xiaomi.stat.p009d.C0167e;
import com.xiaomi.stat.p009d.C0176j;
import com.xiaomi.stat.p009d.C0177k;
import com.xiaomi.stat.p009d.C0178l;
import com.xiaomi.stat.p009d.C0180m;
import com.xiaomi.stat.p009d.C0184r;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.GZIPOutputStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.xiaomi.stat.c.i */
public class C0161i {

    /* renamed from: m */
    private static volatile C0161i f107m;

    /* renamed from: n */
    private final byte[] f108n = new byte[0];

    /* renamed from: o */
    private FileLock f109o;

    /* renamed from: p */
    private FileChannel f110p;

    /* renamed from: q */
    private C0159g f111q;

    /* renamed from: r */
    private C0162a f112r;

    /* renamed from: com.xiaomi.stat.c.i$a */
    private class C0162a extends Handler {
        public C0162a(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what == 1) {
                C0161i.this.m214f();
            }
        }
    }

    /* renamed from: a */
    private int m197a(int i) {
        int i2 = 1;
        if (i == 1) {
            return -1;
        }
        if (i == 3) {
            i2 = 0;
        }
        return i2;
    }

    /* renamed from: a */
    public static C0161i m198a() {
        if (f107m == null) {
            synchronized (C0161i.class) {
                if (f107m == null) {
                    f107m = new C0161i();
                }
            }
        }
        return f107m;
    }

    private C0161i() {
        m213e();
    }

    /* renamed from: e */
    private void m213e() {
        HandlerThread handlerThread = new HandlerThread("mi_analytics_uploader_worker");
        handlerThread.start();
        this.f112r = new C0162a(handlerThread.getLooper());
        this.f111q = new C0159g(handlerThread.getLooper());
    }

    /* renamed from: b */
    public void mo2425b() {
        this.f111q.mo2419b();
        mo2426c();
    }

    /* renamed from: c */
    public void mo2426c() {
        if (!C0178l.m328a()) {
            C0177k.m318b("UploaderEngine postToServer network is not connected ");
        } else if (!C0143b.m73a() || !C0143b.m75b()) {
            C0177k.m318b("UploaderEngine postToServer statistic disable or network disable access! ");
        } else {
            Message obtain = Message.obtain();
            obtain.what = 1;
            m201a(obtain);
        }
    }

    /* renamed from: a */
    private void m201a(Message message) {
        synchronized (this.f108n) {
            if (this.f112r == null || this.f111q == null) {
                m213e();
            }
            this.f112r.sendMessage(message);
        }
    }

    /* renamed from: a */
    public static byte[] m205a(String str) {
        GZIPOutputStream gZIPOutputStream;
        ByteArrayOutputStream byteArrayOutputStream;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream(str.getBytes("UTF-8").length);
            try {
                gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            } catch (Exception e) {
                e = e;
                gZIPOutputStream = null;
                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append("UploaderEngine zipData failed! ");
                    sb.append(e.toString());
                    C0177k.m324e(sb.toString());
                    C0176j.m310a((OutputStream) byteArrayOutputStream);
                    C0176j.m310a((OutputStream) gZIPOutputStream);
                    return null;
                } catch (Throwable th) {
                    th = th;
                    C0176j.m310a((OutputStream) byteArrayOutputStream);
                    C0176j.m310a((OutputStream) gZIPOutputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                gZIPOutputStream = null;
                C0176j.m310a((OutputStream) byteArrayOutputStream);
                C0176j.m310a((OutputStream) gZIPOutputStream);
                throw th;
            }
            try {
                gZIPOutputStream.write(str.getBytes("UTF-8"));
                gZIPOutputStream.finish();
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                C0176j.m310a((OutputStream) byteArrayOutputStream);
                C0176j.m310a((OutputStream) gZIPOutputStream);
                return byteArray;
            } catch (Exception e2) {
                e = e2;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("UploaderEngine zipData failed! ");
                sb2.append(e.toString());
                C0177k.m324e(sb2.toString());
                C0176j.m310a((OutputStream) byteArrayOutputStream);
                C0176j.m310a((OutputStream) gZIPOutputStream);
                return null;
            }
        } catch (Exception e3) {
            e = e3;
            byteArrayOutputStream = null;
            gZIPOutputStream = null;
            StringBuilder sb22 = new StringBuilder();
            sb22.append("UploaderEngine zipData failed! ");
            sb22.append(e.toString());
            C0177k.m324e(sb22.toString());
            C0176j.m310a((OutputStream) byteArrayOutputStream);
            C0176j.m310a((OutputStream) gZIPOutputStream);
            return null;
        } catch (Throwable th3) {
            th = th3;
            byteArrayOutputStream = null;
            gZIPOutputStream = null;
            C0176j.m310a((OutputStream) byteArrayOutputStream);
            C0176j.m310a((OutputStream) gZIPOutputStream);
            throw th;
        }
    }

    /* renamed from: a */
    private byte[] m206a(byte[] bArr) {
        return C0151h.m148a().mo2408a(bArr);
    }

    /* renamed from: b */
    private String m207b(byte[] bArr) {
        return C0166d.m234a(bArr);
    }

    /* access modifiers changed from: private */
    /* renamed from: f */
    public void m214f() {
        if (m216h()) {
            if (C0143b.m87e()) {
                m208b(true);
                m208b(false);
            } else {
                m204a(m211c(false), C0147d.m122a().mo2394c());
            }
            m217i();
        }
    }

    /* renamed from: b */
    private void m208b(boolean z) {
        m204a(m211c(z), C0147d.m122a().mo2392a(z));
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x00b9  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00c1  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00fa  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00fc A[SYNTHETIC] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m204a(com.xiaomi.stat.p006a.C0134b[] r10, java.lang.String r11) {
        /*
            r9 = this;
            int r0 = r10.length
            if (r0 != 0) goto L_0x000b
            java.lang.String r9 = "UploaderEngine"
            java.lang.String r10 = "privacy policy or network state not matched"
            com.xiaomi.stat.p009d.C0177k.m322c(r9, r10)
            return
        L_0x000b:
            com.xiaomi.stat.a.c r0 = com.xiaomi.stat.p006a.C0135c.m41a()
            com.xiaomi.stat.a.k r0 = r0.mo2377a(r10)
            java.util.concurrent.atomic.AtomicInteger r1 = new java.util.concurrent.atomic.AtomicInteger
            r1.<init>()
            r2 = 1
            if (r0 == 0) goto L_0x001e
            boolean r3 = r0.f36c
            goto L_0x001f
        L_0x001e:
            r3 = r2
        L_0x001f:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "UploaderEngine"
            r4.append(r5)
            r4.append(r0)
            java.lang.String r4 = r4.toString()
            com.xiaomi.stat.p009d.C0177k.m318b(r4)
            r4 = 0
            r5 = r3
            r3 = r4
        L_0x0036:
            if (r0 == 0) goto L_0x0101
            java.util.ArrayList<java.lang.Long> r3 = r0.f35b
            org.json.JSONArray r0 = r0.f34a
            java.lang.String r0 = r9.m199a(r0, r11)     // Catch:{ Exception -> 0x00b6 }
            boolean r6 = com.xiaomi.stat.p009d.C0177k.m316a()     // Catch:{ Exception -> 0x00b6 }
            if (r6 == 0) goto L_0x005a
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00b6 }
            r6.<init>()     // Catch:{ Exception -> 0x00b6 }
            java.lang.String r7 = "UploaderEngine payload:"
            r6.append(r7)     // Catch:{ Exception -> 0x00b6 }
            r6.append(r0)     // Catch:{ Exception -> 0x00b6 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x00b6 }
            com.xiaomi.stat.p009d.C0177k.m318b(r6)     // Catch:{ Exception -> 0x00b6 }
        L_0x005a:
            byte[] r0 = m205a(r0)     // Catch:{ Exception -> 0x00b6 }
            byte[] r0 = r9.m206a(r0)     // Catch:{ Exception -> 0x00b6 }
            java.lang.String r0 = r9.m207b(r0)     // Catch:{ Exception -> 0x00b6 }
            boolean r6 = com.xiaomi.stat.p009d.C0177k.m316a()     // Catch:{ Exception -> 0x00b6 }
            if (r6 == 0) goto L_0x0080
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00b6 }
            r6.<init>()     // Catch:{ Exception -> 0x00b6 }
            java.lang.String r7 = "UploaderEngine encodePayload "
            r6.append(r7)     // Catch:{ Exception -> 0x00b6 }
            r6.append(r0)     // Catch:{ Exception -> 0x00b6 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x00b6 }
            com.xiaomi.stat.p009d.C0177k.m318b(r6)     // Catch:{ Exception -> 0x00b6 }
        L_0x0080:
            com.xiaomi.stat.b.f r6 = com.xiaomi.stat.p007b.C0149f.m133a()     // Catch:{ Exception -> 0x00b6 }
            java.lang.String r6 = r6.mo2402c()     // Catch:{ Exception -> 0x00b6 }
            java.util.HashMap r0 = r9.m210c(r0)     // Catch:{ Exception -> 0x00b6 }
            java.lang.String r0 = com.xiaomi.stat.p008c.C0155c.m172a(r6, r0, r2)     // Catch:{ Exception -> 0x00b6 }
            boolean r6 = com.xiaomi.stat.p009d.C0177k.m316a()     // Catch:{ Exception -> 0x00b6 }
            if (r6 == 0) goto L_0x00aa
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00b6 }
            r6.<init>()     // Catch:{ Exception -> 0x00b6 }
            java.lang.String r7 = "UploaderEngine sendDataToServer response: "
            r6.append(r7)     // Catch:{ Exception -> 0x00b6 }
            r6.append(r0)     // Catch:{ Exception -> 0x00b6 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x00b6 }
            com.xiaomi.stat.p009d.C0177k.m318b(r6)     // Catch:{ Exception -> 0x00b6 }
        L_0x00aa:
            boolean r6 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x00b6 }
            if (r6 == 0) goto L_0x00b1
            goto L_0x00b6
        L_0x00b1:
            boolean r0 = r9.m209b(r0)     // Catch:{ Exception -> 0x00b6 }
            goto L_0x00b7
        L_0x00b6:
            r0 = r4
        L_0x00b7:
            if (r0 == 0) goto L_0x00c1
            com.xiaomi.stat.a.c r6 = com.xiaomi.stat.p006a.C0135c.m41a()
            r6.mo2379a(r3)
            goto L_0x00c4
        L_0x00c1:
            r1.addAndGet(r2)
        L_0x00c4:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r6 = "UploaderEngine deleteData= "
            r3.append(r6)
            r3.append(r0)
            java.lang.String r6 = " retryCount.get()= "
            r3.append(r6)
            int r6 = r1.get()
            r3.append(r6)
            java.lang.String r3 = r3.toString()
            com.xiaomi.stat.p009d.C0177k.m318b(r3)
            if (r5 != 0) goto L_0x0102
            if (r0 != 0) goto L_0x00f0
            int r3 = r1.get()
            r6 = 3
            if (r3 <= r6) goto L_0x00f0
            goto L_0x0102
        L_0x00f0:
            com.xiaomi.stat.a.c r3 = com.xiaomi.stat.p006a.C0135c.m41a()
            com.xiaomi.stat.a.k r3 = r3.mo2377a(r10)
            if (r3 == 0) goto L_0x00fc
            boolean r5 = r3.f36c
        L_0x00fc:
            r8 = r3
            r3 = r0
            r0 = r8
            goto L_0x0036
        L_0x0101:
            r0 = r3
        L_0x0102:
            com.xiaomi.stat.c.g r10 = r9.f111q
            if (r10 == 0) goto L_0x010b
            com.xiaomi.stat.c.g r9 = r9.f111q
            r9.mo2420b(r0)
        L_0x010b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.stat.p008c.C0161i.m204a(com.xiaomi.stat.a.b[], java.lang.String):void");
    }

    /* renamed from: b */
    private boolean m209b(String str) {
        try {
            int optInt = new JSONObject(str).optInt("code");
            if (optInt != 200) {
                if (!(optInt == 1002 || optInt == 1004 || optInt == 1005 || optInt == 1006 || optInt == 1007)) {
                    if (optInt != 1011) {
                        if (optInt == 2002) {
                            C0151h.m148a().mo2407a(true);
                            C0147d.m122a().mo2393b();
                        }
                    }
                }
                C0151h.m148a().mo2407a(true);
                C0147d.m122a().mo2393b();
                return false;
            }
            return true;
        } catch (Exception e) {
            C0177k.m326e("UploaderEngine", "parseUploadingResult exception ", e);
            return false;
        }
    }

    /* renamed from: c */
    private C0134b[] m211c(boolean z) {
        ArrayList g = m215g();
        int size = g.size();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < size; i++) {
            String str = (String) g.get(i);
            int a = m197a(new C0158f(str, z).mo2416a());
            if (a != -1) {
                arrayList.add(new C0134b(str, a, z));
            }
        }
        C0134b d = m212d(z);
        if (d != null) {
            arrayList.add(d);
        }
        return (C0134b[]) arrayList.toArray(new C0134b[arrayList.size()]);
    }

    /* renamed from: d */
    private C0134b m212d(boolean z) {
        int a = new C0158f(z).mo2416a();
        StringBuilder sb = new StringBuilder();
        sb.append("UploaderEngine createMainAppFilter: ");
        sb.append(a);
        C0177k.m318b(sb.toString());
        int a2 = m197a(a);
        if (a2 != -1) {
            return new C0134b(null, a2, z);
        }
        return null;
    }

    /* renamed from: g */
    private ArrayList<String> m215g() {
        String[] o = C0143b.m103o();
        int length = o != null ? o.length : 0;
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            if (!TextUtils.isEmpty(o[i])) {
                arrayList.add(o[i]);
            }
        }
        return arrayList;
    }

    /* renamed from: c */
    private HashMap<String, String> m210c(String str) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("ai", C0131I.m29b());
        hashMap.put("sv", "3.0.8");
        hashMap.put("pv", "3.0");
        hashMap.put("rg", C0180m.m342g());
        hashMap.put("p", str);
        hashMap.put("fc", C0151h.m148a().mo2410c());
        hashMap.put("sid", C0151h.m148a().mo2409b());
        return hashMap;
    }

    /* renamed from: a */
    private String m199a(JSONArray jSONArray, String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("id", str);
            m203a(str, jSONObject);
            jSONObject.put("aii", C0167e.m249d());
            jSONObject.put("rc", C0180m.m343h());
            jSONObject.put("av", C0165c.m232b());
            jSONObject.put("ac", C0143b.m108t());
            jSONObject.put("os", "Android");
            jSONObject.put("rd", C0180m.m334a(C0131I.m27a()));
            jSONObject.put("pp", this.f111q != null ? this.f111q.mo2417a() : 0);
            jSONObject.put("st", String.valueOf(C0184r.m385b()));
            jSONObject.put("tz", C0180m.m341e());
            jSONObject.put("cc", C0154a.m169a(C0131I.m29b()));
            String[] o = C0143b.m103o();
            if (o != null && o.length > 0) {
                jSONObject.put("cs", m200a(o));
            }
            jSONObject.put("ob", C0180m.m340d());
            jSONObject.put("n", C0178l.m329b(C0131I.m27a()));
            jSONObject.put("ud", C0143b.m94h());
            jSONObject.put("es", jSONArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    /* renamed from: a */
    private void m203a(String str, JSONObject jSONObject) {
        try {
            if (!C0143b.m87e() && TextUtils.isEmpty(str)) {
                Context a = C0131I.m27a();
                jSONObject.put("ia", C0167e.m244b(a));
                jSONObject.put("mcm", C0167e.m263k(a));
                jSONObject.put("bm", C0167e.m266n(a));
                jSONObject.put("aa", C0167e.m269q(a));
                jSONObject.put("ai", C0167e.m268p(a));
            }
        } catch (Exception unused) {
        }
    }

    /* renamed from: a */
    private JSONArray m200a(String[] strArr) {
        JSONArray jSONArray = new JSONArray();
        for (int i = 0; i < strArr.length; i++) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(strArr[i], C0154a.m169a(strArr[i]));
                jSONArray.put(jSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jSONArray;
    }

    /* renamed from: d */
    public synchronized void mo2427d() {
        if (this.f111q != null) {
            this.f111q.mo2421c();
        }
    }

    /* renamed from: a */
    public void mo2424a(boolean z) {
        if (this.f111q != null) {
            this.f111q.mo2418a(z);
        }
    }

    /* renamed from: h */
    private boolean m216h() {
        File file = new File(C0131I.m27a().getFilesDir(), "mistat");
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            this.f110p = new FileOutputStream(new File(file, "uploader")).getChannel();
            try {
                this.f109o = this.f110p.tryLock();
                if (this.f109o != null) {
                    C0177k.m321c("UploaderEngine acquire lock for uploader");
                    if (this.f109o == null) {
                        try {
                            this.f110p.close();
                            this.f110p = null;
                        } catch (IOException unused) {
                        }
                    }
                    return true;
                }
                C0177k.m321c("UploaderEngine acquire lock for uploader failed");
                if (this.f109o == null) {
                    try {
                        this.f110p.close();
                        this.f110p = null;
                    } catch (IOException unused2) {
                    }
                }
                return false;
            } catch (IOException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("UploaderEngine acquire lock for uploader failed with ");
                sb.append(e);
                C0177k.m321c(sb.toString());
                if (this.f109o == null) {
                    try {
                        this.f110p.close();
                        this.f110p = null;
                    } catch (IOException unused3) {
                    }
                }
                return false;
            } catch (Throwable th) {
                if (this.f109o == null) {
                    try {
                        this.f110p.close();
                        this.f110p = null;
                    } catch (IOException unused4) {
                    }
                }
                throw th;
            }
        } catch (FileNotFoundException e2) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("UploaderEngine acquire lock for uploader failed with ");
            sb2.append(e2);
            C0177k.m321c(sb2.toString());
            return false;
        }
    }

    /* renamed from: i */
    private void m217i() {
        try {
            if (this.f109o != null) {
                this.f109o.release();
                this.f109o = null;
            }
            if (this.f110p != null) {
                this.f110p.close();
                this.f110p = null;
            }
            C0177k.m321c("UploaderEngine releaseLock lock for uploader");
        } catch (IOException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("UploaderEngine releaseLock lock for uploader failed with ");
            sb.append(e);
            C0177k.m321c(sb.toString());
        }
    }
}
