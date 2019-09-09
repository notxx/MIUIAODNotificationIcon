package com.miui.aod.settings;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public abstract class DarkModeSupportActivity extends Activity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        setDayNightThemeIfNeed(this);
        super.onCreate(bundle);
    }

    private static int getDayNightThemeRes(Context context) {
        return context.getResources().getIdentifier("Theme.DayNight", "style", "miui");
    }

    public static void setDayNightThemeIfNeed(Activity activity) {
        int dayNightThemeRes = getDayNightThemeRes(activity);
        if (dayNightThemeRes > 0) {
            activity.setTheme(dayNightThemeRes);
        }
    }
}
