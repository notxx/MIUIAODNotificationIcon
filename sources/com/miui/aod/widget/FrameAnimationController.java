package com.miui.aod.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.view.Choreographer;
import android.view.Choreographer.FrameCallback;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FrameAnimationController {
    private AnimationCallBack mAnimationCallback;
    /* access modifiers changed from: private */
    public Queue<BitmapInfo> mBitmapQueue = new ConcurrentLinkedQueue();
    private final Context mContext;
    /* access modifiers changed from: private */
    public Handler mDecodeHandler;
    private DecodeTask mDecodeTask;
    private HandlerThread mDecodeThread;
    /* access modifiers changed from: private */
    public volatile int mFrameInterval = 32;
    private FrameUpdate mFrameUpdate;
    /* access modifiers changed from: private */
    public final Object mHandlerLock = new Object();
    /* access modifiers changed from: private */
    public Handler mMainHandler = new Handler(Looper.getMainLooper());
    private final TextureView mTextureView;

    public interface AnimationCallBack {
        void onAnimationEnd() {
        }

        void onAnimationStart() {
        }
    }

    private class BitmapInfo {
        /* access modifiers changed from: private */
        public boolean isEnd;
        /* access modifiers changed from: private */
        public final Bitmap mBitmap;
        /* access modifiers changed from: private */
        public final int mPosition;

        public BitmapInfo(Bitmap bitmap, int i, boolean z) {
            this.mBitmap = bitmap;
            this.mPosition = i;
            this.isEnd = z;
        }
    }

    private class DecodeTask implements Runnable {
        private final int[] mAnimRes;
        private volatile int mCurrentPosition;
        private volatile boolean mDecoding;

        private DecodeTask(int[] iArr, int i) {
            this.mDecoding = false;
            this.mAnimRes = iArr;
            this.mCurrentPosition = i % iArr.length;
        }

        /* access modifiers changed from: private */
        public void setDecoding(boolean z) {
            this.mDecoding = z;
        }

        private boolean shouldFinish() {
            return this.mAnimRes == null || this.mAnimRes.length == 0 || !this.mDecoding || this.mCurrentPosition >= this.mAnimRes.length;
        }

        public void run() {
            if (shouldFinish()) {
                setDecoding(false);
                return;
            }
            if (FrameAnimationController.this.mBitmapQueue.offer(new BitmapInfo(FrameAnimationController.this.decodeBitmap(this.mAnimRes[this.mCurrentPosition]), this.mCurrentPosition, this.mCurrentPosition >= this.mAnimRes.length - 1))) {
                this.mCurrentPosition++;
            }
            if (shouldFinish()) {
                setDecoding(false);
                return;
            }
            synchronized (FrameAnimationController.this.mHandlerLock) {
                if (FrameAnimationController.this.mDecodeHandler != null) {
                    FrameAnimationController.this.mDecodeHandler.post(this);
                }
            }
        }
    }

    private class FrameUpdate implements FrameCallback {
        private volatile boolean mDrawing;
        private long mLastFrameTime;
        private Matrix mMatrix;
        private Paint mPaint;

        private FrameUpdate() {
            this.mLastFrameTime = -1;
            this.mPaint = new Paint();
            this.mMatrix = new Matrix();
        }

        /* access modifiers changed from: 0000 */
        public void setDrawing(boolean z) {
            this.mDrawing = z;
            if (!this.mDrawing) {
                Choreographer.getInstance().removeFrameCallback(this);
            }
        }

        public void doFrame(long j) {
            BitmapInfo bitmapInfo = (BitmapInfo) FrameAnimationController.this.mBitmapQueue.peek();
            if (bitmapInfo != null) {
                if (this.mLastFrameTime == -1) {
                    FrameAnimationController.this.mMainHandler.post(new Runnable() {
                        public final void run() {
                            FrameAnimationController.this.notifyAnimationStart();
                        }
                    });
                } else {
                    long j2 = j - this.mLastFrameTime;
                    if (this.mDrawing && j2 < ((long) (FrameAnimationController.this.mFrameInterval * 1000000))) {
                        Choreographer.getInstance().postFrameCallback(this);
                        return;
                    }
                }
                FrameAnimationController.this.mBitmapQueue.poll();
                FrameAnimationController.this.drawBitmap(bitmapInfo.mBitmap, this.mPaint, this.mMatrix, bitmapInfo.mPosition);
                this.mLastFrameTime = j;
                if (bitmapInfo.isEnd) {
                    FrameAnimationController.this.mMainHandler.post(new Runnable() {
                        public final void run() {
                            FrameAnimationController.this.notifyAnimationEnd();
                        }
                    });
                }
                if (this.mDrawing && !bitmapInfo.isEnd) {
                    Choreographer.getInstance().postFrameCallback(this);
                }
            } else if (this.mDrawing) {
                Choreographer.getInstance().postFrameCallback(this);
            }
        }
    }

    public FrameAnimationController(TextureView textureView, SurfaceTextureListener surfaceTextureListener) {
        this.mTextureView = textureView;
        textureView.setSurfaceTextureListener(surfaceTextureListener);
        this.mContext = textureView.getContext();
    }

    public void setFrameInterval(int i) {
        if (i >= 0) {
            this.mFrameInterval = i;
            return;
        }
        throw new UnsupportedOperationException("frameInterval < 0");
    }

    private void prepareDecodeThread() {
        if (this.mDecodeThread == null) {
            this.mDecodeThread = new HandlerThread("aod_frame_animation");
            this.mDecodeThread.start();
        }
        synchronized (this.mHandlerLock) {
            if (this.mDecodeHandler == null) {
                this.mDecodeHandler = new Handler(this.mDecodeThread.getLooper());
            }
        }
    }

    public void startAnimation(int[] iArr, int i) {
        release();
        prepareDecodeThread();
        this.mBitmapQueue.clear();
        this.mDecodeTask = new DecodeTask(iArr, i);
        this.mDecodeTask.setDecoding(true);
        this.mDecodeHandler.post(this.mDecodeTask);
        this.mFrameUpdate = new FrameUpdate();
        this.mFrameUpdate.setDrawing(true);
        Choreographer.getInstance().postFrameCallback(this.mFrameUpdate);
    }

    public void release() {
        if (this.mDecodeTask != null) {
            this.mDecodeTask.setDecoding(false);
            this.mDecodeTask = null;
        }
        synchronized (this.mHandlerLock) {
            if (this.mDecodeHandler != null) {
                this.mDecodeHandler.removeCallbacksAndMessages(null);
                this.mDecodeHandler = null;
            }
        }
        if (this.mDecodeThread != null) {
            this.mDecodeThread.quit();
            this.mDecodeThread = null;
        }
        if (this.mFrameUpdate != null) {
            this.mFrameUpdate.setDrawing(false);
            Choreographer.getInstance().removeFrameCallback(this.mFrameUpdate);
            this.mFrameUpdate = null;
        }
        this.mBitmapQueue.clear();
    }

    /* access modifiers changed from: private */
    public void drawBitmap(Bitmap bitmap, Paint paint, Matrix matrix, int i) {
        Canvas lockCanvas = this.mTextureView.lockCanvas();
        if (lockCanvas != null && bitmap != null) {
            try {
                matrix.reset();
                int width = bitmap.getWidth();
                int width2 = this.mTextureView.getWidth();
                int height = bitmap.getHeight();
                int height2 = this.mTextureView.getHeight();
                float round = (float) Math.round(((float) (width2 - width)) * 0.5f);
                float round2 = (float) Math.round(((float) (height2 - height)) * 0.5f);
                float f = (float) width2;
                float f2 = (f * 1.0f) / ((float) width);
                float f3 = (float) height2;
                float min = Math.min(f2, (1.0f * f3) / ((float) height));
                matrix.postTranslate(round, round2);
                matrix.postScale(min, min, f / 2.0f, f3 / 2.0f);
                lockCanvas.drawColor(0, Mode.CLEAR);
                lockCanvas.drawBitmap(bitmap, matrix, paint);
            } finally {
                unlockCanvasAndPostSafely(lockCanvas);
            }
        }
    }

    private void unlockCanvasAndPostSafely(Canvas canvas) {
        if (this.mTextureView != null && this.mTextureView.isAvailable()) {
            this.mTextureView.unlockCanvasAndPost(canvas);
        }
    }

    /* access modifiers changed from: private */
    public Bitmap decodeBitmap(int i) {
        try {
            return BitmapFactory.decodeResource(this.mContext.getResources(), i);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void notifyAnimationStart() {
        if (this.mAnimationCallback != null) {
            this.mAnimationCallback.onAnimationStart();
        }
    }

    public void notifyAnimationEnd() {
        if (this.mAnimationCallback != null) {
            this.mAnimationCallback.onAnimationEnd();
        }
    }

    public void setAnimationCallBack(AnimationCallBack animationCallBack) {
        this.mAnimationCallback = animationCallBack;
    }
}
