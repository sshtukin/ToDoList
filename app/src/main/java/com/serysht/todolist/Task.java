package com.serysht.todolist;

import java.util.Date;
import java.util.UUID;

public class Task {
    private UUID mUUID;
    private String mTitle;
    private String mAdditional;
    private Date mDate;
    private boolean mDateEnabled;
    private boolean mIsDone;

    public Task(){
        mUUID = UUID.randomUUID();
        mTitle = "";
        mAdditional = "";
        mDate = null;
        mDateEnabled = false;
        mIsDone = false;
    }

//
//    public Task(String title) {
//        mUUID = UUID.randomUUID();
//        mTitle = title;
//        mAdditional = "";
//        mIsDone = false;
//    }
//
//    public Task(String id, String title, String extended, boolean isDone) {
//        mUUID = UUID.fromString(id);
//        mTitle = title;
//        mAdditional = extended;
//        mIsDone = isDone;
//    }


    public UUID getUUID() {
        return mUUID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getAdditional() {
        return mAdditional;
    }

    public void setAdditional(String additional) {
        mAdditional = additional;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isDateEnabled() {
        return mDateEnabled;
    }

    public void setDateEnabled(boolean dateEnabled) {
        mDateEnabled = dateEnabled;
    }

    public boolean isDone() {
        return mIsDone;
    }

    public void setDone(boolean done) {
        mIsDone = done;
    }
}
