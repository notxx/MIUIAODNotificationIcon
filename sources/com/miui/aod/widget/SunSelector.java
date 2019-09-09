package com.miui.aod.widget;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Settings.System;
import android.util.Log;
import java.util.Calendar;
import java.util.TimeZone;

public class SunSelector {
    private static int[] sChangePoint = {330, 340, 350, 360, 370, 380, 475, 570, 665, 760, 855, 950, 1045, 1060, 1070, 1080, 1090, 1100, 1110, 1205, 1300, 45, 140, 235};
    /* access modifiers changed from: private */
    public static long sSunRise;
    /* access modifiers changed from: private */
    public static long sSunSet;

    public static int getDrawableIndex(int i) {
        int length = sChangePoint.length - 1;
        int i2 = 0;
        while (i2 < sChangePoint.length - 1) {
            if (i >= sChangePoint[i2] && i < sChangePoint[i2 + 1]) {
                length = i2;
            }
            int i3 = i2 + 1;
            if (sChangePoint[i3] < sChangePoint[i2] && (i >= sChangePoint[i2] || i < sChangePoint[i3])) {
                length = i2;
            }
            i2 = i3;
        }
        return length;
    }

    public static boolean shouldUpdateSunriseTime(Context context, int i) {
        return System.getInt(context.getContentResolver(), "sunrise_update", 0) != i;
    }

    public static int getDrawableLength() {
        return AODBg.getSunImage().length;
    }

    public static int getChangePointLength() {
        return sChangePoint.length;
    }

    public static void updateSunRiseTime(final Context context) {
        final Calendar instance = Calendar.getInstance();
        final int i = instance.get(6);
        if (shouldUpdateSunriseTime(context, i)) {
            new AsyncTask<Void, Void, Boolean>() {
                /* access modifiers changed from: protected */
                public Boolean doInBackground(Void... voidArr) {
                    Cursor query;
                    boolean z = false;
                    try {
                        query = context.getContentResolver().query(Uri.parse("content://weather/weather"), null, null, null, null);
                        if (query != null) {
                            try {
                                query.moveToFirst();
                                long j = query.getLong(query.getColumnIndex("sunrise"));
                                long j2 = query.getLong(query.getColumnIndex("sunset"));
                                TimeZone timeZone = TimeZone.getDefault();
                                SunSelector.sSunRise = SunSelector.getTimeWithOnlyHourAndMinute(instance, j, timeZone);
                                SunSelector.sSunSet = SunSelector.getTimeWithOnlyHourAndMinute(instance, j2, timeZone);
                                z = true;
                                query.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                                query.close();
                            }
                        }
                    } catch (Exception e2) {
                        Log.e("SunSelector", "query sunRiseSet fail", e2);
                    } catch (Throwable th) {
                        query.close();
                        throw th;
                    }
                    return Boolean.valueOf(z);
                }

                /* access modifiers changed from: protected */
                public void onPostExecute(Boolean bool) {
                    if (bool.booleanValue()) {
                        System.putLong(context.getContentResolver(), "sunrise", SunSelector.sSunRise);
                        System.putLong(context.getContentResolver(), "sunset", SunSelector.sSunSet);
                        SunSelector.updateChangepoint(SunSelector.sSunRise, SunSelector.sSunSet);
                        System.putInt(context.getContentResolver(), "sunrise_update", i);
                    }
                }
            }.execute(new Void[0]);
        } else {
            updateChangepoint(System.getLong(context.getContentResolver(), "sunrise", 360), System.getLong(context.getContentResolver(), "sunset", 1080));
        }
    }

    /* access modifiers changed from: private */
    public static void updateChangepoint(long j, long j2) {
        sChangePoint[3] = (int) (j / 60000);
        sChangePoint[15] = (int) (j2 / 60000);
        int i = ((sChangePoint[15] - sChangePoint[3]) - 40) / 8;
        int i2 = (((sChangePoint[3] + 1440) - sChangePoint[15]) - 60) / 6;
        sChangePoint[0] = sChangePoint[3] - 30;
        sChangePoint[1] = sChangePoint[3] - 20;
        sChangePoint[2] = sChangePoint[3] - 10;
        for (int i3 = 0; i3 <= 5; i3++) {
            sChangePoint[i3] = sChangePoint[3] + ((i3 - 3) * 10);
        }
        for (int i4 = 6; i4 <= 12; i4++) {
            sChangePoint[i4] = sChangePoint[3] + 20 + ((i4 - 5) * i);
        }
        for (int i5 = 13; i5 <= 18; i5++) {
            sChangePoint[i5] = sChangePoint[15] + ((i5 - 15) * 10);
        }
        for (int i6 = 19; i6 <= 23; i6++) {
            sChangePoint[i6] = sChangePoint[15] + 30 + ((i6 - 18) * i2);
            if (sChangePoint[i6] > 1440) {
                sChangePoint[i6] = sChangePoint[i6] - 1440;
            }
        }
    }

    /* access modifiers changed from: private */
    public static long getTimeWithOnlyHourAndMinute(Calendar calendar, long j, TimeZone timeZone) {
        calendar.setTimeZone(timeZone);
        calendar.setTimeInMillis(j);
        int i = calendar.get(11);
        int i2 = calendar.get(12);
        calendar.clear();
        return ((long) ((i * 60) + i2)) * 60000;
    }

    public static int getSunImage(int i) {
        return AODBg.getSunImage()[i];
    }

    public static int getChangePoint(int i) {
        return sChangePoint[i];
    }
}
