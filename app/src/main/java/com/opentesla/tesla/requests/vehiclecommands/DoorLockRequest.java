package com.opentesla.tesla.requests.vehiclecommands;

import com.opentesla.tesla.requests.VehicleJsonPost;

import java.io.Serializable;

/**
 * Created by Nick on 10/26/2016.
 */

public class DoorLockRequest extends VehicleJsonPost implements Serializable {
    private static final String CMD_door_lock = "door_lock";
    DoorLockRequest(long vehicle_id, String vehicle_namne)
    {
        super(vehicle_id, vehicle_namne);
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
    public String getUrlString() {
        return getUrl_vehicle_cmd(getVehicle_id(), CMD_door_lock);
    }

}
