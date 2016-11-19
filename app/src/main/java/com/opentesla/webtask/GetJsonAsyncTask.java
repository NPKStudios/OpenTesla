package com.opentesla.webtask;

import org.json.JSONObject;

/**
 * Created by Nick on 10/16/2016.
 */

public class GetJsonAsyncTask extends JsonAsyncTask
{

    public GetJsonAsyncTask(String url, String token, OnTaskDoneListener onTaskDoneListener)
    {
        super(url, token ,onTaskDoneListener);
    }
    @Override
    protected JSONObject doInBackground(Void... params){
            return getRequest(mUrl, mToken);
    }
}
