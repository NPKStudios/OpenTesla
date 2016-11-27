package com.opentesla.android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.opentesla.android.database.DbTask;
import com.opentesla.android.database.TasksDb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Nick on 11/25/2016.
 */
public class TaskAdapter extends BaseAdapter {

    Context context;
    ArrayList<DbTask> tasks;
    TasksDb tasksDb;
    private static LayoutInflater inflater = null;

    public TaskAdapter(Context context, ArrayList<DbTask>  tasks, TasksDb tasksDb) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.tasks = tasks;
        this.tasksDb = tasksDb;
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
    public View getView(int position, View convertView, final ViewGroup parent) {
        // Get object from list that contains Alarm data for this view
        // I assume that your alarm data correspond to an object name AlarmData
        final DbTask task = tasks.get(position);

        //DbTask alarmData = (DbTask)getItem(position);
        SimpleDateFormat sdf;
        View rootView = convertView;
        if (rootView == null)
            rootView = inflater.inflate(R.layout.task_item, parent, false);
        TextView tv_time = (TextView) rootView.findViewById(R.id.tv_time);
        Switch s_enable = (Switch) rootView.findViewById(R.id.s_enable);
        final TextView et_label = (TextView) rootView.findViewById(R.id.et_label);


        if(false) {
            sdf = new SimpleDateFormat("HH:mm");
        }
        else
        {

            sdf = new SimpleDateFormat("h:mm a");
        }

        s_enable.setChecked(task.getEnable());
        et_label.setText(task.get_label());
        tv_time.setText(sdf.format(task.getScheduleCalendar().getTime()));

        //TODO: Not a fan of this method. needs fixed
        s_enable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                long i = task.get_id();
                task.setEnable(isChecked);
                if(task.getEnable() == true)
                {
                    scheduleTask(parent,task);
                }
                else
                {
                    cancelTask(parent, task);
                }
            }
        });

        et_label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
                builder.setTitle("Label");

// Set up the input
                final EditText input = new EditText(parent.getContext());
                input.setText(task.get_label());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        task.set_label(input.getText().toString());
                        et_label.setText(input.getText());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        /*
        TextView alarmClockName= (TextView) rootView.findViewById(R.id.alarm_clock_name);
        ImageView clockIcon = (ImageView ) rootView.findViewById(R.id.alarm_clock_name);

        // Set your data there

        clockIcon.setImageResource(iconRes);
        alarmClockName.setText(alarmData.getName());
        */

        return rootView;
    }
    protected void scheduleTask(View view, DbTask task)
    {
        task.setEnable(true);
        task.updateNextWakeTime();
        //task.updateNextWakeTime();
        tasksDb.updateTask(task);
        if(DbTaskScheduler.setAlarm(view.getContext(), task)) {        //
            Snackbar.make(view, task.getNextWakeTimeString() + "\n" +
                    task.getTask().getCommandName() + ": " + task.getVehicleName(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            //Toast.makeText(view.getContext(), "Command Set:\n" + task.getNextWakeTimeString() + "\n" +
            //        "Set " + userConfig.getSelectedVehicleDisplayName() + " to " + getPercent() + "%", Toast.LENGTH_LONG).show();
        }
        else
        {
            Snackbar.make(view, "Failed to set command", Snackbar.LENGTH_LONG).show();
        }
    }
    protected void cancelTask(View view, DbTask task) {
        task.setEnable(false);
        tasksDb.updateTask(task);
        DbTaskScheduler.cancelAlarm(view.getContext(), task);
        Snackbar.make(view, "Command Canceled", Snackbar.LENGTH_LONG).show();
    }
}