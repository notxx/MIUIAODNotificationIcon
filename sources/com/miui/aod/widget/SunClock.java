package com.miui.aod.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;
import com.miui.aod.R;
import java.util.TimeZone;
import miui.date.Calendar;

public class SunClock implements IAodClock {
    private boolean m24HourFormat;
    protected Calendar mCal;
    private TextView mClockHorizontal;
    private SunClockView mClockView;
    protected Context mContext;
    private int mSize;

    public int getLayoutResource() {
        return R.layout.aod_content_sun;
    }

    public void setClockMask(int i, int i2) {
    }

    public void setTimeZone(TimeZone timeZone) {
    }

    public void setTimeZone2(TimeZone timeZone) {
    }

    public SunClock(int i) {
        this.mSize = i;
    }

    public void bindView(View view) {
        this.mContext = view.getContext();
        this.mClockView = (SunClockView) view.findViewById(R.id.clock);
        this.mClockView.setSize(this.mSize);
        this.mClockHorizontal = (TextView) view.findViewById(R.id.clock_horizontal);
        this.mClockHorizontal.setTypeface(Typeface.createFromAsset(view.getContext().getAssets(), "fonts/Mitype2018-clock.ttf"));
    }

    public void updateTime(boolean z) {
        this.m24HourFormat = z;
        this.mCal = new Calendar(TimeZone.getDefault());
        int i = this.mCal.get(18);
        if (!this.m24HourFormat && i > 12) {
            i -= 12;
        }
        if (!this.m24HourFormat && i == 0) {
            i = 12;
        }
        if (this.mClockHorizontal != null) {
            this.mClockHorizontal.setText(String.format("%02d:%02d", new Object[]{Integer.valueOf(i), Integer.valueOf(this.mCal.get(20))}));
        }
    }
}
