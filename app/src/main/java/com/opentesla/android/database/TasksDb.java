package com.opentesla.android.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.opentesla.tesla.requests.VehicleJsonPost;
import com.opentesla.android.database.TasksContract.*;

import java.util.ArrayList;

/**
 * Created by Nick on 11/7/2016.
 */

public class TasksDb {
    private static final String TAG = TasksDb.class.getSimpleName();
    private TasksDbHelper mDbHelper;
    private String[] projection = {
            TaskEntry._ID,
            TaskEntry.COLUMN_NAME_LABEL,
            TaskEntry.COLUMN_NAME_NEXT_WAKE_TIME,
            TaskEntry.COLUMN_NAME_SCHEDULE_TIME,
            TaskEntry.COLUMN_NAME_WEEK_DAY_FLAG,
            TaskEntry.COLUMN_NAME_VIBRATE,
            TaskEntry.COLUMN_NAME_REPEAT,
            TaskEntry.COLUMN_NAME_TASK_CLASS,
            TaskEntry.COLUMN_NAME_TASK_TYPE,
            TaskEntry.COLUMN_NAME_VEHICLE_ID,
            TaskEntry.COLUMN_NAME_VEHICLE_NAME,
            TaskEntry.COLUMN_NAME_ENABLE
    };

    public TasksDb(Context context)
    {
        mDbHelper = new TasksDbHelper(context);
    }
    public void ResetDatabase()
    {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        mDbHelper.onUpgrade(db, TasksDbHelper.DATABASE_VERSION, TasksDbHelper.DATABASE_VERSION);
        db.close();
    }

    public TasksDbHelper getDbHelper()
    {
        return mDbHelper;
    }
    public void close()
    {
        mDbHelper.close();
    }
    public int getTasksCount(long vehicle_id)
    {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        int rowCount = getVehicleRows(db, vehicle_id).getCount();
        db.close();
        return rowCount;
    }
    public ArrayList<DbTask> getEnabledTasks()
    {
        ArrayList<DbTask> tasks = new ArrayList<DbTask>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        try {
            db.beginTransaction();
            Cursor c = getEnabledRows(db);
            c.moveToFirst();
            do {
                int count = c.getCount();
                if (count > 0) {
                    tasks.add(new DbTask(c));
                }
            } while (c.moveToNext());
        }
        catch(Exception e)
        {
            Log.e(TAG, e.getLocalizedMessage());
        }
        finally {
            db.endTransaction();
            db.close();
        }
        return tasks;
    }
    public ArrayList<DbTask> getTasks(long vehicle_id, String type)
    {
        ArrayList<DbTask> tasks = new ArrayList<DbTask>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        try {
            db.beginTransaction();
            Cursor c = getVehicleRows(db, vehicle_id, type);
            c.moveToFirst();
            do {
                int count = c.getCount();
                if (count > 0) {
                    tasks.add(new DbTask(c));
                }
            } while (c.moveToNext());
        }
        catch(Exception e)
        {
            Log.e(TAG, e.getLocalizedMessage());
        }
        finally {
            db.endTransaction();
            db.close();
        }
        return tasks;
    }
    public ArrayList<DbTask> getTasks(long vehicle_id)
    {
        ArrayList<DbTask> tasks = new ArrayList<DbTask>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        try {
            db.beginTransaction();
            Cursor c = getVehicleRows(db, vehicle_id);
            c.moveToFirst();
            do {
                int count = c.getCount();
                if (count > 0) {
                    tasks.add(new DbTask(c));
                }
            } while (c.moveToNext());
        }
        catch(Exception e)
        {
            Log.e(TAG, e.getLocalizedMessage());
        }
        finally {
            db.endTransaction();
            db.close();
        }
        return tasks;
    }

    public boolean updateTask(DbTask task)
    {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

// New value for one column
        ContentValues values = task.getContentValues();

// Which row to update, based on the title
        String selection = TaskEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(task.get_id()) };

        int count = db.update(
                TaskEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count > 0;
    }

