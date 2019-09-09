package com.xiaomi.stat;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import com.xiaomi.stat.p006a.C0135c;
import com.xiaomi.stat.p006a.C0142l;
import com.xiaomi.stat.p008c.C0161i;
import com.xiaomi.stat.p009d.C0165c;
import com.xiaomi.stat.p009d.C0177k;
import com.xiaomi.stat.p009d.C0180m;
import com.xiaomi.stat.p009d.C0181n;
import com.xiaomi.stat.p009d.C0184r;
import java.util.Calendar;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* renamed from: com.xiaomi.stat.e */
public class C0186e {
    /* access modifiers changed from: private */

    /* renamed from: a */
    public boolean f168a = true;
    /* access modifiers changed from: private */

    /* renamed from: b */
    public String f169b;
    /* access modifiers changed from: private */

    /* renamed from: c */
    public String f170c;

    /* renamed from: d */
    private Context f171d;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public Executor f172e;
    /* access modifiers changed from: private */

    /* renamed from: f */
    public long f173f;

    /* renamed from: h */
    private C0132J f174h;
    /* access modifiers changed from: private */

    /* renamed from: i */
    public int f175i = 0;
    /* access modifiers changed from: private */

    /* renamed from: j */
    public int f176j = 0;
    /* access modifiers changed from: private */

    /* renamed from: k */
    public int f177k = 0;
    /* access modifiers changed from: private */

    /* renamed from: l */
    public long f178l;

    public C0186e(Context context, String str, String str2, boolean z, String str3) {
        m390a(context, str, str2, z, str3);
    }

    /* renamed from: a */
    private void m390a(Context context, String str, String str2, boolean z, String str3) {
        this.f171d = context.getApplicationContext();
        C0131I.m28a(context.getApplicationContext(), str, str2);
        if (!this.f168a) {
            str = this.f169b;
        }
        this.f170c = str;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue());
        this.f172e = threadPoolExecutor;
        if (this.f168a) {
            m409e();
        }
        C0184r.m382a();
        this.f172e.execute(new C0187f(this, str3, z));
    }

    /* access modifiers changed from: private */
    /* renamed from: d */
    public long m406d() {
        return C0184r.m385b();
    }

    /* renamed from: e */
    private void m409e() {
        ((Application) this.f171d).registerActivityLifecycleCallbacks(new C0193q(this));
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m395a(String str, long j, long j2) {
        Executor executor = this.f172e;
        C0196t tVar = new C0196t(this, str, j, j2);
        executor.execute(tVar);
    }

    /* renamed from: a */
    public void mo2436a(String str, String str2, MiStatParams miStatParams) {
        m396a(str, str2, miStatParams, false);
    }

    /* renamed from: a */
    private void m396a(String str, String str2, MiStatParams miStatParams, boolean z) {
        if (!C0181n.m346a(str)) {
            C0181n.m349e(str);
        } else if (str2 != null && !C0181n.m346a(str2)) {
            C0181n.m349e(str2);
        } else if (miStatParams == null || m405c(miStatParams)) {
            Executor executor = this.f172e;
            C0197x xVar = new C0197x(this, z, str, str2, miStatParams);
            executor.execute(xVar);
        }
    }

    /* renamed from: c */
    public void mo2440c(boolean z) {
        if (this.f168a) {
            C0143b.m82d(z);
            if (this.f174h != null) {
                this.f174h.mo2366a(z);
            } else if (z) {
                this.f174h = new C0132J(this);
                this.f174h.mo2365a();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public void mo2437a(Throwable th, String str, boolean z) {
        if (th != null) {
            this.f172e.execute(new C0198y(this, th, str, z));
        }
    }

    /* renamed from: a */
    public void mo2435a(int i) {
        if (this.f168a) {
            C0143b.m69a(i);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: f */
    public void m411f() {
        if (this.f168a) {
            int p = C0143b.m104p();
            int a = C0165c.m231a();
            if (p == -1) {
                C0143b.m85e(a);
                return;
            }
            if (p < a) {
                this.f172e.execute(new C0189j(this, a, p));
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: g */
    public boolean m414g(boolean z) {
        boolean z2 = true;
        if (C0143b.m83d(this.f170c)) {
            if (C0143b.m84e(this.f170c) == 2) {
                z2 = false;
            }
            return z2;
        } else if (!C0143b.m87e() || z) {
            return C0180m.m338b(this.f171d);
        } else {
            return true;
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: g */
    public boolean m413g() {
        boolean z = true;
        if (!C0143b.m83d(this.f170c)) {
            return true;
        }
        if (C0143b.m84e(this.f170c) == 2) {
            z = false;
        }
        return z;
    }

    /* access modifiers changed from: private */
    /* renamed from: h */
    public void m416h() {
        this.f172e.execute(new C0190k(this));
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public boolean m397a(long j, long j2) {
        boolean z = true;
        if (j == -1) {
            return true;
        }
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(j);
        Calendar instance2 = Calendar.getInstance();
        instance2.setTimeInMillis(j2);
        if (instance.get(1) == instance2.get(1) && instance.get(6) == instance2.get(6)) {
            z = false;
        }
        return z;
    }

    /* renamed from: a */
    public void mo2438a(boolean z, String str) {
        if (this.f168a) {
            this.f172e.execute(new C0191l(this, z, str));
        }
    }

    /* renamed from: e */
    public boolean mo2441e(boolean z) {
        return mo2439a(z, false);
    }

    /* renamed from: a */
    public boolean mo2439a(boolean z, boolean z2) {
        if (!m418i()) {
            return false;
        }
        C0143b.m90f(z);
        C0143b.m92g(z2);
        return true;
    }

    /* renamed from: i */
    private boolean m418i() {
        boolean z = true;
        if ((this.f171d.getApplicationInfo().flags & 1) == 0) {
            return false;
        }
        PackageManager packageManager = this.f171d.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(this.f171d.getPackageName(), 64);
            PackageInfo packageInfo2 = packageManager.getPackageInfo("android", 64);
            if (packageInfo != null) {
                if (((packageInfo.signatures != null) && (packageInfo.signatures.length > 0)) && packageInfo2 != null) {
                    boolean z2 = packageInfo2.signatures != null;
                    if (packageInfo2.signatures.length <= 0) {
                        z = false;
                    }
                    if (z && z2) {
                        return packageInfo2.signatures[0].equals(packageInfo.signatures[0]);
                    }
                }
            }
        } catch (NameNotFoundException unused) {
        }
        return false;
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m389a(int i, int i2, long j, long j2) {
        Executor executor = this.f172e;
        C0192o oVar = new C0192o(this, i, i2, j, j2);
        executor.execute(oVar);
    }

    /* renamed from: f */
    public void mo2442f(boolean z) {
        C0177k.m315a(z);
    }

    /* renamed from: c */
    private boolean m405c(MiStatParams miStatParams) {
        return miStatParams.getClass().equals(MiStatParams.class) && miStatParams.getParamsNumber() <= 30;
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m391a(C0142l lVar) {
        C0135c.m41a().mo2378a(lVar);
        C0161i.m198a().mo2427d();
    }
}
