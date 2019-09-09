package com.miui.aod;

import android.content.Context;
import com.xiaomi.stat.MiStat;
import com.xiaomi.stat.MiStatParams;
import miui.os.Build;

public class AnalyticsWrapper {
    private static String resolveChannelName() {
        if (Build.IS_ALPHA_BUILD) {
            return "MIUI10-alpha";
        }
        return Build.IS_DEVELOPMENT_VERSION ? "MIUI10-dev" : "MIUI10";
    }

    public static void init(Context context) {
        MiStat.initialize(context, "2882303761517996425", "5741799662425", false, resolveChannelName());
        if (Build.IS_INTERNATIONAL_BUILD) {
            MiStat.setInternationalRegion(true, Build.getRegion());
        }
        MiStat.setUploadNetworkType(8);
        MiStat.setUseSystemUploadingService(true);
        MiStat.setExceptionCatcherEnabled(false);
        MiStat.setDebugModeEnabled(false);
    }

    public static void trackEvent(String str, MiStatParams miStatParams) {
        trackEvent(null, str, miStatParams);
    }

    public static void trackEvent(String str, String str2, MiStatParams miStatParams) {
        MiStat.trackEvent(str2, str, miStatParams);
    }
}
