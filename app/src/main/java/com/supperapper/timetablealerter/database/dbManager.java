package com.supperapper.timetablealerter.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.supperapper.timetablealerter.activity.MapViewActivity;
import com.supperapper.timetablealerter.dataset.Task;
import com.supperapper.timetablealerter.viewholder.TaskAdapter;

/**
 * Created by dell on 7/4/2017.
 */

public class dbManager extends SQLiteAssetHelper {

    TaskAdapter.TaskViewHolder taskViewHolder;

    MapViewActivity mapViewActivity;

   // Task task;
    public dbManager(Context context) {
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

}
