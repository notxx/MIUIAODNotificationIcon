package com.miui.aod;

import android.content.Context;
import android.util.Log;
import miui.external.ApplicationDelegate;

public class Application extends miui.external.Application {
    public ApplicationDelegate onCreateApplicationDelegate() {
        return new AODApplication();
    }

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context context) {
        Log.i("AOD.application", "application attached");
        super.attachBaseContext(context);
    }
}
