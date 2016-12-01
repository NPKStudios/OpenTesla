package com.opentesla.tesla.requests.vehiclecommands;

import com.opentesla.tesla.requests.VehicleJsonPost;

import java.io.Serializable;

/**
 * Created by Nick on 10/26/2016.
 */

public class DoorUnlockRequest extends VehicleJsonPost implements Serializable {
    public static final String CMD_NAME = "Unlock Doors";
    private static final String CMD_door_unlock = "door_unlock";
    public DoorUnlockRequest(long vehicle_id, String vehicle_name)
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
            return "Doors unlocked.";
        }
        else
        {
            return "Unable to unlock doors.";
        }
    }

    @Override
    public String getCommandName() {
        return "Unlock Doors";
    }
    @Override
    public String getCommandDescription() {
        return "Unlock " + getVehicle_name();
    }
    @Override
    public String getUrlString() {
        return getUrl_vehicle_cmd(getVehicle_id(), CMD_door_unlock);
    }
}
