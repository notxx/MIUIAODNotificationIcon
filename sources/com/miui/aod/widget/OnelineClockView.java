package com.miui.aod.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.miui.aod.R;

public class OnelineClockView extends FrameLayout {
    private TextView mClockHorizontal;
    private int mClockPadddingEnd;
    private TextView mDate;
    private int mDateStyle;
    private int mOnelineStyle;
    private int mPaddingTop;
    private int mSize;
    private int mTimePaddingBottom;
    private int mTimeStyle;

    public OnelineClockView(Context context) {
        super(context);
    }

    public OnelineClockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setSize(int i) {
        this.mSize = i;
        if (this.mSize == 0) {
            this.mDateStyle = R.style.Aod_date_p;
            this.mOnelineStyle = R.style.Aod_oneline_p;
            this.mTimeStyle = R.style.Aod_oneline_time_p;
        } else if (this.mSize == 2) {
            this.mDateStyle = R.style.Aod_date_s;
            this.mOnelineStyle = R.style.Aod_oneline_s;
            this.mTimeStyle = R.style.Aod_oneline_time_s;
        } else if (this.mSize == 1) {
            this.mDateStyle = R.style.Aod_date_oneline;
            this.mOnelineStyle = R.style.Aod_oneline;
            this.mTimeStyle = R.style.Aod_oneline_time;
        } else {
            setPadding(0, 0, 0, 0);
            return;
        }
        this.mClockHorizontal = (TextView) findViewById(R.id.clock_horizontal);
        this.mDate = (TextView) findViewById(R.id.date);
        this.mClockHorizontal.setTextAppearance(this.mTimeStyle);
        this.mClockHorizontal.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Mitype2018-clock.ttf"));
        this.mDate.setTextAppearance(this.mDateStyle);
        this.mDate.setTypeface(null);
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(this.mOnelineStyle, R.styleable.oneline);
        this.mPaddingTop = (int) obtainStyledAttributes.getDimension(0, 0.0f);
        this.mClockPadddingEnd = (int) obtainStyledAttributes.getDimension(2, 0.0f);
        this.mTimePaddingBottom = (int) obtainStyledAttributes.getDimension(4, 0.0f);
        obtainStyledAttributes.recycle();
        setPadding(0, this.mPaddingTop, 0, 0);
        this.mClockHorizontal.setPadding(0, 0, 0, this.mTimePaddingBottom);
    }
}
