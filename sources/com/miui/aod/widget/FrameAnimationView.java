package com.miui.aod.widget;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import com.miui.aod.widget.FrameAnimationController.AnimationCallBack;

public class FrameAnimationView extends TextureView implements SurfaceTextureListener {
    private FrameAnimationController mFrameAnimation;
    private int mFrameInterval;
    private boolean mPendingStartAnimation;
    private int[] mResArray;
    private boolean mSurfaceReady;

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    public FrameAnimationView(Context context, int i, int[] iArr) {
        super(context);
        if (i <= 0) {
            i = 30;
        }
        this.mFrameInterval = 1000 / i;
        this.mResArray = iArr;
        init();
    }

    private void init() {
        this.mPendingStartAnimation = false;
        this.mSurfaceReady = false;
        this.mFrameAnimation = new FrameAnimationController(this, this);
        this.mFrameAnimation.setFrameInterval(this.mFrameInterval);
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        this.mSurfaceReady = true;
        if (this.mPendingStartAnimation) {
            startAnimation();
            this.mPendingStartAnimation = false;
        }
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        this.mSurfaceReady = false;
        return true;
    }

    public void startAnimation() {
        if (this.mSurfaceReady) {
            this.mFrameAnimation.startAnimation(this.mResArray, 0);
            this.mPendingStartAnimation = false;
            return;
        }
        this.mPendingStartAnimation = true;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mFrameAnimation.release();
    }

    public void setAnimationCallBack(AnimationCallBack animationCallBack) {
        if (this.mFrameAnimation != null) {
            this.mFrameAnimation.setAnimationCallBack(animationCallBack);
        }
    }
}
