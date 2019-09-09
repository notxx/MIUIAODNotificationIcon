package com.miui.aod;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import com.miui.aod.DozeHost.Callback;
import com.miui.aod.IMiuiAodService.Stub;

public class MiuiAodService extends Service {
    private Stub mBinder = new Stub() {
        public void registerCallback(IMiuiAodCallback iMiuiAodCallback) throws RemoteException {
            MiuiAodService.this.mCallback = iMiuiAodCallback;
            MiuiAodService.this.mCallback.onDozingRequested(AODApplication.getHost().isDozing());
        }

        public void unregisterCallback() throws RemoteException {
            MiuiAodService.this.mCallback = null;
        }

        public void onKeyguardTransparent() {
            AODApplication.getHost().onKeyguardTransparent();
        }

        public void fireAodState(boolean z) {
            AODApplication.getHost().fireAodState(z, "reason_fod");
        }

        public void onGxzwIconChanged(boolean z) {
            AODApplication.getHost().onGxzwIconChanged(z);
        }

        public void fireFingerprintPressed(boolean z) {
            AODApplication.getHost().fireFingerprintPressed(z);
        }

        public void stopDozing() {
            AODApplication.getHost().stopDozing();
        }

        public void onSimPinSecureChanged(boolean z) {
            AODApplication.getHost().setSimPinSecure(z);
        }
    };
    /* access modifiers changed from: private */
    public IMiuiAodCallback mCallback;
    private Callback mHostCallback = new Callback() {
        public void fireAodState(boolean z, String str) {
        }

        public void onAodAnimate(boolean z) {
        }

        public void onFingerprintPressed(boolean z) {
        }

        public void onPowerSaveChanged(boolean z) {
        }

        public void notifyKeycodeGoto() {
            if (MiuiAodService.this.mCallback != null) {
                try {
                    MiuiAodService.this.mCallback.notifyKeycodeGoto();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        public void onDozingRequested(boolean z) {
            if (MiuiAodService.this.mCallback != null) {
                try {
                    MiuiAodService.this.mCallback.onDozingRequested(z);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        public void onExtendPulse() {
            if (MiuiAodService.this.mCallback != null) {
                try {
                    MiuiAodService.this.mCallback.onExtendPulse();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public void onCreate() {
        super.onCreate();
        if (Utils.SUPPORT_AOD) {
            AODApplication.getHost().addCallback(this.mHostCallback);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        AODApplication.getHost().removeCallback(this.mHostCallback);
    }

    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }
}
