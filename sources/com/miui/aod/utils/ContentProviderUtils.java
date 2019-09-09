package com.miui.aod.utils;

import android.content.Context;
import android.content.IContentProvider;
import android.net.Uri;

public class ContentProviderUtils {
    private ContentProviderUtils() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0031  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void updateData(android.content.Context r8, android.net.Uri r9, android.content.ContentValues r10) {
        /*
            r0 = 0
            android.content.ContentResolver r1 = r8.getContentResolver()     // Catch:{ Exception -> 0x0027 }
            android.content.IContentProvider r1 = r1.acquireUnstableProvider(r9)     // Catch:{ Exception -> 0x0027 }
            java.lang.String r3 = r8.getPackageName()     // Catch:{ Exception -> 0x0022, all -> 0x001f }
            r6 = 0
            r7 = 0
            r2 = r1
            r4 = r9
            r5 = r10
            r2.update(r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x0022, all -> 0x001f }
            if (r1 == 0) goto L_0x0038
            android.content.ContentResolver r8 = r8.getContentResolver()
            r8.releaseUnstableProvider(r1)
            goto L_0x0038
        L_0x001f:
            r9 = move-exception
            r0 = r1
            goto L_0x0039
        L_0x0022:
            r9 = move-exception
            r0 = r1
            goto L_0x0028
        L_0x0025:
            r9 = move-exception
            goto L_0x0039
        L_0x0027:
            r9 = move-exception
        L_0x0028:
            java.lang.String r10 = "WallpaperUtils"
            java.lang.String r1 = "tellThemeWallpaperPath"
            android.util.Log.d(r10, r1, r9)     // Catch:{ all -> 0x0025 }
            if (r0 == 0) goto L_0x0038
            android.content.ContentResolver r8 = r8.getContentResolver()
            r8.releaseUnstableProvider(r0)
        L_0x0038:
            return
        L_0x0039:
            if (r0 == 0) goto L_0x0042
            android.content.ContentResolver r8 = r8.getContentResolver()
            r8.releaseUnstableProvider(r0)
        L_0x0042:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.miui.aod.utils.ContentProviderUtils.updateData(android.content.Context, android.net.Uri, android.content.ContentValues):void");
    }

    public static boolean isProviderExists(Context context, Uri uri) {
        IContentProvider acquireUnstableProvider = context.getContentResolver().acquireUnstableProvider(uri);
        if (acquireUnstableProvider == null) {
            return false;
        }
        context.getContentResolver().releaseUnstableProvider(acquireUnstableProvider);
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0036  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.os.Bundle getResultFromProvider(android.content.Context r2, java.lang.String r3, java.lang.String r4, java.lang.String r5, android.os.Bundle r6) {
        /*
            r0 = 0
            android.content.ContentResolver r1 = r2.getContentResolver()     // Catch:{ Exception -> 0x0024, all -> 0x0021 }
            android.net.Uri r3 = android.net.Uri.parse(r3)     // Catch:{ Exception -> 0x0024, all -> 0x0021 }
            android.content.IContentProvider r3 = r1.acquireUnstableProvider(r3)     // Catch:{ Exception -> 0x0024, all -> 0x0021 }
            java.lang.String r1 = r2.getPackageName()     // Catch:{ Exception -> 0x001f }
            android.os.Bundle r4 = r3.call(r1, r4, r5, r6)     // Catch:{ Exception -> 0x001f }
            if (r3 == 0) goto L_0x001e
            android.content.ContentResolver r2 = r2.getContentResolver()
            r2.releaseUnstableProvider(r3)
        L_0x001e:
            return r4
        L_0x001f:
            r4 = move-exception
            goto L_0x0026
        L_0x0021:
            r4 = move-exception
            r3 = r0
            goto L_0x0034
        L_0x0024:
            r4 = move-exception
            r3 = r0
        L_0x0026:
            r4.printStackTrace()     // Catch:{ all -> 0x0033 }
            if (r3 == 0) goto L_0x0032
            android.content.ContentResolver r2 = r2.getContentResolver()
            r2.releaseUnstableProvider(r3)
        L_0x0032:
            return r0
        L_0x0033:
            r4 = move-exception
        L_0x0034:
            if (r3 == 0) goto L_0x003d
            android.content.ContentResolver r2 = r2.getContentResolver()
            r2.releaseUnstableProvider(r3)
        L_0x003d:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.miui.aod.utils.ContentProviderUtils.getResultFromProvider(android.content.Context, java.lang.String, java.lang.String, java.lang.String, android.os.Bundle):android.os.Bundle");
    }
}
