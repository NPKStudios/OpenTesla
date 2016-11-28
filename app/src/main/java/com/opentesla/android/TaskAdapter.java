package com.opentesla.android;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;

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
        final TextView tv_label = (TextView) rootView.findViewById(R.id.et_label);




        s_enable.setChecked(task.getEnable());
        tv_label.setText(task.get_label());

        //TODO: Not a fan of this method. needs fixed
        setup_time(parent,tv_time,task,false);
        setup_enable_button(parent, s_enable, task);
        setup_label(parent, tv_label, task);



        /*
        TextView alarmClockName= (TextView) rootView.findViewById(R.id.alarm_clock_name);
        ImageView clockIcon = (ImageView ) rootView.findViewById(R.id.alarm_clock_name);

        // Set your data there

        clockIcon.setImageResource(iconRes);
        alarmClockName.setText(alarmData.getName());
        */

        return rootView;
    }
    protected void set_time(TextView textView, DbTask task, boolean twenty_four_hour)
    {
        SimpleDateFormat sdf;
        if (twenty_four_hour) {
            sdf = new SimpleDateFormat("HH:mm");
        } else {

            sdf = new SimpleDateFormat("h:mm a");
        }
        textView.setText(sdf.format(task.getScheduleCalendar().getTime()));
    }
    protected void setup_time(final View parent, final TextView textView, final DbTask task, final boolean twenty_four_hour)
    {
        set_time(textView, task, false);
        final TimePickerDialog.OnTimeSetListener tsl = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay,
                                  int minute) {
                task.setScheduledTime(hourOfDay, minute, 0, 0);

                if (task.getEnable() == true) {
                    enableTask(parent, task);
                }
                else
                {
                    updateTask(task);
                }

                set_time(textView, task, false);
                parent.refreshDrawableState();
            }
        };

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(parent.getContext(),tsl,task.getScheduleHour_24(), task.getScheduleMinute(), twenty_four_hour);
                timePickerDialog.show();
            }
        });
    }
    protected void setup_label(final View parent, final TextView textView, final DbTask task)
    {
        textView.setOnClickListener(new View.OnClickListener()
        {
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
                        updateTask(task);
                        textView.setText(input.getText());
                        parent.refreshDrawableState();
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
    }
    protected void setup_enable_button(final View parent, Switch s_enable, final DbTask task)
    {
        s_enable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                long i = task.get_id();
                task.setEnable(isChecked);
                if (task.getEnable() == true) {
                    enableTask(parent, task);
                }
                else
                {
                    cancelTask(parent, task);
                }
            }
        });
    }
    protected void updateTask(DbTask task)
    {
            tasksDb.updateTask(task);
    }
    protected void enableTask(View view, DbTask task)
    {
        task.updateNextWakeTime();
        updateTask(task);
        if (DbTaskScheduler.setAlarm(view.getContext(), task)) {        //
            Snackbar.make(view, task.getNextWakeTimeString() + "\n" +
                    task.getTask().getCommandName() + ": " + task.getVehicleName(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            //Toast.makeText(view.getContext(), "Command Set:\n" + task.getNextWakeTimeString() + "\n" +
            //        "Set " + userConfig.getSelectedVehicleDisplayName() + " to " + getPercent() + "%", Toast.LENGTH_LONG).show();
        } else {
            Snackbar.make(view, "Failed to set command", Snackbar.LENGTH_LONG).show();
        }
    }
    protected void cancelTask(View view, DbTask task) {
        updateTask(task);
        DbTaskScheduler.cancelAlarm(view.getContext(), task);
        Snackbar.make(view, "Command Canceled", Snackbar.LENGTH_LONG).show();
    }
}