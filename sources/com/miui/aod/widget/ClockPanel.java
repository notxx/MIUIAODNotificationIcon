package com.miui.aod.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.utils.MiuiSettingsUtils;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import miui.date.Calendar;

public class ClockPanel implements IAodClock {
    private boolean m24HourFormat;
    private String mAodStyleName;
    private TextView mCity;
    private int mCityMarginBottom;
    private int mCityPaddingTop;
    private int mCityStyle;
    private ClockPanelView mClockPanel;
    private Context mContext;
    private int mDateMarginTop;
    private int mDateStyle;
    private TextView mDateView;
    protected GradientLinearLayout mGradientLayout;
    private boolean mIsDual;
    private int mSize;
    private TimeZone mTimeZone;

    public int getLayoutResource() {
        return R.layout.clock_panel;
    }

    public void setTimeZone2(TimeZone timeZone) {
    }

    public ClockPanel(int i, boolean z) {
        this(i, z, null);
    }

    public ClockPanel(int i, boolean z, String str) {
        this.mSize = i;
        this.mIsDual = z;
        this.mAodStyleName = str;
    }

    public void bindView(View view) {
        this.mContext = view.getContext();
        this.mClockPanel = (ClockPanelView) view.findViewById(R.id.clock_panel);
        this.mClockPanel.init(this.mSize, this.mIsDual, this.mAodStyleName);
        this.mDateView = (TextView) view.findViewById(R.id.date);
        this.mCity = (TextView) view.findViewById(R.id.city);
        int i = 0;
        if (this.mSize == 0) {
            this.mDateStyle = this.mIsDual ? R.style.Aod_date_dual_p : R.style.Aod_date_p;
            this.mCityStyle = R.style.Aod_clock_panel_dual_city_p;
            this.mDateMarginTop = this.mContext.getResources().getDimensionPixelSize(R.dimen.clock_panel_margin_p);
            this.mCityPaddingTop = this.mContext.getResources().getDimensionPixelOffset(R.dimen.aod_clock_panel_padding_top_p);
            this.mCityMarginBottom = this.mContext.getResources().getDimensionPixelOffset(R.dimen.clock_panel_margin_p);
            if (!this.mIsDual) {
                this.mCity.setVisibility(8);
            }
        } else if (this.mSize == 2) {
            boolean needFrameAnimation = FrameAnimationManager.needFrameAnimation(this.mAodStyleName);
            this.mDateStyle = this.mIsDual ? R.style.Aod_date_dual_s : R.style.Aod_date_s;
            this.mCityStyle = R.style.Aod_clock_panel_dual_city_s;
            this.mDateMarginTop = !needFrameAnimation ? this.mContext.getResources().getDimensionPixelSize(R.dimen.clock_panel_margin_s) : 0;
            this.mCityPaddingTop = this.mContext.getResources().getDimensionPixelOffset(R.dimen.aod_clock_panel_padding_top_s);
            this.mCityMarginBottom = this.mContext.getResources().getDimensionPixelOffset(R.dimen.clock_panel_margin_s);
            if (!this.mIsDual && needFrameAnimation) {
                this.mCity.setVisibility(8);
            }
        } else {
            this.mDateStyle = this.mIsDual ? R.style.Aod_date_dual : R.style.Aod_date_horizontal;
            this.mCityStyle = R.style.Aod_clock_panel_dual_city;
            this.mDateMarginTop = this.mContext.getResources().getDimensionPixelSize(R.dimen.clock_panel_margin);
            this.mCityPaddingTop = this.mContext.getResources().getDimensionPixelOffset(R.dimen.aod_clock_panel_padding_top);
            this.mCityMarginBottom = this.mContext.getResources().getDimensionPixelOffset(R.dimen.clock_panel_margin);
        }
        this.mDateView.setTextAppearance(this.mDateStyle);
        LayoutParams layoutParams = (LayoutParams) this.mDateView.getLayoutParams();
        layoutParams.setMargins(0, this.mDateMarginTop, 0, 0);
        this.mDateView.setLayoutParams(layoutParams);
        this.mCity.setTextAppearance(this.mCityStyle);
        this.mCity.setPadding(0, this.mCityPaddingTop, 0, 0);
        LayoutParams layoutParams2 = (LayoutParams) this.mCity.getLayoutParams();
        layoutParams2.bottomMargin = this.mCityMarginBottom;
        this.mCity.setLayoutParams(layoutParams2);
        if (this.mCity.getVisibility() == 8 && this.mSize == 0) {
            LayoutParams layoutParams3 = (LayoutParams) this.mClockPanel.getLayoutParams();
            layoutParams3.topMargin = this.mContext.getResources().getDimensionPixelOffset(R.dimen.aod_clock_panel_padding_top);
            this.mClockPanel.setLayoutParams(layoutParams3);
        }
        if (this.mTimeZone == null || !this.mIsDual) {
            this.mCity.setText(null);
        } else {
            if (this.mTimeZone.getID().equals(TimeZone.getDefault().getID())) {
                this.mCity.setText(MiuiSettingsUtils.getStringFromSystem(this.mContext.getContentResolver(), "local_city", -2));
            } else {
                String namebyZone = AODSettings.getNamebyZone(this.mTimeZone.getID());
                if (!TextUtils.isEmpty(namebyZone)) {
                    this.mCity.setText(namebyZone);
                }
            }
            if (this.mSize == 2) {
                i = this.mContext.getResources().getDimensionPixelSize(R.dimen.clock_panel_gaps_s);
            } else if (this.mSize == 0) {
                i = this.mContext.getResources().getDimensionPixelSize(R.dimen.clock_panel_gaps_p);
            } else {
                i = this.mContext.getResources().getDimensionPixelSize(R.dimen.clock_panel_gaps);
            }
        }
        this.mGradientLayout = (GradientLinearLayout) view.findViewById(R.id.gradient_layout);
        LayoutParams layoutParams4 = (LayoutParams) this.mGradientLayout.getLayoutParams();
        layoutParams4.leftMargin = i;
        layoutParams4.rightMargin = i;
        this.mGradientLayout.setLayoutParams(layoutParams4);
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
        if (this.mClockPanel != null) {
            this.mClockPanel.setTime(i, calendar.get(20));
        }
        this.mDateView.setText(calendar.format(this.mContext.getResources().getString(this.m24HourFormat ? R.string.aod_lock_screen_date : R.string.aod_lock_screen_date_12)));
    }

    public void setTimeZone(TimeZone timeZone) {
        this.mTimeZone = timeZone;
    }

    public void setClockMask(int i, int i2) {
        int i3 = i == 3 ? R.color.aod_light_purple_x : i == 4 ? R.color.aod_light_blue_x : i == 5 ? R.color.aod_light_orange_x : 0;
        if (i3 > 0) {
            this.mDateView.setTextColor(this.mContext.getResources().getColor(i3));
        }
    }
}
