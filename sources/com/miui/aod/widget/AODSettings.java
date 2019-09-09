package com.miui.aod.widget;

import android.content.ContentResolver;
import android.content.Context;
import android.icu.text.TimeZoneNames;
import android.icu.text.TimeZoneNames.NameType;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.text.TextUtils;
import com.miui.aod.R;
import com.miui.aod.common.StyleInfo;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;
import miui.os.Build;

public class AODSettings {
    public static final String AOD_MODE = (VERSION.SDK_INT >= 28 ? "doze_always_on" : "aod_mode");
    public static final int AOD_SHOW_STYLE_DEF = (isKeycodeGotoEnable() ^ true ? 1 : 0);
    public static final String AOD_STYLE_NAME = "aod_style_name";
    public static final String AOD_STYLE_NAME_DUAL = "aod_style_name_dual";
    private static final String[] HIGH_PERFORMANCE_DEVICE = {"cepheus", "grus", "davinci", "raphael", "pyxis", "laurus"};
    private static final String[] KEYCODE_GOTO_ENABLE_DEVICE = {"pyxis"};
    private static String[] sClockStyles = {"clock_panel_first", "clock_panel_second", "clock_panel_third", "clock_panel", "white_horizontal_left", "white_horizontal", "pink_horizontal", "blue_horizontal", "white_vertical", "pink_vertical", "blue_vertical", "paint_vertical", "tree_oneline", "spirit_oneline", "succulent_oneline", "cactus_oneline", "sun"};
    private static String[] sClockStylesHigh = {"clock_panel_first", "clock_panel_second", "clock_panel_third", "clock_panel", "sun", "white_horizontal", "blue_horizontal", "pink_horizontal", "white_horizontal_left", "white_vertical", "blue_vertical", "pink_vertical", "moonlight_vertical", "paint_vertical", "shadow_vertical", "cactus_oneline", "succulent_oneline", "spirit_oneline", "tree_oneline", "spaceman_oneline", "ghost_oneline"};
    private static String[] sClockStylesHighOld = {"sun", "white_horizontal", "pink_horizontal", "blue_horizontal", "white_vertical", "pink_vertical", "blue_vertical", "paint_vertical", "shadow_vertical", "moonlight_vertical", "tree_oneline", "cactus_oneline", "succulent_oneline", "spirit_oneline", "ghost_oneline", "spaceman_oneline"};
    private static String[] sClockStylesOld = {"white_horizontal", "pink_horizontal", "blue_horizontal", "paint_vertical", "white_vertical", "pink_vertical", "blue_vertical", "paint_vertical", "tree_oneline", "spirit_oneline", "succulent_oneline", "cactus_oneline", "sun"};
    private static String[] sDualClockStyles = {"dual_default", "dual_panel", "dual_together"};
    private static boolean sIsHighPerformance = true;
    private static final HashMap<String, StyleInfo> sPresetStyles = new HashMap<>();

