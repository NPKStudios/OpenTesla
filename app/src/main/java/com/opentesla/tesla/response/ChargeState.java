package com.opentesla.tesla.response;

import org.json.JSONObject;

/**
 * Created by Nick on 10/25/2016.
 */

public class ChargeState extends Response {


    //0 = {java.util.LinkedHashMap$LinkedHashMapEntry@5138} "charging_state" -> "Charging"
    public static final String JSON_CHARGE_STATE = "charging_state";

    //1 = {java.util.LinkedHashMap$LinkedHashMapEntry@5139} "charge_limit_soc" -> "80"
    public static final String JSON_CHARGE_LIMIT_SOC = "charge_limit_soc";

    //2 = {java.util.LinkedHashMap$LinkedHashMapEntry@5140} "charge_limit_soc_std" -> "90"
    public static final String JSON_CHARGE_LIMIT_SOC_STD= "charge_limit_soc_std";

    //3 = {java.util.LinkedHashMap$LinkedHashMapEntry@5141} "charge_limit_soc_min" -> "50"
    public static final String JSON_CHARGE_LIMIT_SOC_MIN= "charge_limit_soc_min";

    //4 = {java.util.LinkedHashMap$LinkedHashMapEntry@5142} "charge_limit_soc_max" -> "100"
    public static final String JSON_CHARGE_LIMIT_SOC_MAX= "charge_limit_soc_max";

    //5 = {java.util.LinkedHashMap$LinkedHashMapEntry@5143} "charge_to_max_range" -> "false"
    public static final String JSON_CHARGE_TO_MAX_RANGE = "charge_to_max_range";

    //6 = {java.util.LinkedHashMap$LinkedHashMapEntry@5144} "battery_heater_on" -> "false"
    public static final String JSON_BATTERY_HEATER_ON = "battery_heater_on";

    //7 = {java.util.LinkedHashMap$LinkedHashMapEntry@5145} "not_enough_power_to_heat" -> "false"
    public static final String JSON_NOT_ENOUGH_POWER_TO_HEAT = "not_enough_power_to_heat";

    //8 = {java.util.LinkedHashMap$LinkedHashMapEntry@5146} "max_range_charge_counter" -> "0"
    public static final String JSON_MAX_RANGE_CHARGE_COUNTER = "max_range_charge_counter";

    //9 = {java.util.LinkedHashMap$LinkedHashMapEntry@5147} "fast_charger_present" -> "false"
    public static final String JSON_FAST_CHARGER_PRESENT = "fast_charger_present";

    //10 = {java.util.LinkedHashMap$LinkedHashMapEntry@5148} "fast_charger_type" -> "<invalid>"
    public static final String JSON_FAST_CHARGER_TYPE = "fast_charger_type";

    //11 = {java.util.LinkedHashMap$LinkedHashMapEntry@5149} "battery_range" -> "167.94"
    public static final String JSON_BATTERY_RANGE = "battery_range";

    //12 = {java.util.LinkedHashMap$LinkedHashMapEntry@5150} "est_battery_range" -> "160.59"
    public static final String JSON_EST_BATTERY_RANGE = "est_battery_range";

    //13 = {java.util.LinkedHashMap$LinkedHashMapEntry@5151} "ideal_battery_range" -> "209.93"
    public static final String JSON_IDEAL_BATTERY_RANGE = "ideal_battery_range";

    //14 = {java.util.LinkedHashMap$LinkedHashMapEntry@5152} "battery_level" -> "72"
    public static final String JSON_BATTERY_LEVEL = "battery_level";

    //15 = {java.util.LinkedHashMap$LinkedHashMapEntry@5153} "usable_battery_level" -> "72"
    public static final String JSON_USABLE_BATTERY_LEVEL= "usable_battery_level";

    //16 = {java.util.LinkedHashMap$LinkedHashMapEntry@5154} "battery_current" -> "26.8"
    public static final String JSON_BATTERY_CURRENT = "battery_current";

    //17 = {java.util.LinkedHashMap$LinkedHashMapEntry@5155} "charge_energy_added" -> "2.12"
    public static final String JSON_CHARGE_ENERGY_ADDED = "charge_energy_added";

    //18 = {java.util.LinkedHashMap$LinkedHashMapEntry@5156} "charge_miles_added_rated" -> "7.0"
    public static final String JSON_CHARGE_MILES_ADDED_RATED = "charge_miles_added_rated";

