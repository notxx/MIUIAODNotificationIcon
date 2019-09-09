package com.xiaomi.p003a.p004a.p005a;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.Map;

/* renamed from: com.xiaomi.a.a.a.a */
public interface C0122a extends IInterface {

    /* renamed from: com.xiaomi.a.a.a.a$a */
    public static abstract class C0123a extends Binder implements C0122a {

        /* renamed from: com.xiaomi.a.a.a.a$a$a */
        private static class C0124a implements C0122a {

            /* renamed from: a */
            private IBinder f2a;

            C0124a(IBinder iBinder) {
                this.f2a = iBinder;
            }

            public IBinder asBinder() {
                return this.f2a;
            }

            /* renamed from: a */
            public String mo2342a(String str, Map map) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.xmsf.push.service.IHttpService");
                    obtain.writeString(str);
                    obtain.writeMap(map);
                    this.f2a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: b */
            public String mo2343b(String str, Map map) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.xmsf.push.service.IHttpService");
                    obtain.writeString(str);
                    obtain.writeMap(map);
                    this.f2a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        /* renamed from: a */
        public static C0122a m4a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.xmsf.push.service.IHttpService");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof C0122a)) {
                return new C0124a(iBinder);
            }
            return (C0122a) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.xiaomi.xmsf.push.service.IHttpService");
                        String a = mo2342a(parcel.readString(), parcel.readHashMap(getClass().getClassLoader()));
                        parcel2.writeNoException();
                        parcel2.writeString(a);
                        return true;
                    case 2:
                        parcel.enforceInterface("com.xiaomi.xmsf.push.service.IHttpService");
                        String b = mo2343b(parcel.readString(), parcel.readHashMap(getClass().getClassLoader()));
                        parcel2.writeNoException();
                        parcel2.writeString(b);
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.xiaomi.xmsf.push.service.IHttpService");
                return true;
            }
        }
    }

    /* renamed from: a */
    String mo2342a(String str, Map map) throws RemoteException;

    /* renamed from: b */
    String mo2343b(String str, Map map) throws RemoteException;
}
