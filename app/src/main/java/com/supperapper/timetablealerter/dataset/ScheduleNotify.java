package com.supperapper.timetablealerter.dataset;

/**
 * Created by User on 7/16/2017.
 */

public class ScheduleNotify {
    private int id;
    private String mSubject,mAbbreviation,mSchool,mDay,mStartTime,mEndTime,mTeacher, mRoom, mContact;

    public ScheduleNotify(int id, String mSubject, String mAbbreviation, String mSchool, String mDay, String mStartTime, String mEndTime, String mTeacher, String mRoom, String mContact) {
        this.id = id;
        this.mSubject = mSubject;
        this.mAbbreviation = mAbbreviation;
        this.mSchool = mSchool;
        this.mDay = mDay;
        this.mStartTime = mStartTime;
        this.mEndTime = mEndTime;
        this.mTeacher = mTeacher;
        this.mRoom = mRoom;
        this.mContact = mContact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getmSubject() {
        return mSubject;
    }

    public void setmSubject(String mSubject) {
        this.mSubject = mSubject;
    }

    public String getmAbbreviation() {
        return mAbbreviation;
    }

    public void setmAbbreviation(String mAbbreviation) {
        this.mAbbreviation = mAbbreviation;
    }

    public String getmSchool() {
        return mSchool;
    }

    public void setmSchool(String mSchool) {
        this.mSchool = mSchool;
    }

    public String getmDay() {
        return mDay;
    }

    public void setmDay(String mDay) {
        this.mDay = mDay;
    }

    public String getmStartTime() {
        return mStartTime;
    }

    public void setmStartTime(String mStartTime) {
        this.mStartTime = mStartTime;
    }

    public String getmEndTime() {
        return mEndTime;
    }

    public void setmEndTime(String mEndTime) {
        this.mEndTime = mEndTime;
    }

    public String getmTeacher() {
        return mTeacher;
    }

    public void setmTeacher(String mTeacher) {
        this.mTeacher = mTeacher;
    }

    public String getmRoom() {
        return mRoom;
    }

    public void setmRoom(String mRoom) {
        this.mRoom = mRoom;
    }

    public String getmContact() {
        return mContact;
    }

    public void setmContact(String mContact) {
        this.mContact = mContact;
    }
}
