package com.miui.aod;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.util.SparseArray;
import android.util.TypedValue;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class BatteryIcon {
    private static BatteryIcon sBatteryIcon;
    private final int BATTERY_RANGE_LOAD = 10;
    private int mBatteryColumns;
    private int mChargeLevel = -1;
    private Context mContext;
    private LevelListDrawable mGraphicChargeIcon;
    private LevelListDrawable mGraphicIcon;
    private SparseArray<ArrayList<Drawable>> mGraphicRes2Drawables = new SparseArray<>();
    private int mLevel = -1;

    public static BatteryIcon getInstance(Context context) {
        if (sBatteryIcon == null) {
            sBatteryIcon = new BatteryIcon(context);
        }
        return sBatteryIcon;
    }

    private BatteryIcon(Context context) {
        this.mContext = context;
        this.mBatteryColumns = this.mContext.getResources().getInteger(R.integer.battery_columns);
    }

    public LevelListDrawable getGraphicIcon(int i) {
        if (this.mLevel == -1 || this.mLevel - i > 10 || this.mLevel - i < 0) {
            this.mGraphicIcon = generateIcon(R.raw.stat_sys_battery, i, false);
            this.mLevel = i;
        }
        return this.mGraphicIcon;
    }

    public LevelListDrawable getGraphicChargeIcon(int i) {
        if (this.mChargeLevel == -1 || i - this.mChargeLevel > 10 || i - this.mChargeLevel < 0) {
            this.mGraphicChargeIcon = generateIcon(R.raw.stat_sys_battery_charge, i, true);
            this.mChargeLevel = i;
        }
        return this.mGraphicChargeIcon;
    }

    private LevelListDrawable generateIcon(int i, int i2, boolean z) {
        int i3;
        LevelListDrawable levelListDrawable = new LevelListDrawable();
        ArrayList extractDrawable = extractDrawable(i);
        int size = extractDrawable.size();
        if (size > 0) {
            float f = 0.4f;
            float f2 = 100.0f / ((float) size);
            if (z) {
                i3 = i2;
            } else {
                i3 = i2 - 10;
                if (i3 < 0) {
                    i3 = 0;
                }
            }
            if (z) {
                i2 += 10;
                if (i2 > 100) {
                    i2 = 100;
                }
            }
            for (int i4 = 0; i4 < size; i4++) {
                int i5 = (int) f;
                f += f2;
                int i6 = (int) f;
                if (i6 < i3 || i5 > i2) {
                    levelListDrawable.addLevel(i5, i6, null);
                } else {
                    levelListDrawable.addLevel(i5, i6, (Drawable) extractDrawable.get(i4));
                }
            }
        }
        levelListDrawable.setAutoMirrored(true);
        return levelListDrawable;
    }

    private ArrayList<Drawable> extractDrawable(int i) {
        ArrayList<Drawable> arrayList = (ArrayList) this.mGraphicRes2Drawables.get(i, null);
        if (arrayList != null) {
            return arrayList;
        }
        ArrayList<Drawable> doExtractDrawable = doExtractDrawable(i);
        this.mGraphicRes2Drawables.put(i, doExtractDrawable);
        return doExtractDrawable;
    }

    private ArrayList<Drawable> doExtractDrawable(int i) {
        ArrayList<Drawable> arrayList = new ArrayList<>();
        Resources resources = this.mContext.getResources();
        TypedValue typedValue = new TypedValue();
        InputStream openRawResource = resources.openRawResource(i, typedValue);
        Bitmap decodeStream = BitmapFactory.decodeStream(openRawResource);
        try {
            openRawResource.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (decodeStream == null) {
            return arrayList;
        }
        int max = Math.max(typedValue.density, 240);
        int i2 = max == 240 ? 38 : max == 320 ? 50 : max == 640 ? 72 : 60;
        int width = decodeStream.getWidth() / this.mBatteryColumns;
        int height = decodeStream.getHeight() / i2;
        int width2 = decodeStream.getWidth() / width;
        int[] iArr = new int[(i2 * width)];
        int i3 = 0;
        while (i3 < height) {
            int i4 = 0;
            while (i4 < width2) {
                int i5 = width;
                int i6 = i4;
                int i7 = i3;
                int[] iArr2 = iArr;
                decodeStream.getPixels(iArr, 0, i5, i4 * width, i3 * i2, width, i2);
                Bitmap createBitmap = Bitmap.createBitmap(iArr2, 0, width, i5, i2, Config.ARGB_8888);
                createBitmap.setDensity(max);
                arrayList.add(new BitmapDrawable(resources, createBitmap));
                i4 = i6 + 1;
                i3 = i7;
                iArr = iArr2;
            }
            int[] iArr3 = iArr;
            i3++;
        }
        decodeStream.recycle();
        return arrayList;
    }

    public void clear() {
        this.mGraphicIcon = null;
        this.mGraphicChargeIcon = null;
        this.mGraphicRes2Drawables.clear();
        this.mLevel = -1;
        this.mChargeLevel = -1;
    }
}
