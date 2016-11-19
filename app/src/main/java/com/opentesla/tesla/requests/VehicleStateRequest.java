package com.opentesla.tesla.requests;

import android.util.Log;

import com.opentesla.tesla.response.Response;
import com.opentesla.tesla.response.VehicleState;
import com.opentesla.webtask.RequestType;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Nick on 10/23/2016.
 */
//https://owner-api.teslamotors.com/api/1/vehicles/vehicle_id/data_request/vehicle_state
public class VehicleStateRequest extends VehicleJsonGet implements Serializable{

    private static final String CMD_VEHICLE_STATE = "vehicle_state";
    private VehicleState vehicleState;

    public VehicleStateRequest(long vehicle_id)
    {
        super(vehicle_id);

    }

    @Override
    public RequestType getRequestType() {
        return RequestType.GET;
    }

    @Override
    public String getUrlString() {
        //https://owner-api.teslamotors.com/api/1/vehicles/vehicle_id/data_request/charge_state
        return getUrl_data_request(getVehicle_id(), CMD_VEHICLE_STATE);
    }

    public boolean processJsonResponse(JSONObject response)
    {
        try {
            JSONObject r = response.getJSONObject(Response.JSON_RESPONSE);
            vehicleState = new VehicleState(r);
        } catch (JSONException e) {
            Log.e(this.toString(), e.getLocalizedMessage());
            return false;
        }

        return true;
    }

    public VehicleState getVehicleState() {
        return vehicleState;
    }
}
