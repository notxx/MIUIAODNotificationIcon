package com.miui.aod.util;

import android.content.AsyncQueryHandler;
import android.content.AsyncQueryHandler.WorkerHandler;
import android.content.Context;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteDiskIOException;
import android.database.sqlite.SQLiteFullException;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public abstract class QueryHandler extends AsyncQueryHandler {

    protected class CatchingWorkerHandler extends WorkerHandler {
        public CatchingWorkerHandler(Looper looper) {
            super(QueryHandler.this, looper);
        }

        public void handleMessage(Message message) {
            try {
                QueryHandler.super.handleMessage(message);
            } catch (SQLiteDiskIOException e) {
                Log.w("QueryHandler", "Exception on background worker thread", e);
            } catch (SQLiteFullException e2) {
                Log.w("QueryHandler", "Exception on background worker thread", e2);
            } catch (SQLiteDatabaseCorruptException e3) {
                Log.w("QueryHandler", "Exception on background worker thread", e3);
            }
        }
    }

    /* JADX WARNING: type inference failed for: r0v0, types: [android.os.Handler, com.miui.aod.util.QueryHandler$CatchingWorkerHandler] */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v0, types: [android.os.Handler, com.miui.aod.util.QueryHandler$CatchingWorkerHandler]
      assigns: [com.miui.aod.util.QueryHandler$CatchingWorkerHandler]
      uses: [android.os.Handler]
      mth insns count: 2
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.os.Handler createHandler(android.os.Looper r2) {
        /*
            r1 = this;
            com.miui.aod.util.QueryHandler$CatchingWorkerHandler r0 = new com.miui.aod.util.QueryHandler$CatchingWorkerHandler
            r0.<init>(r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.miui.aod.util.QueryHandler.createHandler(android.os.Looper):android.os.Handler");
    }

    public QueryHandler(Context context) {
        super(context.getContentResolver());
    }
}
