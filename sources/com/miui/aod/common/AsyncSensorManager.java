package com.miui.aod.common;

import android.hardware.HardwareBuffer;
import android.hardware.Sensor;
import android.hardware.SensorAdditionalInfo;
import android.hardware.SensorDirectChannel;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.SensorManager.DynamicSensorCallback;
import android.hardware.TriggerEventListener;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.MemoryFile;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.util.List;

public class AsyncSensorManager extends SensorManager {
    private static final String TAG = "AsyncSensorManager";
    private static final HandlerThread sHandlerThread = new HandlerThread("async_sensor");
    private static AsyncSensorManager sInstance;
    final Handler mHandler;
    private final SensorManager mInner;
    private final List<Sensor> mSensorCache;

    private AsyncSensorManager(SensorManager sensorManager) {
        this.mInner = sensorManager;
        if (!sHandlerThread.isAlive()) {
            sHandlerThread.start();
        }
        this.mHandler = new Handler(sHandlerThread.getLooper());
        this.mSensorCache = this.mInner.getSensorList(-1);
    }

    public static synchronized AsyncSensorManager getInstance(SensorManager sensorManager) {
        AsyncSensorManager asyncSensorManager;
        synchronized (AsyncSensorManager.class) {
            if (sInstance == null) {
                sInstance = new AsyncSensorManager(sensorManager);
            }
            asyncSensorManager = sInstance;
        }
        return asyncSensorManager;
    }

    /* access modifiers changed from: protected */
    public List<Sensor> getFullSensorList() {
        return this.mSensorCache;
    }

    /* access modifiers changed from: protected */
    public List<Sensor> getFullDynamicSensorList() {
        return this.mInner.getDynamicSensorList(-1);
    }

    /* access modifiers changed from: protected */
    public boolean registerListenerImpl(SensorEventListener sensorEventListener, Sensor sensor, int i, Handler handler, int i2, int i3) {
        Handler handler2 = this.mHandler;
        $$Lambda$AsyncSensorManager$Dl2cLRVKRVTszrVz6T6RyFbfM9Q r0 = new Runnable(sensorEventListener, sensor, i, i2, handler) {
            private final /* synthetic */ SensorEventListener f$1;
            private final /* synthetic */ Sensor f$2;
            private final /* synthetic */ int f$3;
            private final /* synthetic */ int f$4;
            private final /* synthetic */ Handler f$5;

            {
                this.f$1 = r2;
                this.f$2 = r3;
                this.f$3 = r4;
                this.f$4 = r5;
                this.f$5 = r6;
            }

            public final void run() {
                AsyncSensorManager.lambda$registerListenerImpl$0(AsyncSensorManager.this, this.f$1, this.f$2, this.f$3, this.f$4, this.f$5);
            }
        };
        handler2.post(r0);
        return true;
    }

