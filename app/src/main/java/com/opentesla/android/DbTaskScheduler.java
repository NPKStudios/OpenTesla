package com.opentesla.android;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.opentesla.android.backgroundservices.AlarmReceiver;
import com.opentesla.android.database.DbTask;

/**
 * Created by Nick on 11/13/2016.
 */

public final class DbTaskScheduler {
    public static PendingIntent createPendingIntent(Context context, DbTask task)
    {
        Intent newIntent = getAlarmIntent(context);
        Bundle bundle = new Bundle();
        bundle.putLong(AlarmReceiver.ARG_PARAM1_DB_ID, task.get_id());
        newIntent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int)task.get_id(), newIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), (int)task.get_id(), newIntent, flags);
        return pendingIntent;
    }
    public static Intent getAlarmIntent(Context context)
    {
        return new Intent(context, AlarmReceiver.class);
    }
    public static boolean setAlarm(Context context, DbTask task) {
        if(task.getEnable() == true) {

            long nextWakeTime = task.getNextWakeTime();
            PendingIntent pendingIntent = createPendingIntent(context, task);
            AlarmManager alarmManager = (AlarmManager) (context.getSystemService(Context.ALARM_SERVICE));

            if (nextWakeTime != 0 && alarmManager != null && pendingIntent != null) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, nextWakeTime, pendingIntent);
                return true;
            }
        }
        return false;
    }
    public static boolean cancelAlarm(Context context, DbTask task) {
        AlarmManager alarmManager = (AlarmManager) (context.getSystemService(Context.ALARM_SERVICE));
        PendingIntent pendingIntent = createPendingIntent(context, task);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
            return true;
        }
        return false;
    }
}
