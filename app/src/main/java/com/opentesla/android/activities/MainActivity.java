package com.opentesla.android.activities;

import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.opentesla.android.fragments.ScheduleListFragment;
import com.opentesla.tesla.response.Vehicle;
import com.opentesla.tesla.TeslaApiClient;
import com.opentesla.android.fragments.AboutFragment;
import com.opentesla.android.fragments.ChargeStatusFragment;
import com.opentesla.android.fragments.ScheduleFragment;
import com.opentesla.android.fragments.ScheduleHVACFragment;
import com.opentesla.android.fragments.VehicleStatusFragment;
import com.opentesla.android.MySharedPreferences;
import com.opentesla.android.R;
import com.opentesla.android.UserConfig;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    public static final String TAG = "MainActivity";
    public static final String VEHICLE_LIST = "VehicleList";

    private TeslaApiClient mTeslaClient;
    private SharedPreferences mSharedPreferences;
    UserConfig userConfig;
    private ArrayList<Vehicle> vehicles;
    FragmentManager fragmentManager;
    private Menu mOptionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            vehicles = this.getIntent().getParcelableArrayListExtra(getString(R.string.par_user_vehicle_list));
        }
        else {
            vehicles = savedInstanceState.getParcelableArrayList(VEHICLE_LIST);
        }

        // here


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        mSharedPreferences = MySharedPreferences.getSharedPreferences(this.getApplicationContext());
        mTeslaClient = new TeslaApiClient(mSharedPreferences);
        userConfig = new UserConfig(mSharedPreferences);


        if (mTeslaClient.IsLoggedIn()) {
            fragmentManager = getFragmentManager();
            String token = mTeslaClient.getOauthTokenString();

            Fragment f = ScheduleListFragment.newInstance();
            set_fragment(f);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mOptionsMenu = menu;
        for (int i = 0; i < vehicles.size(); i++) {
            //Add the menu item for the vehicle
            MenuItem mi = menu.add(Menu.NONE, i, i, vehicles.get(i).getDisplay_name());
            //Check if the menu item equals the selected vehicle
            if(vehicles.get(i).getId() == (userConfig.getSelectedVehicleId())) {
                //Promote the vehicle to the top
                mi.setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT | MenuItem.SHOW_AS_ACTION_ALWAYS);
            }
            else {
                //Demote the vheicle
                mi.setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);
            }
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        try {
            int id = item.getItemId();
            Vehicle v = vehicles.get(id);

            //Update the vehicle id
            if (v != null) {
                userConfig.setSelectedVehicle(v.getId(), v.getDisplay_name());

                if(v.getId() == userConfig.getSelectedVehicleId()) {
                    //Hide the nonselected ones
                    for (int i = 0; i < mOptionsMenu.size(); i++) {
                        if (item != mOptionsMenu.getItem(i)) {
                            mOptionsMenu.getItem(i).setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);
                        }
                    }
                    //Set the selected one
                    item.setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT | MenuItem.SHOW_AS_ACTION_ALWAYS);
                }
            }
        }
        catch(Exception e)
        {
            Log.e(TAG, e.getLocalizedMessage());
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        //FragmentManager fragmentManager = getFragmentManager(); // For AppCompat use getSupportFragmentManager
        switch(id) {
            case R.id.nav_vehicle_status:
                fragment = VehicleStatusFragment.newInstance(mTeslaClient.getOauthTokenString(), userConfig.getSelectedVehicleId());
                break;
            case R.id.nav_charging_status:
                fragment = ChargeStatusFragment.newInstance(mTeslaClient.getOauthTokenString(), userConfig.getSelectedVehicleId());
                break;
            case R.id.nav_about:
                fragment = AboutFragment.newInstance();
                break;
            case R.id.nav_logout:
                showLogoutAlert(this, "Are you sure you want to logout?");
                return true;
            case R.id.nav_schedule_list:
                fragment = ScheduleListFragment.newInstance();
                break;
//                navigate_intent(TeslaDebugActivity.class);
//                break;
//            case R.id.nav_debug:
//                navigate_intent(TeslaDebugActivity.class);
//                break;
            default:
                //fragment = (Fragment) MainFragment.newInstance();
                break;
        }
        if(fragment != null) {
            set_fragment(fragment);
        }
        return true;
    }
    public void set_fragment(Fragment fragment)
    {
        fragmentManager.beginTransaction()
                .replace(R.id.content_main, fragment)
                .commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void showLogoutAlert(Context context, String message)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        logout();
                    }
                });
        alertDialog.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.create().show();
    }
    private void logout()
    {
        mTeslaClient.Logout(mSharedPreferences);
        Intent myIntent = new Intent(this.getApplicationContext(), SplashScreenActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(myIntent);
        finish();
    }

    private void navigate_intent(Class target)
    {
        Intent myIntent = new Intent(this, target);
        startActivity(myIntent);
        finish();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelableArrayList(VEHICLE_LIST, vehicles);
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }
}