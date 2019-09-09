package com.xiaomi.stat;

import android.os.Handler;
import android.os.HandlerThread;
import com.xiaomi.stat.p009d.C0177k;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/* renamed from: com.xiaomi.stat.c */
public class C0152c {

    /* renamed from: b */
    private static String f83b = "mistat_db";

    /* renamed from: e */
    private static Handler f84e;

    /* renamed from: f */
    private static FileLock f85f;

    /* renamed from: g */
    private static FileChannel f86g;

    /* renamed from: com.xiaomi.stat.c$a */
    private static class C0153a implements Runnable {

        /* renamed from: a */
        private Runnable f87a;

        public C0153a(Runnable runnable) {
            this.f87a = runnable;
        }

        public void run() {
            if (C0152c.m167d()) {
                if (this.f87a != null) {
                    this.f87a.run();
                }
                C0152c.m168e();
            }
        }
    }

    /* renamed from: c */
    private static void m166c() {
        if (f84e == null) {
            synchronized (C0152c.class) {
                if (f84e == null) {
                    HandlerThread handlerThread = new HandlerThread(f83b);
                    handlerThread.start();
                    f84e = new Handler(handlerThread.getLooper());
                }
            }
        }
    }

    /* renamed from: a */
    public static void m163a(Runnable runnable) {
        m166c();
        f84e.post(new C0153a(runnable));
    }

    /* access modifiers changed from: private */
    /* renamed from: d */
    public static boolean m167d() {
        File file = new File(C0131I.m27a().getFilesDir(), "mistat");
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            f86g = new FileOutputStream(new File(file, "db.lk")).getChannel();
            try {
                f85f = f86g.lock();
                C0177k.m322c("DBExecutor", "acquire lock for db");
                return true;
            } catch (IOException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("acquire lock for db failed with ");
                sb.append(e);
                C0177k.m322c("DBExecutor", sb.toString());
                try {
                    f86g.close();
                    f86g = null;
                } catch (IOException unused) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("close file stream failed with ");
                    sb2.append(e);
                    C0177k.m322c("DBExecutor", sb2.toString());
                }
                return false;
            }
        } catch (FileNotFoundException e2) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("acquire lock for db failed with ");
            sb3.append(e2);
            C0177k.m322c("DBExecutor", sb3.toString());
            return false;
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: e */
    public static void m168e() {
        if (f85f != null) {
            try {
                f85f.release();
                f85f = null;
                C0177k.m322c("DBExecutor", "release lock for db");
                f86g.close();
                f86g = null;
            } catch (IOException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("release lock for db failed with ");
                sb.append(e);
                C0177k.m322c("DBExecutor", sb.toString());
            }
        }
    }
}
