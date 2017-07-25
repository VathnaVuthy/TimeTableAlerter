package com.supperapper.timetablealerter.activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.supperapper.timetablealerter.R;
import com.supperapper.timetablealerter.database.DbManager;
import com.supperapper.timetablealerter.fragment.school.MondayFragment;

public class SchoolDetailActivity extends AppCompatActivity  {

    EditText tv_subject, tv_abb, tv_school, tv_room, tv_teacher, tv_contact, tv_timeStart, tv_timeEnd;
    private int hour, min;
    MenuItem itemEdit, itemDelete;
    String id, subject, abb, school, room, teacher, contact, timestart, timeend, day;
    private int OnClick = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Schedule Detail");
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // Reference to view
         tv_subject = (EditText) findViewById(R.id.tv_subject_detail);
         tv_abb = (EditText) findViewById(R.id.tv_abb_detail);
         tv_school = (EditText) findViewById(R.id.tv_school_detail);
         tv_room = (EditText) findViewById(R.id.tv_room_detail);
         tv_teacher = (EditText) findViewById(R.id.tv_teacher_detail);
         tv_contact = (EditText) findViewById(R.id.tv_contact_detail);
         tv_timeStart = (EditText) findViewById(R.id.tv_time_start);
         tv_timeEnd  = (EditText) findViewById(R.id.tv_time_end);

         id = getIntent().getStringExtra("id");
         subject = getIntent().getStringExtra("subject");
         abb = getIntent().getStringExtra("abb");
         school = getIntent().getStringExtra("school");
         room = getIntent().getStringExtra("room");
         teacher = getIntent().getStringExtra("teacher");
         contact = getIntent().getStringExtra("contact");
         timestart = getIntent().getStringExtra("timestart");
         timeend = getIntent().getStringExtra("timeend");
         day = getIntent().getStringExtra("date");

