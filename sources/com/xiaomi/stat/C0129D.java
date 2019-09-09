package com.xiaomi.stat;

/* renamed from: com.xiaomi.stat.D */
class C0129D implements Runnable {

    /* renamed from: a */
    final /* synthetic */ String f8a;

    /* renamed from: b */
    final /* synthetic */ String f9b;

    /* renamed from: c */
    final /* synthetic */ C0125A f10c;

    C0129D(C0125A a, String str, String str2) {
        this.f10c = a;
        this.f8a = str;
        this.f9b = str2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x008e  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0094  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r12 = this;
            r0 = 0
            com.xiaomi.stat.A r1 = r12.f10c     // Catch:{ Exception -> 0x0075 }
            android.database.sqlite.SQLiteOpenHelper r1 = r1.f5f     // Catch:{ Exception -> 0x0075 }
            android.database.sqlite.SQLiteDatabase r1 = r1.getWritableDatabase()     // Catch:{ Exception -> 0x0075 }
            java.lang.String r2 = r12.f8a     // Catch:{ Exception -> 0x0075 }
            boolean r2 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x0075 }
            r10 = 0
            r11 = 1
            if (r2 == 0) goto L_0x0023
            java.lang.String r2 = "pref"
            java.lang.String r3 = "pref_key=?"
            java.lang.String[] r4 = new java.lang.String[r11]     // Catch:{ Exception -> 0x0075 }
            java.lang.String r12 = r12.f9b     // Catch:{ Exception -> 0x0075 }
            r4[r10] = r12     // Catch:{ Exception -> 0x0075 }
            r1.delete(r2, r3, r4)     // Catch:{ Exception -> 0x0075 }
            return
        L_0x0023:
            java.lang.String r3 = "pref"
            r4 = 0
            java.lang.String r5 = "pref_key=?"
            java.lang.String[] r6 = new java.lang.String[r11]     // Catch:{ Exception -> 0x0075 }
            java.lang.String r2 = r12.f9b     // Catch:{ Exception -> 0x0075 }
            r6[r10] = r2     // Catch:{ Exception -> 0x0075 }
            r7 = 0
            r8 = 0
            r9 = 0
            r2 = r1
            android.database.Cursor r2 = r2.query(r3, r4, r5, r6, r7, r8, r9)     // Catch:{ Exception -> 0x0075 }
            int r3 = r2.getCount()     // Catch:{ Exception -> 0x0070, all -> 0x006d }
            if (r3 <= 0) goto L_0x003e
            r3 = r10
            goto L_0x003f
        L_0x003e:
            r3 = r11
        L_0x003f:
            android.content.ContentValues r4 = new android.content.ContentValues     // Catch:{ Exception -> 0x0070, all -> 0x006d }
            r4.<init>()     // Catch:{ Exception -> 0x0070, all -> 0x006d }
            java.lang.String r5 = "pref_key"
            java.lang.String r6 = r12.f9b     // Catch:{ Exception -> 0x0070, all -> 0x006d }
            r4.put(r5, r6)     // Catch:{ Exception -> 0x0070, all -> 0x006d }
            java.lang.String r5 = "pref_value"
            java.lang.String r6 = r12.f8a     // Catch:{ Exception -> 0x0070, all -> 0x006d }
            r4.put(r5, r6)     // Catch:{ Exception -> 0x0070, all -> 0x006d }
            if (r3 == 0) goto L_0x005a
            java.lang.String r12 = "pref"
            r1.insert(r12, r0, r4)     // Catch:{ Exception -> 0x0070, all -> 0x006d }
            goto L_0x0067
        L_0x005a:
            java.lang.String r0 = "pref"
            java.lang.String r3 = "pref_key=?"
            java.lang.String[] r5 = new java.lang.String[r11]     // Catch:{ Exception -> 0x0070, all -> 0x006d }
            java.lang.String r12 = r12.f9b     // Catch:{ Exception -> 0x0070, all -> 0x006d }
            r5[r10] = r12     // Catch:{ Exception -> 0x0070, all -> 0x006d }
            r1.update(r0, r4, r3, r5)     // Catch:{ Exception -> 0x0070, all -> 0x006d }
        L_0x0067:
            if (r2 == 0) goto L_0x0091
            r2.close()
            goto L_0x0091
        L_0x006d:
            r12 = move-exception
            r0 = r2
            goto L_0x0092
        L_0x0070:
            r12 = move-exception
            r0 = r2
            goto L_0x0076
        L_0x0073:
            r12 = move-exception
            goto L_0x0092
        L_0x0075:
            r12 = move-exception
        L_0x0076:
            java.lang.String r1 = "MiStatPref"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0073 }
            r2.<init>()     // Catch:{ all -> 0x0073 }
            java.lang.String r3 = "update pref db failed with "
            r2.append(r3)     // Catch:{ all -> 0x0073 }
            r2.append(r12)     // Catch:{ all -> 0x0073 }
            java.lang.String r12 = r2.toString()     // Catch:{ all -> 0x0073 }
            com.xiaomi.stat.p009d.C0177k.m322c(r1, r12)     // Catch:{ all -> 0x0073 }
            if (r0 == 0) goto L_0x0091
            r0.close()
        L_0x0091:
            return
        L_0x0092:
            if (r0 == 0) goto L_0x0097
            r0.close()
        L_0x0097:
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.stat.C0129D.run():void");
    }
}
