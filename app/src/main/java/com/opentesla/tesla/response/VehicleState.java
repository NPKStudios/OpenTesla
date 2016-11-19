package com.opentesla.tesla.response;

import org.json.JSONObject;

/**
 * Created by Nick on 10/25/2016.
 */

/*{
"api_version":3,
"autopark_state":"standby",
"autopark_state_v2":"standby",
"autopark_style":"standard",
"calendar_supported":true,
"car_type":"s2",
"car_version":"2.40.21",
"center_display_state":0,
"dark_rims":false,
"df":0,
"dr":0,
"exterior_color":"SteelGrey",
"ft":0,
"has_spoiler":false,
"homelink_nearby":true,
"last_autopark_error":"no_error",
"locked":true,
"notifications_supported":true,
"odometer":6369.229474,
"parsed_calendar_supported":true,
"perf_config":"P3",
"pf":0,
"pr":0,
"rear_seat_heaters":0,
"rear_seat_type":1,
"remote_start":false,
"remote_start_supported":true,
"rhd":false,
"roof_color":"None",
"rt":0,
"seat_type":1,
"spoiler_type":"None",
"sun_roof_installed":2,
"sun_roof_percent_open":0,
"sun_roof_state":"unknown",
"third_row_seats":"None",
"valet_mode":false,
"vehicle_name":"",
"wheel_type":"AeroTurbine19"}*/
public class VehicleState extends Response {
    public static final String JSON_api_version= "api_version";
    public static final String JSON_autopark_state = "autopark_state";
    public static final String JSON_autopark_state_v2 = "autopark_state_v2";
    public static final String JSON_autopark_style = "autopark_style";
    public static final String JSON_calendar_supported = "calendar_supported";
    public static final String JSON_car_type = "car_type";
    public static final String JSON_center_display_state = "center_display_state";
    public static final String JSON_homelink_nearby = "homelink_nearby";
    public static final String JSON_last_autopark_error = "last_autopark_error";
    public static final String JSON_notifications_supported = "notifications_supported";
    public static final String JSON_odometer = "odometer";
    public static final String JSON_exterior_color = "exterior_color";
    public static final String JSON_rear_seat_heaters = "rear_seat_heaters";
    public static final String JSON_rear_seat_type= "rear_seat_type";
    public static final String JSON_remote_start = "remote_start";
    public static final String JSON_remote_start_supported = "remote_start_supported";
    public static final String JSON_parsed_calendar_supported = "parsed_calendar_supported";
    public static final String JSON_rhd = "rhd"; //Right hand drive
    public static final String JSON_seat_type = "seat_type";
    public static final String JSON_spoiler_type = "spoiler_type";
    public static final String JSON_third_row_seats = "third_row_seats";
    public static final String JSON_valet_mode = "valet_mode";
    public static final String JSON_vehicle_name = "vehicle_name";
    //"df": false,                  // driver's side front door open
    public static final String JSON_df = "df";
    //"dr": false,                  // driver's side rear door open
    public static final String JSON_dr = "dr";
    //"pf": false,                  // passenger's side front door open
    public static final String JSON_PF = "pf";
    //"pr": false,                  // passenger's side rear door open
    public static final String JSON_PR = "pr";
    //"ft": false,                  // front trunk is open
    public static final String JSON_FT = "ft";
    //"rt": false,                  // rear trunk is open
    public static final String JSON_RT = "rt";
    //"car_verson": "1.19.42",      // car firmware version
    public static final String JSON_CAR_VERSION = "car_version";
    //"locked": true,               // car is locked
    public static final String JSON_LOCKED = "locked";
    //"sun_roof_installed": false,  // panoramic roof is installed
    public static final String JSON_SUN_ROOF_INSTALLED = "sun_roof_installed";
    //"sun_roof_state": "unknown",
    public static final String JSON_SUN_ROOF_STATE = "sun_roof_state";
    //"sun_roof_percent_open": 0,   // null if not installed
    public static final String JSON_SUN_ROOF_PERCENT_OPEN = "sun_roof_percent_open";
    //"dark_rims": false,           // gray rims installed
    public static final String JSON_DARK_RIMS = "dark_rims";
    //"wheel_type": "Base19",       // wheel type installed
    public static final String JSON_WHEEL_TYPE = "wheel_type";
    //"has_spoiler": false,         // spoiler is installed
    public static final String JSON_HAS_SPOILER = "has_spoiler";
    // "roof_color": "Colored",      // "None" for panoramic roof
    public static final String JSON_ROOF_COLOR = "roof_color";
    //"perf_config": "Base"
    public static final String JSON_PERF_CONFIG= "perf_config";


