package com.miui.aod.widget;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;
import com.miui.aod.R;

public class OneLineClock extends HorizontalClock {
    private OnelineClockView mClockView;
    private int mSize;

    public int getLayoutResource() {
        return R.layout.aod_content_oneline;
    }

    public void setClockMask(int i, int i2) {
    }

    public OneLineClock(int i) {
        super(i, 0);
        this.mSize = i;
    }

    public void bindView(View view) {
        this.mContext = view.getContext();
        this.mClockView = (OnelineClockView) view.findViewById(R.id.clock);
        this.mClockView.setSize(this.mSize);
        this.mDateView = (TextView) view.findViewById(R.id.date);
        this.mClockHorizontal = (TextView) view.findViewById(R.id.clock_horizontal);
        this.mCity = (TextView) view.findViewById(R.id.city);
        this.mGradientLayout = (GradientLinearLayout) view.findViewById(R.id.gradient_layout);
        this.mClockHourView = (TextView) view.findViewById(R.id.clock_hour);
        this.mClockMinuteView = (TextView) view.findViewById(R.id.clock_minute);
        this.mClockHorizontal = (TextView) view.findViewById(R.id.clock_horizontal);
        if (this.mSize == 3) {
            if (this.mClockHourView != null) {
                this.mClockHourView.setTypeface(null);
            }
            if (this.mClockMinuteView != null) {
                this.mClockMinuteView.setTypeface(null);
            }
            this.mClockHorizontal = (TextView) view.findViewById(R.id.clock_horizontal);
            Typeface createFromAsset = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/Mitype2018-clock.ttf");
            if (this.mClockHorizontal != null) {
                this.mClockHorizontal.setTypeface(createFromAsset);
                return;
            }
            return;
        }
        this.mGradientLayout.setPadding(0, 0, 0, 0);
    }
}
