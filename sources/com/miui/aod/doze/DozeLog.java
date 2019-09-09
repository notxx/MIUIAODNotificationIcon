package com.miui.aod.doze;

import android.content.Context;
import android.util.Log;
import com.miui.aod.doze.DozeMachine.State;
import com.miui.aod.utils.MiuiSettingsUtils;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;

public class DozeLog {
    private static final boolean DEBUG = Log.isLoggable("DozeLog", 3);
    static final SimpleDateFormat FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
    private static final int SIZE = (MiuiSettingsUtils.getIntFromSystemProperties("ro.debuggable", 0) == 1 ? 400 : 50);
    private static int sCount;
    private static SummaryStats sEmergencyCallStats;
    private static String[] sMessages;
    private static SummaryStats sNotificationPulseStats;
    private static SummaryStats sPickupPulseNearVibrationStats;
    private static SummaryStats sPickupPulseNotNearVibrationStats;
    private static int sPosition;
    private static SummaryStats[][] sProxStats;
    private static boolean sPulsing;
    private static boolean sRegisterKeyguardCallback = true;
    private static SummaryStats sScreenOnNotPulsingStats;
    private static SummaryStats sScreenOnPulsingStats;
    private static long sSince;
    private static long[] sTimes;

    private static class SummaryStats {
        private SummaryStats() {
        }
    }

    private DozeLog() {
    }

    private static void init(Context context) {
        synchronized (DozeLog.class) {
            if (sMessages == null) {
                sTimes = new long[SIZE];
                sMessages = new String[SIZE];
                sSince = System.currentTimeMillis();
                sPickupPulseNearVibrationStats = new SummaryStats();
                sPickupPulseNotNearVibrationStats = new SummaryStats();
                sNotificationPulseStats = new SummaryStats();
                sScreenOnPulsingStats = new SummaryStats();
                sScreenOnNotPulsingStats = new SummaryStats();
                sEmergencyCallStats = new SummaryStats();
                sProxStats = (SummaryStats[][]) Array.newInstance(SummaryStats.class, new int[]{6, 2});
                for (int i = 0; i < 6; i++) {
                    sProxStats[i][0] = new SummaryStats();
                    sProxStats[i][1] = new SummaryStats();
                }
                log("init");
            }
        }
    }

    public static void traceDozing(Context context, boolean z) {
        sPulsing = false;
        init(context);
        StringBuilder sb = new StringBuilder();
        sb.append("dozing ");
        sb.append(z);
        log(sb.toString());
    }

    public static void traceMissedTick(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("missedTick by=");
        sb.append(str);
        log(sb.toString());
    }

    public static void traceState(State state) {
        StringBuilder sb = new StringBuilder();
        sb.append("state ");
        sb.append(state);
        log(sb.toString());
    }

    public static int aodReasonToInt(String str) {
        if ("reason_keycode_goto".equals(str)) {
            return 7;
        }
        return "reason_fod".equals(str) ? 6 : -1;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0031, code lost:
        if (DEBUG == false) goto L_0x0038;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0033, code lost:
        android.util.Log.d("DozeLog", r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0038, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void log(java.lang.String r5) {
        /*
            java.lang.Class<com.miui.aod.doze.DozeLog> r0 = com.miui.aod.doze.DozeLog.class
            monitor-enter(r0)
            java.lang.String[] r1 = sMessages     // Catch:{ all -> 0x0039 }
            if (r1 != 0) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x0039 }
            return
        L_0x0009:
            long[] r1 = sTimes     // Catch:{ all -> 0x0039 }
            int r2 = sPosition     // Catch:{ all -> 0x0039 }
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0039 }
            r1[r2] = r3     // Catch:{ all -> 0x0039 }
            java.lang.String[] r1 = sMessages     // Catch:{ all -> 0x0039 }
            int r2 = sPosition     // Catch:{ all -> 0x0039 }
            r1[r2] = r5     // Catch:{ all -> 0x0039 }
            int r1 = sPosition     // Catch:{ all -> 0x0039 }
            int r1 = r1 + 1
            int r2 = SIZE     // Catch:{ all -> 0x0039 }
            int r1 = r1 % r2
            sPosition = r1     // Catch:{ all -> 0x0039 }
            int r1 = sCount     // Catch:{ all -> 0x0039 }
            int r1 = r1 + 1
            int r2 = SIZE     // Catch:{ all -> 0x0039 }
            int r1 = java.lang.Math.min(r1, r2)     // Catch:{ all -> 0x0039 }
            sCount = r1     // Catch:{ all -> 0x0039 }
            monitor-exit(r0)     // Catch:{ all -> 0x0039 }
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x0038
            java.lang.String r0 = "DozeLog"
            android.util.Log.d(r0, r5)
        L_0x0038:
            return
        L_0x0039:
            r5 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0039 }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.miui.aod.doze.DozeLog.log(java.lang.String):void");
    }

    public static void tracePulseDropped(Context context, boolean z, State state, boolean z2) {
        init(context);
        StringBuilder sb = new StringBuilder();
        sb.append("pulseDropped pulsePending=");
        sb.append(z);
        sb.append(" state=");
        sb.append(state);
        sb.append(" blocked=");
        sb.append(z2);
        log(sb.toString());
    }
}
