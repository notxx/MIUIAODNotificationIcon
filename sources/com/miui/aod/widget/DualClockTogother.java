package com.miui.aod.widget;

import android.graphics.Typeface;
import android.icu.text.TimeZoneNames;
import android.provider.Settings.System;
import android.view.View;
import android.widget.TextView;
import com.miui.aod.R;
import com.miui.aod.Utils;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import miui.date.Calendar;

public class DualClockTogother extends HorizontalClock {
    private boolean m24HourFormat;
    private Calendar mCal2;
    private TextView mCity;
    private TextView mCity2;
    private String mCityName;
    private String mCityName2;
    private int mSize;
    private TimeZone mTimeZone;
    private TimeZone mTimeZone2;

    public DualClockTogother(int i) {
        super(i, 0);
        this.mSize = i;
    }

    public int getLayoutResource() {
        if (this.mSize == 0) {
            return R.layout.aod_content_horizontal_togother_p;
        }
        return this.mSize == 2 ? R.layout.aod_content_horizontal_togother_small : R.layout.aod_content_horizontal_togother;
    }

    public void bindView(View view) {
        this.mContext = view.getContext();
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
        this.mGradientLayout = (GradientLinearLayout) view.findViewById(R.id.gradient_layout);
        this.mCity = (TextView) view.findViewById(R.id.city);
        this.mCity2 = (TextView) view.findViewById(R.id.another_city);
        this.mCityName = System.getString(this.mContext.getContentResolver(), "local_city");
        TimeZoneNames.getInstance(Locale.getDefault());
        this.mCityName2 = AODSettings.getNamebyZone(this.mTimeZone2.getID());
    }

    public void updateTime(boolean z) {
        String str;
        StringBuilder sb;
        StringBuilder sb2;
        this.m24HourFormat = z;
        TimeZone timeZone = TimeZone.getDefault();
        this.mCal = new Calendar(timeZone);
        this.mCal2 = new Calendar(this.mTimeZone2);
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
            StringBuilder sb3 = new StringBuilder();
            sb3.append(i);
            sb3.append("");
            textView.setText(sb3.toString());
        }
        if (this.mClockMinuteView != null) {
            this.mClockMinuteView.setText(String.format("%02d", new Object[]{Integer.valueOf(this.mCal.get(20))}));
        }
        if (this.mClockHorizontal != null) {
            this.mClockHorizontal.setText(String.format("%d:%02d", new Object[]{Integer.valueOf(i), Integer.valueOf(this.mCal.get(20))}));
        }
        String string = this.mContext.getResources().getString(this.m24HourFormat ? R.string.aod_lock_screen_date : R.string.aod_lock_screen_date_12);
        TextView textView2 = this.mDateView;
        StringBuilder sb4 = new StringBuilder();
        sb4.append(this.mCal.format(string));
        sb4.append(" ");
        sb4.append(this.mCityName);
        textView2.setText(sb4.toString());
        long convert = TimeUnit.MINUTES.convert((long) this.mTimeZone2.getRawOffset(), TimeUnit.MILLISECONDS) - TimeUnit.MINUTES.convert((long) this.mTimeZone.getRawOffset(), TimeUnit.MILLISECONDS);
        if (convert % 60 == 0) {
            convert /= 60;
            if (convert > 0) {
                sb2 = new StringBuilder();
                sb2.append("+");
                sb2.append(convert);
            } else {
                sb2 = new StringBuilder();
                sb2.append(convert);
                sb2.append("");
            }
            str = sb2.toString();
        } else {
            double d = (((double) convert) * 1.0d) / 60.0d;
            if (convert > 0) {
                sb = new StringBuilder();
                sb.append("+");
                sb.append(d);
            } else {
                sb = new StringBuilder();
                sb.append(d);
                sb.append("");
            }
            str = sb.toString();
        }
        String string2 = this.mContext.getResources().getString(this.m24HourFormat ? R.string.aod_dual_togother : R.string.aod_dual_togother_12);
        TextView textView3 = this.mCity2;
        StringBuilder sb5 = new StringBuilder();
        sb5.append(this.mCal2.format(string2));
        sb5.append(this.mContext.getResources().getQuantityString(R.plurals.aod_dual_togother_city, (int) convert, new Object[]{str, this.mCityName2}));
        textView3.setText(sb5.toString());
    }

    public void setTimeZone(TimeZone timeZone) {
        this.mTimeZone = timeZone;
    }

    public void setTimeZone2(TimeZone timeZone) {
        this.mTimeZone2 = timeZone;
    }
}
