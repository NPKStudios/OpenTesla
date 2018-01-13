package com.opentesla.android.backgroundservices;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.opentesla.tesla.requests.vehiclecommands.WakeUpCarRequest;
import com.opentesla.tesla.requests.VehicleJsonPost;
import com.opentesla.tesla.TeslaApiClient;
import com.opentesla.android.database.DbTask;
import com.opentesla.android.database.TasksDb;
import com.opentesla.android.MySharedPreferences;
import com.opentesla.android.R;
import com.opentesla.webtask.OnTaskDoneListener;
import com.opentesla.webtask.PostJsonAsyncTask;

import org.json.JSONObject;

import static android.app.Notification.VISIBILITY_PUBLIC;

/**
 * Created by Nick on 10/28/2016.
 */

/***
 * Creates a background task for the to send the api commands
 */
public class BackgroundTaskService extends Service {
    private static final String TAG = BackgroundTaskService.class.getSimpleName();
    public static final String ARG_PARAM1 = "DB_ID";
    public static final int DEFAULT_RETRIES = 2;

    private boolean isRunning;
    private Context context;
    private Thread backgroundThread;
    private VehicleJsonPost postRequest;
    private WakeUpCarRequest wakeUpCarRequest;

    private TasksDb mTasksDb;
    private DbTask mDbTask;
    private VehicleJsonPost vehicleJsonPost;

