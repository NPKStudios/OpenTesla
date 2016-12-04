package com.opentesla.android.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;

import com.opentesla.android.TaskAdapter;
import com.opentesla.android.database.DbTask;
import com.opentesla.tesla.requests.vehiclecommands.DoorLockRequest;
import com.opentesla.tesla.requests.vehiclecommands.DoorUnlockRequest;
import com.opentesla.tesla.requests.vehiclecommands.SetChargeLimitRequest;
import com.opentesla.tesla.requests.vehiclecommands.StartHVACRequest;
import com.opentesla.tesla.requests.vehiclecommands.StopHVACRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick on 11/29/2016.
 */

public class CreateTaskDialog extends AlertDialog.Builder  {

    private static final int TASK_CHARGE = 0;
    private static final int TASK_HVAC_ON = 1;
    private static final int TASK_HVAC_OFF = 2;
    private static final int TASK_UNLOCK = 3;
    private static final int TASK_LOCK = 4;

    private long mVehicleId;
    private String mVehicleName;
    private Dialog mSelectDialog;
    private DbTask mTask;
    private View mView;
    private TaskAdapter.TaskManagementCaller mTaskManagementCaller;
    public CreateTaskDialog(View view, DbTask task, TaskAdapter.TaskManagementCaller taskManager) {
        super(view.getContext());
        this.mView = view;
        this.mTask = task;
        this.mVehicleId = mTask.getVehicleId();
        this.mVehicleName = mTask.getVehicleName();
        this.mTaskManagementCaller = taskManager;
        setTitle("Select Command");
        ListView modeList = new ListView(mView.getContext());
        final ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(mView.getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, get_task_list());

        modeList.setAdapter(modeAdapter);


        setView(modeList);

        setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        modeList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {
                switch (position) {
                    case TASK_CHARGE:
                        show_charge_dialog(mView ,mTask);
                        break;
                    case TASK_HVAC_ON:
                        mTask.setTask(new StartHVACRequest(mVehicleId, mVehicleName));
                        break;
                    case TASK_HVAC_OFF:
                        mTask.setTask(new StopHVACRequest(mVehicleId, mVehicleName));
                        break;
                    case TASK_UNLOCK:
                        mTask.setTask(new DoorUnlockRequest(mVehicleId, mVehicleName));
                        break;
                    case TASK_LOCK:
                        mTask.setTask(new DoorLockRequest(mVehicleId, mVehicleName));
                        break;
                    default:
                        break;
                }
                updateTask(mTask);
                mSelectDialog.dismiss();
            }
        });

        mSelectDialog = this.create();
    }

    @Override
    public AlertDialog show()
    {
        mSelectDialog = super.show();
        return (AlertDialog) mSelectDialog;
    }
    private List<String> get_task_list()
    {
        List<String> l=new ArrayList<String>();
        l.add(SetChargeLimitRequest.CMD_NAME);
        l.add(StartHVACRequest.CMD_NAME);
        l.add(StopHVACRequest.CMD_NAME);
        l.add(DoorUnlockRequest.CMD_NAME);
        l.add(DoorLockRequest.CMD_NAME);
        return l;
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
                task.setTask(new SetChargeLimitRequest(task.getVehicleId(),task.getVehicleName(), sb_percent.getProgress()*10));
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
    private void updateTask(DbTask task)
    {
        mTaskManagementCaller.updateTask(task);
    }
}