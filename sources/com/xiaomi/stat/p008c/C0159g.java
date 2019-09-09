package com.xiaomi.stat.p008c;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.xiaomi.stat.C0131I;
import com.xiaomi.stat.C0143b;
import com.xiaomi.stat.p006a.C0135c;
import com.xiaomi.stat.p009d.C0177k;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: com.xiaomi.stat.c.g */
public class C0159g extends Handler {

    /* renamed from: a */
    public AtomicBoolean f97a;

    /* renamed from: b */
    BroadcastReceiver f98b;

    /* renamed from: i */
    private long f99i;

    /* renamed from: j */
    private AtomicInteger f100j;
    /* access modifiers changed from: private */

    /* renamed from: k */
    public int f101k;
    /* access modifiers changed from: private */

    /* renamed from: l */
    public boolean f102l;

    /* renamed from: m */
    private long f103m;
    /* access modifiers changed from: private */

    /* renamed from: n */
    public long f104n;

    /* renamed from: o */
    private boolean f105o;

    /* renamed from: a */
    private int m183a(int i) {
        if (i < 0) {
            return 0;
        }
        if (i > 0 && i < 5) {
            return 5;
        }
        if (i > 86400) {
            return 86400;
        }
        return i;
    }

    public C0159g(Looper looper) {
        super(looper);
        this.f99i = 300000;
        this.f100j = new AtomicInteger();
        this.f97a = new AtomicBoolean(true);
        this.f101k = 15000;
        this.f102l = true;
        this.f105o = true;
        this.f98b = new C0160h(this);
        this.f101k = 60000;
        this.f100j.set(this.f101k);
        sendEmptyMessageDelayed(1, (long) this.f101k);
        m186a(C0131I.m27a());
        StringBuilder sb = new StringBuilder();
        sb.append("UploadTimer UploadTimer: ");
        sb.append(this.f101k);
        C0177k.m318b(sb.toString());
    }

    /* renamed from: d */
    private int m189d() {
        int a = m183a(C0143b.m101m());
        if (a > 0) {
            return a * 1000;
        }
        int a2 = m183a(C0143b.m98j());
        if (a2 > 0) {
            return a2 * 1000;
        }
        return 15000;
    }

    public void handleMessage(Message message) {
        super.handleMessage(message);
        if (message.what == 1) {
            m190e();
            this.f103m = (long) (this.f102l ? this.f100j.get() : this.f101k);
            sendEmptyMessageDelayed(1, this.f103m);
            StringBuilder sb = new StringBuilder();
            sb.append("UploadTimer handleMessage: ");
            sb.append(this.f102l);
            C0177k.m318b(sb.toString());
        } else if (message.what == 2) {
            m191f();
        }
    }

    /* renamed from: e */
    private void m190e() {
        C0161i.m198a().mo2426c();
    }

    /* renamed from: a */
    public long mo2417a() {
        return this.f103m;
    }

    /* renamed from: a */
    public void mo2418a(boolean z) {
        if (!z && !this.f105o) {
            mo2419b();
        }
        this.f102l = z;
        this.f105o = false;
    }

    /* renamed from: b */
    public void mo2419b() {
        this.f101k = m189d();
        this.f100j.set(this.f101k);
        removeMessages(1);
        sendEmptyMessageDelayed(1, (long) this.f101k);
        StringBuilder sb = new StringBuilder();
        sb.append("UploadTimer resetBackgroundInterval: ");
        sb.append(this.f101k);
        C0177k.m318b(sb.toString());
    }

    /* renamed from: a */
    private void m186a(Context context) {
        if (context != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            context.registerReceiver(this.f98b, intentFilter);
        }
    }

    /* renamed from: b */
    public void mo2420b(boolean z) {
        long c = C0135c.m41a().mo2381c();
        int i = (c > 0 ? 1 : (c == 0 ? 0 : -1));
        if (i == 0) {
            this.f97a.set(true);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("UploadTimer totalCount=");
        sb.append(c);
        sb.append(" deleteData=");
        sb.append(z);
        C0177k.m318b(sb.toString());
        if (((long) this.f100j.get()) < this.f99i && this.f102l) {
            if (i == 0 || !z) {
                this.f100j.addAndGet(this.f101k);
            }
        }
    }

    /* renamed from: c */
    public void mo2421c() {
        if (this.f102l && this.f97a.get()) {
            sendEmptyMessage(2);
        }
    }

    /* renamed from: f */
    private void m191f() {
        int i = (C0135c.m41a().mo2381c() > 0 ? 1 : (C0135c.m41a().mo2381c() == 0 ? 0 : -1));
        if (i >= 0) {
            if (i > 0) {
                mo2419b();
                this.f97a.set(false);
            } else {
                this.f97a.set(true);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("UploadTimer checkDatabase mIsDatabaseEmpty=");
            sb.append(this.f97a.get());
            C0177k.m318b(sb.toString());
        }
    }
}
