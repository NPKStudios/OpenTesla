package com.opentesla.android.fragments;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.opentesla.tesla.requests.vehiclecommands.SetChargeLimitRequest;
import com.opentesla.android.database.DbTask;
import com.opentesla.android.database.TasksDb;
import com.opentesla.android.DbTaskScheduler;
import com.opentesla.android.MySharedPreferences;
import com.opentesla.android.R;
import com.opentesla.android.UserConfig;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScheduleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = ScheduleFragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TITLE = "Schedule Charging";
    private static final String ARG_PARAM1 = "mOauthToken";
    private static final String ARG_PARAM2 = "mVehicleID";

    // TODO: Rename and change types of parameters
    private String mOauthToken;
    private long mVehicleID;
    private boolean loading;

    private TasksDb mTasksDb;
    private DbTask mTask;

    private AlarmManager alarmManager;
    private SetChargeLimitRequest setChargeLimit;
    private PendingIntent pendingIntent;
    private Intent newIntent;

    private TimePicker timePicker;
    private ToggleButton enableButton;
    private SeekBar sb_percent;

    private UserConfig userConfig;
    private SharedPreferences mSharedPreferences;
    private ArrayList<DbTask> mTasks;

    //private OnFragmentInteractionListener mListener;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param oauthToken Parameter 1.
     * @param vehicleID Parameter 2.
     * @return A new instance of fragment ChargeStatusFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScheduleFragment newInstance(String oauthToken, long vehicleID) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, oauthToken);
        args.putLong(ARG_PARAM2, vehicleID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mOauthToken = getArguments().getString(ARG_PARAM1);
            mVehicleID = getArguments().getLong(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_schedule, container, false);

        getActivity().setTitle(TITLE);

        sb_percent = (SeekBar)v.findViewById(R.id.sb_percent);

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
                if(mTasks != null && loading == false)
                {
                    if(mTask.getEnable() == true && (seekBar.getProgress() * 10) != ((SetChargeLimitRequest) mTask.getTask()).getPercent())
                    {
                        mTask.setEnable(false);
                        mTasksDb.updateTask(mTask);
                        enableButton.setChecked(mTask.getEnable());
                        DbTaskScheduler.cancelAlarm(getActivity(), mTask);
                    }
                }
//                try {
//                    int percent = seekBar.getProgress() * 10;
//                    ((SetChargeLimitRequest) mTask.getTask()).setPercent(percent);
//                    mTasksDb.updateTask(mTask);
//                    //Toast.makeText(seekBar.getContext(), "Charge limit set to: " + percent +  "%", Toast.LENGTH_LONG).show();
//
//                }
//                catch(Exception e)
//                {
//                    Log.e(TAG, e.getLocalizedMessage());
//                }
            }
        });

        timePicker = (TimePicker)v.findViewById(R.id.timePicker);



        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                if(mTasks != null && loading == false)
                {
                    if(mTask.getEnable() == true &&
                            (mTask.getScheduleHour_24() != hourOfDay || mTask.getScheduleMinute() != minute))
                    {
                        mTask.setEnable(false);
                        mTasksDb.updateTask(mTask);
                        enableButton.setChecked(mTask.getEnable());
                        DbTaskScheduler.cancelAlarm(getActivity(), mTask);
                    }
                }
            }
        });

        enableButton = (ToggleButton)v.findViewById(R.id.btn_set);
        enableButton.setOnClickListener(this);

        if(mTask != null) {
            loading = true;
            timePicker.setCurrentHour(mTask.getScheduleHour_24());
            timePicker.setCurrentMinute(mTask.getScheduleMinute());
            enableButton.setChecked(mTask.getEnable());
            try {
                setChargeLimit = (SetChargeLimitRequest) mTask.getTask();
            }catch(Exception e)
            {
                Log.e(TAG,e.getLocalizedMessage());
            }
            if(setChargeLimit != null)
            {
                setPercent(setChargeLimit.getPercent());
            }
            loading = false;
        }
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        //if (mListener != null) {
        //    mListener.onFragmentInteraction(uri);
        //}
    }


    public int getPercent() {
        return sb_percent.getProgress() * 10;
    }
    private void setPercent(int percent) {
        sb_percent.setProgress(percent / 10);
    }

    public void setAlarm(View view, DbTask task)
    {
        setChargeLimit = new SetChargeLimitRequest(userConfig.getSelectedVehicleId(), userConfig.getSelectedVehicleDisplayName(),getPercent());
        task.setTask(setChargeLimit);
        task.setEnable(true);
        timePicker.clearFocus();
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();
        int second = 0;
        int millisecond = 0;
        task.setScheduledTime(hour,minute,second,millisecond);
        //task.updateNextWakeTime();
        mTasksDb.updateTask(task);


        if(DbTaskScheduler.setAlarm(getActivity(), task)) {        //
            Snackbar.make(view, task.getNextWakeTimeString() + "\n" +
                    task.getTask().getCommandName() + ": " + userConfig.getSelectedVehicleDisplayName() + " to " + getPercent() + "%", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            //Toast.makeText(view.getContext(), "Command Set:\n" + task.getNextWakeTimeString() + "\n" +
            //        "Set " + userConfig.getSelectedVehicleDisplayName() + " to " + getPercent() + "%", Toast.LENGTH_LONG).show();
        }
        else
        {
            Snackbar.make(view, "Failed to set command", Snackbar.LENGTH_LONG).show();
        }
        //showMessageBox(view.getContext(), "Alarm Set", calendar.getTime().toString(), "Ok");
    }

    private void cancelAlarm( View view, DbTask task) {
        task.setEnable(false);
        mTasksDb.updateTask(task);
        DbTaskScheduler.cancelAlarm(getActivity(), task);
        Snackbar.make(view, "Command Canceled", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_set:

                //pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, getAlarmIntent(), PendingIntent.FLAG_NO_CREATE);
                //button.isChecked()
                if(enableButton.isChecked())
                {
                    setAlarm(v, mTask);
                }
                else
                {
                    cancelAlarm(v, mTask);
                }
                //setCharge(v);
                break;
        }
    }

    public DbTask createTask()
    {
        /*
        DbTask(String label, long next_wake_time, long schedule_time,
            int week_days_flag, boolean vibrate, boolean repeat,
                  VehicleJsonPost post, long vehicle_id,
                  String vehicle_name, boolean enable)
                  */

        DbTask task = new DbTask("My Task", 0, System.currentTimeMillis(),
                0,false,false,
                new SetChargeLimitRequest(userConfig.getSelectedVehicleId(), userConfig.getSelectedVehicleIdString(),80),
                userConfig.getSelectedVehicleId(), userConfig.getSelectedVehicleDisplayName(), false)  ;
        return task;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mSharedPreferences = MySharedPreferences.getSharedPreferences(context);
        userConfig = new UserConfig(mSharedPreferences);

        try {
            //Create the database
            mTasksDb = new TasksDb(context);
            if(userConfig.getSelectedVehicleId() != 0) {
                mTasks = mTasksDb.getTasks(userConfig.getSelectedVehicleId(), SetChargeLimitRequest.class.getSimpleName());
                if(mTasks.size() < 1)
                {
                    DbTask newTask = mTasksDb.createTask(userConfig.getSelectedVehicleId(), userConfig.getSelectedVehicleDisplayName(),
                            new
                                    SetChargeLimitRequest(userConfig.getSelectedVehicleId(), userConfig.getSelectedVehicleDisplayName() ,80));

                    mTasks = mTasksDb.getTasks(userConfig.getSelectedVehicleId());
                }
            }
            mTask = mTasks.get(0);

        }
        catch(Exception e)
        {
            Log.e(TAG, e.getLocalizedMessage());
        }
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
