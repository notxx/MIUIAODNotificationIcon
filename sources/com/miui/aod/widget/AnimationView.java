package com.miui.aod.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.Choreographer.FrameCallback;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class AnimationView extends TextureView implements SurfaceTextureListener {
    private FrameCallback frameCallback;
    /* access modifiers changed from: private */
    public volatile boolean mAnimationRunning;
    private final List<AnimationDrawer> mDrawerList;
    private boolean mPendingStartAnimation;
    private boolean mSurfaceAvailable;

    public interface AnimationDrawer {

        public interface AnimationStateListener {
            void onAnimationEnd() {
            }

            void onAnimationStart() {
            }
        }

        boolean onAnimationDraw(Canvas canvas, long j);
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return true;
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    public AnimationView(Context context) {
        this(context, null);
    }

    public AnimationView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AnimationView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDrawerList = Collections.synchronizedList(new ArrayList());
        this.frameCallback = new FrameCallback() {
            public void doFrame(long j) {
                AnimationView.this.dispatchDraw(j);
                if (AnimationView.this.mAnimationRunning) {
                    Choreographer.getInstance().postFrameCallback(this);
                }
            }
        };
        init();
    }

    private void init() {
        setOpaque(false);
        this.mSurfaceAvailable = false;
        this.mAnimationRunning = false;
        setSurfaceTextureListener(this);
    }

    private void startAnimation() {
        if (!this.mAnimationRunning) {
            if (this.mSurfaceAvailable) {
                this.mAnimationRunning = true;
                Choreographer.getInstance().postFrameCallback(this.frameCallback);
            } else {
                this.mPendingStartAnimation = true;
            }
        }
    }

    private void stopAnimation() {
        this.mAnimationRunning = false;
        this.mPendingStartAnimation = false;
        Choreographer.getInstance().postFrameCallback(this.frameCallback);
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        this.mSurfaceAvailable = true;
        if (this.mPendingStartAnimation) {
            startAnimation();
            this.mPendingStartAnimation = false;
        }
    }

    /* access modifiers changed from: private */
    public void dispatchDraw(long j) {
        if (this.mSurfaceAvailable && !this.mDrawerList.isEmpty()) {
            Canvas lockCanvas = lockCanvas();
            if (lockCanvas != null) {
                try {
                    lockCanvas.drawColor(0, Mode.CLEAR);
                    synchronized (this.mDrawerList) {
                        ListIterator listIterator = this.mDrawerList.listIterator();
                        while (listIterator.hasNext()) {
                            if (!((AnimationDrawer) listIterator.next()).onAnimationDraw(lockCanvas, j)) {
                                listIterator.remove();
                            }
                        }
                        if (this.mDrawerList.isEmpty()) {
                            stopAnimation();
                        }
                    }
                    unlockCanvasAndPostSafely(lockCanvas);
                } catch (Throwable th) {
                    unlockCanvasAndPostSafely(lockCanvas);
                    throw th;
                }
            }
        }
    }

    private void unlockCanvasAndPostSafely(Canvas canvas) {
        if (this.mSurfaceAvailable) {
            unlockCanvasAndPost(canvas);
        }
    }

    public void addAnimationDrawer(AnimationDrawer animationDrawer) {
        if (animationDrawer != null) {
            synchronized (this.mDrawerList) {
                for (AnimationDrawer equals : this.mDrawerList) {
                    if (animationDrawer.equals(equals)) {
                        Log.e("AnimationView", "addAnimationDrawer: duplicate");
                        return;
                    }
                }
                this.mDrawerList.add(animationDrawer);
                if (!this.mAnimationRunning) {
                    startAnimation();
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!this.mDrawerList.isEmpty()) {
            startAnimation();
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mDrawerList.clear();
        stopAnimation();
    }
}
