package com.sshtukin.todolist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskBaseHelper extends SQLiteOpenHelper {

    public static final String UUID = "uuid";
    public static final String TITLE = "title";
    public static final String ADDITIONAL = "additional";
    public static final String DATE = "date";
    public static final String IS_DATE_ENABLED = "is_date_enabled";
    public static final String IS_DONE = "is_done";

    private static final String DATABASE_NAME = "tasksBase.db";
    public static final String TABLE_NAME = "tasks";
    private static final int VERSION = 1;

    public TaskBaseHelper(Context context) {
        super(context, DATABASE_NAME, null , VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(" +
                " _id integer primary key autoincrement, " +
                UUID + ", " +
                TITLE + ", " +
                ADDITIONAL + ", " +
                DATE + ", " +
                IS_DATE_ENABLED + ", " +
                IS_DONE + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
