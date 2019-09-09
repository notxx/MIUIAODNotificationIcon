package com.xiaomi.stat.p006a;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* renamed from: com.xiaomi.stat.a.a */
public class C0133a extends SQLiteOpenHelper {
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public C0133a(Context context) {
        super(context, "mistat_ev", null, 1);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE events (_id INTEGER PRIMARY KEY AUTOINCREMENT,e TEXT,eg TEXT,tp TEXT,ps TEXT,ts INTEGER,sub TEXT,is_am INTEGER,priority INTEGER)");
    }
}
