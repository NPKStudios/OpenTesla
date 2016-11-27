package com.opentesla.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextClock;
import android.widget.TextView;

import com.google.android.gms.gcm.Task;
import com.opentesla.android.database.DbTask;

import java.util.ArrayList;

/**
 * Created by Nick on 11/25/2016.
 */
public class TaskAdapter extends BaseAdapter {

    Context context;
    ArrayList<DbTask> tasks;
    private static LayoutInflater inflater = null;

    public TaskAdapter(Context context, ArrayList<DbTask>  tasks) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.tasks = tasks;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return tasks.get(position).get_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get object from list that contains Alarm data for this view
        // I assume that your alarm data correspond to an object name AlarmData

        DbTask alarmData = (DbTask)getItem(position);

        View rootView = convertView;
        if (rootView == null)
            rootView = inflater.inflate(R.layout.task_item, parent, false);
        TextView tv_time = (TextView) rootView.findViewById(R.id.tv_time);

        tv_time.setText(alarmData.getScheduleCalendar().getTime().toString());
        /*
        TextView alarmClockName= (TextView) rootView.findViewById(R.id.alarm_clock_name);
        ImageView clockIcon = (ImageView ) rootView.findViewById(R.id.alarm_clock_name);

        // Set your data there

        clockIcon.setImageResource(iconRes);
        alarmClockName.setText(alarmData.getName());
        */

        return rootView;
    }
}