    private double api_version;
    private String autopark_state;
    private String autopark_state_v2;
    private String autopark_style;
    private boolean calendar_supported;
    private String car_type;
    private int center_display_state;
    private boolean homelink_nearby;
    private String last_autopark_error;
    private boolean notifications_supported;
    private double odometer;
    private String exterior_color;
    private int rear_seat_heaters;
    private int rear_seat_type;
    private boolean remote_start;
    private boolean remote_start_supported;
    private boolean parsed_calendar_supported;
    private boolean rhd; //Right hand drive
    private int seat_type;
    private String spoiler_type;
    private String third_row_seats;
    private boolean valet_mode;
    private String vehicle_name;
    private boolean df;
    private boolean dr;
    private boolean pf;
    private boolean pr;
    private boolean ft;
    private boolean rt;
    private String car_version;
    private boolean locked;
    private boolean sun_roof_installed;
    private String sun_roof_state;
    private int sun_roof_percent_open;
    private boolean dark_rims;
    private String wheel_type;
    private boolean has_spoiler;
    private String roof_color;
    private String perf_config;

    public VehicleState(JSONObject object) {
        super(object);
        df = object.optBoolean(JSON_df, false);
        dr = object.optBoolean(JSON_dr, false);
        pf = object.optBoolean(JSON_PF, false);
        pr = object.optBoolean(JSON_PR, false);
        ft = object.optBoolean(JSON_FT, false);
        rt = object.optBoolean(JSON_RT, false);
        car_version = object.optString(JSON_CAR_VERSION, "");
        locked = object.optBoolean(JSON_LOCKED, false);
        sun_roof_installed = object.optBoolean(JSON_SUN_ROOF_INSTALLED, false);
        sun_roof_state = object.optString(JSON_SUN_ROOF_STATE, "");
        sun_roof_percent_open = object.optInt(JSON_SUN_ROOF_PERCENT_OPEN, 0);
        dark_rims = object.optBoolean(JSON_DARK_RIMS, false);
        wheel_type = object.optString(JSON_WHEEL_TYPE, "");
        has_spoiler = object.optBoolean(JSON_HAS_SPOILER, false);
        roof_color = object.optString(JSON_ROOF_COLOR, "");
        perf_config = object.optString(JSON_PERF_CONFIG, "");
    }
//    @Override
//    public String toString()
//    {
//        String state = "";
//        state += getLine(JSON_df, df);
//        state += getLine(JSON_dr, dr);
//        state += getLine(JSON_PF, pf);
//        state += getLine(JSON_PR, pr);
//        state += getLine(JSON_FT, ft);
//        state += getLine(JSON_RT, rt);
//        state += getLine(JSON_CAR_VERSION, car_version);
//        state += getLine(JSON_LOCKED, locked);
//        state += getLine(JSON_SUN_ROOF_INSTALLED, sun_roof_installed);
//        state += getLine(JSON_SUN_ROOF_STATE, sun_roof_state);
//        state += getLine(JSON_SUN_ROOF_PERCENT_OPEN, sun_roof_percent_open);
//        state += getLine(JSON_DARK_RIMS, dark_rims);
//        state += getLine(JSON_WHEEL_TYPE, wheel_type);
//        state += getLine(JSON_HAS_SPOILER, has_spoiler);
//        state += getLine(JSON_ROOF_COLOR, roof_color);
//        state += getLine(JSON_PERF_CONFIG, perf_config);
//        return state;
//    }
}