    //19 = {java.util.LinkedHashMap$LinkedHashMapEntry@5157} "charge_miles_added_ideal" -> "9.0"
    public static final String JSON_CHARGE_MILES_ADDED_IDEAL = "charge_miles_added_ideal";

    //20 = {java.util.LinkedHashMap$LinkedHashMapEntry@5158} "charger_voltage" -> "244"
    public static final String JSON_CHARGER_VOLTAGE = "charger_voltage";

    //21 = {java.util.LinkedHashMap$LinkedHashMapEntry@5159} "charger_pilot_current" -> "40"
    public static final String JSON_CHARGER_PILOT_CURRENT = "charger_pilot_current";

    //22 = {java.util.LinkedHashMap$LinkedHashMapEntry@5160} "charger_actual_current" -> "40"
    public static final String JSON_CHARGER_ACTUAL_CURRENT = "charger_actual_current";

    //23 = {java.util.LinkedHashMap$LinkedHashMapEntry@5161} "charger_power" -> "10"
    public static final String JSON_CHARGER_POWER = "charger_power";

    //24 = {java.util.LinkedHashMap$LinkedHashMapEntry@5162} "time_to_full_charge" -> "0.58"
    public static final String JSON_TIME_TO_FULL_CHARGE = "time_to_full_charge";

    //25 = {java.util.LinkedHashMap$LinkedHashMapEntry@5163} "trip_charging" -> "false"
    public static final String JSON_TRIP_CHARGING = "trip_charging";

    //26 = {java.util.LinkedHashMap$LinkedHashMapEntry@5164} "charge_rate" -> "29.4"
    public static final String JSON_CHARGE_RATE = "charge_rate";

    //27 = {java.util.LinkedHashMap$LinkedHashMapEntry@5165} "charge_port_door_open" -> "true"
    public static final String JSON_CHARGE_PORT_DOOR_OPEN = "charge_port_door_open";

    //28 = {java.util.LinkedHashMap$LinkedHashMapEntry@5166} "motorized_charge_port" -> "true"
    public static final String JSON_MOTORIZED_CHARGE_PORT = "motorized_charge_port";

    //29 = {java.util.LinkedHashMap$LinkedHashMapEntry@5167} "scheduled_charging_start_time" -> "null"
    public static final String JSON_SCHEDULED_CHARGING_START_TIME = "scheduled_charging_start_time";

    //30 = {java.util.LinkedHashMap$LinkedHashMapEntry@5168} "scheduled_charging_pending" -> "false"
    public static final String JSON_SCHEDULED_CHARGING_PENDING = "scheduled_charging_pending";

    //31 = {java.util.LinkedHashMap$LinkedHashMapEntry@5169} "user_charge_enable_request" -> "null"
    public static final String JSON_USER_CHARGE_ENABLED_REQUEST = "user_charge_enable_request";

    //32 = {java.util.LinkedHashMap$LinkedHashMapEntry@5170} "charge_enable_request" -> "true"
    public static final String JSON_CHARGE_ENABLED_REQUEST = "charge_enable_request";

    //33 = {java.util.LinkedHashMap$LinkedHashMapEntry@5171} "eu_vehicle" -> "false"
    public static final String JSON_EU_VEHICLE = "eu_vehicle";

    //34 = {java.util.LinkedHashMap$LinkedHashMapEntry@5172} "charger_phases" -> "1"
    public static final String JSON_CHARGER_PHASES = "charger_phases";

    //35 = {java.util.LinkedHashMap$LinkedHashMapEntry@5173} "charge_port_latch" -> "Engaged"
    public static final String JSON_CHARGE_PORT_LATCH = "charge_port_latch";

    //36 = {java.util.LinkedHashMap$LinkedHashMapEntry@5174} "charge_current_request" -> "40"
    public static final String JSON_CHARGE_CURRENT_REQUEST = "charge_current_request";

    //37 = {java.util.LinkedHashMap$LinkedHashMapEntry@5175} "charge_current_request_max" -> "40"
    public static final String JSON_CHARGE_CURRENT_REQUEST_MAX = "charge_current_request_max";

    //38 = {java.util.LinkedHashMap$LinkedHashMapEntry@5176} "managed_charging_active" -> "false"
    public static final String JSON_MANAGED_CHARGING_ACTIVE = "managed_charging_active";