    public long addTask(DbTask task)
    {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = task.getContentValues();
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TaskEntry.TABLE_NAME, null, values);
        task.set_id(newRowId);
        return newRowId;
    }


    public DbTask createTask(long vehicle_id, String vehicle_name, VehicleJsonPost post)
    {
        DbTask task = new DbTask(vehicle_id, vehicle_name, post);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        try {
// Create a new map of values, where column names are the keys
            ContentValues values = task.getContentValues();
// Insert the new row, returning the primary key value of the new row
            long newRowId = db.insertOrThrow(TaskEntry.TABLE_NAME, null, values);
            task.set_id(newRowId);
        }catch (Exception e)
        {
            Log.e(TAG,e.getLocalizedMessage());
        }
        finally {
            db.close();
        }
        return task;
    }

    public Cursor getVehicleRows(SQLiteDatabase db, long vehicle_id)
    {

// Filter results WHERE "title" = 'My Title'
        String selection = TaskEntry.COLUMN_NAME_VEHICLE_ID + " = ?";
        String[] selectionArgs = { Long.toString(vehicle_id) };

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                TaskEntry._ID + " DESC";

        Cursor c = db.query(
                TaskEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        return c;
    }
    public Cursor getVehicleRows(SQLiteDatabase db, long vehicle_id, String type)
    {

// Filter results WHERE "title" = 'My Title'
        String selection = TaskEntry.COLUMN_NAME_VEHICLE_ID + " = ?" + "AND " +
                TaskEntry.COLUMN_NAME_TASK_TYPE + " = ?";
        String[] selectionArgs = { Long.toString(vehicle_id) , type};

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                TaskEntry._ID + " DESC";

        Cursor c = db.query(
                TaskEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        return c;
    }

    public Cursor getEnabledRows(SQLiteDatabase db)
    {

// Filter results WHERE "title" = 'My Title'
        String selection = TaskEntry.COLUMN_NAME_ENABLE + " = ?";
        String[] selectionArgs = { Integer.toString(DbTask.TRUE) };

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                TaskEntry._ID + " DESC";

        Cursor c = db.query(
                TaskEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        return c;
    }

    public DbTask getTask(long id)
    {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        try {

            Cursor c = getRow(db, id);
            if (c != null) {
                c.moveToFirst();
                do {
                    if (c.getCount() > 0) {
                        return new DbTask(c);
                    }
                } while (c.moveToNext());
            }
        }
        catch (Exception e)
        {
            Log.e(TAG,e.getLocalizedMessage());
        }
        finally {
            db.close();
        }
        return null;
    }

    public Cursor getRow(SQLiteDatabase db, long id)
    {
// Define a projection that specifies which columns from the database
// you will actually use after this query.


// Filter results WHERE "title" = 'My Title'
        String selection = TaskEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                TaskEntry._ID + " DESC";

        Cursor c = db.query(
                TaskEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        return c;
    }

    public boolean delete(long id) {
        int result = 0;
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        if (db == null) {
            return false;
        }
        result = db.delete(TaskEntry.TABLE_NAME, "_id = ?", new String[] { String.valueOf(id) });
        db.close();
        return result > 0;
    }
    public int deleteRow(long row_id)
    {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        // Define 'where' part of query.
        String selection = TaskEntry._ID + " LIKE ?";
// Specify arguments in placeholder order.
        String[] selectionArgs = { Long.toString(row_id) };
// Issue SQL statement.
        return db.delete(TaskEntry.TABLE_NAME, selection, selectionArgs);
        //return db.delete(TaskEntry.TABLE_NAME, selection, selectionArgs);



    }
    public boolean deleteTask(DbTask task) {
        return delete(task.get_id());
    }
    public int updateRow(int id, String label)
    {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(TaskEntry.COLUMN_NAME_LABEL, label);

// Which row to update, based on the title
        String selection = TaskEntry._ID + " LIKE ?";
        String[] selectionArgs = { id + "" };

        int count = db.update(
                TaskEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }
}
