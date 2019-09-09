package com.miui.aod;

import java.util.HashMap;
import java.util.Map;

public class AODNotificationColor {
    private static Map<String, ColorItem> sColorMap = new HashMap();
    private static final ColorItem sDefault;

    static class ColorItem {
        public int left;
        public int right;

        public ColorItem(int i, int i2) {
            this.left = i;
            this.right = i2;
        }
    }

    static {
        ColorItem colorItem = new ColorItem(R.drawable.blue_left, R.drawable.blue_right);
        ColorItem colorItem2 = new ColorItem(R.drawable.green_left, R.drawable.green_right);
        ColorItem colorItem3 = new ColorItem(R.drawable.purple_left, R.drawable.purple_right);
        new ColorItem(R.drawable.yellow_left, R.drawable.yellow_right);
        sDefault = colorItem3;
        sColorMap.put("com.tencent.mm", colorItem2);
        sColorMap.put("com.tencent.mobileqq", colorItem);
        sColorMap.put("com.whatsapp", colorItem2);
        sColorMap.put("com.facebook.orca", colorItem);
        sColorMap.put("jp.naver.line.android", colorItem2);
        sColorMap.put("com.google.android.gm", colorItem3);
        sColorMap.put("com.android.email", colorItem3);
        sColorMap.put("com.google.android.calendar", colorItem3);
        sColorMap.put("com.android.calendar", colorItem3);
        sColorMap.put("com.android.server.telecom", colorItem3);
        sColorMap.put("com.android.mms", colorItem3);
    }

    public static ColorItem getColorItem(String str) {
        return sColorMap.get(str) == null ? sDefault : (ColorItem) sColorMap.get(str);
    }
}