    //39 = {java.util.LinkedHashMap$LinkedHashMapEntry@5177} "managed_charging_user_canceled" -> "false"
    public static final String JSON_MANAGED_CHARGING_USER_CANCELED = "managed_charging_user_canceled";

    //40 = {java.util.LinkedHashMap$LinkedHashMapEntry@5178} "managed_charging_start_time" -> "null"
    public static final String JSON_MANAGED_CHARGING_START_TIME = "managed_charging_start_time";

    private String charging_state;
    private double charge_limit_soc;
    private double charge_limit_soc_std;
    private double charge_limit_soc_min;
    private double charge_limit_soc_max;
    private boolean charge_to_max_range;
    private boolean battery_heater_on;
    private boolean not_enough_power_to_heat;
    private double max_range_charge_counter;
    private boolean fast_charger_present;
    private String fast_charger_type;
    private double battery_range;
    private double est_battery_range;
    private double ideal_battery_range;
    private double battery_level;
    private double usable_battery_level;
    private double battery_current;
    private double charge_energy_added;
    private double charge_miles_added_rated;
    private double charge_miles_added_ideal;
    private double charger_voltage;
    private double charger_pilot_current;
    private double charger_actual_current;
    private double charger_power;
    private double time_to_full_charge;
    private boolean trip_charging;
    private double charge_rate;
    private boolean charge_port_door_open;
    private boolean motorized_charge_port;
    private double scheduled_charging_start_time;
    private boolean scheduled_charging_pending;
    private boolean user_charge_enable_request;
    private boolean charge_enable_request;
    private boolean eu_vehicle;
    private double charger_phases;
    private String charge_port_latch;
    private double charge_current_request;
    private double charge_current_request_max;
    private boolean managed_charging_active;
    private boolean managed_charging_user_canceled;
    private String managed_charging_start_time;

    public ChargeState(JSONObject r)
    {
        super(r);
        charging_state = r.optString(JSON_CHARGE_STATE, "");
        charge_limit_soc = r.optDouble(JSON_CHARGE_LIMIT_SOC,0);
        charge_limit_soc_std = r.optDouble(JSON_CHARGE_LIMIT_SOC_STD, 0);
        charge_limit_soc_min = r.optDouble(JSON_CHARGE_LIMIT_SOC_MIN, 0);
        charge_limit_soc_max = r.optDouble(JSON_CHARGE_LIMIT_SOC_MAX, 0);
        battery_heater_on = r.optBoolean(JSON_BATTERY_HEATER_ON, false);
        charge_to_max_range = r.optBoolean(JSON_CHARGE_TO_MAX_RANGE, false);
        not_enough_power_to_heat = r.optBoolean(JSON_NOT_ENOUGH_POWER_TO_HEAT, false);
        max_range_charge_counter = r.optDouble(JSON_MAX_RANGE_CHARGE_COUNTER, 0);
        fast_charger_present = r.optBoolean(JSON_FAST_CHARGER_PRESENT, false);
        fast_charger_type = r.optString(JSON_FAST_CHARGER_TYPE, "");
        battery_range = r.optDouble(JSON_BATTERY_RANGE, 0);
        est_battery_range = r.optDouble(JSON_EST_BATTERY_RANGE, 0);
        ideal_battery_range = r.optDouble(JSON_IDEAL_BATTERY_RANGE, 0);
        battery_level = r.optDouble(JSON_BATTERY_LEVEL, 0);
        usable_battery_level = r.optDouble(JSON_USABLE_BATTERY_LEVEL, 0);
        battery_current = r.optDouble(JSON_BATTERY_CURRENT, 0);
        charge_energy_added = r.optDouble(JSON_CHARGE_ENERGY_ADDED, 0);
        charge_miles_added_rated = r.optDouble(JSON_CHARGE_MILES_ADDED_RATED, 0);
        charge_miles_added_ideal = r.optDouble(JSON_CHARGE_MILES_ADDED_IDEAL, 0);
        charger_voltage = r.optDouble(JSON_CHARGER_VOLTAGE, 0);
        charger_pilot_current = r.optDouble(JSON_CHARGER_PILOT_CURRENT, 0);
        charger_actual_current = r.optDouble(JSON_CHARGER_ACTUAL_CURRENT, 0);
        charger_power = r.optDouble(JSON_CHARGER_POWER, 0);
        time_to_full_charge = r.optDouble(JSON_TIME_TO_FULL_CHARGE, 0);
        trip_charging = r.optBoolean(JSON_TRIP_CHARGING, false);
        charge_rate = r.optDouble(JSON_CHARGE_RATE, 0);
        charge_port_door_open = r.optBoolean(JSON_CHARGE_PORT_DOOR_OPEN, false);
        motorized_charge_port = r.optBoolean(JSON_MOTORIZED_CHARGE_PORT, false);
        scheduled_charging_start_time = r.optDouble(JSON_SCHEDULED_CHARGING_START_TIME, 0);
        scheduled_charging_pending = r.optBoolean(JSON_SCHEDULED_CHARGING_PENDING, false);
        user_charge_enable_request = r.optBoolean(JSON_USER_CHARGE_ENABLED_REQUEST, false);
        charge_enable_request = r.optBoolean(JSON_CHARGE_ENABLED_REQUEST, false);
        eu_vehicle = r.optBoolean(JSON_EU_VEHICLE, false);
        charger_phases = r.optDouble(JSON_CHARGER_PHASES, 0);
        charge_port_latch = r.optString(JSON_CHARGE_PORT_LATCH, "");
        charge_current_request = r.optDouble(JSON_CHARGE_CURRENT_REQUEST, 0);
        charge_current_request_max = r.optDouble(JSON_CHARGE_CURRENT_REQUEST_MAX, 0);
        managed_charging_active = r.optBoolean(JSON_MANAGED_CHARGING_ACTIVE, false);
        managed_charging_user_canceled = r.optBoolean(JSON_MANAGED_CHARGING_USER_CANCELED, false);
        managed_charging_start_time = r.optString(JSON_MANAGED_CHARGING_START_TIME, "");
    }

