package com.opentesla.tesla.requests;

import com.opentesla.webtask.RequestType;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Nick on 10/26/2016.
 */

public abstract class VehicleJsonPost extends TeslaJsonRequest implements Serializable {
    private static final String JSON_result = "result";
    private static final String JSON_reason = "reason";

    private boolean result;
    private String reason;
    private long vehicle_id;
    private String vehicle_name;

    public VehicleJsonPost(long vehicle_id, String vehicle_name)
    {
        super();
        this.vehicle_id = vehicle_id;
        this.vehicle_name = vehicle_name;
        result = false;
        reason = "";
    }
/*
    public static final Creator<VehicleJsonPost> CREATOR = new Creator<VehicleJsonPost>() {
        @Override
        public VehicleJsonPost createFromParcel(Parcel in) {
            return new VehicleJsonPost(in);
        }

        @Override
        public VehicleJsonPost[] newArray(int size) {
            return new VehicleJsonPost[size];
        }
    };
*/
    //Parcible commands
    @Override
    public RequestType getRequestType() {
        return RequestType.POST;
    }

    @Override
    public abstract String getUrlString();
    public abstract String getBody();
    public abstract String getResultString();
    public abstract String getCommandName();
    public abstract String getCommandDescription();

    @Override
    public boolean processJsonResponse(JSONObject response) {
        try {
            JSONObject objects = response.getJSONObject(JSON_response);
            setResult(objects.optBoolean(JSON_result, false));
            setReason(objects.optString(JSON_reason, ""));
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean getResult() {
        return result;
    }

    protected void setResult(boolean result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    protected void setReason(String reason) {
        this.reason = reason;
    }

    public long getVehicle_id() {
        return vehicle_id;
    }

    public String getVehicle_name(){ return vehicle_name; }
}