    static {
        String str = Build.DEVICE;
        int i = 0;
        while (true) {
            if (i >= HIGH_PERFORMANCE_DEVICE.length) {
                break;
            } else if (str.equals(HIGH_PERFORMANCE_DEVICE[i])) {
                break;
            } else {
                i++;
            }
        }
        sPresetStyles.put("white_horizontal", new StyleInfo(0, 0, 0, R.drawable.notification_icons_mask));
        sPresetStyles.put("pink_horizontal", new StyleInfo(2, 0, 0, R.drawable.icon_pink_x));
        sPresetStyles.put("blue_horizontal", new StyleInfo(1, 0, 0, R.drawable.icon_blue_x));
        sPresetStyles.put("paint_horizontal", new StyleInfo(0, R.drawable.aod_bg_paint, 0, R.drawable.notification_icons_mask));
        sPresetStyles.put("white_vertical", new StyleInfo(0, 0, 1, R.drawable.notification_icons_mask));
        sPresetStyles.put("pink_vertical", new StyleInfo(2, 0, 1, R.drawable.icon_pink_x));
        sPresetStyles.put("blue_vertical", new StyleInfo(1, 0, 1, R.drawable.icon_blue_x));
        sPresetStyles.put("paint_vertical", new StyleInfo(0, R.drawable.aod_bg_paint, 1, R.drawable.notification_icons_mask));
        sPresetStyles.put("tree_oneline", new StyleInfo(0, sIsHighPerformance ? R.drawable.aod_bg_tree_high : R.drawable.aod_bg_tree, 2, R.drawable.notification_icons_mask));
        sPresetStyles.put("spirit_oneline", new StyleInfo(0, sIsHighPerformance ? R.drawable.aod_bg_spirit_high : R.drawable.aod_bg_spirit, 2, R.drawable.notification_icons_mask));
        sPresetStyles.put("succulent_oneline", new StyleInfo(0, sIsHighPerformance ? R.drawable.aod_bg_succulent_high : R.drawable.aod_bg_succulent, 2, R.drawable.notification_icons_mask));
        sPresetStyles.put("cactus_oneline", new StyleInfo(0, sIsHighPerformance ? R.drawable.aod_bg_cactus_high : R.drawable.aod_bg_cactus, 2, R.drawable.notification_icons_mask));
        sPresetStyles.put("shadow_vertical", new StyleInfo(0, R.drawable.aod_bg_shadow, 1, R.drawable.notification_icons_mask));
        sPresetStyles.put("moonlight_vertical", new StyleInfo(0, R.drawable.aod_bg_moonlight, 1, R.drawable.notification_icons_mask));
        sPresetStyles.put("ghost_oneline", new StyleInfo(0, R.drawable.aod_bg_ghost, 2, R.drawable.notification_icons_mask));
        sPresetStyles.put("spaceman_oneline", new StyleInfo(0, R.drawable.aod_bg_spaceman, 2, R.drawable.notification_icons_mask));
        sPresetStyles.put("sun", new StyleInfo(0, 0, 5, R.drawable.notification_icons_mask));
        sPresetStyles.put("white_horizontal_left", new StyleInfo(0, 0, 7, R.drawable.notification_icons_mask));
        sPresetStyles.put("clock_panel", new StyleInfo(0, 0, 8, R.drawable.notification_icons_mask));
        sPresetStyles.put("clock_panel_first", new StyleInfo(3, 0, 8, R.drawable.notification_icons_mask));
        sPresetStyles.put("clock_panel_second", new StyleInfo(4, 0, 8, R.drawable.notification_icons_mask));
        sPresetStyles.put("clock_panel_third", new StyleInfo(5, 0, 8, R.drawable.notification_icons_mask));
    }

    public static StyleInfo getClockStyleInfo(Context context, String str) {
        if (isDualClock(context)) {
            return null;
        }
        StyleInfo styleInfo = (StyleInfo) sPresetStyles.get(str);
        if (styleInfo == null) {
            styleInfo = getDefaultClockInfo();
        }
        return styleInfo;
    }

    public static boolean isKeycodeGotoEnable() {
        String str = Build.DEVICE;
        for (String equals : KEYCODE_GOTO_ENABLE_DEVICE) {
            if (str.equals(equals)) {
                return true;
            }
        }
        return false;
    }

    public static int translateShowStyleValue2Index(int i) {
        return isKeycodeGotoEnable() ? i : i - 1;
    }

    public static CharSequence[] getShowStyleEntries(Context context) {
        String[] stringArray = context.getResources().getStringArray(R.array.show_style_entries);
        if (isKeycodeGotoEnable()) {
            return stringArray;
        }
        CharSequence[] charSequenceArr = new CharSequence[(stringArray.length - 1)];
        int i = 0;
        while (i < charSequenceArr.length) {
            int i2 = i + 1;
            charSequenceArr[i] = stringArray[i2];
            i = i2;
        }
        return charSequenceArr;
    }

