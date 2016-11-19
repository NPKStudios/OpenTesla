package com.opentesla.android.database;

import android.provider.BaseColumns;

/**
 * Created by Nick on 11/4/2016.
 */

public final class TasksContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.

    private TasksContract() {
    }

    /* Inner class that defines the table contents */
    public static class TaskEntry implements BaseColumns {

        public static final String TABLE_NAME = "tasks";

        public static final String COLUMN_NAME_LABEL = "label";
        public static final String COLUMN_NAME_NEXT_WAKE_TIME = "next_wake_time";
        public static final String COLUMN_NAME_SCHEDULE_TIME = "schedule_time";
        public static final String COLUMN_NAME_WEEK_DAY_FLAG = "week_days_flag";
        public static final String COLUMN_NAME_VIBRATE = "vibrate";
        public static final String COLUMN_NAME_REPEAT = "repeat";
        public static final String COLUMN_NAME_TASK_CLASS = "task_class";
        public static final String COLUMN_NAME_TASK_TYPE = "type";
        public static final String COLUMN_NAME_VEHICLE_ID = "vehicle_id";
        public static final String COLUMN_NAME_VEHICLE_NAME = "vehicle_name";
        public static final String COLUMN_NAME_ENABLE = "enable";

        public static final int SUNDAY = 1;
        public static final int MONDAY = SUNDAY * 2;
        public static final int TUESDAY = MONDAY * 2;
        public static final int WEDNESDAY = TUESDAY * 2;
        public static final int THURSDAY = WEDNESDAY * 2;
        public static final int FRIDAY = THURSDAY * 2;
        public static final int SATURDAY = FRIDAY * 2;


        private static final String SQL_DELETE_TASKS =
                "DROP TABLE IF EXISTS " + TaskEntry.TABLE_NAME;
    }
}

