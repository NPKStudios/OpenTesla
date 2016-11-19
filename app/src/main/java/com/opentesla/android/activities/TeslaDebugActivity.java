package com.opentesla.android.activities;

import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.ads.AdView;
import com.opentesla.webtask.GetJsonAsyncTask;
import com.opentesla.android.MySharedPreferences;
import com.opentesla.webtask.OnTaskDoneListener;
import com.opentesla.webtask.PostJsonAsyncTask;
import com.opentesla.android.R;
import com.opentesla.tesla.TeslaApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TeslaDebugActivity extends AppCompatActivity {

    private TeslaApiClient mTeslaClient;
    private SharedPreferences mSharedPreferences;
    private ArrayList<String> vehicles;
    private ArrayAdapter<String> v_adapter;
    private Spinner v_spinner;
    private AdView mAdView;
    private int notificationID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSharedPreferences = MySharedPreferences.getSharedPreferences(this.getApplicationContext());
        mTeslaClient = new TeslaApiClient(mSharedPreferences);


        setContentView(R.layout.activity_debug);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        populateVehicleList();
        setHonkButton();
        setFlashButton();
        setWakeButton();
        setNotificationButton();
    }

    public void populateVehicleList()
    {
        //GetJsonAsyncTask(String url, String token, OnTaskDoneListener onTaskDoneListener)
        v_spinner=(Spinner) findViewById(R.id.spinner_vId);
        GetJsonAsyncTask vehicleTask = new GetJsonAsyncTask(mTeslaClient.getUrl_All_Vehicles(), mTeslaClient.getOauthTokenString(), new UpdateVehicles());
        vehicleTask.execute();
    }

    public class UpdateVehicles implements OnTaskDoneListener {
        @Override
        public void onTaskDone(JSONObject responseData) {
            vehicles = new ArrayList<String>();

            try {
                JSONArray response = responseData.getJSONArray("response");
                for (int i = 0; i < response.length(); i++) {
                    JSONObject row = response.getJSONObject(i);
                    vehicles.add(String.valueOf(row.getLong("id")));
                    //name = row.getString("name");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            //ArrayAdapter<String> s_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner);
            v_adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, vehicles);
            v_spinner.setAdapter(v_adapter);
        }

        @Override
        public void onError(String error) {

        }
    }

    public void setHonkButton() {
        // Perform action on click
        final Button button = (Button) findViewById(R.id.button_horn);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                if(mTeslaClient.IsLoggedIn())
                {
                    String vehicle_id = v_spinner.getSelectedItem().toString();
                    //boolean result = true;
                    if(vehicle_id == null) {
                        return;
                    }
                    else {
                        PostJsonAsyncTask task = new PostJsonAsyncTask(mTeslaClient.getUrl_HonkHorn(vehicle_id),mTeslaClient.getOauthTokenString(), null, "");
                        task.execute();
                    }
                    return;
                }
            }

        });
    }
    private void setNotificationButton()
    {

        // Perform action on click
        final Button button = (Button) findViewById(R.id.button_notification);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                showNotification();
            }

        });
    }
    private void showNotification()
    {
        Log.d(this.toString(),"NOTIFY");
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(android.R.drawable.ic_menu_agenda);
        mBuilder.setTicker("THIS IS THE TICKER");
        mBuilder.setContentTitle("Notification Alert, Click Me!");
        mBuilder.setContentText("Hi, This is Android Notification Detail!");
// Sets an ID for the notification
        int mNotificationId = 001;
// Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
    public void setFlashButton() {
        // Perform action on click
        final Button button = (Button) findViewById(R.id.button_flash);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                if(mTeslaClient.IsLoggedIn())
                {
                    String vehicle_id = v_spinner.getSelectedItem().toString();
                    //boolean result = true;
                    if(vehicle_id == null) {
                        return;
                    }
                    else {
                        PostJsonAsyncTask task = new PostJsonAsyncTask(mTeslaClient.getUrl_FlashLights(vehicle_id), mTeslaClient.getOauthTokenString(), null, "");
                        task.execute();
                    }
                    return;
                }
            }

        });
    }
    public void setWakeButton() {
        // Perform action on click
        final Button button = (Button) findViewById(R.id.button_wake);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                if(mTeslaClient.IsLoggedIn())
                {
                    String vehicle_id = v_spinner.getSelectedItem().toString();
                    //boolean result = true;
                    if(vehicle_id == null) {
                        return;
                    }
                    else {
                        PostJsonAsyncTask task = new PostJsonAsyncTask(mTeslaClient.getUrl_WakeUp(vehicle_id), mTeslaClient.getOauthTokenString(), null, "");
                        task.execute();
                    }
                    return;
                }
            }

        });
    }

}
