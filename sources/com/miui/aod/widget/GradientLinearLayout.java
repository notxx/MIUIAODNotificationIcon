package com.miui.aod.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.miui.aod.utils.CommonUtils;

public class GradientLinearLayout extends LinearLayout {
    private Drawable mGradientOverlayDrawable;

    public GradientLinearLayout(Context context) {
        super(context);
    }

    public GradientLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public GradientLinearLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public GradientLinearLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        int saveLayer = canvas.saveLayer(0.0f, 0.0f, (float) getWidth(), (float) getHeight(), null);
        super.dispatchDraw(canvas);
        if (this.mGradientOverlayDrawable != null) {
            CommonUtils.setXfermodeForDrawable(this.mGradientOverlayDrawable, new PorterDuffXfermode(Mode.SRC_IN));
            this.mGradientOverlayDrawable.setBounds(getLeft(), getTop(), getRight(), getBottom());
            this.mGradientOverlayDrawable.draw(canvas);
        }
        canvas.restoreToCount(saveLayer);
    }

    public void setGradientOverlayDrawable(Drawable drawable) {
        this.mGradientOverlayDrawable = drawable;
    }
}
