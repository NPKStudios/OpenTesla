package com.opentesla.android;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Nick on 10/12/2016.
 */

public class MySharedPreferences {

    public static SharedPreferences getSharedPreferences(Context context)
    {
        return context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    }
    public static SharedPreferences.Editor getSharedPreferencesEditor(SharedPreferences sharedPreferences)
    {
        return sharedPreferences.edit();
    }
    public static void SaveSharedPreference(SharedPreferences sharedPreferences, String preference, String value)
    {
        SharedPreferences.Editor editor = getSharedPreferencesEditor(sharedPreferences);
        editor.remove(preference);
        editor.putString(preference, value);
        editor.commit();
    }
    public static void SaveSharedPreference(SharedPreferences sharedPreferences, String preference, int value)
    {
        SharedPreferences.Editor editor = getSharedPreferencesEditor(sharedPreferences);
        editor.remove(preference);
        editor.putInt(preference, value);
        editor.commit();
    }
    public static void SaveSharedPreference(SharedPreferences sharedPreferences, String preference, long value)
    {
        SharedPreferences.Editor editor = getSharedPreferencesEditor(sharedPreferences);
        editor.remove(preference);
        editor.putLong(preference, value);
        editor.commit();
    }
    public static String LoadSharedPreference(SharedPreferences sharedPreferences, String preference, String defaultValue)
    {
        return sharedPreferences.getString(preference, defaultValue);
    }
    public static int LoadSharedPreference(SharedPreferences sharedPreferences, String preference, int defaultValue)
    {
        return sharedPreferences.getInt(preference, defaultValue);
    }
    public static long LoadSharedPreference(SharedPreferences sharedPreferences, String preference, long defaultValue)
    {
        return sharedPreferences.getLong(preference, defaultValue);
    }
    public static void RemoveSharedPreference(SharedPreferences sharedPreferences, String preference)
    {
        SharedPreferences.Editor editor = getSharedPreferencesEditor(sharedPreferences);
        editor.remove(preference);
    }
}
