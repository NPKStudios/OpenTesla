package com.opentesla.tesla;

import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.HttpURLConnection;

import org.json.JSONException;
import org.json.JSONObject;
import 	java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Nick on 10/9/2016.
 */
public class TeslaApiClient {

    private static final String TESLA_CLIENT_ID = "e4a9949fcfa04068f59abb5a658f2bac0a3428e4652315490b659d5ab3f35a9e";
    private static final String TESLA_CLIENT_SECRET = "c75f14bbadc8bee3a7594412c31416f8300256d7668ea7e6e7f06727bfb9d220";
    private static final String API_PORTAL = "https://owner-api.teslamotors.com/api/1";
    private static final String API_VERSION_1 = "api/1";
    private static final String API_URL = "https://owner-api.teslamotors.com/";
    private static final String OAUTH_ADDR = "oauth/token";
    private static final String OAUTH_REVOKE = "oauth/revoke";
    private static final String LIST_ALL_VEHICLES = "/vehicles";
    private static final String CMD_HONK_HORN = "honk_horn";
    private static final String CMD_FLASH_LIGHTS = "flash_lights";
    private static final String CMD = "/command/";
    private static final String CMD_WAKE_UP = "wake_up";

    private static final String VEHICLE_MOBILE_ENABLED = "/mobile_enabled";


    private String mUserName;
    private TeslaOauthToken mToken;
    private boolean mLoggedIn;
    private ArrayList<TeslaVehicle> mTeslaVehicles;

    public TeslaApiClient(SharedPreferences sharedPreferences)
    {
        mUserName = "";
        mLoggedIn = false;
        mToken = new TeslaOauthToken(sharedPreferences);
        String at = mToken.getAccessToken();
        if(at.compareTo("") != 0 && mToken.isExpired() == false)
        {
            mLoggedIn = true;
        }
    }

    public boolean IsLoggedIn()
    {
        return mLoggedIn;
    }


    public boolean Login(String username, String password)
    {
        JSONObject oauthToken = GetAuthToken(username, password);
        if(oauthToken == null)
        {
            return false;
        }

        mToken.SetTeslaOauthToken(oauthToken);
        return true;
    }
    public void Logout(SharedPreferences sharedPreferences)
    {
        //TODO: Fix this to invalidate the token
        mToken.deleteTeslaOauthToken(sharedPreferences);
    }

    public JSONObject GetAuthToken(String username, String password)
    {
        //Create the login json
        JSONObject parent=new JSONObject();
        try
        {
            parent.put("grant_type", "password" );
            parent.put( "client_id" ,TESLA_CLIENT_ID);
            parent.put( "client_secret",TESLA_CLIENT_SECRET);
            parent.put( "email" ,username );
            parent.put( "password", password );
            password = "";
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Post the request
        try {
            URL url = new URL(getOauthURL());
            HttpURLConnection connection =
                    (HttpURLConnection)url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("POST");

            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(parent.toString());
            wr.flush();
            parent = null;
            //Read the buffer to get the access token
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp="";
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();

            connection.disconnect();

            JSONObject data = new JSONObject(json.toString());

            // This value will be 404 if the request was not
            // successful
            //if(data.getInt("cod") != 200){
            //    return null;
            //}

            return data;
        }catch(Exception e) {
            return null;
        }
        finally {
        }
    }
    public void saveOauthToken(SharedPreferences sharedPreferences)
    {
        mToken.saveTeslaOauthToken(sharedPreferences);
    }

    public String getUrl_MobileEnabled(String vehicle_id){
        //Get
        //https://owner-api.teslamotors.com/api/1/vehicles/vehicle_id/mobile_enabled
        //boolean result = false;
        return getUrl_Vehicle(vehicle_id) + VEHICLE_MOBILE_ENABLED;
    }
    public String getUrl_Vehicle(String vehicle_id)
    {
        return getUrl_All_Vehicles() + "/" + vehicle_id;
    }
    public String getUrl_All_Vehicles()
    {
        return API_PORTAL + LIST_ALL_VEHICLES;
    }
    public String getUrl_WakeUp(String vehicle_id)
    {
        return getUrl_vehicle_cmd(vehicle_id, CMD_WAKE_UP);
    }
    public String getUrl_HonkHorn(String vehicle_id)
    {
        return getUrl_vehicle_cmd(vehicle_id, CMD_HONK_HORN);
    }
    public String getUrl_FlashLights(String vehicle_id)
    {
        return getUrl_vehicle_cmd(vehicle_id, CMD_FLASH_LIGHTS);
    }
    public String getUrl_vehicle_cmd(String vehicle_id, String cmd)
    {
        //https://owner-api.teslamotors.com/api/1/vehicles/vehicle_id/wake_up
        return getUrl_Vehicle(vehicle_id) + CMD + cmd;
    }


    public String getOauthURL() { return API_URL + OAUTH_ADDR;}
    public String getOauthLogoutURL() { return API_URL + OAUTH_REVOKE;}
    public String getUsername() { return mUserName; }
    public String getOauthTokenString(){
        if(mToken != null)
        {
            return mToken.getAccessToken();
        }
        else
        {
            return "";
        }
    }
    public Date getOauthTokenExpirationTime(){
        Date date = new Date();
        if(mToken != null)
        {
            date = mToken.getExpirationTime();
        }
        return date;
    }
    public Date getOauthTokenCreationTime(){
        Date date = new Date();
        if(mToken != null)
        {
            date = mToken.getCreationTime();
        }
        return date;
    }
}
