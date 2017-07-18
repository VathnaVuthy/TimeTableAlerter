package com.supperapper.timetablealerter.dataset;

import java.sql.Time;

/**
 * Created by User on 4/30/2017.
 */

public class Schedule {
    private String mSubject,mAbbreviation,mSchool,mDay,mStartTime,mEndTime,mTeacher, mRoom, mContact,mID;


    private final String mType = "Schedule";

    public Schedule(String subject, String mSubject, String mRoom, String mDay, String mStartTime, String mEndTime, Time start, Time end, String mTeacher) {
        this.mSubject = mSubject;
        this.mRoom = mRoom;
        this.mDay = mDay;
        this.mStartTime = mStartTime;
        this.mEndTime = mEndTime;
        this.mTeacher = mTeacher;
    }

    public Schedule(String mSubject, String mAbbreviation, String mSchool, String mRoom, String mContact, String mDay, String mStartTime, String mEndTime, String mTeacher) {
        this.mSubject = mSubject;
        this.mAbbreviation = mAbbreviation;
        this.mSchool = mSchool;
        this.mRoom = mRoom;
        this.mContact = mContact;
        this.mDay = mDay;
        this.mStartTime = mStartTime;
        this.mEndTime = mEndTime;
        this.mTeacher = mTeacher;
    }

    public String getmID() {
        return mID;
    }

    public void setmID(String mID) {
        this.mID = mID;
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

    public String getmTeacher() {
        return mTeacher;
    }

    public void setmTeacher(String mTeacher) {
        this.mTeacher = mTeacher;
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

    public String getmType(){ return this.mType; }
}
