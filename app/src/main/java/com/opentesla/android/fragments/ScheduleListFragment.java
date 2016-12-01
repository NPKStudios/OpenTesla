package com.opentesla.android.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.opentesla.android.MySharedPreferences;
import com.opentesla.android.R;
import com.opentesla.android.TaskAdapter;
import com.opentesla.android.UserConfig;
import com.opentesla.android.database.DbTask;
import com.opentesla.android.database.TasksDb;
import com.opentesla.tesla.requests.vehiclecommands.SetChargeLimitRequest;
import com.opentesla.tesla.requests.vehiclecommands.StartHVACRequest;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScheduleListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScheduleListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleListFragment extends Fragment {
    private static final String TAG = ScheduleListFragment.class.getSimpleName();

    private static final String TITLE = "Schedule";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private UserConfig userConfig;
    private SharedPreferences mSharedPreferences;
    private ArrayList<DbTask> taskList;
    private TasksDb mTasksDb;

    private ListView listview;
    private TaskAdapter taskAdapter;

    private OnFragmentInteractionListener mListener;

    public ScheduleListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ScheduleListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScheduleListFragment newInstance() {
        ScheduleListFragment fragment = new ScheduleListFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_schedule_list, container, false);
        getActivity().setTitle(TITLE);
        // Get listivew from main layout
        listview = (ListView) v.findViewById(R.id.lv_db_items);

        // Add alarm to display in alarmlist
        taskList = getTasks(v.getContext(), mTasksDb);
        // Create alarm listview adapter with current context (this) and alarmlist
        taskAdapter = new TaskAdapter(v.getContext(), taskList, mTasksDb);
        setup_fab(v,taskAdapter);
        // Set previous adapter on listview
        listview.setAdapter(taskAdapter);
        return v;
    }
    public void addTask(View v) {
        DbTask newTask = mTasksDb.createTask(userConfig.getSelectedVehicleId(), userConfig.getSelectedVehicleDisplayName(),
                new StartHVACRequest(userConfig.getSelectedVehicleId(), userConfig.getSelectedVehicleDisplayName()));
        taskAdapter.addTask(newTask);
    }
    private void setup_fab(View v, final TaskAdapter adapter)
    {
        FloatingActionButton fab = (FloatingActionButton)v.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbTask newTask = mTasksDb.createTask(userConfig.getSelectedVehicleId(), userConfig.getSelectedVehicleDisplayName(),
                        new StartHVACRequest(userConfig.getSelectedVehicleId(), userConfig.getSelectedVehicleDisplayName()));
                adapter.addTask(newTask);
            }
        });
    }

    public ArrayList<DbTask> getTasks(Context context, TasksDb tasksDb)
    {
        mSharedPreferences = MySharedPreferences.getSharedPreferences(context);
        userConfig = new UserConfig(mSharedPreferences);

        try {
            //Create the database
            if(userConfig.getSelectedVehicleId() != 0) {
                taskList = tasksDb.getTasks(userConfig.getSelectedVehicleId());
                if(taskList.size() < 1)
                {
                    DbTask newTask = tasksDb.createTask(userConfig.getSelectedVehicleId(), userConfig.getSelectedVehicleDisplayName(),
                            new
                                    SetChargeLimitRequest(userConfig.getSelectedVehicleId(), userConfig.getSelectedVehicleDisplayName() ,80));

                    taskList = tasksDb.getTasks(userConfig.getSelectedVehicleId());
                }
            }
        }
        catch(Exception e)
        {
            Log.e(TAG, e.getLocalizedMessage());
        }
        return taskList;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mTasksDb = new TasksDb(context);
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
        mListener = null;
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
