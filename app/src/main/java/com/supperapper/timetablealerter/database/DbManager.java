package com.supperapper.timetablealerter.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.supperapper.timetablealerter.R;
import com.supperapper.timetablealerter.activity.MapViewActivity;
import com.supperapper.timetablealerter.dataset.Schedule;
import com.supperapper.timetablealerter.dataset.ScheduleNotify;
import com.supperapper.timetablealerter.dataset.Task;
import com.supperapper.timetablealerter.viewholder.TaskAdapter;

import java.sql.Time;
import java.util.ArrayList;

/**
 * Created by dell on 7/4/2017.
 */

public class DbManager extends SQLiteAssetHelper {

    TaskAdapter.TaskViewHolder taskViewHolder;

    MapViewActivity mapViewActivity;
    public static DbManager instance;

    public static DbManager getInstance(Context context){
        if(instance==null)
            instance = new DbManager(context);
        return instance;
    }

   // Task task;
    private DbManager(Context context) {
        super(context, "TimeTable.db.sqlite", null, null, 1);
    }


    public void insertTask(String Topic, String Subject, String TaskType, String Date, String Note){


        SQLiteDatabase write = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("topic", Topic);
        row.put("subject", Subject);
        row.put("type", TaskType);
        row.put("date", Date);
     //   row.put("lat", Lat);
        row.put("note", Note);


        write.insert("tblTask", null, row);

    }

    public Task[] getAlltasks(String[] TaskType){

        SQLiteDatabase read = getReadableDatabase();
//        Cursor cursor = read.query("tblTask", null, null, null, null, null, null);
//        String sql = "select * from tblTask Where type = EXAM";
        Cursor cursor = read.query("tblTask", null, "type = ?", TaskType, null, null, null);
        Task[] tasks = new Task[cursor.getCount()];
        int index = 0;

        while(cursor.moveToNext()){

            int id = cursor.getInt(0);
            String topic = cursor.getString(1);
            String subject = cursor.getString(2);
            String type = cursor.getString(3);
            String date = cursor.getString(4);
//            String lat = cursor.getString(5);
//            String lng = cursor.getString(6);
            String note = cursor.getString(7);

            Task task = new Task(topic, subject, type, date, note);
            tasks[index] = task;
            index++;

        }

        return tasks;
    }

    public void insertSchedule(String TableName, String Subject, String Abbreviation, String School, String Room, String Teacher, String Contact, String Start, String End, String Date){

        SQLiteDatabase write = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("subject", Subject);
        row.put("abbrev", Abbreviation);
        row.put("school", School);
        row.put("room", Room);
        row.put("teacher", Teacher);
        row.put("contact", Contact);
        row.put("start", Start);
        row.put("end", End);
        row.put("date", Date);

        write.insert(TableName, null, row);
    }

    public Schedule[] getAllSchdule(String TableName){

        SQLiteDatabase read = getReadableDatabase();
        Cursor cursor = read.query(TableName, null, null, null, null, null, null);
        Schedule[] schedules = new Schedule[cursor.getCount()];
        int index = 0;

        while (cursor.moveToNext()){

            String id = cursor.getString(0);
            String Subject = cursor.getString(1);
            String Abbreviation = cursor.getString(2);
            String School = cursor.getString(3);
            String Room = cursor.getString(4);
            String Teacher = cursor.getString(5);
            String Contact = cursor.getString(6);
            String Start = (cursor.getString(7));
            String End = (cursor.getString(8));
            String Date = cursor.getString(9);

            //  Schedule schedule = new Schedule(Subject, Abbreviation, School, Room, Teacher, Contact, Start, End);
            Schedule schedule = new Schedule(Subject, Abbreviation, School, Room, Contact, Date, Start, End, Teacher, id);
            schedules[index] = schedule;
            index++;
        }

        return schedules;
    }

    public ArrayList<ScheduleNotify> getListSchdule(String TableName){

        SQLiteDatabase read = getReadableDatabase();
        Cursor cursor = read.query(TableName, null, null, null, null, null, null);
        ArrayList<ScheduleNotify> schedules = new ArrayList<ScheduleNotify>();

        while (cursor.moveToNext()){

            int id = cursor.getInt(0);
            String Subject = cursor.getString(1);
            String Abbreviation = cursor.getString(2);
            String School = cursor.getString(3);
            String Room = cursor.getString(4);
            String Teacher = cursor.getString(5);
            String Contact = cursor.getString(6);
            String Start = (cursor.getString(7));
            String End = (cursor.getString(8));

            //  Schedule schedule = new Schedule(Subject, Abbreviation, School, Room, Teacher, Contact, Start, End);

            String Day = TableName.replace("tbl","").replace("schedule","").toUpperCase();

            ScheduleNotify schedule = new ScheduleNotify(id,Subject,Abbreviation,School,Day,Start,End,Teacher,Room,Contact);
            schedules.add(schedule);
        }
        return schedules;
    }

    public void updateSchedule(String TableName, String ColumnID, String[] ID, String Subject, String Abbreviation, String School, String Room, String Teacher, String Contact, String Start, String End){

        SQLiteDatabase update = this.getWritableDatabase();
        ContentValues row = new ContentValues();

        row.put("subject", Subject);
        row.put("abbrev", Abbreviation);
        row.put("school", School);
        row.put("room", Room);
        row.put("teacher", Teacher);
        row.put("contact", Contact);
        row.put("start", Start);
        row.put("end", End);

        update.update(TableName, row, ColumnID, ID);
    }
}
