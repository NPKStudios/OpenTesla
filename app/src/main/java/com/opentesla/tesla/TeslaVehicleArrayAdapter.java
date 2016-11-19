package com.opentesla.tesla;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Nick on 10/22/2016.
 */

public class TeslaVehicleArrayAdapter extends ArrayAdapter<TeslaVehicle> {

    private ArrayList<TeslaVehicle> objects;

    public TeslaVehicleArrayAdapter(Context context, int resource, ArrayList<TeslaVehicle> objects) {
        //super(context, resource);
        // declaring our ArrayList of items
        super(context, resource, 0, objects);
        objects = objects;
    }
}
