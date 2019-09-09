package com.miui.aod;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IMiuiAodService extends IInterface {

    public static abstract class Stub extends Binder implements IMiuiAodService {
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.miui.aod.IMiuiAodService");
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            String str = "com.miui.aod.IMiuiAodService";
            if (i != 1598968902) {
                boolean z = false;
                switch (i) {
                    case 1:
                        parcel.enforceInterface(str);
                        registerCallback(com.miui.aod.IMiuiAodCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    case 2:
                        parcel.enforceInterface(str);
                        unregisterCallback();
                        return true;
                    case 3:
                        parcel.enforceInterface(str);
                        onKeyguardTransparent();
                        return true;
                    case 4:
                        parcel.enforceInterface(str);
                        if (parcel.readInt() != 0) {
                            z = true;
                        }
                        onGxzwIconChanged(z);
                        return true;
                    case 5:
                        parcel.enforceInterface(str);
                        if (parcel.readInt() != 0) {
                            z = true;
                        }
                        fireFingerprintPressed(z);
                        return true;
                    case 6:
                        parcel.enforceInterface(str);
                        stopDozing();
                        return true;
                    case 7:
                        parcel.enforceInterface(str);
                        if (parcel.readInt() != 0) {
                            z = true;
                        }
                        onSimPinSecureChanged(z);
                        return true;
                    case 8:
                        parcel.enforceInterface(str);
                        if (parcel.readInt() != 0) {
                            z = true;
                        }
                        fireAodState(z);
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString(str);
                return true;
            }
        }
    }

    void fireAodState(boolean z) throws RemoteException;

    void fireFingerprintPressed(boolean z) throws RemoteException;

    void onGxzwIconChanged(boolean z) throws RemoteException;

    void onKeyguardTransparent() throws RemoteException;

    void onSimPinSecureChanged(boolean z) throws RemoteException;

    void registerCallback(IMiuiAodCallback iMiuiAodCallback) throws RemoteException;

    void stopDozing() throws RemoteException;

    void unregisterCallback() throws RemoteException;
}
