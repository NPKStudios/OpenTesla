package com.opentesla.android.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.opentesla.android.database.TasksContract.*;

/**
 * Created by Nick on 11/4/2016.
 */

public class TasksDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Tasks.db";


    private static final String TYPE_TEXT = " TEXT"; //Text
    private static final String TYPE_INT = " INTEGER"; //Ints
    private static final String TYPE_REAL = " REAL"; // Floats
    private static final String TYPE_NULL = " NULL"; // Floats
    private static final String TYPE_BLOB = " BLOB"; // Floats
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TaskEntry.TABLE_NAME + " (" +
                    TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 1" + COMMA_SEP +
                    //TaskEntry._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                    TaskEntry.COLUMN_NAME_LABEL + TYPE_TEXT + COMMA_SEP +
                    TaskEntry.COLUMN_NAME_TASK_TYPE + TYPE_TEXT + COMMA_SEP +
                    TaskEntry.COLUMN_NAME_NEXT_WAKE_TIME + TYPE_INT + COMMA_SEP +
                    TaskEntry.COLUMN_NAME_SCHEDULE_TIME + TYPE_INT + COMMA_SEP +
                    TaskEntry.COLUMN_NAME_WEEK_DAY_FLAG + TYPE_INT + COMMA_SEP +
                    TaskEntry.COLUMN_NAME_VIBRATE + TYPE_INT + COMMA_SEP +
                    TaskEntry.COLUMN_NAME_REPEAT + TYPE_INT + COMMA_SEP +
                    TaskEntry.COLUMN_NAME_VEHICLE_ID + TYPE_INT + COMMA_SEP +
                    TaskEntry.COLUMN_NAME_VEHICLE_NAME + TYPE_TEXT + COMMA_SEP +
                    TaskEntry.COLUMN_NAME_ENABLE + TYPE_INT + COMMA_SEP +
                    TaskEntry.COLUMN_NAME_TASK_CLASS + TYPE_BLOB + " )";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " +  TaskEntry.TABLE_NAME;

    public TasksDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
