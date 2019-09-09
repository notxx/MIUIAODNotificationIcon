package com.miui.aod.common;

import com.miui.aod.R;

public class StyleInfo {
    private int mClockBg;
    private int mClockColor;
    private int mClockOrientation;
    private int mIconMask;

    public StyleInfo(int i, int i2, int i3, int i4) {
        this.mClockColor = i;
        this.mClockBg = i2;
        this.mClockOrientation = i3;
        this.mIconMask = i4;
    }

    public int getClockColor() {
        return this.mClockColor;
    }

    public int getClockMask() {
        if (this.mClockOrientation == 1) {
            if (this.mClockColor == 1) {
                return R.drawable.aod_num_big_blue_y;
            }
            if (this.mClockColor == 2) {
                return R.drawable.aod_num_big_pink_y;
            }
        } else if (this.mClockOrientation == 0) {
            if (this.mClockColor == 1) {
                return R.drawable.aod_num_big_blue_x;
            }
            if (this.mClockColor == 2) {
                return R.drawable.aod_num_big_pink_x;
            }
        } else if (this.mClockOrientation == 8) {
            if (this.mClockColor == 3) {
                return R.drawable.light_purple_x;
            }
            if (this.mClockColor == 4) {
                return R.drawable.light_blue_x;
            }
            if (this.mClockColor == 5) {
                return R.drawable.light_orange_x;
            }
        }
        return -1;
    }

    public int getClockBg() {
        return this.mClockBg;
    }

    public int getClockOrientation() {
        return this.mClockOrientation;
    }

    public int getIconMask() {
        return this.mIconMask;
    }

    public boolean isSunStyle() {
        return this.mClockOrientation == 5;
    }
}
