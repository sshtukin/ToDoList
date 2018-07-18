package com.serysht.todolist.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.serysht.todolist.Task;

public class TaskCursorWrapper extends CursorWrapper {

    public TaskCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Task getTask() {
        String uuid = getString(getColumnIndex(TaskBaseHelper.UUID));
        String title = getString(getColumnIndex(TaskBaseHelper.TITLE));
        String additional = getString(getColumnIndex(TaskBaseHelper.ADDITIONAL));
        long date = getLong(getColumnIndex(TaskBaseHelper.DATE));
        int isDateEnabled = getInt(getColumnIndex(TaskBaseHelper.IS_DATE_ENABLED));
        int isDone = getInt(getColumnIndex(TaskBaseHelper.IS_DONE));

        return new Task(uuid, title, additional, date, (isDateEnabled != 0), (isDone != 0));
    }
}
