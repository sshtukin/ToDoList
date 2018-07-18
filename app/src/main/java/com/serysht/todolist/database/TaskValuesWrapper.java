package com.serysht.todolist.database;

import android.content.ContentValues;

import com.serysht.todolist.Task;

public class TaskValuesWrapper {

    public static ContentValues getContentValues(Task task){
        ContentValues values = new ContentValues();
        values.put(TaskBaseHelper.UUID, task.getUUID().toString());
        values.put(TaskBaseHelper.TITLE, task.getTitle());
        values.put(TaskBaseHelper.ADDITIONAL, task.getAdditional());
        values.put(TaskBaseHelper.DATE, task.getDate().getTime());
        values.put(TaskBaseHelper.IS_DATE_ENABLED, task.isDateEnabled());
        values.put(TaskBaseHelper.IS_DONE, task.isDone());
        return values;
    }
}
