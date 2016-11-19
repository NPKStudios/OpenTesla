package com.opentesla.webtask;

import org.json.JSONObject;

/**
 * Created by Nick on 10/17/2016.
 */

public interface OnTaskDoneListener {
    void onTaskDone(JSONObject responseData);
    void onError(String error);
}