    public String getCharging_state() {
        return charging_state;
    }

    public boolean isCharge_to_max_range() {
        return charge_to_max_range;
    }

    public double getMax_range_charge_counter() {
        return max_range_charge_counter;
    }

    public boolean isFast_charger_present() {
        return fast_charger_present;
    }

    public double getBattery_range() {
        return battery_range;
    }

    public double getEst_battery_range() {
        return est_battery_range;
    }

    public double getIdeal_battery_range() {
        return ideal_battery_range;
    }

    public double getBattery_level() {
        return battery_level;
    }

    public double getBattery_current() {
        return battery_current;
    }

    public double getCharger_voltage() {
        return charger_voltage;
    }

    public double getCharger_pilot_current() {
        return charger_pilot_current;
    }

    public double getCharger_actual_current() {
        return charger_actual_current;
    }

    public double getCharger_power() {
        return charger_power;
    }

    public double getTime_to_full_charge() {
        return time_to_full_charge;
    }

    public double getCharge_rate() {
        return charge_rate;
    }

    public boolean isCharge_port_door_open() {
        return charge_port_door_open;
    }

    public double getCharge_limit_soc() {
        return charge_limit_soc;
    }

    public double getCharge_limit_soc_std() {
        return charge_limit_soc_std;
    }

    public double getCharge_limit_soc_min() {
        return charge_limit_soc_min;
    }

    public double getCharge_limit_soc_max() {
        return charge_limit_soc_max;
    }

    public boolean isBattery_heater_on() {
        return battery_heater_on;
    }

    public String getManaged_charging_start_time() {
        return managed_charging_start_time;
    }

    public boolean isNot_enough_power_to_heat() {
        return not_enough_power_to_heat;
    }

    public String getFast_charger_type() {
        return fast_charger_type;
    }

    public double getUsable_battery_level() {
        return usable_battery_level;
    }

    public double getCharge_miles_added_rated() {
        return charge_miles_added_rated;
    }

    public double getCharge_miles_added_ideal() {
        return charge_miles_added_ideal;
    }

    public boolean isTrip_charging() {
        return trip_charging;
    }

    public boolean isMotorized_charge_port() {
        return motorized_charge_port;
    }

    public double getScheduled_charging_start_time() {
        return scheduled_charging_start_time;
    }

    public boolean isScheduled_charging_pending() {
        return scheduled_charging_pending;
    }

    public boolean isUser_charge_enable_request() {
        return user_charge_enable_request;
    }

    public boolean isCharge_enable_request() {
        return charge_enable_request;
    }

