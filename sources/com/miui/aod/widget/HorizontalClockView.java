package com.miui.aod.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.miui.aod.R;

public class HorizontalClockView extends FrameLayout {
    protected TextView mClockHorizontal;
    private int mClockPaddingTop;
    private int mClockStyle;
    private int mDateMarginBottom;
    private int mDateMarginTop;
    private int mDateStyle;
    protected TextView mDateView;
    private int mGravity;
    private int mHorizontalStyle;
    private int mSize;

    public HorizontalClockView(Context context) {
        super(context);
    }

    public HorizontalClockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public HorizontalClockView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void init(int i, int i2) {
        this.mSize = i;
        this.mGravity = i2;
        boolean z = this.mGravity == -1;
        if (this.mSize == 0) {
            this.mClockStyle = z ? R.style.Aod_widget_horizontal_left_p : R.style.Aod_widget_horizontal_p;
            this.mDateStyle = R.style.Aod_date_p;
            this.mHorizontalStyle = z ? R.style.Aod_horizontal_left_p : R.style.Aod_horizontal_p;
        } else if (this.mSize == 2) {
            this.mClockStyle = z ? R.style.Aod_widget_horizontal_left_s : R.style.Aod_widget_horizontal_s;
            this.mDateStyle = R.style.Aod_date_s;
            this.mHorizontalStyle = z ? R.style.Aod_horizontal_left_s : R.style.Aod_horizontal_s;
        } else {
            this.mClockStyle = z ? R.style.Aod_widget_horizontal_left : R.style.Aod_widget_horizontal;
            this.mDateStyle = z ? R.style.Aod_date_horizontal_left : R.style.Aod_date_horizontal;
            this.mHorizontalStyle = z ? R.style.Aod_horizontal_left : R.style.Aod_horizontal;
        }
        this.mClockHorizontal = (TextView) findViewById(R.id.clock_horizontal);
        this.mClockHorizontal.setTextAppearance(this.mClockStyle);
        this.mDateView = (TextView) findViewById(R.id.date);
        this.mDateView.setTextAppearance(this.mDateStyle);
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(this.mHorizontalStyle, R.styleable.horizontalClock);
        this.mClockPaddingTop = (int) obtainStyledAttributes.getDimension(0, 0.0f);
        this.mDateMarginTop = (int) obtainStyledAttributes.getDimension(1, 0.0f);
        this.mDateMarginBottom = (int) obtainStyledAttributes.getDimension(3, 0.0f);
        obtainStyledAttributes.recycle();
        LayoutParams layoutParams = (LayoutParams) this.mDateView.getLayoutParams();
        layoutParams.setMargins(0, this.mDateMarginTop, 0, this.mDateMarginBottom);
        this.mDateView.setLayoutParams(layoutParams);
        this.mClockHorizontal.setPadding(0, this.mClockPaddingTop, 0, 0);
    }
}
