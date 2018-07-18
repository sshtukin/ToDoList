package com.serysht.todolist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.serysht.todolist.database.TaskBaseHelper;
import com.serysht.todolist.database.TaskCursorWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.serysht.todolist.database.TaskValuesWrapper.getContentValues;

public class TaskManager {
    private static TaskManager mTaskManager;
    private SQLiteDatabase mDatabase;
    private List mTaskList;

    private TaskManager(Context context) {
        mDatabase = new TaskBaseHelper(context).getWritableDatabase();
    }

    public static TaskManager get(Context context) {
        if (mTaskManager == null) {
            mTaskManager = new TaskManager(context);
        }
        return mTaskManager;
    }


    private TaskCursorWrapper queryTasks(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                TaskBaseHelper.TABLE_NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new TaskCursorWrapper(cursor);
    }

    public void addTask(Task task){
        mDatabase.insert(TaskBaseHelper.TABLE_NAME, null, getContentValues(task));
    }

    public void updateTask(Task task){
        mDatabase.update(TaskBaseHelper.TABLE_NAME,
                getContentValues(task),
                TaskBaseHelper.UUID + " =  ?",
                new String[] {task.getUUID().toString()});
    }

    public void deleteTask(UUID id){
        mDatabase.delete(TaskBaseHelper.TABLE_NAME,
                TaskBaseHelper.UUID + " =  ?",
                new String[] {id.toString()});
    }

    public List<Task> getTaskList(){
        List<Task> mTaskList = new ArrayList<>();
        TaskCursorWrapper cursor = queryTasks(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                mTaskList.add(cursor.getTask());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return mTaskList;
    }

    public Task getTaskById(UUID uuid){
        TaskCursorWrapper cursor = queryTasks(
                TaskBaseHelper.UUID + " = ?",
                new String[] {uuid.toString()}
        );

        try {
            if (cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getTask();
        } finally {
            cursor.close();
        }
    }

}
