package com.opentesla.tesla.requests.vehiclecommands;

import com.opentesla.tesla.requests.VehicleJsonPost;

import java.io.Serializable;

/**
 * Created by Nick on 10/26/2016.
 */

public class DoorLockRequest extends VehicleJsonPost implements Serializable {
    public static final String CMD_NAME = "Lock Doors";
    private static final String CMD_door_lock = "door_lock";
    public DoorLockRequest(long vehicle_id, String vehicle_name)
    {
        super(vehicle_id, vehicle_name);
    }

    @Override
    public String getBody() {
        return "";
    }

    @Override
    public String getResultString() {
        if(getResult()) {
            return "Doors locked.";
        }
        else
        {
            return "Unable to lock doors.";
        }
    }

    @Override
    public String getCommandName() {
        return "Lock Doors";
    }
    @Override
    public String getCommandDescription() {
        return "Lock " + getVehicle_name();
    }

    @Override
    public String getUrlString() {
        return getUrl_vehicle_cmd(getVehicle_id(), CMD_door_lock);
    }

}
