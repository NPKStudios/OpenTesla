package com.opentesla.webtask;

import org.json.JSONObject;

/**
 * Created by Nick on 10/16/2016.
 */

public class PostJsonAsyncTask  extends JsonAsyncTask
{
    private String mData;
    private OnTaskDoneListener mOnTaskDoneListener;

    public PostJsonAsyncTask(String url, String token, OnTaskDoneListener onTaskDoneListener, String postData)
    {
        super(url, token, onTaskDoneListener);
        mData = postData;
    }
    @Override
    protected JSONObject doInBackground(Void... params){
            return postRequest(mUrl, mData, mToken);
    }
}