    public static /* synthetic */ void lambda$registerListenerImpl$0(AsyncSensorManager asyncSensorManager, SensorEventListener sensorEventListener, Sensor sensor, int i, int i2, Handler handler) {
        if (!asyncSensorManager.mInner.registerListener(sensorEventListener, sensor, i, i2, handler)) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Registering ");
            sb.append(sensorEventListener);
            sb.append(" for ");
            sb.append(sensor);
            sb.append(" failed.");
            Log.e(str, sb.toString());
        }
    }

    /* access modifiers changed from: protected */
    public boolean flushImpl(SensorEventListener sensorEventListener) {
        return this.mInner.flush(sensorEventListener);
    }

    /* access modifiers changed from: protected */
    public SensorDirectChannel createDirectChannelImpl(MemoryFile memoryFile, HardwareBuffer hardwareBuffer) {
        throw new UnsupportedOperationException("not implemented");
    }

    /* access modifiers changed from: protected */
    public void destroyDirectChannelImpl(SensorDirectChannel sensorDirectChannel) {
        throw new UnsupportedOperationException("not implemented");
    }

    /* access modifiers changed from: protected */
    public int configureDirectChannelImpl(SensorDirectChannel sensorDirectChannel, Sensor sensor, int i) {
        throw new UnsupportedOperationException("not implemented");
    }

    /* access modifiers changed from: protected */
    public void registerDynamicSensorCallbackImpl(DynamicSensorCallback dynamicSensorCallback, Handler handler) {
        this.mHandler.post(new Runnable(dynamicSensorCallback, handler) {
            private final /* synthetic */ DynamicSensorCallback f$1;
            private final /* synthetic */ Handler f$2;

            {
                this.f$1 = r2;
                this.f$2 = r3;
            }

            public final void run() {
                AsyncSensorManager.this.mInner.registerDynamicSensorCallback(this.f$1, this.f$2);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void unregisterDynamicSensorCallbackImpl(DynamicSensorCallback dynamicSensorCallback) {
        this.mHandler.post(new Runnable(dynamicSensorCallback) {
            private final /* synthetic */ DynamicSensorCallback f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                AsyncSensorManager.this.mInner.unregisterDynamicSensorCallback(this.f$1);
            }
        });
    }

    /* access modifiers changed from: protected */
    public boolean requestTriggerSensorImpl(TriggerEventListener triggerEventListener, Sensor sensor) {
        this.mHandler.post(new Runnable(triggerEventListener, sensor) {
            private final /* synthetic */ TriggerEventListener f$1;
            private final /* synthetic */ Sensor f$2;

            {
                this.f$1 = r2;
                this.f$2 = r3;
            }

            public final void run() {
                AsyncSensorManager.lambda$requestTriggerSensorImpl$3(AsyncSensorManager.this, this.f$1, this.f$2);
            }
        });
        return true;
    }

    public static /* synthetic */ void lambda$requestTriggerSensorImpl$3(AsyncSensorManager asyncSensorManager, TriggerEventListener triggerEventListener, Sensor sensor) {
        if (!asyncSensorManager.mInner.requestTriggerSensor(triggerEventListener, sensor)) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Requesting ");
            sb.append(triggerEventListener);
            sb.append(" for ");
            sb.append(sensor);
            sb.append(" failed.");
            Log.e(str, sb.toString());
        }
    }

    /* access modifiers changed from: protected */
    public boolean cancelTriggerSensorImpl(TriggerEventListener triggerEventListener, Sensor sensor, boolean z) {
        Preconditions.checkArgument(z);
        this.mHandler.post(new Runnable(triggerEventListener, sensor) {
            private final /* synthetic */ TriggerEventListener f$1;
            private final /* synthetic */ Sensor f$2;

            {
                this.f$1 = r2;
                this.f$2 = r3;
            }

            public final void run() {
                AsyncSensorManager.lambda$cancelTriggerSensorImpl$4(AsyncSensorManager.this, this.f$1, this.f$2);
            }
        });
        return true;
    }

    public static /* synthetic */ void lambda$cancelTriggerSensorImpl$4(AsyncSensorManager asyncSensorManager, TriggerEventListener triggerEventListener, Sensor sensor) {
        if (!asyncSensorManager.mInner.cancelTriggerSensor(triggerEventListener, sensor)) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Canceling ");
            sb.append(triggerEventListener);
            sb.append(" for ");
            sb.append(sensor);
            sb.append(" failed.");
            Log.e(str, sb.toString());
        }
    }

    /* access modifiers changed from: protected */
    public boolean initDataInjectionImpl(boolean z) {
        throw new UnsupportedOperationException("not implemented");
    }

    /* access modifiers changed from: protected */
    public boolean injectSensorDataImpl(Sensor sensor, float[] fArr, int i, long j) {
        throw new UnsupportedOperationException("not implemented");
    }

    /* access modifiers changed from: protected */
    public boolean setOperationParameterImpl(SensorAdditionalInfo sensorAdditionalInfo) {
        this.mHandler.post(new Runnable(sensorAdditionalInfo) {
            private final /* synthetic */ SensorAdditionalInfo f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                AsyncSensorManager.this.mInner.setOperationParameter(this.f$1);
            }
        });
        return true;
    }

    /* access modifiers changed from: protected */
    public void unregisterListenerImpl(SensorEventListener sensorEventListener, Sensor sensor) {
        this.mHandler.post(new Runnable(sensor, sensorEventListener) {
            private final /* synthetic */ Sensor f$1;
            private final /* synthetic */ SensorEventListener f$2;

            {
                this.f$1 = r2;
                this.f$2 = r3;
            }

            public final void run() {
                AsyncSensorManager.lambda$unregisterListenerImpl$6(AsyncSensorManager.this, this.f$1, this.f$2);
            }
        });
    }

    public static /* synthetic */ void lambda$unregisterListenerImpl$6(AsyncSensorManager asyncSensorManager, Sensor sensor, SensorEventListener sensorEventListener) {
        if (sensor == null) {
            asyncSensorManager.mInner.unregisterListener(sensorEventListener);
        } else {
            asyncSensorManager.mInner.unregisterListener(sensorEventListener, sensor);
        }
    }
}
