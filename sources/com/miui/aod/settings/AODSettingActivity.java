package com.miui.aod.settings;

import android.content.Context;
import android.os.Build.VERSION;

public class AODSettingActivity extends DarkModeSupportActivity {
    /* access modifiers changed from: protected */
    public void attachBaseContext(Context context) {
        super.attachBaseContext(injectContext(context));
    }

    /* JADX WARNING: type inference failed for: r2v1, types: [android.app.Fragment, com.miui.aod.settings.AODFragment] */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r2v1, types: [android.app.Fragment, com.miui.aod.settings.AODFragment]
      assigns: [com.miui.aod.settings.AODFragment]
      uses: [android.app.Fragment]
      mth insns count: 8
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onCreate(android.os.Bundle r2) {
        /*
            r1 = this;
            super.onCreate(r2)
            android.app.FragmentManager r1 = r1.getFragmentManager()
            android.app.FragmentTransaction r1 = r1.beginTransaction()
            com.miui.aod.settings.AODFragment r2 = new com.miui.aod.settings.AODFragment
            r2.<init>()
            r0 = 16908290(0x1020002, float:2.3877235E-38)
            r1.replace(r0, r2)
            r2 = 0
            r1.addToBackStack(r2)
            r1.commit()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.miui.aod.settings.AODSettingActivity.onCreate(android.os.Bundle):void");
    }

    private Context injectContext(Context context) {
        return VERSION.SDK_INT >= 24 ? context.createDeviceProtectedStorageContext() : context;
    }

    public void onBackPressed() {
        finish();
    }
}
