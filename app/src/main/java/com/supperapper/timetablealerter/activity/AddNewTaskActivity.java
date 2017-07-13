package com.supperapper.timetablealerter.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.supperapper.timetablealerter.R;
import com.supperapper.timetablealerter.database.DbManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by User on 4/21/2017.
 */

public class AddNewTaskActivity extends AppCompatActivity{
    private int mYear,mMoth,mDay;
    private final int DAILOG_ID = 0;
    TextView textView,txtLocation;
    Button btn_discard, btn_done;
    EditText etxTopic, etxSubject, etxNote;
    Spinner spinner;

    MainActivity mainActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

         spinner = (Spinner) findViewById(R.id.sp_taskType);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.taskType, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        etxTopic = (EditText) findViewById(R.id.etx_topic);
        etxSubject = (EditText) findViewById(R.id.etx_subject);
        etxNote = (EditText) findViewById(R.id.etx_note);
        txtLocation = (TextView) findViewById(R.id.tv_location);

        final Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMoth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
//        weekDay = dayFormat.format(calendar.getTime());

        showDatePickerDailog();
        buttonDiscardClicked();
        buttonDoneClicked();
        onSetLocationClick();
    }

    private void showDatePickerDailog(){
        textView = (TextView) findViewById(R.id.tv_date);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DAILOG_ID);
            }
        });
    }

    private void buttonDoneClicked(){

        btn_done = (Button) findViewById(R.id.btn_done);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          //      MapViewActivity mapViewActivity = new MapViewActivity();
                DbManager Manager = new DbManager(AddNewTaskActivity.this);
                String Topic = etxTopic.getText().toString();
                String Subject = etxSubject.getText().toString();
                String TaskType = spinner.getSelectedItem().toString();
                String Date = textView.getText().toString();
                String Note = etxNote.getText().toString();
          //      Double Lat = mapViewActivity.Lat;

                Log.d("ckcc", String.valueOf(etxTopic.getText()));
           //     Log.d("", String.valueOf(Lat));
//                Log.d("ckcc", task.getmTopic());

                Manager.insertTask(Topic, Subject, TaskType, Date, Note);
                onBackPressed();

            }
        });
    }

    private void onSetLocationClick(){

        txtLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AddNewTaskActivity.this, MapViewActivity.class);
                startActivity(intent);
            }
        });
    }


    private void buttonDiscardClicked(){
        btn_discard = (Button) findViewById(R.id.btn_discard);
        btn_discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        if(id==DAILOG_ID)
            return new DatePickerDialog(this,datePickerListener,mYear,mMoth,mDay);
        return null;
    }
    private DatePickerDialog.OnDateSetListener
            datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
            Date date = new Date();
            String dayofWeek = dayFormat.format(date);

            mDay = i2;
            mMoth=i1+1;
            mYear=i;
            textView.setText(dayofWeek + "-" + mDay + "-" + mMoth + "-" + mYear);
        }
    };
}


















