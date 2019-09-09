package com.miui.aod;

import android.content.Context;
import android.provider.Settings.System;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.widget.AODSettings;
import com.miui.aod.widget.ClockPanel;
import com.miui.aod.widget.DualClock;
import com.miui.aod.widget.DualClockTogother;
import com.miui.aod.widget.GradientLinearLayout;
import com.miui.aod.widget.HorizontalClock;
import com.miui.aod.widget.IAodClock;
import com.miui.aod.widget.OneLineClock;
import com.miui.aod.widget.SunClock;
import com.miui.aod.widget.SunSelector;
import com.miui.aod.widget.VerticalClock;
import java.util.Calendar;
import java.util.TimeZone;

public class AODStyleController {
    IAodClock mAodClock = null;
    IAodClock mAodClock2 = null;
    private AODBatteryMeterView mInflatedBattery;
    private View mInflatedIcons;
    private LayoutInflater mLayoutInflater;
    private int mSize;

    public AODStyleController(int i, LayoutInflater layoutInflater) {
        this.mSize = i;
        this.mLayoutInflater = layoutInflater;
    }

    public void inflateClockView(View view) {
        inflateView(view, AODSettings.getAodStyleName(view.getContext()));
    }

    public void inflateView(View view, String str) {
        int i;
        Context context = view.getContext();
        String string = System.getString(context.getContentResolver(), "resident_timezone");
        ViewGroup viewGroup = (ViewGroup) view.findViewById(R.id.content);
        ViewGroup viewGroup2 = (ViewGroup) view.findViewById(R.id.content2);
        boolean isDualClock = AODSettings.isDualClock(context);
        StyleInfo clockStyleInfo = AODSettings.getClockStyleInfo(context, str);
        int i2 = 0;
        if (clockStyleInfo == null) {
            i = 0;
        } else {
            i = clockStyleInfo.getClockOrientation();
        }
        inflateView(isDualClock, viewGroup, viewGroup2, string, clockStyleInfo, str);
        if (this.mSize != 2) {
            int clockMask = clockStyleInfo != null ? clockStyleInfo.getClockMask() : -1;
            if (clockStyleInfo != null) {
                i2 = clockStyleInfo.getClockOrientation();
            }
            setupBatteryViews(isDualClock, clockMask, i2, view);
            if (this.mInflatedIcons == null) {
                ViewStub viewStub = (ViewStub) view.findViewById(R.id.aod_icons);
                viewStub.setLayoutResource(R.layout.aod_icons);
                this.mInflatedIcons = viewStub.inflate();
            }
            setupNotificationIcons(this.mInflatedIcons, isDualClock ? 6 : i);
        } else {
            View findViewById = view.findViewById(R.id.battery_container);
            if (findViewById != null) {
                findViewById.setVisibility(8);
            }
        }
        setBg(isDualClock, clockStyleInfo, view);
        setupAodContent(view, i);
    }

