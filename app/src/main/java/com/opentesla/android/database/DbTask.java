package com.opentesla.android.database;

import android.content.ContentValues;
import android.database.Cursor;
import com.opentesla.android.database.TasksContract.TaskEntry;
import com.opentesla.tesla.requests.VehicleJsonPost;
import java.util.Calendar;

/**
 * Created by Nick on 11/4/2016.
 */

public class DbTask {
    public static final int TRUE = 1;
    public static final int FALSE = 0;
    public static final String DEFAULT_LABEL = "Label";
    public static final int DB_TASK_ID_START = 3000;
    public static final int NUM_CURSOR_LINES = 11;
    /*
    public static final String COLUMN_NAME_LABEL = "label";
        public static final String COLUMN_NAME_NEXT_WAKE_TIME = "next_wake_time";
        public static final String COLUMN_EPOC_TIME = "schedule_time";
        public static final String COLUMN_NAME_WEEK_DAY_FLAG = "week_days_flag";
        public static final String COLUMN_NAME_VIBRATE = "vibrate";
        public static final String COLUMN_NAME_REPEAT = "repeat";
        public static final String COLUMN_NAME_TASK_CLASS = "mTask_class";
        public static final String COLUMN_NAME_TASK_TYPE = "type";
        public static final String COLUMN_NAME_VEHICLE_ID = "vehicle_id";

     */
    private long m_id;
    private String mlabel;
    private long mNext_wake_time;
    private long mSchedule_time;
    private int mWeek_days_flag;
    private boolean mVibrate;
    private boolean mRepeat;
    private VehicleJsonPost mTask_class;
    private String mType;
    private long mVehicle_id;
    private String mVehicle_name;
    private boolean mEnable;

    public DbTask()
    {
        m_id = 0;
        mlabel = DEFAULT_LABEL;
        mNext_wake_time = 0;
        mSchedule_time = 0;
        mWeek_days_flag = 0;
        mVibrate = false;
        mRepeat = false;
        mTask_class = null;
        mType = "";
        mVehicle_id = 0;
        mVehicle_name = "";
        mEnable = false;
    }
    public DbTask(long vehicle_id, String vehicle_name, VehicleJsonPost post)
    {
        m_id = 0;
        mlabel = DEFAULT_LABEL;
        mNext_wake_time = 0;
        mSchedule_time = System.currentTimeMillis();
        mWeek_days_flag = 0;
        mVibrate = false;
        mRepeat = false;
        mTask_class = post;
        mType = post.getClass().getSimpleName();
        mVehicle_id = vehicle_id;
        mVehicle_name = vehicle_name;
        mEnable = false;
    }

    public DbTask(String label, long next_wake_time,
                  long schedule_time,  int week_days_flag,
                  boolean vibrate, boolean repeat,
                  VehicleJsonPost post, long vehicle_id,
                  String vehicle_name, boolean enable)
    {
        mlabel = label;
        mNext_wake_time = next_wake_time;
        mSchedule_time = schedule_time;
        mWeek_days_flag = week_days_flag;
        mVibrate = vibrate;
        mRepeat = repeat;
        mTask_class = post;
        mVehicle_id = vehicle_id;
        mVehicle_name = vehicle_name;
        mEnable = enable;
        mType = post.getClass().getSimpleName();
    }

    public VehicleJsonPost getTask()
    {
        return this.mTask_class;
    }
    public void setTask(VehicleJsonPost task)
    {
        mType = task.getClass().getSimpleName();
        mTask_class = task;

    }

