package com.xiaomi.stat.p008c;

import android.os.IBinder;
import android.os.RemoteException;
import com.xiaomi.p003a.p004a.p005a.C0122a;
import com.xiaomi.p003a.p004a.p005a.C0122a.C0123a;
import com.xiaomi.stat.C0143b;
import com.xiaomi.stat.p009d.C0177k;

/* renamed from: com.xiaomi.stat.c.e */
class C0157e implements Runnable {

    /* renamed from: a */
    final /* synthetic */ IBinder f93a;

    /* renamed from: b */
    final /* synthetic */ C0156d f94b;

    C0157e(C0156d dVar, IBinder iBinder) {
        this.f94b = dVar;
        this.f93a = iBinder;
    }

    public void run() {
        C0122a a = C0123a.m4a(this.f93a);
        try {
            if (!C0143b.m87e()) {
                this.f94b.f90a[0] = a.mo2342a(this.f94b.f91b, this.f94b.f92c);
            } else if (C0143b.m112x()) {
                this.f94b.f90a[0] = a.mo2343b(this.f94b.f91b, this.f94b.f92c);
            } else {
                this.f94b.f90a[0] = null;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("UploadMode connected, do remote http post ");
            sb.append(this.f94b.f90a[0]);
            C0177k.m318b(sb.toString());
            synchronized (C0161i.class) {
                try {
                    C0161i.class.notify();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (RemoteException e2) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("UploadMode error while uploading the data by IPC.");
            sb2.append(e2.toString());
            C0177k.m324e(sb2.toString());
            this.f94b.f90a[0] = null;
        }
    }
}