    private TeslaApiClient mTeslaClient;
    private SharedPreferences mSharedPreferences;
    private String mOauthToken;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        this.context = this;
        this.isRunning = false;
        this.backgroundThread = new Thread(myTask);
    }

    private Runnable myTask = new Runnable() {
        public void run() {
            // Do something here
            mSharedPreferences = MySharedPreferences.getSharedPreferences(context);
            mTeslaClient = new TeslaApiClient(mSharedPreferences);
            mOauthToken = mTeslaClient.getOauthTokenString();

            //showNotification("DEBUG","STARTING","Starting wake car" + vehicleJsonPost.getClass().getSimpleName());
            wakeUpCarRequest = new WakeUpCarRequest(vehicleJsonPost.getVehicle_id(), vehicleJsonPost.getVehicle_name());

            PostJsonAsyncTask wakeTask = new PostJsonAsyncTask(wakeUpCarRequest.getUrlString(), mOauthToken, new BackgroundTaskService.WakeCarDone(context,DEFAULT_RETRIES), wakeUpCarRequest.getBody());
            //PostJsonAsyncTask task = new PostJsonAsyncTask(mTeslaClient.(mVehicleID),mTeslaClient.getOauthTokenString(), null, "");
            wakeTask.execute();

            if(mDbTask != null) {
                if(mDbTask.getRepeat() == false) {
                    mDbTask.setEnable(false);
                    mTasksDb.updateTask(mDbTask);
                }
            }

            stopSelf();
        }
    };

    /***
     * Executed after the wake request is performed
     */
    public class WakeCarDone implements OnTaskDoneListener {
        private Context c;
        private int retries;
        public WakeCarDone(Context c, int retries)
        {
            this.c = c;
            this.retries = retries;
        }
        @Override
        public void onTaskDone(JSONObject responseData) {
            String message = "";
            boolean failed = false;
            if(responseData == null | (responseData.length() <= 0))
            {
                Log.d(TAG, "Failed to communicate with server, Retries left: " + this.retries);
                message = "Failed to communicate with server. ";
                failed = true;
            }
            else if(wakeUpCarRequest.processJsonResponse(responseData) == true)
            {
                if(wakeUpCarRequest.getResult())
                {
                    //Setup setcharge limit
                    showNotification("Command", "Success", "Woke Tesla");
                    PostJsonAsyncTask vehicleTask = new PostJsonAsyncTask(vehicleJsonPost.getUrlString(), mOauthToken, new BackgroundTaskService.VehiclePostDone(context, DEFAULT_RETRIES), vehicleJsonPost.getBody());
                    vehicleTask.execute();
                }
                else if(retries > 0)
                {
                    Log.d(TAG, "Failed to wake car");
                    message = "Failed to wake car. ";
                    failed = true;
                }
            }
            else
            {
                Log.e(TAG, "Failed to parse JSON: " + responseData.toString());
                message = "Failed to parse JSON: " + responseData.toString() + ". ";
                failed = true;
            }
            if(failed) {
                if(retries>0) {
                    retry(message);
                }
                else
                {
                    showNotification("Sending Command", "Failed", message);
                }
            }
        }
        private  void retry(String message)
        {
            //Create wake car request
            Log.d(TAG, "Retrying: "+ message + "Trying to wake the car again, Retries left: " + retries);
            showNotification("Retying Command", "Retry", message + "Trying to wake the car again, Retries left: " + retries);
            wakeUpCarRequest = new WakeUpCarRequest(vehicleJsonPost.getVehicle_id(), vehicleJsonPost.getVehicle_name());

            //Create wake car task
            PostJsonAsyncTask wakeTask = new PostJsonAsyncTask(wakeUpCarRequest.getUrlString(), mOauthToken, new BackgroundTaskService.WakeCarDone(context, this.retries--), wakeUpCarRequest.getBody());

            //Executes task
            wakeTask.execute();
        }

        @Override
        public void onError(String error) {
            Log.e(TAG, error);
        }
    }


    public class VehiclePostDone implements OnTaskDoneListener {
        private Context c;
        private int retries;
        public VehiclePostDone(Context c, int retries)
        {
            this.c = c;
            this.retries=retries;
        }
        @Override
        public void onTaskDone(JSONObject responseData) {
            boolean failed = false;
            String message = "";
            if(responseData == null | (responseData.length() <= 0))
            {
                Log.d(TAG, "Failed to communicate with server, Retries left: " + this.retries);
                message = "Failed to communicate with server. ";
                failed = true;
            }
            else if(vehicleJsonPost.processJsonResponse(responseData) == true)
            {
                if(vehicleJsonPost.getResult())
                {
                    //Successfully sent command
                    showNotification("Command Success", vehicleJsonPost.getCommandName(), vehicleJsonPost.getResultString());
                }
                else
                {
                    //Command sent but not successful
                    message = vehicleJsonPost.getResultString() + ". ";
                    failed = true;
                }
            }
            else
            {
                message = "Failed to parse JSON: " + responseData.toString() + ". ";
                Log.e(TAG, "Failed to parse JSON: " + responseData.toString());
                failed = true;
            }
            if(failed) {
                if(retries>0) {
                    retry(message);
                }
                else
                {
                    showNotification("Sending Command", "Failed", message);
                }
            }
        }

        private  void retry(String message)
        {

            Log.d(TAG, message+ "Retrying: " + vehicleJsonPost.getCommandName() + ". Retries left: " + this.retries);
            //Show notification
            showNotification("Retying Command", "Retry", message+ "Retrying " + vehicleJsonPost.getCommandName() + ". Retries left: " + this.retries);
            //create new task
            PostJsonAsyncTask vehicleTask = new PostJsonAsyncTask(vehicleJsonPost.getUrlString(), mOauthToken, new BackgroundTaskService.VehiclePostDone(context, this.retries--), vehicleJsonPost.getBody());
            //launch task
            vehicleTask.execute();
        }

        @Override
        public void onError(String error) {
            Log.e(TAG, error);
        }
    }

    @Override
    public void onDestroy() {
        this.isRunning = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!this.isRunning) {
            this.isRunning = true;

            Bundle bundle = intent.getExtras();
            mDbTask = null;
            mTasksDb = new TasksDb(context);
            long _id = 0;
            try {
//                if (bundle.isEmpty()) {
//                    Log.d(TAG, "Bundle is empty");
//                }

                _id = bundle.getLong(ARG_PARAM1);

                mDbTask = mTasksDb.getTask(_id);

                if (mDbTask.getEnable()) {
                    vehicleJsonPost = mDbTask.getTask();
                    if (vehicleJsonPost != null) {
                        this.backgroundThread.start();
                    } else {
                        Log.e(TAG, "Failed to get request from database");
                        showNotification("Error", "Request Failure", "Failed to get request from database");
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage());
                showNotification("Error", "Request Failure", "Error retrieving request from database");
            }
        }
        return START_STICKY;
    }

    /***
     * Shows a nitification to on the device
     * @param ticker The ticker string
     * @param contentTitle The content title
     * @param content The content to be displayed
     */
    private void showNotification(String ticker, String contentTitle, String content)
    {
        //Ticker
        Log.d(this.toString(),"NOTIFY");
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(android.R.drawable.ic_menu_info_details);
        mBuilder.setTicker(ticker);
        mBuilder.setContentTitle(contentTitle);
        mBuilder.setContentText(content);
        mBuilder.setVisibility(VISIBILITY_PUBLIC);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_square);
        mBuilder.setLargeIcon(bm);
// Sets an ID for the notification
        //Random number plus current time to create notification
        int mNotificationId =365 + ((int) System.currentTimeMillis());
// Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
}
