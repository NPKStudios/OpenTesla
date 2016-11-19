package com.opentesla.android.backgroundservices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.opentesla.android.database.DbTask;
import com.opentesla.android.database.TasksDb;
import com.opentesla.android.DbTaskScheduler;

import java.util.ArrayList;

/**
 * Created by Nick on 11/13/2016.
 */

public class MyBootReceiver extends BroadcastReceiver
{
    private static final String TAG = "MyBootReceiver";

    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");
        //Toast.makeText(context, "onReceive", Toast.LENGTH_LONG).show();
        TasksDb mTasksDb = new TasksDb(context);
        ArrayList<DbTask> tasks = mTasksDb.getEnabledTasks();
        Log.d(TAG, "Tasks " + tasks.size());
        //Toast.makeText(context, "Tasks " + tasks.size(), Toast.LENGTH_LONG).show();
        if(tasks != null && tasks.size()>0) {
            this.scheduleAlarms(context, tasks, mTasksDb);
        }
    }

    private void scheduleAlarms(Context context, ArrayList<DbTask> tasks ,TasksDb mTasksDb) {
        Log.d(TAG, "scheduleAlarms");
        for(DbTask task : tasks){
            if(task.getEnable()) {
                if(task.getNext_wake_time() < System.currentTimeMillis())
                {
                    if(task.getRepeat() == true)
                    {
                        //Todo: recalculate task and save
                    }
                    else {
                        //Toast.makeText(context, "Command time has already passed " + task.get_id(), Toast.LENGTH_LONG).show();
                        Log.d(TAG, "Command time has already passed");
                        task.setEnable(false);
                        mTasksDb.updateTask(task);
                        continue;
                    }
                }

                if (DbTaskScheduler.setAlarm(context, task)) {
                    Log.d(TAG, "Command Set:\n" + task.getNextWakeTimeString() + "\n" +
                            "Set " + task.getTask().getVehicle_name() + " task " +task.get_id());
                } else {
                    //Toast.makeText(context, "Failed to set command", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Failed to set command " +task.get_id());
                }
            }
            else
            {
                Log.d(TAG, "Task not enabled " +task.get_id());
            }
        }
    }
}
