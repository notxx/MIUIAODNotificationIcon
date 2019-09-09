package com.miui.aod.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.miui.aod.R;

public class VerticalClockView extends FrameLayout {
    private int mClockStyle;
    private int mDateMarginBottom;
    private int mDateStyle;
    private TextView mDateView;
    private int mHourPaddingTop;
    private TextView mHourTextView;
    private int mMinuteMarginTop;
    private TextView mMinuteTextView;
    private int mSize;
    private int mVerticalStyle;

    public VerticalClockView(Context context) {
        super(context);
    }

    public VerticalClockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public VerticalClockView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setSize(int i) {
        this.mSize = i;
        if (this.mSize == 0) {
            this.mClockStyle = R.style.Aod_widget_p;
            this.mDateStyle = R.style.Aod_date_p;
            this.mVerticalStyle = R.style.Aod_vertical_p;
        } else if (this.mSize == 2) {
            this.mClockStyle = R.style.Aod_widget_s;
            this.mDateStyle = R.style.Aod_date_s;
            this.mVerticalStyle = R.style.Aod_vertical_s;
        } else {
            this.mClockStyle = R.style.Aod_widget_vertical;
            this.mDateStyle = R.style.Aod_date_vertical;
            this.mVerticalStyle = R.style.Aod_vertical;
        }
        Typeface createFromAsset = Typeface.createFromAsset(getContext().getAssets(), "fonts/Mitype2018-clock2.ttf");
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(this.mVerticalStyle, R.styleable.verticalClock);
        this.mHourPaddingTop = (int) obtainStyledAttributes.getDimension(1, 0.0f);
        this.mMinuteMarginTop = (int) obtainStyledAttributes.getDimension(2, 0.0f);
        this.mDateMarginBottom = (int) obtainStyledAttributes.getDimension(5, 0.0f);
        obtainStyledAttributes.recycle();
        this.mHourTextView = (TextView) findViewById(R.id.clock_hour);
        this.mMinuteTextView = (TextView) findViewById(R.id.clock_minute);
        this.mHourTextView.setTextAppearance(this.mClockStyle);
        this.mHourTextView.setTypeface(createFromAsset);
        this.mHourTextView.setPadding(0, this.mHourPaddingTop, 0, 0);
        this.mMinuteTextView.setTextAppearance(this.mClockStyle);
        this.mMinuteTextView.setTypeface(createFromAsset);
        LayoutParams layoutParams = (LayoutParams) this.mMinuteTextView.getLayoutParams();
        layoutParams.setMargins(0, this.mMinuteMarginTop, 0, 0);
        this.mMinuteTextView.setLayoutParams(layoutParams);
        this.mDateView = (TextView) findViewById(R.id.date);
        this.mDateView.setTypeface(createFromAsset);
        this.mDateView.setTextAppearance(this.mDateStyle);
        LayoutParams layoutParams2 = (LayoutParams) this.mDateView.getLayoutParams();
        layoutParams2.setMargins(0, 0, 0, this.mDateMarginBottom);
        this.mDateView.setLayoutParams(layoutParams2);
    }
}