        tv_subject.setText(subject);
        tv_abb.setText(abb);
        tv_school.setText(school);
        tv_room.setText(room);
        tv_teacher.setText(teacher);
        tv_contact.setText(contact);
        tv_timeStart.setText(timestart);
        tv_timeEnd.setText(timeend);
    //    Log.d("ID:", id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_delete_edit, menu);
         itemEdit = menu.getItem(0);
         itemDelete = menu.getItem(1);
         itemEdit.setTitle("Save");
         itemDelete.setTitle("Delete");
       // menuItem.setIcon(getResources().getDrawable(R.drawable.ic_delete));
      //  menuItem.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.edit){
          //  button.setText("Done");
           // item.setTitle("Done");
            if(OnClick == 1){
                OnClick = 2;
                itemDelete.setTitle("Cancel");
                itemEdit.setIcon(null);
                itemDelete.setIcon(null);
                Log.d("CLICK", String.valueOf(itemEdit));
//                tv_subject.requestFocus();
//                EnableTextViewEdit(tv_subject);
//                EnableTextViewEdit(tv_abb);
//                EnableTextViewEdit(tv_school);
//                EnableTextViewEdit(tv_room);
//                EnableTextViewEdit(tv_teacher);
//                EnableTextViewEdit(tv_contact);

                tv_timeStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        TimePickerDialog timePickerDialog = new TimePickerDialog(SchoolDetailActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                hour = hourOfDay;
                                min = minute;

                                if(min == 00){

                                    Log.d("Minute", "00");
                                    tv_timeStart.setText(hour + ":" + min + "0");
                                    return;
                                }

                                tv_timeStart.setText(hour  + ":" + min);
                            }
                        }, hour, min, false);

                        timePickerDialog.show();
                    }
                });

                tv_timeEnd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        TimePickerDialog timePickerDialog = new TimePickerDialog(SchoolDetailActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                hour =hourOfDay;
                                min = minute;

                                if(min == 0){

                                    tv_timeEnd.setText(hour + ":" + min + "0");
                                    return;

                                }

                                tv_timeEnd.setText(hour + ":" + min);
                            }
                        }, hour, min, false);

                        timePickerDialog.show();
                    }
                });

            } else if(OnClick == 2){
                OnClick = 1;

//                DbManager dbManager = new DbManager(SchoolDetailActivity.this);
//                dbManager.updateSchedule();
                String Subject = tv_subject.getText().toString();
                String Abb = tv_abb.getText().toString();
                String School = tv_school.getText().toString();
                String Room = tv_room.getText().toString();
                String Teacher = tv_teacher.getText().toString();
                String Contact = tv_contact.getText().toString();
                String Timestart = tv_timeStart.getText().toString();
                String Timeend = tv_timeEnd.getText().toString();

                DbManager dbManager = DbManager.getInstance(SchoolDetailActivity.this);
                if(day.equals("MONDAY")){

                    dbManager.updateSchedule("tblmondayschedule", "idmonday = ?", new String[]{id}, Subject, Abb, School, Room, Teacher, Contact, Timestart, Timeend);

                } else if(day.equals("TUESDAY")){

                    dbManager.updateSchedule("tbltuesdayschedule", "idmonday = ?", new String[]{id}, Subject, Abb, School, Room, Teacher, Contact, Timestart, Timeend);

                } else if(day.equals("WEDNESDAY")){

                    dbManager.updateSchedule("tblwednesdayschedule", "idmonday = ?", new String[]{id}, Subject, Abb, School, Room, Teacher, Contact, Timestart, Timeend);

                } else if (day.equals("THURSDAY")){

                    dbManager.updateSchedule("tblthursdayschedule", "idmonday = ?", new String[]{id}, Subject, Abb, School, Room, Teacher, Contact, Timestart, Timeend);

                } else if (day.equals("FRIDAY")){

                    dbManager.updateSchedule("tblfridayschedule", "idmonday = ?", new String[]{id}, Subject, Abb, School, Room, Teacher, Contact, Timestart, Timeend);

                } else if(day.equals("SATURDAY")){

                    dbManager.updateSchedule("tblsaturdayschedule", "idmonday = ?", new String[]{id}, Subject, Abb, School, Room, Teacher, Contact, Timestart, Timeend);

                } else {

                    dbManager.updateSchedule("tblsundayschedule", "idmonday = ?", new String[]{id}, Subject, Abb, School, Room, Teacher, Contact, Timestart, Timeend);

                }
                dbManager.close();
                itemEdit.setIcon(getResources().getDrawable(R.drawable.ic_edit));
                itemDelete.setIcon(getResources().getDrawable(R.drawable.ic_delete));
//                DisableTextViewEdit(tv_subject);
//                DisableTextViewEdit(tv_abb);
//                DisableTextViewEdit(tv_school);
//                DisableTextViewEdit(tv_room);
//                DisableTextViewEdit(tv_teacher);
//                DisableTextViewEdit(tv_contact);

            }
            Toast.makeText(this, "Edit", Toast.LENGTH_SHORT).show();
        } else if(item.getItemId()==R.id.delete){

            if(itemDelete.getTitle().equals("Delete")) {

                String tablename;
                DbManager dbManager = DbManager.getInstance(SchoolDetailActivity.this);
                if(day.equals("MONDAY")){

                    tablename = "tblmondayschedule";
                } else if (day.equals("TUESDAY")){

                    tablename = "tbltuesdayschedule";

                } else if (day.equals("WEDNESDAY")){

                    tablename = "tblwednesdayschedule";
                } else if (day.equals("THURSDAY")){

                    tablename = "tblthursdayschedule";

                } else if (day.equals("FRIDAY")){

                    tablename = "tblfridayschedule";

                } else if (day.equals("SATURDAY")){

                    tablename = "tblsaturdayschedule";

                } else {

                    tablename = "tblsundayschedule";

                }

                dbManager.deleteSchedule(tablename, "idmonday = ?", new String[]{id});
                onBackPressed();
                Log.d("CC", "true");

              } else {

                        tv_subject.setText(subject);
                        tv_abb.setText(abb);
                        tv_school.setText(school);
                        tv_room.setText(room);
                        tv_teacher.setText(teacher);
                        tv_contact.setText(contact);
                        tv_timeStart.setText(timestart);
                        tv_timeEnd.setText(timeend);
                        Log.d("ID: ", id);
                        itemEdit.setIcon(getResources().getDrawable(R.drawable.ic_edit));
                        itemDelete.setIcon(getResources().getDrawable(R.drawable.ic_delete));
                        Log.d("CC", "false");
            }

            Log.d("Edited", tv_subject.getText().toString());
            Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
            onBackPressed();
            }else{
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void EnableTextViewEdit(TextView textView){
        textView.setFocusableInTouchMode(true);
        textView.setInputType(InputType.TYPE_CLASS_TEXT);
        textView.setFocusable(true);
        textView.setClickable(true);

    }

    private void DisableTextViewEdit(TextView textView){

        textView.setFocusable(false);
        textView.setFocusable(false);
        textView.setClickable(false);

    }

}