    public boolean isEu_vehicle() {
        return eu_vehicle;
    }

    public double getCharger_phases() {
        return charger_phases;
    }

    public double getCharge_current_request() {
        return charge_current_request;
    }

    public double getCharge_current_request_max() {
        return charge_current_request_max;
    }

    public boolean isManaged_charging_active() {
        return managed_charging_active;
    }

    public boolean isManaged_charging_user_canceled() {
        return managed_charging_user_canceled;
    }

    public String toString()
    {
        String state = "";
        state += getLine(JSON_CHARGE_STATE, charging_state);
        state += getLine(JSON_CHARGE_LIMIT_SOC, charge_limit_soc);
        state += getLine(JSON_CHARGE_LIMIT_SOC_STD, charge_limit_soc_std);
        state += getLine(JSON_CHARGE_LIMIT_SOC_MIN, charge_limit_soc_min);
        state += getLine(JSON_CHARGE_LIMIT_SOC_MAX, charge_limit_soc_max);
        state += getLine(JSON_CHARGE_TO_MAX_RANGE, charge_to_max_range);
        state += getLine(JSON_BATTERY_HEATER_ON, battery_heater_on);
        state += getLine(JSON_NOT_ENOUGH_POWER_TO_HEAT, not_enough_power_to_heat);
        state += getLine(JSON_MAX_RANGE_CHARGE_COUNTER, max_range_charge_counter);
        state += getLine(JSON_FAST_CHARGER_PRESENT, fast_charger_present);
        state += getLine(JSON_FAST_CHARGER_TYPE, fast_charger_type);
        state += getLine(JSON_BATTERY_RANGE, battery_range);
        state += getLine(JSON_EST_BATTERY_RANGE, est_battery_range);
        state += getLine(JSON_IDEAL_BATTERY_RANGE, ideal_battery_range);
        state += getLine(JSON_BATTERY_LEVEL, battery_level);
        state += getLine(JSON_USABLE_BATTERY_LEVEL, usable_battery_level);
        state += getLine(JSON_BATTERY_CURRENT, battery_current);
        state += getLine(JSON_CHARGE_ENERGY_ADDED, charge_energy_added);
        state += getLine(JSON_CHARGE_MILES_ADDED_RATED, charge_miles_added_rated);
        state += getLine(JSON_CHARGE_MILES_ADDED_IDEAL, charge_miles_added_ideal);
        state += getLine(JSON_CHARGER_VOLTAGE, charger_voltage);
        state += getLine(JSON_CHARGER_PILOT_CURRENT, charger_pilot_current);
        state += getLine(JSON_CHARGER_ACTUAL_CURRENT, charger_actual_current);
        state += getLine(JSON_CHARGER_POWER, charger_power);
        state += getLine(JSON_TIME_TO_FULL_CHARGE, time_to_full_charge);
        state += getLine(JSON_TRIP_CHARGING, trip_charging);
        state += getLine(JSON_CHARGE_RATE, charge_rate);
        state += getLine(JSON_CHARGE_PORT_DOOR_OPEN, charge_port_door_open);
        state += getLine(JSON_MOTORIZED_CHARGE_PORT, motorized_charge_port);
        state += getLine(JSON_SCHEDULED_CHARGING_START_TIME, scheduled_charging_start_time);
        state += getLine(JSON_SCHEDULED_CHARGING_PENDING, scheduled_charging_pending);
        state += getLine(JSON_USER_CHARGE_ENABLED_REQUEST,  user_charge_enable_request);
        state += getLine(JSON_CHARGE_ENABLED_REQUEST, charge_enable_request);
        state += getLine(JSON_EU_VEHICLE, eu_vehicle);
        state += getLine(JSON_CHARGER_PHASES, charger_phases);
        state += getLine(JSON_CHARGE_PORT_LATCH, charge_port_latch);
        state += getLine(JSON_CHARGE_CURRENT_REQUEST, charge_current_request);
        state += getLine(JSON_CHARGE_CURRENT_REQUEST_MAX, charge_current_request_max);
        state += getLine(JSON_MANAGED_CHARGING_ACTIVE, managed_charging_active);
        state += getLine(JSON_MANAGED_CHARGING_USER_CANCELED, managed_charging_user_canceled);
        state += getLine(JSON_MANAGED_CHARGING_START_TIME, managed_charging_start_time);
        return state;
    }
}
