package com.supperapper.timetablealerter.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
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
    public DbManager(Context context) {
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

            String id = cursor.getString(0);
            String topic = cursor.getString(1);
            String subject = cursor.getString(2);
            String type = cursor.getString(3);
            String date = cursor.getString(4);
//            String lat = cursor.getString(5);
//            String lng = cursor.getString(6);
            String note = cursor.getString(7);

            Task task = new Task(topic, subject, type, date,null, note, id);
            tasks[index] = task;
            index++;

        }

        return tasks;
    }

    public void insertSchedule(String TableName, String Subject, String Abbreviation, String School, String Room, String Teacher, String Contact, String Start, String End){

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

            String Day = TableName.replace("tbl","").replace("schedule","").toUpperCase();

            Schedule schedule = new Schedule(id,Subject,Abbreviation,School,Day,Start,End,Teacher,Room,Contact);
            schedules[index] = schedule;
            index++;
        }

        return schedules;
    }
    public Task[] getTaskForDayview(String day){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT * FROM tblTask";
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        Cursor x;
        x = cursor;

        int index = 0;
        int totalTask = 0;
        while (x.moveToNext()){
            String date = x.getString(4);
            String[] myDay = date.split("-");
            if(myDay[0].toLowerCase().equals(day.toLowerCase())){
                totalTask++;
            }
        }
        Task[] tasks = new Task[totalTask];

        while(cursor.moveToPrevious()){
            int id = cursor.getInt(0);
            String topic = cursor.getString(1);
            String subject = cursor.getString(2);
            String type = cursor.getString(3);
            String date = cursor.getString(4);
//            String lat = cursor.getString(5);
//            String lng = cursor.getString(6);
            String note = cursor.getString(7);
            String[] myDay = date.split("-");
            if(myDay[0].toLowerCase().equals(day.toLowerCase())){
                Task task = new Task(topic,subject,type,myDay[0],note);
                tasks[index] = task;
                index++;
            }
        }
        return tasks;
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


    public ArrayList<Task> getListTask(String[] TaskType){

        SQLiteDatabase read = getReadableDatabase();
//        Cursor cursor = read.query("tblTask", null, null, null, null, null, null);
//        String sql = "select * from tblTask Where type = EXAM";
        Cursor cursor = read.query("tblTask", null, "type = ?", TaskType, null, null, null);
        ArrayList<Task> taskArrayList = new ArrayList<>();

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
            taskArrayList.add(task);
        }

        return taskArrayList;
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

    public void deleteSchedule(String TableName, String ColumnID, String[] ID){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TableName, ColumnID, ID);

    }

    public void updateTask(String Topic, String Subject, String Date, String Note, String[] id){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues row = new ContentValues();

        row.put("topic", Topic);
        row.put("subject", Subject);
        row.put("date", Date);
        row.put("note", Note);

        db.update("tblTask", row, "taskid = ?", id);
    }

    public void deleteTask(String[] ID){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("tblTask", "taskid = ?", ID);

    }

}
