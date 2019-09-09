package com.miui.aod.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.miui.aod.R;

public class SunClockView extends FrameLayout {
    private TextView mClockHorizontal;
    private int mClockPaddingTop;
    private int mClockStyle;
    private int mSize;

    public SunClockView(Context context) {
        super(context);
    }

    public SunClockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setSize(int i) {
        int i2;
        this.mSize = i;
        this.mClockHorizontal = (TextView) findViewById(R.id.clock_horizontal);
        if (this.mSize == 0) {
            this.mClockStyle = R.style.Aod_clock_sun_p;
            i2 = R.dimen.sun_paddingTop_p;
        } else if (this.mSize == 2) {
            this.mClockStyle = R.style.Aod_clock_sun_s;
            i2 = R.dimen.sun_paddingTop_s;
            this.mClockHorizontal.setLetterSpacing(1.0f);
        } else if (this.mSize == 1) {
            this.mClockStyle = R.style.Aod_clock_sun;
            i2 = R.dimen.sun_paddingTop_e;
        } else {
            this.mClockStyle = R.style.Aod_clock_sun;
            i2 = R.dimen.sun_paddingTop;
        }
        this.mClockPaddingTop = getResources().getDimensionPixelOffset(i2);
        Typeface createFromAsset = Typeface.createFromAsset(getContext().getAssets(), "fonts/Mitype2018-60.otf");
        this.mClockHorizontal.setTextAppearance(this.mClockStyle);
        this.mClockHorizontal.setTypeface(createFromAsset);
        this.mClockHorizontal.getPaint().setFakeBoldText(true);
        this.mClockHorizontal.setPadding(0, this.mClockPaddingTop, 0, 0);
    }
}
