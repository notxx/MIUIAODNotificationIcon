package com.miui.aod.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.provider.Settings.System;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.miui.aod.R;
import com.miui.aod.Utils;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import miui.date.Calendar;

public class DualClock implements IAodClock {
    private boolean m24HourFormat;
    private TextView mCity;
    private TextView mClockHorizontal;
    private TextView mClockHourView;
    private TextView mClockMinuteView;
    private Context mContext;
    private TextView mDateView;
    private int mSize;
    private TimeZone mTimeZone;

    public void setClockMask(int i, int i2) {
    }

    public void setTimeZone2(TimeZone timeZone) {
    }

    public DualClock(int i) {
        this.mSize = i;
    }

    public int getLayoutResource() {
        if (this.mSize == 0) {
            return R.layout.aod_content_horizontal_dual_p;
        }
        return (this.mSize == 1 || this.mSize == 3) ? R.layout.aod_content_horizontal_dual : R.layout.aod_content_horizontal_dual_small;
    }

    public void bindView(View view) {
        this.mContext = view.getContext();
        if (this.mSize == 1) {
            int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.aod_clock_padding_large);
            view.setPaddingRelative(dimensionPixelSize, view.getPaddingTop(), dimensionPixelSize, view.getPaddingBottom());
        }
        Typeface createFromAsset = Typeface.createFromAsset(this.mContext.getAssets(), "fonts/Mitype2018-clock.ttf");
        this.mClockHourView = (TextView) view.findViewById(R.id.clock_hour);
        this.mClockMinuteView = (TextView) view.findViewById(R.id.clock_minute);
        if (this.mClockHourView != null) {
            this.mClockHourView.setTypeface(createFromAsset);
        }
        if (this.mClockMinuteView != null) {
            this.mClockMinuteView.setTypeface(createFromAsset);
        }
        this.mDateView = (TextView) view.findViewById(R.id.date);
        this.mClockHorizontal = (TextView) view.findViewById(R.id.clock_horizontal);
        if (this.mClockHorizontal != null) {
            this.mClockHorizontal.setTypeface(createFromAsset);
        }
        this.mCity = (TextView) view.findViewById(R.id.city);
        if (this.mTimeZone == null) {
            return;
        }
        if (this.mTimeZone.getID().equals(TimeZone.getDefault().getID())) {
            this.mCity.setText(System.getString(this.mContext.getContentResolver(), "local_city"));
            return;
        }
        String namebyZone = AODSettings.getNamebyZone(this.mTimeZone.getID());
        if (!TextUtils.isEmpty(namebyZone)) {
            this.mCity.setText(namebyZone);
        }
    }

    public void updateTime(boolean z) {
        this.m24HourFormat = z;
        TimeZone timeZone = this.mTimeZone == null ? TimeZone.getDefault() : this.mTimeZone;
        Calendar calendar = new Calendar(timeZone);
        new SimpleDateFormat(Utils.getHourMinformat(this.mContext)).setTimeZone(timeZone);
        int i = calendar.get(18);
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
            this.mClockMinuteView.setText(String.format("%02d", new Object[]{Integer.valueOf(calendar.get(20))}));
        }
        if (this.mClockHorizontal != null) {
            this.mClockHorizontal.setText(String.format("%d:%02d", new Object[]{Integer.valueOf(i), Integer.valueOf(calendar.get(20))}));
        }
        this.mDateView.setText(calendar.format(this.mContext.getResources().getString(this.m24HourFormat ? R.string.aod_lock_screen_date : R.string.aod_lock_screen_date_12)));
    }

    public void setTimeZone(TimeZone timeZone) {
        this.mTimeZone = timeZone;
    }
}
