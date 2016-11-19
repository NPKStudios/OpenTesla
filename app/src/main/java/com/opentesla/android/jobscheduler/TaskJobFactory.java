package com.opentesla.android.jobscheduler;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

/**
 * Created by Nick on 11/13/2016.
 */

public final class TaskJobFactory {

    public static final int MINIMUM_LATENCY = 0;
    public static final boolean REQUIRES_CHARGING = false;
    public static final boolean REQUIRES_DEVICE_IDLE = false;
    public static final int REQUIRED_NETWORK_TYPE = JobInfo.NETWORK_TYPE_ANY;
    public static final boolean JOB_PERSISTED = true;

    public static JobInfo.Builder getBulder(Context context, int jobId)
    {
        JobInfo.Builder builder = new JobInfo.Builder( jobId,
                new ComponentName( context.getPackageName(),
                        TaskService.class.getName() ) );

        builder.setMinimumLatency(MINIMUM_LATENCY);
        builder.setRequiresCharging(REQUIRES_CHARGING);
        builder.setRequiresDeviceIdle(REQUIRES_DEVICE_IDLE);
        builder.setRequiredNetworkType(REQUIRED_NETWORK_TYPE);
        builder.setPersisted(JOB_PERSISTED);
        builder.setOverrideDeadline(60*1000);
        return builder;
    }
    public static void cancelJob(Context context, int jobId)
    {
        JobScheduler jobScheduler = getJobScheduler(context);
        jobScheduler.cancel(jobId);
// Construct a new JobInfo.Builder
        //jobScheduler.schedule(builder.build());
    }
    public static boolean startJob(Context context, JobInfo.Builder builder)
    {
        JobScheduler jobScheduler = getJobScheduler(context);
        boolean result = true;
        if( jobScheduler.schedule( builder.build() ) <= 0 ) {
            //If something goes wrong
            result = false;
        }
        return result;
    }
    public static JobScheduler getJobScheduler(Context context)
    {
        return (JobScheduler) context.getSystemService( Context.JOB_SCHEDULER_SERVICE );
    }
    /*
        JobScheduler jobScheduler = (JobScheduler) getApplicationContext().getSystemService(JOB_SCHEDULER_SERVICE);
        ComponentName componentName = new ComponentName(getApplicationContext(), TestService.class);
        JobInfo jobInfo = new JobInfo.Builder(1, componentName).setOverrideDeadline(10).setRequiresCharging(true).build();
        jobScheduler.schedule(jobInfo);
     */
}
