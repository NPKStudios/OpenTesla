package com.opentesla.tesla;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Nick on 10/12/2016.
 */

public class TeslaVehicle implements Serializable {


    private static final String JSON_COLOR = "color";
    private static final String JSON_DISPLAY_NAME = "display_name";
    private static final String JSON_ID = "id";
    private static final String JSON_OPTION_CODES = "option_codes";
    private static final String JSON_USER_ID = "user_id";
    private static final String JSON_VEHICLE_ID = "vehicle_id";
    private static final String JSON_VIN = "vin";
    private static final String JSON_TOKENS = "tokens";
    private static final String JSON_STATE = "state";
    //"color": null,
    private String mColor;
    //"display_name": null,
    private String mDisplayName;
    // "id": 321,
    private long mId;
    //"option_codes": "MS01,RENA,TM00,DRLH,PF00,BT85,PBCW,RFPO,WT19,IBMB,IDPB,TR00,SU01,SC01,TP01,AU01,CH00,HP00,PA00,PS00,AD02,X020,X025,X001,X003,X007,X011,X013",
    private String mOptionCodes;
    //"user_id": 123,
    private long mUserId;
    //"vehicle_id": ,
    private long mVehicleId;
    //"vin": "",
    private String mVIN;
    //"tokens": ["x", "x"],
    private ArrayList<String> mTokens;
    //"state": "online"
    private String mState;

    public TeslaVehicle()
    {
        mColor = "";
        mDisplayName = "";
        mId = 0;
        mOptionCodes = "";
        mUserId = 0;
        mVehicleId = 0;
        mVIN = "";
        mTokens = new ArrayList<String>();
        mState = "";
    }
    public TeslaVehicle(JSONObject object) throws JSONException {
            //mColor = object.getString(JSON_COLOR);
            mDisplayName = object.getString(JSON_DISPLAY_NAME);
            mId = object.getLong(JSON_ID);
            mOptionCodes = object.getString(JSON_OPTION_CODES);
            mUserId = object.getLong(JSON_USER_ID);
            mVehicleId = object.getLong(JSON_VEHICLE_ID);
            mVIN = object.getString(JSON_VIN);
            //mTokens = object.getString(JSON_TOKENS);
            mState = object.getString(JSON_STATE);
    }
    private void setMId(long id)
    {
        mId = id;
    }
    private void setmVehicleId(long vehicleId)
    {
        mVehicleId = vehicleId;
    }
    private void setmVIN(String vin)
    {
        mVIN = vin;
    }
    private void setmDisplayName(String displayName)
    {
        mDisplayName = displayName;
    }
    @Override
    public String toString()
    {
        return mDisplayName;
    }
    /*
    {
    "response":[{
    "id":,
    "vehicle_id":,
    "vin":"",
    "display_name":"",
    "option_codes":"MDLS,RENA,AF00,AU00,BC0B,BP00,BR02,BS00,BTX5,CDM0,CH05,PMNG,CW00,DA02,DCF0,DRLH,DSH5,DV4W,FG00,HP00,IDBA,IX00,LP00,ME02,MI01,PA00,PF00,PI00,PK00,PS01,PX00,QNLB,RFP2,SC01,SP00,SR01,ST01,SU00,TM00,TP00,TR00,UTAW,WTAS,WTX1,X002,X003,X008,X011,X014,X021,X025,X026,X028,X031,X037,X040,X044,YFFC,COUS",
    "color":null,
    "tokens":["",""],
    "state":"online","in_service":false,"id_s":"",
    "remote_start_enabled":true,
    "calendar_enabled":true,
    "notifications_enabled":true,
    "backseat_token":null,
    "backseat_token_updated_at":null}],
    "count":1}

     */
}
