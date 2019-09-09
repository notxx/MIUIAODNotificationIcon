package com.miui.aod.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.miui.aod.R;
import com.miui.aod.widget.AnimationView.AnimationDrawer.AnimationStateListener;
import com.miui.aod.widget.FrameAnimationController.AnimationCallBack;
import com.miui.aod.widget.FrameAnimationManager.FrameAnimationInfo;

public class ClockPanelView extends FrameLayout {
    /* access modifiers changed from: private */
    public AnimationView mAnimationView;
    /* access modifiers changed from: private */
    public Runnable mClockAnimationEndTask = new Runnable() {
        public final void run() {
            ClockPanelView.lambda$new$3(ClockPanelView.this);
        }
    };
    private int mClockPanelStyle;
    private FrameAnimationView mFrameAnimationBg;
    private FrameAnimationInfo mFrameAnimationInfo;
    private int mHour;
    private ImageView mHourHand;
    private int mHourLength;
    private int mHourLengthTail;
    private boolean mIsDual;
    private int mMinute;
    private ImageView mMinuteHand;
    private int mMinuteLength;
    private int mMinuteLengthTail;
    private Paint mPaint;
    private boolean mPendingAnimation;
    private int mRound;
    private int mSize;
    private boolean mTimeInitialized;

    public ClockPanelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mHourHand = (ImageView) findViewById(R.id.hour_hand);
        this.mMinuteHand = (ImageView) findViewById(R.id.minute_hand);
    }

    public void init(int i, boolean z, String str) {
        this.mFrameAnimationInfo = FrameAnimationManager.getFrameAnimationInfoByStyleName(str);
        this.mSize = i;
        this.mIsDual = z;
        boolean z2 = this.mFrameAnimationInfo != null;
        if (this.mSize == 0) {
            int i2 = this.mIsDual ? R.style.Aod_clock_panel_dual_p : !z2 ? R.style.Aod_clock_panel_p : R.style.Aod_clock_animation_panel_p;
            this.mClockPanelStyle = i2;
        } else if (this.mSize == 2) {
            int i3 = this.mIsDual ? R.style.Aod_clock_panel_dual_s : !z2 ? R.style.Aod_clock_panel_s : R.style.Aod_clock_panel_animation_s;
            this.mClockPanelStyle = i3;
        } else {
            int i4 = this.mIsDual ? R.style.Aod_clock_panel_dual : !z2 ? R.style.Aod_clock_panel : R.style.Aod_clock_animation_panel;
            this.mClockPanelStyle = i4;
        }
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(this.mClockPanelStyle, R.styleable.clockPanel);
        if (this.mIsDual) {
            this.mHourLength = (int) obtainStyledAttributes.getDimension(0, 0.0f);
            this.mHourLengthTail = (int) obtainStyledAttributes.getDimension(1, 0.0f);
            this.mMinuteLength = (int) obtainStyledAttributes.getDimension(2, 0.0f);
            this.mMinuteLengthTail = (int) obtainStyledAttributes.getDimension(3, 0.0f);
            this.mRound = (int) obtainStyledAttributes.getDimension(4, 0.0f);
        } else {
            this.mHourHand.setVisibility(0);
            this.mHourLength = (int) obtainStyledAttributes.getDimension(0, 0.0f);
            LayoutParams layoutParams = this.mHourHand.getLayoutParams();
            layoutParams.width = this.mHourLength;
            layoutParams.height = this.mHourLength;
            this.mHourHand.setLayoutParams(layoutParams);
            this.mMinuteHand.setVisibility(0);
            this.mMinuteLength = (int) obtainStyledAttributes.getDimension(2, 0.0f);
            LayoutParams layoutParams2 = this.mMinuteHand.getLayoutParams();
            layoutParams2.width = this.mMinuteLength;
            layoutParams2.height = this.mMinuteLength;
            this.mMinuteHand.setLayoutParams(layoutParams2);
        }
        if (!z2) {
            setBackgroundResource(this.mIsDual ? R.drawable.clock_panel_dual : R.drawable.clock_panel);
        } else {
            this.mHourHand.setImageResource(this.mFrameAnimationInfo.mResIdHour);
            this.mMinuteHand.setImageResource(this.mFrameAnimationInfo.mResIdMin);
            if (this.mSize == 2) {
                setBackgroundResource(this.mFrameAnimationInfo.mResIdBg);
            } else {
                startAnimation();
            }
        }
        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) getLayoutParams();
        layoutParams3.width = (int) obtainStyledAttributes.getDimension(5, 0.0f);
        layoutParams3.height = (int) obtainStyledAttributes.getDimension(6, 0.0f);
        layoutParams3.bottomMargin = (int) obtainStyledAttributes.getDimension(7, 0.0f);
        setLayoutParams(layoutParams3);
        obtainStyledAttributes.recycle();
    }

    public void setTime(int i, int i2) {
        this.mHour = i;
        this.mMinute = i2;
        this.mTimeInitialized = true;
        if (this.mPendingAnimation) {
            startAnimation();
            return;
        }
        if (this.mHourHand != null) {
            this.mHourHand.setRotation(((float) (((this.mHour % 12) * 360) / 12)) + (((float) (this.mMinute * 360)) / 720.0f));
        }
        if (this.mMinuteHand != null) {
            this.mMinuteHand.setRotation((float) ((this.mMinute * 360) / 60));
        }
    }

    public void onDraw(Canvas canvas) {
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
        super.onDraw(canvas);
        if (this.mIsDual) {
            this.mPaint.setColor(-1);
            this.mPaint.setStrokeWidth(3.0f);
            Canvas canvas2 = canvas;
            canvas2.drawLine(((float) (((double) (-this.mHourLengthTail)) * Math.sin((((double) (((this.mHour * 60) + this.mMinute) * 2)) * 3.141592653589793d) / 720.0d))) + ((float) this.mRound), ((float) (((double) this.mHourLengthTail) * Math.cos((((double) (((this.mHour * 60) + this.mMinute) * 2)) * 3.141592653589793d) / 720.0d))) + ((float) this.mRound), ((float) (((double) this.mHourLength) * Math.sin((((double) (((this.mHour * 60) + this.mMinute) * 2)) * 3.141592653589793d) / 720.0d))) + ((float) this.mRound), ((float) (((double) (-this.mHourLength)) * Math.cos((((double) (((this.mHour * 60) + this.mMinute) * 2)) * 3.141592653589793d) / 720.0d))) + ((float) this.mRound), this.mPaint);
            canvas2.drawLine(((float) (((double) (-this.mMinuteLengthTail)) * Math.sin((((double) (this.mMinute * 2)) * 3.141592653589793d) / 60.0d))) + ((float) this.mRound), ((float) (((double) this.mMinuteLengthTail) * Math.cos((((double) (this.mMinute * 2)) * 3.141592653589793d) / 60.0d))) + ((float) this.mRound), ((float) (((double) this.mMinuteLength) * Math.sin((((double) (this.mMinute * 2)) * 3.141592653589793d) / 60.0d))) + ((float) this.mRound), ((float) (((double) (-this.mMinuteLength)) * Math.cos((((double) (this.mMinute * 2)) * 3.141592653589793d) / 60.0d))) + ((float) this.mRound), this.mPaint);
        }
    }

    public void startAnimation() {
        this.mPendingAnimation = false;
        if (!this.mTimeInitialized) {
            this.mPendingAnimation = true;
            return;
        }
        if (this.mFrameAnimationBg == null || this.mFrameAnimationBg.getParent() == null || this.mAnimationView == null || this.mAnimationView.getParent() == null) {
            addAnimationView();
        }
        setOtherElementsVisibility(false, false);
        startBackgroundAnimation();
        startClockHandAnimation(((float) (((this.mHour % 12) * 360) / 12)) + (((float) (this.mMinute * 360)) / 720.0f), (float) ((this.mMinute * 360) / 60));
    }

    public void setOtherElementsVisibility(boolean z, boolean z2) {
        View rootView = getRootView();
        if (rootView != null) {
            for (int findViewById : new int[]{R.id.date, R.id.aod_battery_layout, R.id.icons}) {
                View findViewById2 = rootView.findViewById(findViewById);
                if (findViewById2 != null) {
                    if (z) {
                        if (z2) {
                            findViewById2.animate().alpha(1.0f).setDuration(200).start();
                        } else {
                            findViewById2.setAlpha(1.0f);
                        }
                    } else if (z2) {
                        findViewById2.animate().alpha(0.0f).setDuration(200).start();
                    } else {
                        findViewById2.setAlpha(0.0f);
                    }
                }
            }
        }
    }

    private void startClockHandAnimation(float f, float f2) {
        if (this.mAnimationView != null && this.mAnimationView.getParent() != null) {
            ClockHandDrawer clockHandDrawer = new ClockHandDrawer(getResources().getDrawable(this.mFrameAnimationInfo.mResIdHour), getResources().getDrawable(this.mFrameAnimationInfo.mResIdMin));
            clockHandDrawer.setAnimationValue(f, f2);
            clockHandDrawer.setStartDelay(600000000);
            if (this.mHourHand != null) {
                clockHandDrawer.setHourDrawableSize(this.mHourLength);
                this.mHourHand.setVisibility(8);
            }
            if (this.mMinuteHand != null) {
                clockHandDrawer.setMinDrawableSize(this.mMinuteLength);
                this.mMinuteHand.setVisibility(8);
            }
            clockHandDrawer.setAnimationListener(new AnimationStateListener() {
                public void onAnimationStart() {
                    ClockPanelView.this.mAnimationView.setAlpha(0.0f);
                    ClockPanelView.this.mAnimationView.animate().alpha(1.0f).setDuration(900).start();
                }

                public void onAnimationEnd() {
                    ClockPanelView.this.removeCallbacks(ClockPanelView.this.mClockAnimationEndTask);
                    ClockPanelView.this.post(ClockPanelView.this.mClockAnimationEndTask);
                }
            });
            this.mAnimationView.addAnimationDrawer(clockHandDrawer);
        }
    }

    public static /* synthetic */ void lambda$new$3(ClockPanelView clockPanelView) {
        clockPanelView.setOtherElementsVisibility(true, true);
        clockPanelView.resetClockHandState();
    }

    private void startBackgroundAnimation() {
        if (this.mFrameAnimationBg != null && this.mFrameAnimationBg.getParent() != null) {
            setBackground(null);
            this.mFrameAnimationBg.setAnimationCallBack(new AnimationCallBack() {
                public void onAnimationEnd() {
                    ClockPanelView.this.resetBackground();
                }
            });
            this.mFrameAnimationBg.startAnimation();
        }
    }

    public void resetClockHandState() {
        if (this.mHourHand != null) {
            this.mHourHand.setVisibility(0);
            this.mHourHand.setRotation(((float) (((this.mHour % 12) * 360) / 12)) + (((float) (this.mMinute * 360)) / 720.0f));
        }
        if (this.mMinuteHand != null) {
            this.mMinuteHand.setVisibility(0);
            this.mMinuteHand.setRotation((float) ((this.mMinute * 360) / 60));
        }
        if (this.mAnimationView != null) {
            removeView(this.mAnimationView);
            this.mAnimationView = null;
        }
    }

    public void resetBackground() {
        if (this.mFrameAnimationInfo != null && this.mSize != 2) {
            int measuredWidth = getMeasuredWidth();
            int measuredHeight = getMeasuredHeight();
            if (measuredWidth != 0 && measuredHeight != 0) {
                int[] iArr = this.mFrameAnimationInfo.mResIdArrays;
                Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), iArr[iArr.length - 1]);
                int width = decodeResource.getWidth();
                int height = decodeResource.getHeight();
                if (width != 0 && height != 0) {
                    float min = Math.min((((float) measuredWidth) * 1.0f) / ((float) width), (((float) measuredHeight) * 1.0f) / ((float) height));
                    Matrix matrix = new Matrix();
                    matrix.postScale(min, min);
                    setBackground(new BitmapDrawable(getResources(), Bitmap.createBitmap(decodeResource, 0, 0, width, height, matrix, true)));
                    if (this.mFrameAnimationBg != null) {
                        this.mFrameAnimationBg.setAnimationCallBack(null);
                        removeView(this.mFrameAnimationBg);
                        this.mFrameAnimationBg = null;
                    }
                }
            }
        }
    }

    private void addAnimationView() {
        clearAnimationView();
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        this.mAnimationView = new AnimationView(getContext());
        addView(this.mAnimationView, 0, layoutParams);
        this.mFrameAnimationBg = new FrameAnimationView(getContext(), this.mFrameAnimationInfo.mFps, this.mFrameAnimationInfo.mResIdArrays);
        addView(this.mFrameAnimationBg, 0, layoutParams);
    }

    public void clearAnimationView() {
        if (this.mFrameAnimationBg != null) {
            this.mFrameAnimationBg.setAnimationCallBack(null);
            removeView(this.mFrameAnimationBg);
            this.mFrameAnimationBg = null;
        }
        if (this.mAnimationView != null) {
            removeView(this.mAnimationView);
            this.mAnimationView = null;
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        removeCallbacks(this.mClockAnimationEndTask);
        setOtherElementsVisibility(true, false);
        resetClockHandState();
        resetBackground();
        super.onDetachedFromWindow();
    }
}
