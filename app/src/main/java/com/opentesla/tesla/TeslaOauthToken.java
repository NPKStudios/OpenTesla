package com.opentesla.tesla;

import android.content.Context;
import android.content.SharedPreferences;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import static com.opentesla.android.MySharedPreferences.LoadSharedPreference;
import static com.opentesla.android.MySharedPreferences.SaveSharedPreference;


/**
 * Created by Nick on 10/11/2016.
 */

public class TeslaOauthToken {
    /*
    {
        "access_token": "abc123",
            "token_type": "bearer",
            "expires_in": 7776000,
            "created_at": 1457385291
    }
    */
    private static final String SP_ACCESS_TOKEN = "TeslaOauthToken_mAccessToken";
    private static final String SP_TOKEN_TYPE = "TeslaOauthToken_mTokenType";
    private static final String SP_EXPIRES_IN = "TeslaOauthToken_mExpiresIn";
    private static final String SP_CREATED_AT = "TeslaOauthToken_mCreatedAt";
    private static final String DV_ACCESS_TOKEN = "";
    private static final String DV_TOKEN_TYPE = "";
    private static final long DV_EXPIRES_IN = 0;
    private static final long DV_CREATED_AT = 0;

    private String mAccessToken;
    private String mTokenType;
    private long mExpiresIn;
    private long mCreatedAt;

    private SharedPreferences mSharedPref;
    Context mContext;

    //private static final String KEY_FILE = "oauth_key.json";

    public TeslaOauthToken()
    {
        mAccessToken = "";
        mTokenType = "";
        mExpiresIn = 0;
        mCreatedAt = 0;
    }

    public TeslaOauthToken(SharedPreferences sharedPreferences)
    {
        loadTeslaOauthToken(sharedPreferences);
    }
    public void loadTeslaOauthToken(SharedPreferences sharedPreferences)
    {
        mAccessToken = LoadSharedPreference(sharedPreferences, SP_ACCESS_TOKEN, DV_ACCESS_TOKEN);
        mTokenType = LoadSharedPreference(sharedPreferences, SP_TOKEN_TYPE, DV_TOKEN_TYPE);
        mExpiresIn = LoadSharedPreference(sharedPreferences, SP_EXPIRES_IN, DV_EXPIRES_IN);
        mCreatedAt = LoadSharedPreference(sharedPreferences, SP_CREATED_AT, DV_CREATED_AT);
    }
    public void saveTeslaOauthToken(SharedPreferences sharedPreferences)
    {
        SaveSharedPreference(sharedPreferences, SP_ACCESS_TOKEN, mAccessToken);
        SaveSharedPreference(sharedPreferences, SP_TOKEN_TYPE, mTokenType);
        SaveSharedPreference(sharedPreferences, SP_EXPIRES_IN, mExpiresIn);
        SaveSharedPreference(sharedPreferences, SP_CREATED_AT, mCreatedAt);
    }
    public void deleteTeslaOauthToken(SharedPreferences sharedPreferences)
    {
        mAccessToken = "";
        mTokenType = "";
        mExpiresIn = 0;
        mCreatedAt = 0;
        saveTeslaOauthToken(sharedPreferences);
    }
    public void SetTeslaOauthToken(JSONObject oauthToken) {
        try {
            setAccessToken(oauthToken.getString("access_token"));

            setTokenType(oauthToken.getString("token_type"));

            setExpiresIn(oauthToken.getLong("expires_in"));

            setCreatedAt(oauthToken.getLong("created_at"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String getAccessToken()
    {
        return mAccessToken;
    }
    private void setAccessToken(String accessToken)
    {
        mAccessToken = accessToken;
    }
    public String GetTokenType()
    {
        return mTokenType;
    }
    private void setTokenType(String tokenType)
    {
        mTokenType = tokenType;
    }

    public long getExpiresIn()
    {
        return mExpiresIn;
    }
    private void setExpiresIn(long expiresIn)
    {
        mExpiresIn = expiresIn;
    }

    private long getCreatedAt()
    {
        return mCreatedAt;
    }
    public void setCreatedAt(long createdAt)
    {
        mCreatedAt = createdAt;
    }
    public Date getExpirationTime()
    {
        return new Date((getCreatedAt() + getExpiresIn()) * 1000L);
    }
    public Date getCreationTime()
    {
        return new Date(getCreatedAt() * 1000L);
    }
    public Boolean isExpired()
    {
        Long tsLong = System.currentTimeMillis()/1000;
        Long expLong = getCreatedAt() + getExpiresIn();
        if(tsLong > expLong) //If the current time is past the expired time
            return true;
        return false;
    }

}
