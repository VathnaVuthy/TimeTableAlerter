package com.supperapper.timetablealerter.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.supperapper.timetablealerter.R;
import com.supperapper.timetablealerter.database.DbManager;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TaskDetailActivity extends AppCompatActivity {

    private TextView topic;
    private TextView subject;
    private TextView date;
    private TextView note;
    private TextView mapName;
    private TextView mapAddress;
    private TextView mapPhone;
    private ImageView imgMap;

    String id;
    private int mYear,mMoth,mDay;

    private final int DAILOG_ID = 0;

    MenuItem itemEdit, itemDelete;

    private boolean edit = true;

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
        date = (TextView) findViewById(R.id.tv_date_detail);
        note = (TextView) findViewById(R.id.et_note_detail);
        topic.setText(getIntent().getStringExtra("topic"));
        subject.setText(getIntent().getStringExtra("subject"));
        date.setText(getIntent().getStringExtra("date"));
        note.setText(getIntent().getStringExtra("note"));

        id = getIntent().getStringExtra("id");

        Log.d("Id:", id);

        final Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMoth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        //Map Information
        mapName = (TextView) findViewById(R.id.tv_place_name);
        mapAddress = (TextView) findViewById(R.id.tv_address);
        mapPhone = (TextView) findViewById(R.id.tv_phone);
        imgMap = (ImageView) findViewById(R.id.img_map);
        mapName.setText(getIntent().getStringExtra("mapName"));
        mapAddress.setText(getIntent().getStringExtra("mapAddress"));
        mapPhone.setText(getIntent().getStringExtra("mapPhone"));

        imgMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(TaskDetailActivity.this,MapViewActivity.class);
                mapIntent.putExtra("mapName",getIntent().getStringExtra("mapName"));
                mapIntent.putExtra("mapAddress",getIntent().getStringExtra("mapAddress"));
                mapIntent.putExtra("mapLat",getIntent().getStringExtra("mapLat"));
                mapIntent.putExtra("mapLang",getIntent().getStringExtra("mapLang"));
                Log.d("LAT lang",getIntent().getStringExtra("mapLat") + "-" + getIntent().getStringExtra("mapLang") );
                startActivity(mapIntent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_delete_edit, menu);
        itemEdit = menu.getItem(0);
        itemDelete = menu.getItem(1);
        itemEdit.setTitle("Save");
        itemDelete.setTitle("Delete");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.edit){

            if(edit == true){

                edit = false;

                itemDelete.setTitle("Cancel");
                itemEdit.setIcon(null);
                itemDelete.setIcon(null);

                enableEdittext();

                date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        showDialog(DAILOG_ID);

                    }
                });

            } else {

                edit = true;

                String Topic = topic.getText().toString();
                String Subject = subject.getText().toString();
                String Date = date.getText().toString();
                String Note = note.getText().toString();

                DbManager dbManager = DbManager.getInstance(TaskDetailActivity.this);
                dbManager.updateTask(Topic, Subject, Date, Note, new String[]{id});

                itemEdit.setIcon(R.drawable.ic_edit);
                itemDelete.setIcon(R.drawable.ic_delete);

                disableEditText();
                dbManager.close();
            }


        } else if(item.getItemId()==R.id.delete) {

            if (itemDelete.getTitle().equals("Delete")){

                DbManager dbManager = DbManager.getInstance(TaskDetailActivity.this);
                dbManager.deleteTask(new String[]{id});
                dbManager.close();
                onBackPressed();

            } else {

                edit = true;

                topic.setText(getIntent().getStringExtra("topic"));
                subject.setText(getIntent().getStringExtra("subject"));
                date.setText(getIntent().getStringExtra("date"));
                note.setText(getIntent().getStringExtra("note"));

                itemEdit.setIcon(R.drawable.ic_edit);
                itemDelete.setIcon(R.drawable.ic_delete);

            }

        }else{

        }
            return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected Dialog onCreateDialog(int id) {

        if(id == DAILOG_ID)

            return new DatePickerDialog(this, dateSetListener, mYear, mMoth, mDay);


        return null;
    }


    private DatePickerDialog.OnDateSetListener
                dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
            Date mydate = new Date(year, month, dayOfMonth-1);
            String dayofWeek = simpleDateFormat.format(mydate);

            date.setText(dayofWeek + "-" + dayOfMonth + "-" + (month+1) + "-" + year);
        }
    };

    private void enableEdittext(){

        topic.setEnabled(true);
        subject.setEnabled(true);
        date.setEnabled(true);
        note.setEnabled(true);
    }

    private void disableEditText(){

        topic.setEnabled(false);
        subject.setEnabled(false);
        date.setEnabled(false);
        note.setEnabled(false);
        date.setOnClickListener(null);
    }
}
