package com.supperapper.timetablealerter.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.supperapper.timetablealerter.R;

import org.w3c.dom.Text;

public class TaskDetailActivity extends AppCompatActivity {

    private TextView topic;
    private TextView subject;
    private TextView taskType;
    private TextView date;
    private TextView note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_task_detail);
        setSupportActionBar(toolbar);
        String title=getIntent().getStringExtra("tasktype");
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        topic = (TextView) findViewById(R.id.tv_topic_detail);
        subject = (TextView) findViewById(R.id.tv_subject_detail);
        taskType = (TextView) findViewById(R.id.tv_tasktype_detail);
        date = (TextView) findViewById(R.id.tv_date_detail);
        note = (TextView) findViewById(R.id.et_note_detail);
        topic.setText(getIntent().getStringExtra("topic"));
        subject.setText(getIntent().getStringExtra("subject"));
        taskType.setText(getIntent().getStringExtra("tasktype"));
        date.setText(getIntent().getStringExtra("date"));
        note.setText(getIntent().getStringExtra("note"));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
