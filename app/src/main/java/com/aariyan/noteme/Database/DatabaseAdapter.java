package com.aariyan.noteme.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


import com.aariyan.noteme.Model.TaskModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAdapter {

    DatabaseHelper helper;
    //list of task:
    private List<TaskModel> listOfTask = new ArrayList<>();


    public DatabaseAdapter(Context context) {
        helper = new DatabaseHelper(context);
    }

    //Inserting task
    public long insertTask(String taskName, String taskCreatedDate, String taskDeadline, String taskStatus, String description, String email, String phone, String url) {

        SQLiteDatabase database = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.TASK_NAME, taskName);
        contentValues.put(DatabaseHelper.TASK_CREATED_DATE, taskCreatedDate);
        contentValues.put(DatabaseHelper.TASK_DEADLINE, taskDeadline);
        contentValues.put(DatabaseHelper.TASK_STATUS, taskStatus);
        contentValues.put(DatabaseHelper.TASK_DESCRIPTION, description);
        contentValues.put(DatabaseHelper.TASK_EMAIL, email);
        contentValues.put(DatabaseHelper.TASK_PHONE, phone);
        contentValues.put(DatabaseHelper.TASK_URL, url);
        long id = database.insert(DatabaseHelper.TABLE_NAME, null, contentValues);
        return id;
    }


    //getting the open task
    public List<TaskModel> getOpenTask(String status) {
        //listOfOpenTask.clear();
        listOfTask.clear();
        SQLiteDatabase database = helper.getWritableDatabase();
        String[] columns = {DatabaseHelper.UID,
                DatabaseHelper.TASK_NAME, DatabaseHelper.TASK_CREATED_DATE, DatabaseHelper.TASK_DEADLINE,
                DatabaseHelper.TASK_STATUS, DatabaseHelper.TASK_DESCRIPTION, DatabaseHelper.TASK_EMAIL,
                DatabaseHelper.TASK_PHONE, DatabaseHelper.TASK_URL
        };
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, DatabaseHelper.TASK_STATUS + " = '" + status + "'", null, null, null, null);

        while (cursor.moveToNext()) {

            TaskModel model = new TaskModel(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8)
            );
            listOfTask.add(model);
        }

        return listOfTask;
    }

    //for updating the task by id
    public int updateTask(String id, String taskName, String taskDeadline, String taskStatus,
                          String description, String email, String phone, String url) {

        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.TASK_NAME, taskName);
        contentValues.put(DatabaseHelper.TASK_DEADLINE, taskDeadline);
        contentValues.put(DatabaseHelper.TASK_STATUS, taskStatus);
        contentValues.put(DatabaseHelper.TASK_DESCRIPTION, description);
        contentValues.put(DatabaseHelper.TASK_EMAIL, email);
        contentValues.put(DatabaseHelper.TASK_PHONE, phone);
        contentValues.put(DatabaseHelper.TASK_URL, url);

        String[] args = {id};

        int count = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper.UID + " =? ", args);
        return count;
    }


    //for deleting a task by id
    public int deleteTask(String id) {
        SQLiteDatabase database = helper.getWritableDatabase();
        String[] args = {id};
        int count = database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.UID + " =? ", args);
        return count;
    }


    class DatabaseHelper extends SQLiteOpenHelper {

        private Context context;
        private static final String DATABASE_NAME = "NoteMe.db";
        private static final int VERSION_NUMBER = 1;

        //Task Table:
        private static final String TABLE_NAME = "Task";

        private static final String UID = "id";
        private static final String TASK_NAME = "TaskName";
        private static final String TASK_CREATED_DATE = "TaskCreatedDate";
        private static final String TASK_DEADLINE = "TaskDeadline";
        private static final String TASK_STATUS = "TaskStatus";
        private static final String TASK_DESCRIPTION = "TaskDescription";
        private static final String TASK_EMAIL = "TaskEmail";
        private static final String TASK_PHONE = "TaskPhone";
        private static final String TASK_URL = "TaskUrl";

        //creating the table with entity
        private static final String CREATE_TASK_TABLE = "CREATE TABLE " + TABLE_NAME
                + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TASK_NAME + " VARCHAR(255),"
                + TASK_CREATED_DATE + " VARCHAR(255),"
                + TASK_DEADLINE + " VARCHAR(255),"
                + TASK_STATUS + " VARCHAR(255),"
                + TASK_DESCRIPTION + " VARCHAR(255),"
                + TASK_EMAIL + " VARCHAR(255),"
                + TASK_PHONE + " VARCHAR(255),"
                + TASK_URL + " VARCHAR(255));";

        //drop the table if needed
        private static final String DROP_TASK_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;


        public DatabaseHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, VERSION_NUMBER);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //Create table:
            try {
                db.execSQL(CREATE_TASK_TABLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            try {
                db.execSQL(DROP_TASK_TABLE);
                onCreate(db);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