    public static CharSequence[] getShowStyleValues(Context context) {
        String[] stringArray = context.getResources().getStringArray(R.array.show_style_values);
        if (isKeycodeGotoEnable()) {
            return stringArray;
        }
        CharSequence[] charSequenceArr = new CharSequence[(stringArray.length - 1)];
        int i = 0;
        while (i < charSequenceArr.length) {
            int i2 = i + 1;
            charSequenceArr[i] = stringArray[i2];
            i = i2;
        }
        return charSequenceArr;
    }

    public static void upgradeIndex2Name(ContentResolver contentResolver, boolean z) {
        if (Secure.getInt(contentResolver, "change_index2name", 0) == 0) {
            if (z) {
                int i = Secure.getInt(contentResolver, "aod_style_index", 0);
                String[] strArr = sIsHighPerformance ? sClockStylesHighOld : sClockStylesOld;
                if (i >= 0 && i < strArr.length) {
                    Secure.putString(contentResolver, AOD_STYLE_NAME, strArr[i]);
                }
                int i2 = Secure.getInt(contentResolver, "aod_style_index_dual", 0);
                if (i2 >= 0 && i2 < sDualClockStyles.length) {
                    Secure.putString(contentResolver, AOD_STYLE_NAME_DUAL, strArr[i2]);
                }
            }
            Secure.putInt(contentResolver, "change_index2name", 1);
        }
    }

    public static String getAodStyleName(Context context) {
        String string = Secure.getString(context.getContentResolver(), isDualClock(context) ? AOD_STYLE_NAME_DUAL : AOD_STYLE_NAME);
        if (!TextUtils.isEmpty(string)) {
            return string;
        }
        return getDefaultStyleName(context);
    }

    public static String getAodStyleName(Context context, int i) {
        if (isDualClock(context)) {
            return sDualClockStyles[i];
        }
        if (sIsHighPerformance) {
            return sClockStylesHigh[i];
        }
        return sClockStyles[i];
    }

    public static boolean isSunStyle(Context context) {
        StyleInfo clockStyleInfo = getClockStyleInfo(context, getAodStyleName(context));
        return clockStyleInfo != null && clockStyleInfo.isSunStyle();
    }

    public static boolean isAnimationClockPanel(Context context) {
        return FrameAnimationManager.needFrameAnimation(getAodStyleName(context));
    }

    private static String getDefaultStyleName(Context context) {
        if (isDualClock(context)) {
            return sDualClockStyles[0];
        }
        if (sIsHighPerformance) {
            return sClockStylesHigh[0];
        }
        return sClockStyles[0];
    }

    public static StyleInfo getDefaultClockInfo() {
        HashMap<String, StyleInfo> hashMap;
        String str;
        if (sIsHighPerformance) {
            hashMap = sPresetStyles;
            str = "sun";
        } else {
            hashMap = sPresetStyles;
            str = "white_horizontal";
        }
        return (StyleInfo) hashMap.get(str);
    }

    public static int getClockStylesSize(Context context) {
        if (isDualClock(context)) {
            return sDualClockStyles.length;
        }
        return sIsHighPerformance ? sClockStylesHigh.length : sClockStyles.length;
    }

    public static boolean isHighPerformance() {
        return sIsHighPerformance;
    }

    public static String getNamebyZone(String str) {
        if (str == null || !str.equals("Asia/Shanghai")) {
            return TimeZoneNames.getInstance(Locale.getDefault()).getExemplarLocationName(str);
        }
        return TimeZoneNames.getInstance(Locale.getDefault()).getDisplayName("Asia/Shanghai", NameType.LONG_STANDARD, new Date().getTime());
    }

    public static boolean isDualClock(Context context) {
        boolean z = System.getInt(context.getContentResolver(), "auto_dual_clock", 0) == 1;
        String string = System.getString(context.getContentResolver(), "resident_timezone");
        if (!z || string == null || TimeZone.getDefault().getID().equals(string)) {
            return false;
        }
        return true;
    }
}
