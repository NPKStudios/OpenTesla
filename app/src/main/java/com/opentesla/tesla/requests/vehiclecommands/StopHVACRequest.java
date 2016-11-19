package com.opentesla.tesla.requests.vehiclecommands;

import com.opentesla.tesla.requests.VehicleJsonPost;

import java.io.Serializable;

/**
 * Created by Nick on 10/26/2016.
 */

public class StopHVACRequest extends VehicleJsonPost implements Serializable {
    //https://owner-api.teslamotors.com/api/1/vehicles/vehicle_id/command/auto_conditioning_start
    private static final String CMD_STRING = "auto_conditioning_stop";
    public StopHVACRequest(long vehicle_id, String vehicle_name)
    {
        super(vehicle_id, vehicle_name);
    }

    @Override
    public String getBody() {
        return "";
    }

    @Override
    public String getResultString() {
        if(getResult())
        {
            return "HVAC Stopped.";
        }
        else
        {
            return "Unable to stop HVAC.";
        }
    }

    @Override
    public String getCommandName() {
        return "Start HVAC";
    }

    @Override
    public String getUrlString() {
        return getUrl_vehicle_cmd(getVehicle_id(), CMD_STRING);
    }
}
