package com.opentesla.tesla.requests;

import android.os.Parcel;

import com.opentesla.tesla.response.Vehicle;
import com.opentesla.webtask.RequestType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Nick on 10/23/2016.
 */
//https://owner-api.teslamotors.com/api/1/vehicles
public class ListVehicleJsonRequest extends TeslaJsonRequest implements Serializable {
    public static final String JSON_response = "response";
    public static final String JSON_count = "count";

    private ArrayList<Vehicle> vehicles;
    private int count;

    public ListVehicleJsonRequest(){
        vehicles = new ArrayList<Vehicle>();
    }

    protected ListVehicleJsonRequest(Parcel in) {
        vehicles = in.createTypedArrayList(Vehicle.CREATOR);
        count = in.readInt();
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.GET;
    }

    @Override
    public String getUrlString() {
        return getUrl_All_Vehicles();
    }

    @Override
    public boolean processJsonResponse(JSONObject response) {
        try {
            count = response.optInt(JSON_count, 0);
            if(count <=0) {
                return false;
            }
            else {
                JSONArray objects = response.getJSONArray(JSON_response);
                for (int i = 0; i < objects.length(); i++)
                {
                    vehicles.add(new Vehicle(objects.getJSONObject(i)));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public ArrayList<Vehicle> getVehicles()
    {
        return vehicles;
    }
}
