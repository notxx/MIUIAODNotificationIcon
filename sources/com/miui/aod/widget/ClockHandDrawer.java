package com.miui.aod.widget;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.animation.Interpolator;
import com.miui.aod.widget.AnimationView.AnimationDrawer;
import com.miui.aod.widget.AnimationView.AnimationDrawer.AnimationStateListener;
import miui.view.animation.SineEaseInOutInterpolator;

public class ClockHandDrawer implements AnimationDrawer {
    private Drawable mHourHandDrawable;
    private int mHourHeight;
    private Rect mHourRect;
    private int mHourWidth;
    private Interpolator mInterpolator = new SineEaseInOutInterpolator();
    private long mLastFrameTime;
    private AnimationStateListener mListener;
    private Drawable mMinHandDrawable;
    private int mMinHeight;
    private Rect mMinRect;
    private int mMinWidth;
    private long mStartDelay;
    private long mStartTime;
    private float mTargetHourDegree;
    private float mTargetMinDegree;

    public ClockHandDrawer(Drawable drawable, Drawable drawable2) {
        this.mHourHandDrawable = drawable;
        this.mMinHandDrawable = drawable2;
        this.mHourWidth = this.mHourHandDrawable.getIntrinsicWidth();
        this.mHourHeight = this.mHourHandDrawable.getIntrinsicHeight();
        this.mMinWidth = this.mMinHandDrawable.getIntrinsicWidth();
        this.mMinHeight = this.mMinHandDrawable.getIntrinsicHeight();
        this.mHourRect = new Rect(0, 0, this.mHourWidth, this.mHourHeight);
        this.mMinRect = new Rect(0, 0, this.mMinWidth, this.mMinHeight);
        this.mLastFrameTime = -1;
        this.mStartTime = -1;
        this.mStartDelay = 0;
    }

    public void setHourDrawableSize(int i) {
        this.mHourHeight = i;
        this.mHourWidth = (i * this.mHourHandDrawable.getIntrinsicWidth()) / this.mHourHandDrawable.getIntrinsicHeight();
        this.mHourRect = new Rect(0, 0, this.mHourWidth, this.mHourHeight);
    }

    public void setMinDrawableSize(int i) {
        this.mMinHeight = i;
        this.mMinWidth = (i * this.mMinHandDrawable.getIntrinsicWidth()) / this.mMinHandDrawable.getIntrinsicHeight();
        this.mMinRect = new Rect(0, 0, this.mMinWidth, this.mMinHeight);
    }

    public void setAnimationValue(float f, float f2) {
        this.mTargetHourDegree = f;
        this.mTargetMinDegree = f2;
    }

    public void setStartDelay(long j) {
        this.mStartDelay = j;
    }

    public void setAnimationListener(AnimationStateListener animationStateListener) {
        this.mListener = animationStateListener;
    }

    public boolean onAnimationDraw(Canvas canvas, long j) {
        boolean z;
        boolean z2;
        float f;
        float f2;
        if (this.mStartDelay > 0 && this.mStartTime < 0) {
            this.mStartTime = j;
            return true;
        } else if (this.mStartDelay > 0 && j - this.mStartTime < this.mStartDelay) {
            return true;
        } else {
            if (this.mLastFrameTime == -1) {
                if (this.mListener != null) {
                    this.mListener.onAnimationStart();
                }
                f2 = this.mTargetHourDegree - 60.0f;
                f = this.mTargetMinDegree - 120.0f;
                this.mLastFrameTime = j;
                z = false;
                z2 = false;
            } else {
                float f3 = ((float) ((j - this.mLastFrameTime) / 1000000)) * 1.0f;
                float interpolation = this.mInterpolator.getInterpolation(Math.min(1.0f, f3 / 600.0f));
                z2 = interpolation == 1.0f;
                f2 = this.mTargetHourDegree - ((1.0f - interpolation) * 60.0f);
                float interpolation2 = this.mInterpolator.getInterpolation(Math.min(1.0f, f3 / 900.0f));
                f = this.mTargetMinDegree - ((1.0f - interpolation2) * 120.0f);
                z = interpolation2 == 1.0f;
            }
            drawHourHand(canvas, f2);
            drawMinHand(canvas, f);
            if (!z2 || !z) {
                return true;
            }
            if (this.mListener != null) {
                this.mListener.onAnimationEnd();
            }
            return false;
        }
    }

    private void drawHourHand(Canvas canvas, float f) {
        int width = canvas.getWidth() / 2;
        int height = canvas.getHeight() / 2;
        canvas.save();
        this.mHourRect.offsetTo(width - (this.mHourWidth / 2), height - (this.mHourHeight / 2));
        canvas.rotate(f, (float) width, (float) height);
        this.mHourHandDrawable.setBounds(this.mHourRect);
        this.mHourHandDrawable.draw(canvas);
        canvas.restore();
    }

    private void drawMinHand(Canvas canvas, float f) {
        int width = canvas.getWidth() / 2;
        int height = canvas.getHeight() / 2;
        canvas.save();
        this.mMinRect.offsetTo(width - (this.mMinWidth / 2), height - (this.mMinHeight / 2));
        canvas.rotate(f, (float) width, (float) height);
        this.mMinHandDrawable.setBounds(this.mMinRect);
        this.mMinHandDrawable.draw(canvas);
        canvas.restore();
    }
}
