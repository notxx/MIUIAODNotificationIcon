package com.miui.aod.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.miui.aod.R;
import com.miui.aod.Utils;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import miui.date.Calendar;

public class HorizontalClock implements IAodClock {
    private boolean m24HourFormat;
    protected Calendar mCal;
    protected TextView mCity;
    protected TextView mClockHorizontal;
    protected TextView mClockHourView;
    protected TextView mClockMinuteView;
    private HorizontalClockView mClockView;
    protected Context mContext;
    protected TextView mDateView;
    protected GradientLinearLayout mGradientLayout;
    private int mLayoutGravity;
    private int mSize;

    public void setTimeZone(TimeZone timeZone) {
    }

    public void setTimeZone2(TimeZone timeZone) {
    }

    public HorizontalClock(int i, int i2) {
        this.mSize = i;
        this.mLayoutGravity = i2;
    }

    public int getLayoutResource() {
        return this.mLayoutGravity == -1 ? R.layout.aod_content_horizontal_left : R.layout.aod_content_horizontal;
    }

    public void bindView(View view) {
        this.mContext = view.getContext();
        this.mClockView = (HorizontalClockView) view.findViewById(R.id.clock);
        this.mClockView.init(this.mSize, this.mLayoutGravity);
        this.mClockHourView = (TextView) view.findViewById(R.id.clock_hour);
        this.mClockMinuteView = (TextView) view.findViewById(R.id.clock_minute);
        this.mDateView = (TextView) view.findViewById(R.id.date);
        this.mClockHorizontal = (TextView) view.findViewById(R.id.clock_horizontal);
        this.mCity = (TextView) view.findViewById(R.id.city);
        this.mGradientLayout = (GradientLinearLayout) view.findViewById(R.id.gradient_layout);
    }

    public void updateTime(boolean z) {
        this.m24HourFormat = z;
        TimeZone timeZone = TimeZone.getDefault();
        this.mCal = new Calendar(timeZone);
        new SimpleDateFormat(Utils.getHourMinformat(this.mContext)).setTimeZone(timeZone);
        int i = this.mCal.get(18);
        if (!this.m24HourFormat && i > 12) {
            i -= 12;
        }
        if (!this.m24HourFormat && i == 0) {
            i = 12;
        }
        if (this.mClockHourView != null) {
            TextView textView = this.mClockHourView;
            StringBuilder sb = new StringBuilder();
            sb.append(i);
            sb.append("");
            textView.setText(sb.toString());
        }
        if (this.mClockMinuteView != null) {
            this.mClockMinuteView.setText(String.format("%02d", new Object[]{Integer.valueOf(this.mCal.get(20))}));
        }
        if (this.mClockHorizontal != null) {
            this.mClockHorizontal.setText(String.format("%d:%02d", new Object[]{Integer.valueOf(i), Integer.valueOf(this.mCal.get(20))}));
        }
        this.mDateView.setText(this.mCal.format(this.mContext.getResources().getString(this.m24HourFormat ? R.string.aod_lock_screen_date : R.string.aod_lock_screen_date_12)));
    }

    public void setClockMask(int i, int i2) {
        if (i2 > 0) {
            this.mGradientLayout.setGradientOverlayDrawable(this.mContext.getResources().getDrawable(i2));
        }
    }
}
