package com.miui.aod;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobInfo.Builder;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.provider.Settings.Secure;
import android.util.Log;
import com.miui.aod.common.BackgroundThread;
import com.miui.aod.widget.AODSettings;
import com.xiaomi.stat.MiStatParams;
import java.util.List;

@TargetApi(21)
public class AnalyticalDataCollectorJobService extends JobService {
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    public static void schedule(Context context) {
        if (isScheduled(context)) {
            Log.i("AnalyticalDataCollectorJobService", "schedule: hasScheduled");
            return;
        }
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JobScheduler.class);
        Builder builder = new Builder(300003, new ComponentName(context, AnalyticalDataCollectorJobService.class));
        builder.setPeriodic(86400000);
        builder.setPersisted(true);
        if (jobScheduler.schedule(builder.build()) > 0) {
            Log.d("AnalyticalDataCollectorJobService", "SettingsJobSchedulerService schedule success");
        } else {
            Log.d("AnalyticalDataCollectorJobService", "SettingsJobSchedulerService schedule failed");
        }
    }

    public static boolean isScheduled(Context context) {
        List<JobInfo> allPendingJobs = ((JobScheduler) context.getSystemService(JobScheduler.class)).getAllPendingJobs();
        if (allPendingJobs == null) {
            return false;
        }
        for (JobInfo id : allPendingJobs) {
            if (id.getId() == 300003) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context context) {
        super.attachBaseContext(context.createDeviceProtectedStorageContext());
    }

    public boolean onStartJob(JobParameters jobParameters) {
        StringBuilder sb = new StringBuilder();
        sb.append("onStartJob   jobParameters=");
        sb.append(jobParameters);
        Log.e("AnalyticalDataCollectorJobService", sb.toString());
        BackgroundThread.post(new Runnable(jobParameters) {
            private final /* synthetic */ JobParameters f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                AnalyticalDataCollectorJobService.lambda$onStartJob$0(AnalyticalDataCollectorJobService.this, this.f$1);
            }
        });
        return true;
    }

    public static /* synthetic */ void lambda$onStartJob$0(AnalyticalDataCollectorJobService analyticalDataCollectorJobService, JobParameters jobParameters) {
        analyticalDataCollectorJobService.trackAodEvent();
        analyticalDataCollectorJobService.jobFinished(jobParameters, false);
    }

    public void trackAodEvent() {
        if (Utils.SUPPORT_AOD) {
            MiStatParams miStatParams = new MiStatParams();
            int i = Secure.getInt(getContentResolver(), AODSettings.AOD_MODE, -1);
            StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(i);
            miStatParams.putString("aod_mode", sb.toString());
            int i2 = Secure.getInt(getContentResolver(), "aod_mode_time", 1);
            String str = "aod_mode_time";
            StringBuilder sb2 = new StringBuilder();
            sb2.append("");
            if (i != 1) {
                i2 = -1;
            }
            sb2.append(i2);
            miStatParams.putString(str, sb2.toString());
            AnalyticsWrapper.trackEvent("aod", miStatParams);
        }
    }
}
