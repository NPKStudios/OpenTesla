package com.opentesla.android;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.opentesla.android.database.DbTask;
import com.opentesla.android.database.TasksDb;
import com.opentesla.tesla.requests.VehicleJsonPost;
import com.opentesla.tesla.requests.vehiclecommands.SetChargeLimitRequest;
import com.opentesla.tesla.requests.vehiclecommands.StartHVACRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * Created by Nick on 11/25/2016.
 */
public class TaskAdapter extends BaseAdapter {
    private static final int TASK_CHARGE = 0;
    private static final int TASK_HVAC_ON = 1;

    Context context;
    ArrayList<DbTask> tasks;
    TasksDb tasksDb;
    private static LayoutInflater inflater = null;
    List<String> list;

    public TaskAdapter(Context context, ArrayList<DbTask>  tasks, TasksDb tasksDb) {
        // TODO Auto-generated constructor stub
        super();
        this.context = context;
        this.tasks = tasks;
        this.tasksDb = tasksDb;

        Comparator<DbTask> myComparator = new Comparator<DbTask>() {
            public int compare(DbTask obj1,DbTask obj2) {
                return (int)(obj1.get_id() - obj2.get_id());
            }
        };
        Collections.sort(tasks, myComparator);

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
        list = get_task_list();

        if (rootView == null)
            rootView = inflater.inflate(R.layout.task_item, parent, false);
        TextView tv_time = (TextView) rootView.findViewById(R.id.tv_time);
        Switch s_enable = (Switch) rootView.findViewById(R.id.s_enable);
        final TextView tv_label = (TextView) rootView.findViewById(R.id.et_label);
        Spinner spinner_tasks = (Spinner) rootView.findViewById(R.id.spinner_tasks);
        Button btn_delete = (Button) rootView.findViewById(R.id.btn_delete);


        s_enable.setChecked(task.getEnable());
        tv_label.setText(task.get_label());

        //TODO: Not a fan of this method. needs fixed
        setup_delete_button(btn_delete,task);
        setup_time(parent,tv_time,task,false);
        setup_enable_button(parent, s_enable, task);
        setup_label(parent, tv_label, task);
        setup_spinner_tasks(parent, spinner_tasks, task, list);



        /*
        TextView alarmClockName= (TextView) rootView.findViewById(R.id.alarm_clock_name);
        ImageView clockIcon = (ImageView ) rootView.findViewById(R.id.alarm_clock_name);

        // Set your data there

        clockIcon.setImageResource(iconRes);
        alarmClockName.setText(alarmData.getName());
        */

        return rootView;
    }
    protected List<String> get_task_list()
    {
        List<String> l=new ArrayList<String>();
        l.add(SetChargeLimitRequest.CMD_NAME);
        l.add(StartHVACRequest.CMD_NAME);
        return l;
    }
    protected void setup_spinner_tasks(View v, Spinner spinner, final DbTask task, List<String> spinnerList)
    {
        ArrayAdapter<String> adp= new ArrayAdapter<String>(v.getContext() ,android.R.layout.simple_list_item_1,spinnerList);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adp);

        VehicleJsonPost vehiclePost = task.getTask();
        if(vehiclePost != null) {
            String name = vehiclePost.getCommandName();
            int index = spinnerList.indexOf(name);
            if (index != -1) {
                //TODO: Fix spinner bug where select gets fired on start or layout is incorrect because it does not animate
                spinner.setSelection(index,false);
            }
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0,
                                       View view,int pos, long arg3) {
                /*
                switch (pos) {
                    case TASK_CHARGE:
                        show_charge_dialog(view,task);
                        updateTask(task);
                        break;
                    case TASK_HVAC_ON:
                        task.setTask(new StartHVACRequest(task.get_id(), task.getVehicleName()));
                        updateTask(task);
                        break;
                    default:
                        break;
                }
                */
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
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
    protected void setup_delete_button(Button button, final DbTask task)
    {
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                deleteTask(task);
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
                        //textView.setText(input.getText());
                        //parent.refreshDrawableState();

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
            TaskAdapter.super.notifyDataSetChanged();
    }
    protected void deleteTask(DbTask task)
    {
        tasksDb.deleteTask(task);
        tasks.remove(task);
        TaskAdapter.super.notifyDataSetChanged();
    }
    public void addTask(DbTask task)
    {
        tasks.add(task);
        TaskAdapter.super.notifyDataSetChanged();
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
    protected  void show_charge_dialog(final View parent, final DbTask task)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
        builder.setTitle(SetChargeLimitRequest.CMD_NAME);

// Set up the input
        final SeekBar sb_percent = new SeekBar(parent.getContext());
        sb_percent.setMax(10);
        sb_percent.setProgress(8);
        sb_percent.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress <= (SetChargeLimitRequest.PERCENT_MIN / 10)) {
                    seekBar.setProgress(SetChargeLimitRequest.PERCENT_MIN / 10);
                } else if (progress >= (SetChargeLimitRequest.PERCENT_MAX / 10)){
                    seekBar.setProgress(SetChargeLimitRequest.PERCENT_MAX / 10);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        builder.setView(sb_percent);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                task.setTask(new SetChargeLimitRequest(task.get_id(),task.getVehicleName(), sb_percent.getProgress()*10));
                updateTask(task);
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
}