package com.opentesla.webtask;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Nick on 10/23/2016.
 */

public interface JsonRequest extends Serializable {
    public RequestType getRequestType();
    public String getUrlString();
    public boolean processJsonResponse(JSONObject response);
}
