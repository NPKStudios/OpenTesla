package com.opentesla.tesla.requests;

import android.os.Parcel;

import com.opentesla.webtask.RequestType;

import org.json.JSONObject;

/**
 * Created by Nick on 10/23/2016.
 */
//https://owner-api.teslamotors.com/api/1/vehicles
public class LogoutRequest extends TeslaJsonRequest {
    public static final String JSON_response = "response";

    public static final String OAUTH_REVOKE = "oauth/revoke";

    public LogoutRequest(){

    }

    protected LogoutRequest(Parcel in) {
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.POST;
    }

    @Override
    public String getUrlString() {
        return getUrl_All_Vehicles();
    }

    @Override
    public boolean processJsonResponse(JSONObject response) {
        return true;
    }

}
