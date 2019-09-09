package com.miui.aod;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;
import com.miui.aod.util.BatteryController;
import com.miui.aod.util.BatteryController.BatteryStateChangeCallback;
import com.miui.aod.widget.GradientLinearLayout;

public class AODBatteryMeterView extends GradientLinearLayout implements BatteryStateChangeCallback {
    private BatteryController mBatteryController;
    private ImageView mBatteryIconView;
    private TextView mBatteryTextDigitView;
    private boolean mCharging;
    private Context mContext;
    private int mIconId;
    private int mLevel;

    public boolean hasOverlappingRendering() {
        return false;
    }

    public AODBatteryMeterView(Context context) {
        this(context, null, 0);
    }

    public AODBatteryMeterView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AODBatteryMeterView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIconId = R.raw.stat_sys_battery;
        this.mContext = context;
        setOrientation(0);
        setGravity(8388627);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mBatteryIconView = (ImageView) findViewById(R.id.aod_battery_icon);
        this.mBatteryTextDigitView = (TextView) findViewById(R.id.aod_battery_digital);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mBatteryController = AODApplication.getBatteryController();
        this.mBatteryController.addCallback(this);
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mBatteryController.removeCallback(this);
    }

    private int getIconId() {
        return this.mCharging ? R.raw.stat_sys_battery_charge : R.raw.stat_sys_battery;
    }

    public void onBatteryLevelChanged(int i, boolean z, boolean z2) {
        if (z2 != this.mCharging) {
            this.mCharging = z2;
            BatteryIcon.getInstance(this.mContext).clear();
        }
        if (this.mIconId != getIconId() || i != this.mLevel) {
            this.mLevel = i;
            this.mCharging = z2;
            this.mIconId = getIconId();
            this.mBatteryIconView.setContentDescription(getContext().getString(z2 ? R.string.accessibility_battery_level_charging : R.string.accessibility_battery_level, new Object[]{Integer.valueOf(i)}));
            update();
        }
    }

    public void update() {
        this.mBatteryIconView.setImageDrawable(getIcon(this.mIconId));
        this.mBatteryIconView.setImageLevel(this.mLevel);
        TextView textView = this.mBatteryTextDigitView;
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(this.mLevel));
        sb.append("%");
        textView.setText(sb.toString());
        invalidate();
    }

    /* access modifiers changed from: protected */
    public Drawable getIcon(int i) {
        switch (i) {
            case R.raw.stat_sys_battery /*2131099648*/:
                return BatteryIcon.getInstance(this.mContext).getGraphicIcon(this.mLevel);
            case R.raw.stat_sys_battery_charge /*2131099649*/:
                return BatteryIcon.getInstance(this.mContext).getGraphicChargeIcon(this.mLevel);
            default:
                return null;
        }
    }
}
