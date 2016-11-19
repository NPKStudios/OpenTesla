package com.opentesla.tesla.response;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Nick on 10/25/2016.
 */
//http://json2java.azurewebsites.net/


public class Response  implements Parcelable {
    public static final String JSON_RESPONSE = "response";
    protected static final String TO_STRING_DIVIDER = ": ";
    protected static final String TO_STRING_NEWLINE = "\n";

    protected JSONObject response;
    public Response(JSONObject object)
    {
        response = object;
    }

    public static String getLine(String field, String value)
    {
        return field + TO_STRING_DIVIDER + value + TO_STRING_NEWLINE;
    }
    public static String getLine(String field, double value)
    {
        return field + TO_STRING_DIVIDER + value + TO_STRING_NEWLINE;
    }
    public static String getLine(String field, boolean value)
    {
        return field + TO_STRING_DIVIDER + value + TO_STRING_NEWLINE;
    }
    public static ArrayList<String> getStringArrayList(JSONArray temp)
    {
        ArrayList<String> tokens = new ArrayList<String>();
        if(temp != null && temp.length() > 0) {
            for (int i = 0; i < temp.length(); i++) {
                tokens.add(temp.optString(i, ""));
            }
        }
        return tokens;
    }
    @Override
    public String toString()
    {
        return response.toString();
    }

    public static final Parcelable.Creator<Response> CREATOR = new Parcelable.Creator<Response>() {
        public Response createFromParcel(Parcel in) {
            return new Response(in);
        }

        public Response[] newArray(int size) {
            return new Response[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(response.toString());
    }

    protected Response(Parcel in) {
        try {
            response = new JSONObject(in.readString());
        } catch (JSONException e) {
            Log.e(this.toString(), e.getLocalizedMessage());
            response = new JSONObject();
        }

    }
}
