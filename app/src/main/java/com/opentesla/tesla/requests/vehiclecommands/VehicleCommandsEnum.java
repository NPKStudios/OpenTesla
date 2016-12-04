package com.opentesla.tesla.requests.vehiclecommands;

/**
 * Created by Nick on 12/1/2016.
 */

public enum VehicleCommandsEnum {
    TASK_CHARGE (0, SetChargeLimitRequest.CMD_NAME, SetChargeLimitRequest.class),
    TASK_HVAC_ON   (1, StartHVACRequest.CMD_NAME, StartHVACRequest.class),
    TASK_HVAC_OFF   (2, StopHVACRequest.CMD_NAME, StopHVACRequest.class),
    TASK_UNLOCK    (3, DoorUnlockRequest.CMD_NAME, StopHVACRequest.class),
    TASK_LOCK (4, DoorLockRequest.CMD_NAME, DoorLockRequest.class);

    private final int id;   // in kilograms
    private final String commandName; // in meters
    private final Class<?> type;
    VehicleCommandsEnum(int id, String commandName, Class<?> type) {
        this.id = id;
        this.commandName = commandName;
        this.type = type;
    }

}
