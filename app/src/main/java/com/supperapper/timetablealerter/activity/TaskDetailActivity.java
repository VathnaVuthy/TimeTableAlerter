package com.supperapper.timetablealerter.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.supperapper.timetablealerter.R;

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

        topic = (TextView) findViewById(R.id.tv_topic_detail);
        subject = (TextView) findViewById(R.id.tv_subject_detail);
        taskType = (TextView) findViewById(R.id.tv_tasktype_detail);
        date = (TextView) findViewById(R.id.tv_date_detail);
        note = (TextView) findViewById(R.id.et_note_detail);



    }
}
