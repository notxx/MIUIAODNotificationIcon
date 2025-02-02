package miui.external;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import miui.external.SdkConstants.SdkError;

public class Application extends android.app.Application implements SdkConstants {
    private ApplicationDelegate mApplicationDelegate;
    private boolean mInitialized;
    private boolean mStarted;

    public ApplicationDelegate onCreateApplicationDelegate() {
        return null;
    }

    public Application() {
        if (loadSdk() && initializeSdk()) {
            this.mInitialized = true;
        }
    }

    private boolean loadSdk() {
        try {
            if (SdkHelper.isMiuiSystem() || SdkLoader.load(SdkHelper.getApkPath(null, "com.miui.core", "miui"), null, SdkHelper.getLibPath(null, "com.miui.core"), Application.class.getClassLoader())) {
                return true;
            }
            SdkErrorInstrumentation.handleSdkError(SdkError.NO_SDK);
            return false;
        } catch (Throwable th) {
            handleGenericError(th);
            return false;
        }
    }

    private boolean initializeSdk() {
        try {
            HashMap hashMap = new HashMap();
            int intValue = ((Integer) SdkEntranceHelper.getSdkEntrance().getMethod("initialize", new Class[]{android.app.Application.class, Map.class}).invoke(null, new Object[]{this, hashMap})).intValue();
            if (intValue == 0) {
                return true;
            }
            handleUnknownError("initialize", intValue);
            return false;
        } catch (Throwable th) {
            handleGenericError(th);
            return false;
        }
    }

    private boolean startSdk() {
        try {
            HashMap hashMap = new HashMap();
            int intValue = ((Integer) SdkEntranceHelper.getSdkEntrance().getMethod("start", new Class[]{Map.class}).invoke(null, new Object[]{hashMap})).intValue();
            if (intValue == 1) {
                SdkErrorInstrumentation.handleSdkError(SdkError.LOW_SDK_VERSION);
                return false;
            } else if (intValue == 0) {
                return true;
            } else {
                handleUnknownError("start", intValue);
                return false;
            }
        } catch (Throwable th) {
            handleGenericError(th);
            return false;
        }
    }

    private void handleGenericError(Throwable th) {
        while (th != null && th.getCause() != null) {
            if (!(th instanceof InvocationTargetException)) {
                if (!(th instanceof ExceptionInInitializerError)) {
                    break;
                }
                th = th.getCause();
            } else {
                th = th.getCause();
            }
        }
        Log.e("miuisdk", "MIUI SDK encounter errors, please contact miuisdk@xiaomi.com for support.", th);
        SdkErrorInstrumentation.handleSdkError(SdkError.GENERIC);
    }

    private void handleUnknownError(String str, int i) {
        StringBuilder sb = new StringBuilder();
        sb.append("MIUI SDK encounter errors, please contact miuisdk@xiaomi.com for support. phase: ");
        sb.append(str);
        sb.append(" code: ");
        sb.append(i);
        Log.e("miuisdk", sb.toString());
        SdkErrorInstrumentation.handleSdkError(SdkError.GENERIC);
    }

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        if (this.mInitialized && startSdk()) {
            this.mApplicationDelegate = onCreateApplicationDelegate();
            if (this.mApplicationDelegate != null) {
                this.mApplicationDelegate.attach(this);
            }
            this.mStarted = true;
        }
    }

    public final void onCreate() {
        if (this.mStarted) {
            if (this.mApplicationDelegate != null) {
                this.mApplicationDelegate.onCreate();
            } else {
                superOnCreate();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void superOnCreate() {
        super.onCreate();
    }

    public final void onTerminate() {
        if (this.mApplicationDelegate != null) {
            this.mApplicationDelegate.onTerminate();
        } else {
            superOnTerminate();
        }
    }

    /* access modifiers changed from: 0000 */
    public final void superOnTerminate() {
        super.onTerminate();
    }

    public final void onLowMemory() {
        if (this.mApplicationDelegate != null) {
            this.mApplicationDelegate.onLowMemory();
        } else {
            superOnLowMemory();
        }
    }

    /* access modifiers changed from: 0000 */
    public final void superOnLowMemory() {
        super.onLowMemory();
    }

    public final void onTrimMemory(int i) {
        if (this.mApplicationDelegate != null) {
            this.mApplicationDelegate.onTrimMemory(i);
        } else {
            superOnTrimMemory(i);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void superOnTrimMemory(int i) {
        super.onTrimMemory(i);
    }

    public final void onConfigurationChanged(Configuration configuration) {
        if (this.mApplicationDelegate != null) {
            this.mApplicationDelegate.onConfigurationChanged(configuration);
        } else {
            superOnConfigurationChanged(configuration);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void superOnConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }
}
