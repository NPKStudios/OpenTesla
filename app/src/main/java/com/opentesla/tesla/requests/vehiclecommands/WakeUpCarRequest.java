package com.opentesla.tesla.requests.vehiclecommands;

import com.opentesla.tesla.requests.VehicleJsonPost;

import java.io.Serializable;

/**
 * Created by Nick on 10/26/2016.
 */

public class WakeUpCarRequest extends VehicleJsonPost implements Serializable {
    public static final String CMD_NAME = "Wake Vehicle";
    private static final String CMD_WAKE_UP = "wake_up";
    public WakeUpCarRequest(long vehicle_id, String vehicle_name)
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
            return "Woke the vehicle.";
        }
        else
        {
            return "Unable to wake the vehicle.";
        }
    }

    @Override
    public String getCommandName() {
        return CMD_NAME;
    }
    @Override
    public String getCommandDescription() {
        return "Wake up " + getVehicle_name();
    }

    @Override
    public String getUrlString() {
        return getUrl_vehicle_cmd(getVehicle_id(), CMD_WAKE_UP);
    }
}
