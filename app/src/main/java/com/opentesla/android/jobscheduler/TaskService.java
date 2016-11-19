package com.opentesla.android.jobscheduler;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Nick on 11/13/2016.
 */

//http://toastdroid.com/2015/02/21/how-to-use-androids-job-scheduler/

public class TaskService extends JobService {
    private static final String TAG = TaskService.class.getSimpleName();
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("MyService", "myService created");
    }

    // This method is called when the service instance
    // is destroyed
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("MyService", "myService destroyed");
    }

    // This method is called when the scheduled job
    // is started
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i("MyService", "on start job");
        return true;
    }

    // This method is called when the scheduled job
    // is stopped
    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i("MyService", "on stop job");
        return true;
    }

//    MainActivity myMainActivity;
//
//    public void setUICallback(MainActivity activity) {
//        myMainActivity = activity;
//    }


    // This method is called when the start command
    // is fired
    @Override
    public int onStartCommand(Intent intent, int flags,
                              int startId) {
//        Messenger callback = intent.getParcelableExtra("messenger");
//        Message m = Message.obtain();
//        m.what = 2;
//        m.obj = this;
//        try {
//            callback.send(m);
//        } catch (RemoteException e) {
//            Log.e("MyService", "Error passing service object
//                    back to activity.");
//        }
        return START_NOT_STICKY;
    }

    // Method that schedules the job
    public void scheduleJob(JobInfo build) {
        Log.i("MyService","Scheduling job");
        JobScheduler jobScheduler = (JobScheduler)getSystemService
                (Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(build);
    }
}
