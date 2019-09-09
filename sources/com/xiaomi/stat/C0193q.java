package com.xiaomi.stat;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import android.os.SystemClock;
import com.xiaomi.stat.p009d.C0184r;

/* renamed from: com.xiaomi.stat.q */
class C0193q implements ActivityLifecycleCallbacks {

    /* renamed from: a */
    final /* synthetic */ C0186e f195a;

    /* renamed from: b */
    private int f196b;

    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    public void onActivityDestroyed(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    C0193q(C0186e eVar) {
        this.f195a = eVar;
    }

    public void onActivityStarted(Activity activity) {
        if (this.f195a.f175i == 0) {
            this.f195a.f178l = SystemClock.elapsedRealtime();
            this.f195a.f176j = 0;
            this.f195a.f177k = 0;
            this.f195a.f172e.execute(new C0194r(this));
        }
        this.f195a.f175i = this.f195a.f175i + 1;
    }

    public void onActivityResumed(Activity activity) {
        this.f195a.f176j = this.f195a.f176j + 1;
        this.f196b = System.identityHashCode(activity);
        this.f195a.f173f = SystemClock.elapsedRealtime();
        this.f195a.m416h();
    }

    public void onActivityPaused(Activity activity) {
        this.f195a.f177k = this.f195a.f177k + 1;
        if (this.f196b == System.identityHashCode(activity)) {
            long elapsedRealtime = SystemClock.elapsedRealtime() - this.f195a.f173f;
            long l = this.f195a.m406d();
            this.f195a.m395a(activity.getClass().getName(), l - elapsedRealtime, l);
        }
    }

    public void onActivityStopped(Activity activity) {
        this.f195a.f175i = this.f195a.f175i - 1;
        if (this.f195a.f175i == 0) {
            long elapsedRealtime = SystemClock.elapsedRealtime() - this.f195a.f178l;
            long b = C0184r.m385b();
            this.f195a.m389a(this.f195a.f176j, this.f195a.f177k, b - elapsedRealtime, b);
            this.f195a.f172e.execute(new C0195s(this));
        }
    }
}