    public DbTask(Cursor c)
    {
        m_id = c.getLong(c.getColumnIndexOrThrow(TaskEntry._ID ));
        mlabel = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_LABEL ));
        mNext_wake_time = c.getLong(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_NEXT_WAKE_TIME));
        mSchedule_time = c.getLong(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_SCHEDULE_TIME));
        mWeek_days_flag = c.getInt(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_WEEK_DAY_FLAG ));
        mVibrate = intToBool(c.getInt(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_VIBRATE )));
        mRepeat = intToBool(c.getInt(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_REPEAT )));
        mVehicle_id = c.getLong(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_VEHICLE_ID ));
        mVehicle_name = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_VEHICLE_NAME ));
        mEnable = intToBool(c.getInt(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_ENABLE )));
        mTask_class = (VehicleJsonPost)ConvertObject.getJavaObject(c.getBlob(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_TASK_CLASS)));
        mType = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_TASK_TYPE));
    }
    public long get_id()
    {
        return m_id;
    }
    public void set_id(long id)
    {
        m_id = id;
    }
    public String get_label()
    {
        return mlabel;
    }
    public void set_label(String new_label)
    {
        mlabel = new_label;
    }
    public ContentValues getContentValues()
    {
        ContentValues values = new ContentValues();
        values.put(TaskEntry.COLUMN_NAME_LABEL, mlabel);
        values.put(TaskEntry.COLUMN_NAME_NEXT_WAKE_TIME, mNext_wake_time);
        values.put(TaskEntry.COLUMN_NAME_SCHEDULE_TIME, mSchedule_time);
        values.put(TaskEntry.COLUMN_NAME_WEEK_DAY_FLAG, mWeek_days_flag);
        values.put(TaskEntry.COLUMN_NAME_VIBRATE, boolToInt(mVibrate));
        values.put(TaskEntry.COLUMN_NAME_REPEAT, boolToInt(mRepeat));
        values.put(TaskEntry.COLUMN_NAME_VEHICLE_ID, mVehicle_id);
        values.put(TaskEntry.COLUMN_NAME_VEHICLE_NAME, mVehicle_name);
        values.put(TaskEntry.COLUMN_NAME_ENABLE, boolToInt(mEnable));
        values.put(TaskEntry.COLUMN_NAME_TASK_CLASS, ConvertObject.getByteArrayObject(mTask_class));
        values.put(TaskEntry.COLUMN_NAME_TASK_TYPE, mTask_class.getClass().getSimpleName());
        return values;
    }
    public void setEnable(boolean bool)
    {
        mEnable = bool;
    }
    public boolean getEnable()
    {
        return mEnable;
    }
    public void setRepeat(boolean bool)
    {
        mRepeat = bool;
    }
    public boolean getRepeat()
    {
        return mRepeat;
    }
    public String getVehicleName()
    {
        return mVehicle_name;
    }
    public Calendar getScheduleCalendar()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mSchedule_time);
        return calendar;
    }
    public int getScheduleHour_24()
    {
        return getScheduleCalendar().get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
    }
    public int getScheduleHour_12()
    {
        return getScheduleCalendar().get(Calendar.HOUR);        // gets hour in 12h format;
    }
    public int getScheduleMinute()
    {
        return getScheduleCalendar().get(Calendar.MINUTE);        // gets hour in 12h format;
    }
    public int getScheduleSecond()
    {
        return getScheduleCalendar().get(Calendar.SECOND);        // gets hour in 12h format;
    }
    public int getScheduleMillisecond()
    {
        return getScheduleCalendar().get(Calendar.MILLISECOND);        // gets hour in 12h format;
    }
    public long getNext_wake_time()
    {
        return mNext_wake_time;
    }
    public long setNextWakeTime(long milliseconds)
    {
        mNext_wake_time = milliseconds;
        return mNext_wake_time;
    }
    public long updateNextWakeTime() {
        int hour = getScheduleHour_24();
        int minute = getScheduleMinute();
        int second = getScheduleSecond();
        int millisecond = getScheduleMillisecond();
        return updateNextWakeTime(hour, minute, second, millisecond);
    }
    public void setScheduledTime(int hour24, int minute, int second, int millisecond)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.HOUR_OF_DAY, hour24);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.MILLISECOND, millisecond);

        mSchedule_time = calendar.getTimeInMillis();
        updateNextWakeTime(hour24,minute,second,millisecond);
    }
    private long updateNextWakeTime(int hour, int minute, int second, int millisecond)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.MILLISECOND, millisecond);

        if(System.currentTimeMillis() > calendar.getTimeInMillis())
        {
            calendar.add(Calendar.DATE, 1);
        }
        mNext_wake_time = calendar.getTimeInMillis();
        return  mNext_wake_time;
    }
    public long getNextWakeTime()
    {
        return mNext_wake_time;
    }

    public String getNextWakeTimeString()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mNext_wake_time);
        return calendar.getTime().toString();
    }

    private boolean intToBool(int i)
    {
        if(i == FALSE) {
            return false;
        }
        else {
            return true;
        }
    }

    private int boolToInt(boolean b)
    {
        if(b == true) {
            return TRUE;
        }
        else {
            return FALSE;
        }
    }
}
