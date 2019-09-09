package com.miui.aod.utils;

import android.app.ActivityManager;
import android.app.ActivityThread;
import android.content.Context;
import android.database.ContentObserver;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.hardware.fingerprint.FingerprintManager;
import android.miui.Shell;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.Trace;
import android.text.format.DateFormat;
import android.text.format.Formatter;
import android.util.TimeUtils;
import android.view.WindowManager.LayoutParams;
import java.io.PrintWriter;
import miui.os.DeviceFeature;

public class CommonUtils {
    public static final int BRIGHTNESS_ON = ((1 << DeviceFeature.BACKLIGHT_BIT) - 1);
    public static final int LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES = 1;

    private CommonUtils() {
    }

    public static void registerObserverForUser(Context context, Uri uri, boolean z, ContentObserver contentObserver, int i) {
        context.getContentResolver().registerContentObserver(uri, z, contentObserver, i);
    }

    public static boolean is24HourFormat(Context context, int i) {
        return DateFormat.is24HourFormat(context, i);
    }

    public static boolean isProviderAccess(String str, int i) {
        boolean z = false;
        try {
            if (ActivityThread.getPackageManager().resolveContentProvider(str, 790016, i) != null) {
                z = true;
            }
            return z;
        } catch (Exception unused) {
            return false;
        }
    }

    public static String formatShortElapsedTime(Context context, long j) {
        return Formatter.formatShortElapsedTime(context, j);
    }

    public static int getCurrentUser() {
        return ActivityManager.getCurrentUser();
    }

    public static void setXfermodeForDrawable(Drawable drawable, Xfermode xfermode) {
        drawable.setXfermode(xfermode);
    }

    public static boolean hasCallbacks(Handler handler, Runnable runnable) {
        return handler.hasCallbacks(runnable);
    }

    public static void formatDuration(long j, PrintWriter printWriter) {
        TimeUtils.formatDuration(j, printWriter);
    }

    public static void traceAppCounter(String str, int i) {
        Trace.traceCounter(4096, str, i);
    }

    public static void requestWakeUp(Context context) {
        ((PowerManager) context.getSystemService(PowerManager.class)).wakeUp(SystemClock.uptimeMillis(), "com.android.systemui:NODOZE");
    }

    public static void setLayoutInDisplayCutoutMode(LayoutParams layoutParams, int i) {
        layoutParams.layoutInDisplayCutoutMode = i;
    }

    public static int getEnrolledFingerprintsSize(FingerprintManager fingerprintManager) {
        return fingerprintManager.getEnrolledFingerprints().size();
    }

    public static long getRuntimeSharedValue(String str) {
        return Shell.getRuntimeSharedValue(str);
    }

    public static void setAllUserFlag(LayoutParams layoutParams) {
        layoutParams.privateFlags |= 16;
    }

    public static void setProcessForeground(IBinder iBinder) throws RemoteException {
        ActivityManager.getService().setProcessImportant(iBinder, Process.myPid(), true, "aod");
    }
}
