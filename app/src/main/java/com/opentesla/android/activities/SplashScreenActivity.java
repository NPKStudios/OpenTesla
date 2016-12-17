package com.opentesla.android.activities;

import android.support.v7.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.opentesla.tesla.requests.ListVehicleJsonRequest;
import com.opentesla.tesla.response.Vehicle;
import com.opentesla.android.MySharedPreferences;
import com.opentesla.android.R;
import com.opentesla.tesla.TeslaApiClient;
import com.opentesla.android.UserConfig;
import com.opentesla.webtask.GetJsonAsyncTask;
import com.opentesla.webtask.OnTaskDoneListener;

import org.json.JSONObject;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashScreenActivity extends AppCompatActivity {
    private static final String TAG = SplashScreenActivity.class.getSimpleName();
    TeslaApiClient mTeslaClient;
    ListVehicleJsonRequest listVehicleJsonRequest;
    UserConfig userConfig;
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);

        //mControlsView = findViewById(R.id.fullscreen_content_controls);
       // mContentView = findViewById(R.id.fullscreen_content);


        // Set up the user interaction to manually show or hide the system UI.
        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
       // findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);

    }
    public Context getContext() {
        return this;
    }



    public void goToMainActivity(ArrayList<Vehicle> vehicleList)
    {
        //Intent myIntent = new Intent(this, TeslaDebugActivity.class);
        Intent myIntent = new Intent(getContext(), MainActivity.class);
        myIntent.putParcelableArrayListExtra(getString(R.string.par_user_vehicle_list), vehicleList);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(myIntent);
        finish();
    }
    public void goToLoginActivity()
    {
        mTeslaClient.Logout(mSharedPreferences);
        Intent myIntent = new Intent(getContext(), MyTeslaLoginActivity.class);
        startActivity(myIntent);
        finish();
    }
    public void UpdateGui()
    {
        listVehicleJsonRequest = new ListVehicleJsonRequest();
        String urlString = listVehicleJsonRequest.getUrlString();
        GetJsonAsyncTask vehicleTask = new GetJsonAsyncTask(urlString, mTeslaClient.getOauthTokenString(), new SplashScreenActivity.UpdateState());
        vehicleTask.execute();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
//        try {
//            sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        mSharedPreferences = MySharedPreferences.getSharedPreferences(this.getApplicationContext());

        mTeslaClient = new TeslaApiClient(mSharedPreferences);
        userConfig = new UserConfig(mSharedPreferences);

        if(mTeslaClient.IsLoggedIn())
        {
            UpdateGui();
        }
        else
        {
            goToLoginActivity();
        }
    }


    public class UpdateState implements OnTaskDoneListener {
        @Override
        public void onTaskDone(JSONObject responseData) {
            if (mTeslaClient.IsLoggedIn() == false) {
                goToLoginActivity();
                return;
            }
            if (listVehicleJsonRequest.processJsonResponse(responseData) == false) {
                showExitAlert(getContext(), "Unable to process response from server.");
                if(responseData.length() <= 0) {
                    goToLoginActivity();
                }
                return;
            }
            if (listVehicleJsonRequest.getVehicles().isEmpty() == true) {

                showLogoutAlert(getContext(), "No vehicles were found for this account.");
                //Toast.makeText(getContext(), "No vehicles were found for this account.", Toast.LENGTH_LONG).show();
                //goToLoginActivity();
                return;
            }
            //long newid = listVehicleJsonRequest.getVehicles().get(0).getId();
            //userConfig.setSelectedVehicleId(newid);
            try{
                for(Vehicle v : listVehicleJsonRequest.getVehicles())
                {
                    //long id = userConfig.getSelectedVehicleIdString();
                    if(userConfig.getSelectedVehicleId() == v.getId())
                    {
                        //vehicle with the same id has been found
                        goToMainActivity(listVehicleJsonRequest.getVehicles());
                    }
                }
            }catch(Exception e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
            //No vehicle with the same id has been found
            Vehicle first = listVehicleJsonRequest.getVehicles().get(0);
            //Set the id to the first vehicle in the list
            userConfig.setSelectedVehicle(first.getId(), first.getDisplay_name());

            goToMainActivity(listVehicleJsonRequest.getVehicles());
        }

        @Override
        public void onError(String error) {

        }
    }

    public void showLogoutAlert(Context context, String message)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        goToLoginActivity();
                    }
                });
        alertDialog.show();
    }
    public void showExitAlert(Context context, String message)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
        alertDialog.show();
    }
}
