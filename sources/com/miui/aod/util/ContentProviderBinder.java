package com.miui.aod.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import com.miui.aod.Utils;

public class ContentProviderBinder {
    public ChangeObserver mChangeObserver = new ChangeObserver(null);
    protected String[] mColumns;
    private Context mContext;
    private QueryCompleteListener mQueryCompletedListener;
    private QueryHandler mQueryHandler;
    public Uri mUri;
    protected String mWhere;

    public static class Builder {
        private ContentProviderBinder mBinder;

        public Builder(ContentProviderBinder contentProviderBinder) {
            this.mBinder = contentProviderBinder;
        }

        public Builder setWhere(String str) {
            this.mBinder.mWhere = str;
            return this;
        }

        public Builder setColumns(String[] strArr) {
            this.mBinder.mColumns = strArr;
            return this;
        }
    }

    private class ChangeObserver extends ContentObserver {
        public ChangeObserver(Handler handler) {
            super(handler);
        }

        public void onChange(boolean z) {
            ContentProviderBinder.this.startQuery();
        }
    }

    public interface QueryCompleteListener {
        void onQueryCompleted(Uri uri, int i);
    }

    public void setUri(Uri uri) {
        this.mUri = uri;
    }

    public ContentProviderBinder(Context context) {
        this.mContext = context;
        this.mQueryHandler = new QueryHandler(context) {
            /* access modifiers changed from: protected */
            public void onQueryComplete(int i, Object obj, Cursor cursor) {
                ContentProviderBinder.this.onQueryComplete(cursor);
            }
        };
    }

    public void init() {
        registerObserver(true);
        startQuery();
    }

    public void finish() {
        registerObserver(false);
    }

    private void registerObserver(boolean z) {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        contentResolver.unregisterContentObserver(this.mChangeObserver);
        if (z && this.mUri != null) {
            try {
                contentResolver.registerContentObserver(this.mUri, true, this.mChangeObserver);
            } catch (IllegalArgumentException e) {
                StringBuilder sb = new StringBuilder();
                sb.append(e.toString());
                sb.append("  uri:");
                sb.append(this.mUri);
                Log.e("ContentProviderBinder", sb.toString());
            }
        }
    }

    public void startQuery() {
        if (this.mUri == null) {
            Log.d("ContentProviderBinder", "startQuery  uri == null");
        } else if (Utils.isBootCompleted()) {
            this.mQueryHandler.cancelOperation(100);
            String str = this.mWhere;
            StringBuilder sb = new StringBuilder();
            sb.append("start query: ");
            sb.append(this.mUri);
            sb.append("\n where:");
            sb.append(str);
            Log.d("ContentProviderBinder", sb.toString());
            this.mQueryHandler.startQuery(100, null, this.mUri, this.mColumns, str, null, null);
        }
    }

    /* access modifiers changed from: private */
    public void onQueryComplete(Cursor cursor) {
        int i;
        Exception e;
        if (cursor != null) {
            try {
                i = cursor.getCount();
                String str = "ContentProviderBinder";
                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append("num=");
                    sb.append(i);
                    sb.append("; muri=");
                    sb.append(this.mUri);
                    Log.d(str, sb.toString());
                } catch (Exception e2) {
                    e = e2;
                }
            } catch (Exception e3) {
                Exception exc = e3;
                i = 0;
                e = exc;
                try {
                    e.printStackTrace();
                    if (cursor != null) {
                        try {
                            cursor.close();
                        } catch (Exception e4) {
                            e4.printStackTrace();
                        }
                    }
                    if (this.mQueryCompletedListener != null) {
                        return;
                    }
                } finally {
                    if (cursor != null) {
                        try {
                            cursor.close();
                        } catch (Exception e5) {
                            e5.printStackTrace();
                        }
                    }
                }
            }
        } else {
            i = 0;
        }
        if (cursor != null) {
            cursor.close();
        }
        if (this.mQueryCompletedListener != null && this.mUri != null) {
            this.mQueryCompletedListener.onQueryCompleted(this.mUri, i);
        }
    }

    public void setQueryCompleteListener(QueryCompleteListener queryCompleteListener) {
        this.mQueryCompletedListener = queryCompleteListener;
    }
}
