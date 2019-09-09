package com.xiaomi.stat.p006a;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.text.TextUtils;
import com.xiaomi.stat.C0131I;
import com.xiaomi.stat.C0152c;
import com.xiaomi.stat.MiStatParams;
import com.xiaomi.stat.p009d.C0165c;
import com.xiaomi.stat.p009d.C0177k;
import com.xiaomi.stat.p009d.C0178l;
import com.xiaomi.stat.p009d.C0180m;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/* renamed from: com.xiaomi.stat.a.c */
public class C0135c {

    /* renamed from: k */
    private static C0135c f23k;
    /* access modifiers changed from: private */

    /* renamed from: l */
    public C0133a f24l;

    /* renamed from: m */
    private File f25m;

    /* renamed from: a */
    public static C0135c m41a() {
        if (f23k == null) {
            synchronized (C0135c.class) {
                if (f23k == null) {
                    f23k = new C0135c();
                }
            }
        }
        return f23k;
    }

    private C0135c() {
        Context a = C0131I.m27a();
        this.f24l = new C0133a(a);
        this.f25m = a.getDatabasePath("mistat_ev");
    }

    /* renamed from: a */
    public void mo2378a(C0142l lVar) {
        C0152c.m163a(new C0136d(this, lVar));
        StringBuilder sb = new StringBuilder();
        sb.append("add event: name=");
        sb.append(lVar.f37a);
        C0177k.m322c("EventManager", sb.toString());
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public void m48b(C0142l lVar) {
        m51d();
        SQLiteDatabase writableDatabase = this.f24l.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("e", lVar.f37a);
        contentValues.put("eg", lVar.f38b);
        contentValues.put("tp", lVar.f39c);
        contentValues.put("ts", Long.valueOf(lVar.f41e));
        if (m50c(lVar)) {
            m43a((MiStatParams) lVar.f40d);
        }
        contentValues.put("ps", lVar.f40d.toJsonString());
        contentValues.put("sub", lVar.f42f);
        contentValues.put("is_am", Integer.valueOf(lVar.f43g ? 1 : 0));
        contentValues.put("priority", Integer.valueOf(TextUtils.equals(lVar.f38b, "mistat_basic") ? 10 : 0));
        writableDatabase.insert("events", null, contentValues);
    }

    /* renamed from: c */
    private boolean m50c(C0142l lVar) {
        return !lVar.f39c.startsWith("profile_");
    }

    /* renamed from: a */
    private void m43a(MiStatParams miStatParams) {
        miStatParams.putString("mi_av", C0165c.m232b());
        miStatParams.putString("mi_sv", "3.0.8");
        miStatParams.putString("mi_ov", C0180m.m339c());
        miStatParams.putString("mi_ob", C0180m.m340d());
        miStatParams.putString("mi_n", C0178l.m329b(C0131I.m27a()));
        miStatParams.putString("mi_rd", C0180m.m334a(C0131I.m27a()));
        miStatParams.putString("mi_mf", Build.MANUFACTURER);
        miStatParams.putString("mi_m", Build.MODEL);
        miStatParams.putString("mi_os", C0180m.m337b());
    }

    /* renamed from: a */
    public C0141k mo2377a(C0134b[] bVarArr) {
        FutureTask futureTask = new FutureTask(new C0137e(this, bVarArr));
        C0152c.m163a(futureTask);
        try {
            return (C0141k) futureTask.get();
        } catch (InterruptedException | ExecutionException unused) {
            return null;
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Can't wrap try/catch for region: R(7:28|29|30|31|32|33|(2:66|35)(2:36|69)) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:32:0x0109 */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0116 A[Catch:{ Exception -> 0x0147, all -> 0x0145 }] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x015a  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0163  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x0111 A[SYNTHETIC] */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.xiaomi.stat.p006a.C0141k m47b(com.xiaomi.stat.p006a.C0134b[] r28) {
        /*
            r27 = this;
            r0 = r27
            r1 = r28
            int r3 = r1.length     // Catch:{ Exception -> 0x014d, all -> 0x014a }
            r4 = 0
            r5 = 1
            if (r3 != r5) goto L_0x0012
            r3 = r1[r4]     // Catch:{ Exception -> 0x014d, all -> 0x014a }
            java.lang.String r3 = r3.mo2375a()     // Catch:{ Exception -> 0x014d, all -> 0x014a }
            r9 = r3
            r3 = r4
            goto L_0x0014
        L_0x0012:
            r3 = r5
            r9 = 0
        L_0x0014:
            com.xiaomi.stat.a.a r6 = r0.f24l     // Catch:{ Exception -> 0x014d, all -> 0x014a }
            android.database.sqlite.SQLiteDatabase r6 = r6.getReadableDatabase()     // Catch:{ Exception -> 0x014d, all -> 0x014a }
            java.lang.String r7 = "events"
            r8 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            java.lang.String r13 = "priority DESC, _id ASC"
            android.database.Cursor r6 = r6.query(r7, r8, r9, r10, r11, r12, r13)     // Catch:{ Exception -> 0x014d, all -> 0x014a }
            java.lang.String r7 = "_id"
            int r7 = r6.getColumnIndex(r7)     // Catch:{ Exception -> 0x0147, all -> 0x0145 }
            java.lang.String r8 = "e"
            int r8 = r6.getColumnIndex(r8)     // Catch:{ Exception -> 0x0147, all -> 0x0145 }
            java.lang.String r9 = "eg"
            int r9 = r6.getColumnIndex(r9)     // Catch:{ Exception -> 0x0147, all -> 0x0145 }
            java.lang.String r10 = "tp"
            int r10 = r6.getColumnIndex(r10)     // Catch:{ Exception -> 0x0147, all -> 0x0145 }
            java.lang.String r11 = "ts"
            int r11 = r6.getColumnIndex(r11)     // Catch:{ Exception -> 0x0147, all -> 0x0145 }
            java.lang.String r12 = "ps"
            int r12 = r6.getColumnIndex(r12)     // Catch:{ Exception -> 0x0147, all -> 0x0145 }
            java.lang.String r13 = "sub"
            int r13 = r6.getColumnIndex(r13)     // Catch:{ Exception -> 0x0147, all -> 0x0145 }
            java.lang.String r14 = "is_am"
            int r14 = r6.getColumnIndex(r14)     // Catch:{ Exception -> 0x0147, all -> 0x0145 }
            org.json.JSONArray r15 = new org.json.JSONArray     // Catch:{ Exception -> 0x0147, all -> 0x0145 }
            r15.<init>()     // Catch:{ Exception -> 0x0147, all -> 0x0145 }
            java.util.ArrayList r4 = new java.util.ArrayList     // Catch:{ Exception -> 0x0147, all -> 0x0145 }
            r4.<init>()     // Catch:{ Exception -> 0x0147, all -> 0x0145 }
            r16 = 0
        L_0x0062:
            boolean r17 = r6.moveToNext()     // Catch:{ Exception -> 0x0147, all -> 0x0145 }
            if (r17 == 0) goto L_0x012d
            r18 = r3
            long r2 = r6.getLong(r7)     // Catch:{ Exception -> 0x0147, all -> 0x0145 }
            java.lang.String r5 = r6.getString(r8)     // Catch:{ Exception -> 0x0147, all -> 0x0145 }
            r19 = r7
            java.lang.String r7 = r6.getString(r9)     // Catch:{ Exception -> 0x0147, all -> 0x0145 }
            r20 = r8
            java.lang.String r8 = r6.getString(r10)     // Catch:{ Exception -> 0x0147, all -> 0x0145 }
            r21 = r9
            r22 = r10
            long r9 = r6.getLong(r11)     // Catch:{ Exception -> 0x0147, all -> 0x0145 }
            r23 = r11
            java.lang.String r11 = r6.getString(r12)     // Catch:{ Exception -> 0x0147, all -> 0x0145 }
            r24 = r12
            java.lang.String r12 = r6.getString(r13)     // Catch:{ Exception -> 0x0147, all -> 0x0145 }
            r25 = r13
            int r13 = r6.getInt(r14)     // Catch:{ Exception -> 0x0147, all -> 0x0145 }
            r26 = r14
            r14 = 1
            if (r13 != r14) goto L_0x009f
            r13 = r14
            goto L_0x00a0
        L_0x009f:
            r13 = 0
        L_0x00a0:
            if (r18 == 0) goto L_0x00a8
            boolean r12 = r0.m46a(r1, r12, r7, r13)     // Catch:{ Exception -> 0x0147, all -> 0x0145 }
            if (r12 == 0) goto L_0x0118
        L_0x00a8:
            int r12 = r11.length()     // Catch:{ Exception -> 0x0147, all -> 0x0145 }
            int r12 = r12 * 2
            int r12 = r12 + 55
            int r16 = r16 + r12
            boolean r12 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Exception -> 0x0147, all -> 0x0145 }
            if (r12 != 0) goto L_0x00c0
            int r12 = r5.length()     // Catch:{ Exception -> 0x0147, all -> 0x0145 }
            int r12 = r12 * 2
            int r16 = r16 + r12
        L_0x00c0:
            boolean r12 = android.text.TextUtils.isEmpty(r7)     // Catch:{ Exception -> 0x0147, all -> 0x0145 }
            if (r12 != 0) goto L_0x00ce
            int r12 = r7.length()     // Catch:{ Exception -> 0x0147, all -> 0x0145 }
            int r12 = r12 * 2
            int r16 = r16 + r12
        L_0x00ce:
            r12 = r16
            r13 = 122880(0x1e000, float:1.72192E-40)
            if (r12 <= r13) goto L_0x00d7
            r0 = 0
            goto L_0x012e
        L_0x00d7:
            org.json.JSONObject r13 = new org.json.JSONObject     // Catch:{ Exception -> 0x0147, all -> 0x0145 }
            r13.<init>()     // Catch:{ Exception -> 0x0147, all -> 0x0145 }
            java.lang.String r14 = "e"
            r13.put(r14, r5)     // Catch:{ JSONException -> 0x0109 }
            java.lang.String r5 = "eg"
            r13.put(r5, r7)     // Catch:{ JSONException -> 0x0109 }
            java.lang.String r5 = "tp"
            r13.put(r5, r8)     // Catch:{ JSONException -> 0x0109 }
            java.lang.String r5 = "ts"
            r13.put(r5, r9)     // Catch:{ JSONException -> 0x0109 }
            java.lang.String r5 = "eid"
            r13.put(r5, r2)     // Catch:{ JSONException -> 0x0109 }
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0109 }
            r5.<init>(r11)     // Catch:{ JSONException -> 0x0109 }
            java.lang.String r7 = "ps"
            r13.put(r7, r5)     // Catch:{ JSONException -> 0x0109 }
            r15.put(r13)     // Catch:{ JSONException -> 0x0109 }
            java.lang.Long r2 = java.lang.Long.valueOf(r2)     // Catch:{ JSONException -> 0x0109 }
            r4.add(r2)     // Catch:{ JSONException -> 0x0109 }
        L_0x0109:
            int r2 = r4.size()     // Catch:{ Exception -> 0x0147, all -> 0x0145 }
            r3 = 300(0x12c, float:4.2E-43)
            if (r2 < r3) goto L_0x0116
            boolean r0 = r6.isLast()     // Catch:{ Exception -> 0x0147, all -> 0x0145 }
            goto L_0x012e
        L_0x0116:
            r16 = r12
        L_0x0118:
            r3 = r18
            r7 = r19
            r8 = r20
            r9 = r21
            r10 = r22
            r11 = r23
            r12 = r24
            r13 = r25
            r14 = r26
            r5 = 1
            goto L_0x0062
        L_0x012d:
            r0 = 1
        L_0x012e:
            int r1 = r4.size()     // Catch:{ Exception -> 0x0147, all -> 0x0145 }
            if (r1 <= 0) goto L_0x013f
            com.xiaomi.stat.a.k r1 = new com.xiaomi.stat.a.k     // Catch:{ Exception -> 0x0147, all -> 0x0145 }
            r1.<init>(r15, r4, r0)     // Catch:{ Exception -> 0x0147, all -> 0x0145 }
            if (r6 == 0) goto L_0x013e
            r6.close()
        L_0x013e:
            return r1
        L_0x013f:
            if (r6 == 0) goto L_0x015d
            r6.close()
            goto L_0x015d
        L_0x0145:
            r0 = move-exception
            goto L_0x0161
        L_0x0147:
            r0 = move-exception
            r2 = r6
            goto L_0x014f
        L_0x014a:
            r0 = move-exception
            r6 = 0
            goto L_0x0161
        L_0x014d:
            r0 = move-exception
            r2 = 0
        L_0x014f:
            java.lang.String r1 = "EventManager"
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x015f }
            com.xiaomi.stat.p009d.C0177k.m319b(r1, r0)     // Catch:{ all -> 0x015f }
            if (r2 == 0) goto L_0x015d
            r2.close()
        L_0x015d:
            r1 = 0
            return r1
        L_0x015f:
            r0 = move-exception
            r6 = r2
        L_0x0161:
            if (r6 == 0) goto L_0x0166
            r6.close()
        L_0x0166:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.stat.p006a.C0135c.m47b(com.xiaomi.stat.a.b[]):com.xiaomi.stat.a.k");
    }

    /* renamed from: a */
    private boolean m46a(C0134b[] bVarArr, String str, String str2, boolean z) {
        for (C0134b a : bVarArr) {
            if (a.mo2376a(str, str2, z)) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: a */
    public void mo2379a(ArrayList<Long> arrayList) {
        FutureTask futureTask = new FutureTask(new C0138f(this, arrayList), null);
        C0152c.m163a(futureTask);
        try {
            futureTask.get();
        } catch (InterruptedException | ExecutionException unused) {
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public void m49b(ArrayList<Long> arrayList) {
        if (arrayList != null && arrayList.size() != 0) {
            try {
                SQLiteDatabase writableDatabase = this.f24l.getWritableDatabase();
                StringBuilder sb = new StringBuilder(((Long.toString(((Long) arrayList.get(0)).longValue()).length() + 1) * arrayList.size()) + 16);
                sb.append("_id");
                sb.append(" in (");
                sb.append(arrayList.get(0));
                int size = arrayList.size();
                for (int i = 1; i < size; i++) {
                    sb.append(",");
                    sb.append(arrayList.get(i));
                }
                sb.append(")");
                int delete = writableDatabase.delete("events", sb.toString(), null);
                StringBuilder sb2 = new StringBuilder();
                sb2.append("deleted events number ");
                sb2.append(delete);
                C0177k.m322c("EventManager", sb2.toString());
            } catch (Exception unused) {
            }
        }
    }

    /* renamed from: b */
    public void mo2380b() {
        C0152c.m163a(new C0139g(this));
    }

    /* renamed from: d */
    private void m51d() {
        if (this.f25m.exists() && this.f25m.length() >= 52428800) {
            StringBuilder sb = new StringBuilder();
            sb.append("database too big: ");
            sb.append(this.f25m.length());
            C0177k.m325e("EventManager", sb.toString());
            this.f24l.getWritableDatabase().delete("events", null, null);
        }
    }

    /* renamed from: c */
    public long mo2381c() {
        FutureTask futureTask = new FutureTask(new C0140i(this));
        C0152c.m163a(futureTask);
        try {
            return ((Long) futureTask.get()).longValue();
        } catch (InterruptedException | ExecutionException unused) {
            return -1;
        }
    }
}
