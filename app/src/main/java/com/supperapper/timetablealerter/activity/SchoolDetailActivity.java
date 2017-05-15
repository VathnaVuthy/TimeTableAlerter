package com.supperapper.timetablealerter.activity;

import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.supperapper.timetablealerter.R;

public class SchoolDetailActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Schedule Detail");
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);
        // Reference to view
        TextView tv_subject = (TextView) findViewById(R.id.tv_subject_detail);
        TextView tv_abb = (TextView) findViewById(R.id.tv_abb_detail);
        TextView tv_school = (TextView) findViewById(R.id.tv_school_detail);
        TextView tv_room = (TextView) findViewById(R.id.tv_room_detail);
        TextView tv_teacher = (TextView) findViewById(R.id.tv_teacher_detail);
        TextView tv_contact = (TextView) findViewById(R.id.tv_contact_detail);
        TextView  tv_time= (TextView) findViewById(R.id.tv_time_detail);

        String subject = getIntent().getStringExtra("subject");
        String abb = getIntent().getStringExtra("abb");
        String school = getIntent().getStringExtra("school");
        String room = getIntent().getStringExtra("room");
        String teacher = getIntent().getStringExtra("teacher");
        String contact = getIntent().getStringExtra("contact");
        String time = getIntent().getStringExtra("time");

        tv_subject.setText(subject);
        tv_abb.setText(abb);
        tv_school.setText(school);
        tv_room.setText(room);
        tv_teacher.setText(teacher);
        tv_contact.setText(contact);
        tv_time.setText(time);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_delete_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.edit){
            Toast.makeText(this, "Edit", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
            }
        return super.onOptionsItemSelected(item);
    }
}
