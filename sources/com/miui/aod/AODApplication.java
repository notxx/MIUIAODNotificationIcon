package com.miui.aod;

import android.content.Context;
import android.os.Binder;
import android.util.Log;
import com.miui.aod.util.BatteryController;
import com.miui.aod.utils.CommonUtils;
import miui.external.ApplicationDelegate;

public class AODApplication extends ApplicationDelegate {
    private static BatteryController sBatteryController;
    private static DozeHost sHost;
    private Binder mToken;

    public void onCreate() {
        setTheme(getResources().getIdentifier("Theme.Light", "style", "miui"));
        super.onCreate();
        doSelfProtect();
        init(getApplicationContext());
        AnalyticsWrapper.init(getApplicationContext());
        AnalyticalDataCollectorJobService.schedule(getApplicationContext());
    }

    private void doSelfProtect() {
        try {
            this.mToken = new Binder();
            CommonUtils.setProcessForeground(this.mToken);
        } catch (Exception e) {
            Log.e("AODApplication", "setProcessForeground", e);
        }
    }

    private static void init(Context context) {
        sHost = new DozeHost(context);
        sBatteryController = BatteryController.getInstance(context);
        Utils.updateTouchMode(context);
    }

    public static DozeHost getHost() {
        return sHost;
    }

    public static BatteryController getBatteryController() {
        return sBatteryController;
    }
}
