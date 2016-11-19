package com.opentesla.android;

import android.content.SharedPreferences;

import static com.opentesla.android.MySharedPreferences.LoadSharedPreference;
import static com.opentesla.android.MySharedPreferences.SaveSharedPreference;

/**
 * Created by Nick on 10/29/2016.
 */

public class UserConfig {
    private static String SP_SELECTED_VEHICLE_ID = "com_npk_tcs_uc_sv_id";
    private static String SP_SELECTED_VEHICLE_DISPLAY_NAME = "com_npk_tcs_uc_sv_dn";

    private SharedPreferences sharedPreferences;

    public UserConfig(SharedPreferences sharedPreferences)
    {
        this.sharedPreferences = sharedPreferences;
    }
    public String getSelectedVehicleIdString()
    {
        return "" + (long)LoadSharedPreference(sharedPreferences, SP_SELECTED_VEHICLE_ID, (long)0);
    }
    public long getSelectedVehicleId()
    {
        return LoadSharedPreference(sharedPreferences, SP_SELECTED_VEHICLE_ID, (long)0);
    }
    private void setSelectedVehicleId(long id_s)
    {
        SaveSharedPreference(sharedPreferences, SP_SELECTED_VEHICLE_ID, id_s);
    }
    public String getSelectedVehicleDisplayName()
    {
        return LoadSharedPreference(sharedPreferences, SP_SELECTED_VEHICLE_DISPLAY_NAME, "");
    }
    private void setSelectedVehicleDisplayName(String display_name)
    {
        SaveSharedPreference(sharedPreferences, SP_SELECTED_VEHICLE_DISPLAY_NAME, display_name);
    }
    public void setSelectedVehicle(long id_s, String display_name)
    {
        setSelectedVehicleId(id_s);
        setSelectedVehicleDisplayName(display_name);
    }

}
