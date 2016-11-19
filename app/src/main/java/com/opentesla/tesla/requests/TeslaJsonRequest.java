package com.opentesla.tesla.requests;

import com.opentesla.webtask.JsonRequest;

import java.io.Serializable;

/**
 * Created by Nick on 10/23/2016.
 */

public abstract class TeslaJsonRequest implements JsonRequest, Serializable {

    protected static final String API_URL = "https://owner-api.teslamotors.com/";
    protected static final String API_VERSION_1 = "api/1";
    protected static final String LIST_ALL_VEHICLES = "/vehicles";
    protected static final String CMD = "/command/";
    protected static final String DATA_REQUEST = "/data_request/";
    protected static final String JSON_response = "response";

    public TeslaJsonRequest() {

    }

    protected static String getUrl_data_request(long vehicle_id, String cmd)
    {
        //https://owner-api.teslamotors.com/api/1/vehicles/vehicle_id/data_request/
        String url = getUrl_Vehicle(vehicle_id) + DATA_REQUEST + cmd;
        return url;
    }
    protected static String getUrl_vehicle_cmd(long vehicle_id, String cmd)
    {
        //https://owner-api.teslamotors.com/api/1/vehicles/vehicle_id/wake_up
        String url = getUrl_Vehicle(vehicle_id) + CMD + cmd;
        return url;
    }
    public static String getUrl_Vehicle(long vehicle_id)
    {
        String url = getUrl_All_Vehicles() + "/" + vehicle_id;
        return url;
    }
    public static String getUrl_All_Vehicles()
    {
        String url = getApiPortal() + LIST_ALL_VEHICLES;
        return url;
    }
    public static String getApiPortal()
    {
        String url = API_URL + API_VERSION_1;
        return url;
    }
}
