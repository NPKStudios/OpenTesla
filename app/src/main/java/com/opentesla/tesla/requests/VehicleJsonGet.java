package com.opentesla.tesla.requests;

import java.io.Serializable;

/**
 * Created by Nick on 10/26/2016.
 */

public abstract class VehicleJsonGet extends TeslaJsonRequest implements Serializable {
    private static final String JSON_result = "result";
    private static final String JSON_reason = "reason";

    private boolean result;
    private String reason;
    private long vehicle_id;

    public VehicleJsonGet(long vehicle_id)
    {
        super();
        this.result = false;
        this.reason = "";
        this.vehicle_id = vehicle_id;
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

    protected long getVehicle_id() {
        return vehicle_id;
    }

}
