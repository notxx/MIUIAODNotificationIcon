package com.miui.aod.widget;

import android.view.View;
import android.widget.TextView;
import com.miui.aod.R;

public class VerticalClock extends HorizontalClock {
    private VerticalClockView mClockView;
    private int mSize;

    public int getLayoutResource() {
        return R.layout.aod_content_vertical;
    }

    public VerticalClock(int i) {
        super(i, 0);
        this.mSize = i;
    }

    public void bindView(View view) {
        this.mContext = view.getContext();
        this.mClockView = (VerticalClockView) view.findViewById(R.id.clock);
        this.mClockView.setSize(this.mSize);
        this.mClockHourView = (TextView) view.findViewById(R.id.clock_hour);
        this.mClockMinuteView = (TextView) view.findViewById(R.id.clock_minute);
        this.mDateView = (TextView) view.findViewById(R.id.date);
        this.mClockHorizontal = (TextView) view.findViewById(R.id.clock_horizontal);
        this.mCity = (TextView) view.findViewById(R.id.city);
        this.mGradientLayout = (GradientLinearLayout) view.findViewById(R.id.gradient_layout);
    }

    public void updateTime(boolean z) {
        super.updateTime(z);
        if (this.mClockHourView != null) {
            int i = this.mCal.get(18);
            if (!z && i > 12) {
                i -= 12;
            }
            if (!z && i == 0) {
                i = 12;
            }
            this.mClockHourView.setText(String.format("%02d", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void setClockMask(int i, int i2) {
        if (i2 > 0) {
            this.mGradientLayout.setGradientOverlayDrawable(this.mContext.getResources().getDrawable(i2));
        }
    }
}
