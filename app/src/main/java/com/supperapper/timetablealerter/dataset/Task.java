package com.supperapper.timetablealerter.dataset;

/**
 * Created by User on 5/15/2017.
 */

public class Task {
    String mTopic,mSubject,mTaskType,mDate,mLocation,mNote;

    public Task(String mTopic, String mSubject, String mTaskType, String mDate, String mLocation, String mNote) {
        this.mTopic = mTopic;
        this.mSubject = mSubject;
        this.mTaskType = mTaskType;
        this.mDate = mDate;
        this.mLocation = mLocation;
        this.mNote = mNote;
    }

    public String getmTopic() {
        return mTopic;
    }

    public void setmTopic(String mTopic) {
        this.mTopic = mTopic;
    }

    public String getmSubject() {
        return mSubject;
    }

    public void setmSubject(String mSubject) {
        this.mSubject = mSubject;
    }

    public String getmTaskType() {
        return mTaskType;
    }

    public void setmTaskType(String mTaskType) {
        this.mTaskType = mTaskType;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmLocation() {
        return mLocation;
    }

    public void setmLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public String getmNote() {
        return mNote;
    }

    public void setmNote(String mNote) {
        this.mNote = mNote;
    }
}
