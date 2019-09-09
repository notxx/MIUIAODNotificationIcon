package com.miui.aod.util;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import java.util.ArrayList;

public class NotificationController {
    public static final Uri URI = Uri.parse("content://keyguard.notification/notifications");
    private static NotificationController sInstance;
    /* access modifiers changed from: private */
    public Context mContext;
    ContentObserver mNotificationChangeObserver = new ContentObserver(new Handler()) {
        public void onChange(boolean z) {
            NotificationController.this.update();
        }
    };
    /* access modifiers changed from: private */
    public NotificationChangeListener mNotificationListener;

    public interface NotificationChangeListener {
        void onUpdate(ArrayList<String> arrayList);
    }

    public static synchronized NotificationController getInstance(Context context) {
        NotificationController notificationController;
        synchronized (NotificationController.class) {
            if (sInstance == null) {
                sInstance = new NotificationController(context);
            }
            notificationController = sInstance;
        }
        return notificationController;
    }

    private NotificationController(Context context) {
        this.mContext = context;
        context.getContentResolver().registerContentObserver(URI, false, this.mNotificationChangeObserver);
    }

    public void update() {
        new AsyncTask<Void, Void, ArrayList<String>>() {
            /* access modifiers changed from: protected */
            public ArrayList<String> doInBackground(Void... voidArr) {
                Cursor query;
                ArrayList<String> arrayList = new ArrayList<>();
                try {
                    query = NotificationController.this.mContext.getContentResolver().query(NotificationController.URI, new String[]{"pkg"}, null, null, null);
                    if (query != null) {
                        while (query.moveToNext()) {
                            arrayList.add(query.getString(0));
                        }
                        query.close();
                    }
                } catch (Exception e) {
                    Log.e("NotificationController", "query notification fail", e);
                } catch (Throwable th) {
                    query.close();
                    throw th;
                }
                return arrayList;
            }

            /* access modifiers changed from: protected */
            public void onPostExecute(ArrayList<String> arrayList) {
                if (NotificationController.this.mNotificationListener != null) {
                    NotificationController.this.mNotificationListener.onUpdate(arrayList);
                }
            }
        }.execute(new Void[0]);
    }

    public void setListener(NotificationChangeListener notificationChangeListener) {
        this.mNotificationListener = notificationChangeListener;
        update();
    }
}
