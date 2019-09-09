package com.xiaomi.stat;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.xiaomi.stat.p009d.C0177k;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RejectedExecutionException;

/* renamed from: com.xiaomi.stat.A */
public class C0125A {

    /* renamed from: d */
    private static C0125A f3d;

    /* renamed from: e */
    private Map<String, String> f4e = new HashMap();
    /* access modifiers changed from: private */

    /* renamed from: f */
    public SQLiteOpenHelper f5f;

    /* renamed from: com.xiaomi.stat.A$a */
    private static class C0126a extends SQLiteOpenHelper {
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        }

        public C0126a(Context context) {
            super(context, "mistat_pf", null, 1);
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL("CREATE TABLE pref (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,pref_key TEXT,pref_value TEXT)");
        }
    }

    /* renamed from: a */
    public static C0125A m8a() {
        if (f3d == null) {
            synchronized (C0125A.class) {
                if (f3d == null) {
                    f3d = new C0125A();
                }
            }
        }
        return f3d;
    }

    private C0125A() {
        Context a = C0131I.m27a();
        this.f5f = new C0126a(a);
        m9b();
        m11c(a.getDatabasePath("mistat_pf").getAbsolutePath());
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public void m9b() {
        FutureTask futureTask = new FutureTask(new C0127B(this));
        try {
            C0152c.m163a(futureTask);
            Cursor cursor = null;
            try {
                cursor = (Cursor) futureTask.get();
            } catch (InterruptedException | ExecutionException unused) {
            }
            if (cursor != null) {
                this.f4e.clear();
                try {
                    C0177k.m322c("MiStatPref", "load pref from db");
                    int columnIndex = cursor.getColumnIndex("pref_key");
                    int columnIndex2 = cursor.getColumnIndex("pref_value");
                    while (cursor.moveToNext()) {
                        String string = cursor.getString(columnIndex);
                        String string2 = cursor.getString(columnIndex2);
                        this.f4e.put(string, string2);
                        StringBuilder sb = new StringBuilder();
                        sb.append("key=");
                        sb.append(string);
                        sb.append(" ,value=");
                        sb.append(string2);
                        C0177k.m322c("MiStatPref", sb.toString());
                    }
                } catch (Exception unused2) {
                } catch (Throwable th) {
                    cursor.close();
                    throw th;
                }
                cursor.close();
            }
        } catch (RejectedExecutionException e) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("load data execute failed with ");
            sb2.append(e);
            C0177k.m322c("MiStatPref", sb2.toString());
        }
    }

    /* renamed from: c */
    private void m11c(String str) {
        new C0128C(this, str).startWatching();
    }

    /* renamed from: a */
    public int mo2346a(String str, int i) {
        synchronized (this) {
            if (this.f4e.containsKey(str)) {
                try {
                    int intValue = Integer.valueOf((String) this.f4e.get(str)).intValue();
                    return intValue;
                } catch (NumberFormatException unused) {
                    return i;
                }
            }
        }
    }

    /* renamed from: a */
    public String mo2348a(String str, String str2) {
        synchronized (this) {
            if (!this.f4e.containsKey(str)) {
                return str2;
            }
            String str3 = (String) this.f4e.get(str);
            return str3;
        }
    }

    /* renamed from: a */
    public long mo2347a(String str, long j) {
        synchronized (this) {
            if (this.f4e.containsKey(str)) {
                try {
                    long longValue = Long.valueOf((String) this.f4e.get(str)).longValue();
                    return longValue;
                } catch (NumberFormatException unused) {
                    return j;
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0028, code lost:
        return r3;
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean mo2350a(java.lang.String r2, boolean r3) {
        /*
            r1 = this;
            monitor-enter(r1)
            java.util.Map<java.lang.String, java.lang.String> r0 = r1.f4e     // Catch:{ all -> 0x0029 }
            boolean r0 = r0.containsKey(r2)     // Catch:{ all -> 0x0029 }
            if (r0 == 0) goto L_0x0027
            java.util.Map<java.lang.String, java.lang.String> r0 = r1.f4e     // Catch:{ all -> 0x0029 }
            java.lang.Object r2 = r0.get(r2)     // Catch:{ all -> 0x0029 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ all -> 0x0029 }
            java.lang.String r0 = "true"
            boolean r0 = r0.equalsIgnoreCase(r2)     // Catch:{ all -> 0x0029 }
            if (r0 == 0) goto L_0x001c
            r2 = 1
            monitor-exit(r1)     // Catch:{ all -> 0x0029 }
            return r2
        L_0x001c:
            java.lang.String r0 = "false"
            boolean r2 = r0.equalsIgnoreCase(r2)     // Catch:{ all -> 0x0029 }
            if (r2 == 0) goto L_0x0027
            r2 = 0
            monitor-exit(r1)     // Catch:{ all -> 0x0029 }
            return r2
        L_0x0027:
            monitor-exit(r1)     // Catch:{ all -> 0x0029 }
            return r3
        L_0x0029:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0029 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.stat.C0125A.mo2350a(java.lang.String, boolean):boolean");
    }

    /* renamed from: b */
    public void mo2352b(String str, int i) {
        m12c(str, Integer.toString(i));
    }

    /* renamed from: b */
    public void mo2354b(String str, String str2) {
        m12c(str, str2);
    }

    /* renamed from: b */
    public void mo2353b(String str, long j) {
        m12c(str, Long.toString(j));
    }

    /* renamed from: b */
    public void mo2355b(String str, boolean z) {
        m12c(str, Boolean.toString(z));
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(8:14|15|16|17|18|19|20|21) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x0050 */
    /* renamed from: c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m12c(java.lang.String r5, java.lang.String r6) {
        /*
            r4 = this;
            monitor-enter(r4)
            r0 = 1
            boolean r1 = android.text.TextUtils.isEmpty(r6)     // Catch:{ all -> 0x006b }
            if (r1 == 0) goto L_0x0018
            java.util.Map<java.lang.String, java.lang.String> r1 = r4.f4e     // Catch:{ all -> 0x006b }
            boolean r1 = r1.containsKey(r5)     // Catch:{ all -> 0x006b }
            if (r1 == 0) goto L_0x0016
            java.util.Map<java.lang.String, java.lang.String> r1 = r4.f4e     // Catch:{ all -> 0x006b }
            r1.remove(r5)     // Catch:{ all -> 0x006b }
            goto L_0x001d
        L_0x0016:
            r0 = 0
            goto L_0x001d
        L_0x0018:
            java.util.Map<java.lang.String, java.lang.String> r1 = r4.f4e     // Catch:{ all -> 0x006b }
            r1.put(r5, r6)     // Catch:{ all -> 0x006b }
        L_0x001d:
            java.lang.String r1 = "MiStatPref"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x006b }
            r2.<init>()     // Catch:{ all -> 0x006b }
            java.lang.String r3 = "put value: key="
            r2.append(r3)     // Catch:{ all -> 0x006b }
            r2.append(r5)     // Catch:{ all -> 0x006b }
            java.lang.String r3 = " ,value="
            r2.append(r3)     // Catch:{ all -> 0x006b }
            r2.append(r6)     // Catch:{ all -> 0x006b }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x006b }
            com.xiaomi.stat.p009d.C0177k.m322c(r1, r2)     // Catch:{ all -> 0x006b }
            if (r0 != 0) goto L_0x003f
            monitor-exit(r4)     // Catch:{ all -> 0x006b }
            return
        L_0x003f:
            com.xiaomi.stat.D r0 = new com.xiaomi.stat.D     // Catch:{ all -> 0x006b }
            r0.<init>(r4, r6, r5)     // Catch:{ all -> 0x006b }
            java.util.concurrent.FutureTask r5 = new java.util.concurrent.FutureTask     // Catch:{ all -> 0x006b }
            r6 = 0
            r5.<init>(r0, r6)     // Catch:{ all -> 0x006b }
            com.xiaomi.stat.C0152c.m163a(r5)     // Catch:{ RejectedExecutionException -> 0x0052 }
            r5.get()     // Catch:{ InterruptedException | ExecutionException -> 0x0050 }
        L_0x0050:
            monitor-exit(r4)     // Catch:{ all -> 0x006b }
            return
        L_0x0052:
            r5 = move-exception
            java.lang.String r6 = "MiStatPref"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x006b }
            r0.<init>()     // Catch:{ all -> 0x006b }
            java.lang.String r1 = "execute failed with "
            r0.append(r1)     // Catch:{ all -> 0x006b }
            r0.append(r5)     // Catch:{ all -> 0x006b }
            java.lang.String r5 = r0.toString()     // Catch:{ all -> 0x006b }
            com.xiaomi.stat.p009d.C0177k.m322c(r6, r5)     // Catch:{ all -> 0x006b }
            monitor-exit(r4)     // Catch:{ all -> 0x006b }
            return
        L_0x006b:
            r5 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x006b }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.stat.C0125A.m12c(java.lang.String, java.lang.String):void");
    }

    /* renamed from: a */
    public boolean mo2349a(String str) {
        boolean containsKey;
        synchronized (this) {
            containsKey = this.f4e.containsKey(str);
        }
        return containsKey;
    }

    /* renamed from: b */
    public void mo2351b(String str) {
        mo2354b(str, (String) null);
    }
}
