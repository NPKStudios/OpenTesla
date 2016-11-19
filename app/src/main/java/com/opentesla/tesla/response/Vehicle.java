package com.opentesla.tesla.response;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by Nick on 10/25/2016.
 */
/*


/*{
"id":0,
"vehicle_id":0,
"vin":"",
"display_name":"Name",
"option_codes":"MDLS,RENA,AF00,AU00,BC0B,BP00,BR02,BS00,BTX5,CDM0,CH05,PMNG,CW00,DA02,DCF0,DRLH,DSH5,DV4W,FG00,HP00,IDBA,IX00,LP00,ME02,MI01,PA00,PF00,PI00,PK00,PS01,PX00,QNLB,RFP2,SC01,SP00,SR01,ST01,SU00,TM00,TP00,TR00,UTAW,WTAS,WTX1,X002,X003,X008,X011,X014,X021,X025,X026,X028,X031,X037,X040,X044,YFFC,COUS",
"color":null,
"tokens":["",""],
"state":"online",
"in_service":false,
"id_s":"",
"remote_start_enabled":true,
"calendar_enabled":true,
"notifications_enabled":true,
"backseat_token":null,
"backseat_token_updated_at":null
}*/

public class Vehicle extends Response implements Parcelable {

    private static final String JSON_id = "id";
    private static final String JSON_vehicle_id = "vehicle_id";
    private static final String JSON_vin = "vin";
    private static final String JSON_display_name = "display_name";
    private static final String JSON_option_codes = "option_codes";
    private static final String JSON_color = "color";
    private static final String JSON_tokens = "tokens";
    private static final String JSON_state = "state";
    private static final String JSON_in_service = "in_service";
    private static final String JSON_id_s = "id_s";
    private static final String JSON_remote_start_enabled = "remote_start_enabled";
    private static final String JSON_calendar_enabled = "calendar_enabled";
    private static final String JSON_notifications_enabled = "notifications_enabled";
    private static final String JSON_backseat_token = "backseat_token";
    private static final String JSON_backseat_token_updated_at = "backseat_token_updated_at";


    private long id;
    private long vehicle_id;
    private String vin;
    private String display_name;
    private String option_codes;
    private Color color;
    private ArrayList<String> tokens;
    private String state;
    private boolean in_service;
    private String id_s;
    private boolean remote_start_enabled;
    private boolean calendar_enabled;
    private boolean notifications_enabled;
    //private Backseat_token backseat_token;
    //private Backseat_token_updated_at backseat_token_updated_at;

    public Vehicle(JSONObject object) {
        super(object);
        id = object.optLong(JSON_id, 0);
        vehicle_id = object.optLong(JSON_vehicle_id, 0);
        vin = object.optString(JSON_vin, "");
        display_name = object.optString(JSON_display_name, "");
        option_codes = object.optString(JSON_option_codes,"");
        tokens = getStringArrayList(object.optJSONArray("tokens"));//tokens = object.get(JSON_option_codes,"");
        state = object.optString(JSON_state, "");
        in_service = object.optBoolean(JSON_in_service, false);
        id_s = object.optString(JSON_id_s, "");
        remote_start_enabled = object.optBoolean(JSON_remote_start_enabled, false);
        calendar_enabled = object.optBoolean(JSON_calendar_enabled, false);
        notifications_enabled = object.optBoolean(JSON_notifications_enabled, false);
    }

    public static final Parcelable.Creator<Vehicle> CREATOR = new Parcelable.Creator<Vehicle>() {
        public Vehicle createFromParcel(Parcel in) {
            return new Vehicle(in);
        }

        public Vehicle[] newArray(int size) {
            return new Vehicle[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeLong(id);
        out.writeLong(vehicle_id);
        out.writeString(vin);
        out.writeString(display_name);
        out.writeString(option_codes);
        out.writeArray(tokens.toArray());
        out.writeString(state);
        out.writeByte((byte) (in_service ? 1 : 0));
        out.writeString(id_s);
        out.writeByte((byte) (remote_start_enabled ? 1 : 0));
        out.writeByte((byte) (calendar_enabled ? 1 : 0));
        out.writeByte((byte) (notifications_enabled ? 1 : 0));
    }

    private Vehicle(Parcel in) {
        super(in);
        id = in.readLong();
        vehicle_id = in.readLong();
        vin = in.readString();
        display_name = in.readString();
        option_codes = in.readString();
        tokens = in.readArrayList(String.class.getClassLoader());
        state = in.readString();
        in_service = in.readByte() != 0;
        id_s = in.readString();
        remote_start_enabled = in.readByte() != 0;
        calendar_enabled = in.readByte() != 0;
        notifications_enabled = in.readByte() != 0;
    }
    public long getId()
    {
        return id;
    }
    public long getVehicle_id()
    {
        return vehicle_id;
    }
    public String getDisplay_name()
    {
        return display_name;
    }
    public String getId_s()
    {
        return id_s;
    }
}

