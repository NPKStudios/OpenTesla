package com.opentesla.tesla.requests.vehiclecommands;

import com.opentesla.tesla.requests.VehicleJsonPost;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Nick on 10/26/2016.
 */

public class SetChargeLimitRequest extends VehicleJsonPost implements Serializable {
    public static final String CMD_NAME = "Set Charge Limit";
    private static final String CMD_set_charge_limit= "set_charge_limit";
    private static final int PERCENT_DEFAULT = 80;
    public static final int PERCENT_MIN = 50;
    public static final int PERCENT_MAX = 100;
    private int percent;
    public SetChargeLimitRequest(long vehicle_id, String vehicle_name, int percent)
    {
        super(vehicle_id, vehicle_name);
        setPercent(percent);
    }

    @Override
    public String getUrlString() {
        String url = getUrl_vehicle_cmd(getVehicle_id(), CMD_set_charge_limit) + "?percent=" + percent;
        return url;
    }

    public String getBody()
    {
        JSONObject parent=new JSONObject();
        try {
            parent.put("percent",percent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parent.toString();
    }

    @Override
    public String getResultString() {
        if(getResult())
        {
            return "Set charging level for " + this.getVehicle_name() + " to " + percent + "%.";
        }
        else
        {
            if(getReason().equals("already_set"))
            {
                return "Charging level for" + this.getVehicle_name() + " is already set to " + percent + "%.";
            }
            else
            {
                return "Unable to set charging level for " + this.getVehicle_name() + " to " + percent + "%: " + getReason();
            }
        }
    }

    @Override
    public String getCommandName() {
        return CMD_NAME;
    }

    public void setPercent(int percent) {
        if(percent > PERCENT_MAX && percent < PERCENT_MIN) {
            this.percent = PERCENT_DEFAULT;
        }
        else
        {
            this.percent = percent;
        }
    }

    public int getPercent()
    {
        return this.percent;
    }

    public int describeContents() {
        return 0;
    }

}
