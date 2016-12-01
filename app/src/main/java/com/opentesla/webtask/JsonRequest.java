package com.opentesla.webtask;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Nick on 10/23/2016.
 */

public interface JsonRequest extends Serializable {
    RequestType getRequestType();
    String getUrlString();
    boolean processJsonResponse(JSONObject response);
}
