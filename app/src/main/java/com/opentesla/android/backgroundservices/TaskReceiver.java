package com.opentesla.android.backgroundservices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Nick on 10/27/2016.
 */

public class TaskReceiver extends BroadcastReceiver {
    public static final String ARG_PARAM1_DB_ID = BackgroundTaskService.ARG_PARAM1;

    private static final String TAG = TaskReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "OnReceive");

        try {
            //Extract the bundle
            Bundle bundle = intent.getExtras();

            //Check if the bundle is empty
//            if (bundle.isEmpty()) {
//                Log.d(TAG, "Bundle is empty");
//                return;
//            }
            //Start the background service with the send bundle
            StartBackgroundService(context, bundle);

        }
        catch(Exception e) {
            Log.e(TAG, e.getLocalizedMessage());
            //Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Create the background service to handle the calls to be make
     * @param context
     * @param bundle
     */
    public void StartBackgroundService(Context context, Bundle bundle) {
        //Create the background service intent
        Intent background = new Intent(context, BackgroundTaskService.class);
        //Bundle bundle = new Bundle();
        //bundle.putSerializable(BackgroundService.ARG_PARAM1, bundle);
        //Set the bundle
        background.putExtras(bundle);
        //Start the service
        context.startService(background);
    }
}

