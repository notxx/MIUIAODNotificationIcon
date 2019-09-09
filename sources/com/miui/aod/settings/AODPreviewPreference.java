package com.miui.aod.settings;

import android.content.Context;
import android.preference.Preference;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.miui.aod.AODStyleController;
import com.miui.aod.R;
import com.miui.aod.widget.AODSettings;
import com.miui.aod.widget.ClockPanelView;

public class AODPreviewPreference extends Preference {
    private boolean m24HourFormat;
    private AODStyleController mAodStyleController;
    private View mAodView;
    private Context mContext;
    private LayoutInflater mInflater;
    private LinearLayout mPreview;

    public AODPreviewPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
    }

    public AODPreviewPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mContext = context;
    }

    public AODPreviewPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mContext = context;
    }

    public View onCreateView(ViewGroup viewGroup) {
        super.onCreateView(viewGroup);
        if (this.mAodView == null) {
            this.mInflater = LayoutInflater.from(this.mContext);
            this.mAodStyleController = new AODStyleController(0, this.mInflater);
            this.mAodView = this.mInflater.inflate(R.layout.aod_preview, null);
        }
        return this.mAodView;
    }

    /* access modifiers changed from: protected */
    public void onBindView(View view) {
        super.onBindView(view);
        this.mPreview = (LinearLayout) view.findViewById(R.id.preview);
        addView();
    }

    public void refreshView() {
        if (this.mPreview != null && this.mPreview.isAttachedToWindow()) {
            addView();
        }
    }

    private void addView() {
        if (this.mPreview != null) {
            String aodStyleName = AODSettings.getAodStyleName(getContext());
            String str = (String) this.mPreview.getTag();
            if (aodStyleName == null || !aodStyleName.equals(str)) {
                this.mAodStyleController.inflateClockView(this.mPreview);
                this.mPreview.setTag(AODSettings.getAodStyleName(getContext()));
                updateTime();
                this.mPreview.requestLayout();
                return;
            }
            updateTime();
        }
    }

    private void updateTime() {
        if (this.mAodStyleController != null) {
            this.m24HourFormat = DateFormat.is24HourFormat(this.mContext);
            this.mAodStyleController.handleUpdateTime(this.m24HourFormat);
        }
    }

    public void onStop() {
        if (this.mPreview != null && this.mPreview.getParent() != null) {
            View findViewById = this.mPreview.findViewById(R.id.clock_panel);
            if (findViewById != null && (findViewById instanceof ClockPanelView)) {
                ClockPanelView clockPanelView = (ClockPanelView) findViewById;
                clockPanelView.resetBackground();
                clockPanelView.resetClockHandState();
            }
        }
    }
}