    private void setupAodContent(View view, int i) {
        int i2 = 0;
        boolean z = i == 7;
        View findViewById = view.findViewById(R.id.aod_content_container);
        int i3 = 17;
        if (findViewById != null) {
            LayoutParams layoutParams = (LayoutParams) findViewById.getLayoutParams();
            layoutParams.gravity = (z ? 3 : 17) | 48;
            findViewById.setLayoutParams(layoutParams);
        }
        View findViewById2 = view.findViewById(R.id.clock_content_container);
        if (findViewById2 == null) {
            return;
        }
        if (this.mSize == 2) {
            LayoutParams layoutParams2 = (LayoutParams) findViewById2.getLayoutParams();
            if (z) {
                i3 = 19;
            }
            layoutParams2.gravity = i3;
            if (z) {
                i2 = 10;
            }
            layoutParams2.leftMargin = i2;
            findViewById2.setLayoutParams(layoutParams2);
            return;
        }
        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) findViewById2.getLayoutParams();
        if (z) {
            i3 = 19;
        }
        layoutParams3.gravity = i3;
        findViewById2.setLayoutParams(layoutParams3);
    }

    public void handleUpdateTime(boolean z) {
        if (this.mAodClock != null) {
            this.mAodClock.updateTime(z);
        }
        if (this.mAodClock2 != null) {
            this.mAodClock2.updateTime(z);
        }
    }

    private void inflateView(boolean z, ViewGroup viewGroup, ViewGroup viewGroup2, String str, StyleInfo styleInfo, String str2) {
        View view;
        viewGroup.removeAllViews();
        viewGroup2.removeAllViews();
        View view2 = null;
        if (!z) {
            int clockOrientation = styleInfo.getClockOrientation();
            if (clockOrientation == 0) {
                this.mAodClock = new HorizontalClock(this.mSize, 0);
            } else if (clockOrientation == 7) {
                this.mAodClock = new HorizontalClock(this.mSize, -1);
            } else if (clockOrientation == 1) {
                this.mAodClock = new VerticalClock(this.mSize);
            } else if (clockOrientation == 2) {
                this.mAodClock = new OneLineClock(this.mSize);
            } else if (clockOrientation == 8) {
                this.mAodClock = new ClockPanel(this.mSize, false, str2);
            } else {
                this.mAodClock = new SunClock(this.mSize);
            }
            view = this.mLayoutInflater.inflate(this.mAodClock.getLayoutResource(), viewGroup, true);
        } else {
            TimeZone timeZone = TimeZone.getDefault();
            TimeZone timeZone2 = TimeZone.getTimeZone(str);
            if ("dual_default".equals(str2)) {
                this.mAodClock = new DualClock(this.mSize);
                this.mAodClock2 = new DualClock(this.mSize);
            } else if ("dual_panel".equals(str2)) {
                this.mAodClock = new ClockPanel(this.mSize, true);
                this.mAodClock2 = new ClockPanel(this.mSize, true);
            } else {
                this.mAodClock = new DualClockTogother(this.mSize);
                this.mAodClock2 = null;
                this.mAodClock.setTimeZone2(timeZone2);
            }
            this.mAodClock.setTimeZone(timeZone);
            if (this.mAodClock2 != null) {
                this.mAodClock2.setTimeZone(timeZone2);
            }
            view = this.mLayoutInflater.inflate(this.mAodClock.getLayoutResource(), viewGroup, true);
            if (this.mAodClock2 != null) {
                view2 = this.mLayoutInflater.inflate(this.mAodClock2.getLayoutResource(), viewGroup2, true);
            }
        }
        this.mAodClock.bindView(view);
        if (this.mAodClock2 != null && view2 != null) {
            this.mAodClock2.bindView(view2);
        }
    }

    private void setBg(boolean z, StyleInfo styleInfo, View view) {
        ImageView imageView = (ImageView) view.findViewById(R.id.aod_bg);
        Context context = view.getContext();
        if (this.mSize != 2) {
            int i = R.drawable.notification_icons_mask;
            if (styleInfo != null && styleInfo.getIconMask() > 0) {
                i = styleInfo.getIconMask();
            }
            GradientLinearLayout gradientLinearLayout = (GradientLinearLayout) view.findViewById(R.id.icons);
            gradientLinearLayout.setGradientOverlayDrawable(context.getResources().getDrawable(i));
            gradientLinearLayout.invalidate();
        }
        int i2 = 0;
        if (z || styleInfo == null) {
            imageView.setImageResource(0);
            return;
        }
        this.mAodClock.setClockMask(styleInfo.getClockColor(), styleInfo.getClockMask());
        imageView.setImageResource(styleInfo.getClockBg());
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) imageView.getLayoutParams();
        if (styleInfo.isSunStyle()) {
            Calendar instance = Calendar.getInstance();
            int i3 = instance.get(11);
            int i4 = instance.get(12);
            SunSelector.updateSunRiseTime(context);
            imageView.setImageResource(SunSelector.getSunImage(SunSelector.getDrawableIndex((i3 * 60) + i4)));
            if (this.mSize == 1) {
                marginLayoutParams.width = context.getResources().getDimensionPixelOffset(R.dimen.aod_sun_bg_width);
                marginLayoutParams.height = context.getResources().getDimensionPixelOffset(R.dimen.aod_sun_bg_height);
                marginLayoutParams.topMargin = context.getResources().getDimensionPixelOffset(R.dimen.aod_sun_bg_margin_top);
            } else if (this.mSize == 0) {
                marginLayoutParams.width = -1;
                marginLayoutParams.height = context.getResources().getDimensionPixelOffset(R.dimen.aod_sun_bg_height_p);
                marginLayoutParams.topMargin = 0;
                imageView.setPadding(0, context.getResources().getDimensionPixelOffset(R.dimen.aod_bg_padding_top_sun_p), 0, 0);
            }
        } else if (this.mSize == 1) {
            marginLayoutParams.width = context.getResources().getDimensionPixelOffset(R.dimen.aod_bg_width);
            marginLayoutParams.height = context.getResources().getDimensionPixelOffset(R.dimen.aod_bg_height);
            marginLayoutParams.topMargin = context.getResources().getDimensionPixelOffset(R.dimen.clock_container_margin_top_large);
        } else if (this.mSize == 0) {
            marginLayoutParams.width = context.getResources().getDimensionPixelOffset(R.dimen.aod_bg_width_p);
            marginLayoutParams.height = context.getResources().getDimensionPixelOffset(R.dimen.aod_bg_height_p);
            imageView.setPadding(0, context.getResources().getDimensionPixelOffset(R.dimen.aod_bg_padding_top_default_p), 0, 0);
        }
        imageView.setLayoutParams(marginLayoutParams);
        int clockOrientation = styleInfo.getClockOrientation();
        if (clockOrientation == 2 && this.mSize == 0) {
            imageView.setPadding(imageView.getPaddingLeft(), context.getResources().getDimensionPixelOffset(R.dimen.aod_bg_padding_top_oneline_p), imageView.getPaddingRight(), imageView.getPaddingBottom());
        }
        if (this.mSize == 1) {
            FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.clock_container);
            if (clockOrientation == 5) {
                i2 = context.getResources().getDimensionPixelOffset(R.dimen.aod_bg_sun_padding_top_l);
            }
            frameLayout.setPadding(imageView.getPaddingLeft(), i2, imageView.getPaddingRight(), imageView.getPaddingBottom());
        }
    }

    private void setupBatteryViews(boolean z, int i, int i2, View view) {
        ViewStub viewStub = (ViewStub) view.findViewById(R.id.aod_battery);
        if (z || i2 == 8) {
            if (viewStub != null) {
                viewStub.setLayoutResource(R.layout.aod_battery);
                this.mInflatedBattery = (AODBatteryMeterView) viewStub.inflate().findViewById(R.id.aod_battery_layout);
            }
            if (this.mInflatedBattery != null) {
                if (i > 0) {
                    this.mInflatedBattery.setGradientOverlayDrawable(view.getContext().getResources().getDrawable(i));
                } else {
                    this.mInflatedBattery.setGradientOverlayDrawable(null);
                }
                int i3 = 0;
                this.mInflatedBattery.setVisibility(0);
                LayoutParams layoutParams = (LayoutParams) this.mInflatedBattery.getLayoutParams();
                if (this.mAodClock2 != null) {
                    i3 = view.getContext().getResources().getDimensionPixelOffset(R.dimen.battery_margin_top_vertical);
                }
                layoutParams.topMargin = i3;
                layoutParams.gravity = 1;
                this.mInflatedBattery.setLayoutParams(layoutParams);
            }
        } else if (this.mInflatedBattery != null) {
            this.mInflatedBattery.setVisibility(8);
        }
    }

    private void setupNotificationIcons(View view, int i) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
        if (i == 2) {
            layoutParams.topMargin = view.getContext().getResources().getDimensionPixelOffset(R.dimen.icons_margin_top_oneline);
        } else if (i == 1) {
            layoutParams.topMargin = view.getContext().getResources().getDimensionPixelOffset(R.dimen.icons_margin_top_vertical);
        } else if (i == 5) {
            layoutParams.topMargin = view.getContext().getResources().getDimensionPixelOffset(this.mSize == 0 ? R.dimen.icons_margin_top_sun_p : R.dimen.icons_margin_top_sun);
        } else {
            layoutParams.topMargin = view.getContext().getResources().getDimensionPixelOffset(R.dimen.icons_margin_top);
        }
        if (i == 7) {
            layoutParams.gravity = 3;
            layoutParams.leftMargin = view.getContext().getResources().getDimensionPixelOffset(R.dimen.icons_margin_left);
        } else {
            layoutParams.gravity = 17;
            layoutParams.leftMargin = 0;
        }
        view.setLayoutParams(layoutParams);
        if (this.mSize != 3) {
            ((BadgetImageView) view.findViewById(R.id.first)).setImageResource(R.drawable.phone);
            ((BadgetImageView) view.findViewById(R.id.second)).setImageResource(R.drawable.sms);
            ((BadgetImageView) view.findViewById(R.id.third)).setImageResource(R.drawable.wechat);
            view.setAlpha(1.0f);
        }
    }

    public void setSunImage(int i, View view) {
        ImageView imageView = (ImageView) view.findViewById(R.id.aod_bg);
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.width = view.getContext().getResources().getDimensionPixelOffset(R.dimen.sun_width);
        imageView.setLayoutParams(layoutParams);
        if (i >= 0 && i < SunSelector.getDrawableLength()) {
            imageView.setImageResource(SunSelector.getSunImage(i));
        }
    }
}
