package com.miui.aod;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IMiuiAodCallback extends IInterface {

    public static abstract class Stub extends Binder implements IMiuiAodCallback {

        private static class Proxy implements IMiuiAodCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public void onDozeStateChanged(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.miui.aod.IMiuiAodCallback");
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void setAnimateWakeup(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.miui.aod.IMiuiAodCallback");
                    obtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void onDozingRequested(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.miui.aod.IMiuiAodCallback");
                    obtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void onExtendPulse() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.miui.aod.IMiuiAodCallback");
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void notifyKeycodeGoto() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.miui.aod.IMiuiAodCallback");
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public static IMiuiAodCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.miui.aod.IMiuiAodCallback");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IMiuiAodCallback)) {
                return new Proxy(iBinder);
            }
            return (IMiuiAodCallback) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            String str = "com.miui.aod.IMiuiAodCallback";
            if (i != 1598968902) {
                boolean z = false;
                switch (i) {
                    case 1:
                        parcel.enforceInterface(str);
                        onDozeStateChanged(parcel.readInt());
                        return true;
                    case 2:
                        parcel.enforceInterface(str);
                        if (parcel.readInt() != 0) {
                            z = true;
                        }
                        setAnimateWakeup(z);
                        return true;
                    case 3:
                        parcel.enforceInterface(str);
                        if (parcel.readInt() != 0) {
                            z = true;
                        }
                        onDozingRequested(z);
                        return true;
                    case 4:
                        parcel.enforceInterface(str);
                        onExtendPulse();
                        return true;
                    case 5:
                        parcel.enforceInterface(str);
                        notifyKeycodeGoto();
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

    void notifyKeycodeGoto() throws RemoteException;

    void onDozeStateChanged(int i) throws RemoteException;

    void onDozingRequested(boolean z) throws RemoteException;

    void onExtendPulse() throws RemoteException;

    void setAnimateWakeup(boolean z) throws RemoteException;
}
