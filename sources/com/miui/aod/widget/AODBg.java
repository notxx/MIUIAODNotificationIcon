package com.miui.aod.widget;

import com.miui.aod.R;

public class AODBg {
    private static int[] sSunImage = {R.drawable.aod_sun_rise_30b, R.drawable.aod_sun_rise_20b, R.drawable.aod_sun_rise_10b, R.drawable.aod_sun_rise, R.drawable.aod_sun_rise_10a, R.drawable.aod_sun_rise_20a, R.drawable.aod_sun7, R.drawable.aod_sun8, R.drawable.aod_sun9, R.drawable.aod_sun10, R.drawable.aod_sun11, R.drawable.aod_sun12, R.drawable.aod_sun13, R.drawable.aod_sun_set_20b, R.drawable.aod_sun_set_10b, R.drawable.aod_sun_set, R.drawable.aod_sun_set_10a, R.drawable.aod_sun_set_20a, R.drawable.aod_sun_set_30a, R.drawable.aod_sun20, R.drawable.aod_sun21, R.drawable.aod_sun22, R.drawable.aod_sun23, R.drawable.aod_sun24};
    private static int[] sSunImageHigh = {R.drawable.aod_sun_high_rise_30b, R.drawable.aod_sun_high_rise_20b, R.drawable.aod_sun_high_rise_10b, R.drawable.aod_sun_high_rise, R.drawable.aod_sun_high_rise_10a, R.drawable.aod_sun_high_rise_20a, R.drawable.aod_sun_high7, R.drawable.aod_sun_high8, R.drawable.aod_sun_high9, R.drawable.aod_sun_high10, R.drawable.aod_sun_high11, R.drawable.aod_sun_high12, R.drawable.aod_sun_high13, R.drawable.aod_sun_high_set_20b, R.drawable.aod_sun_high_set_10b, R.drawable.aod_sun_high_set, R.drawable.aod_sun_high_set_10a, R.drawable.aod_sun_high_set_20a, R.drawable.aod_sun_high_set_30a, R.drawable.aod_sun_high20, R.drawable.aod_sun_high21, R.drawable.aod_sun_high22, R.drawable.aod_sun_high23, R.drawable.aod_sun_high24};

    public static int[] getSunImage() {
        return AODSettings.isHighPerformance() ? sSunImageHigh : sSunImage;
    }
}
