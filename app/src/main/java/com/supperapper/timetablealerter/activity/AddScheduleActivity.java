package com.supperapper.timetablealerter.activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.supperapper.timetablealerter.R;
import com.supperapper.timetablealerter.database.DbManager;
import com.supperapper.timetablealerter.service.NotificationChecker;

import java.util.Calendar;

public class AddScheduleActivity extends AppCompatActivity{

    private TextView txtMonStart, txtMonEnd;
    private TextView txtTueStart, txtTueEnd;
    private TextView txtWedStart, txtWedEnd;
    private TextView txtThuStart, txtThuEnd;
    private TextView txtFriStart, txtFriEnd;
    private TextView txtSatStart, txtSatEnd;
    private TextView txtSunStart, txtSunEnd;

    private EditText etxSubject, etxAbbreviation, etxSchool, etxRoom, etxTeacher, etxContact;
    private ToggleButton tgl_btnMon, tgl_btnTue, tgl_btnWed, tgl_btnThu, tgl_btnFri, tgl_btnSat, tgl_btnSun;

    private int Flag = 0;

    private int Hour, Min;
    private int DIALOG_ID = 0;
    String format;
    private TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        Button btnDiscard = (Button) findViewById(R.id.btn_discard);
        Button btnSave = (Button)findViewById(R.id.btn_done);
        txtMonStart = (TextView) findViewById(R.id.txt_mon_start);
        txtMonEnd = (TextView) findViewById(R.id.txt_mon_end);
        txtTueStart = (TextView) findViewById(R.id.txt_tue_start);
        txtTueEnd = (TextView)findViewById(R.id.txt_tue_end);
        txtWedStart = (TextView) findViewById(R.id.txt_wed_start);
        txtWedEnd = (TextView) findViewById(R.id.txt_wed_end);
        txtThuStart = (TextView) findViewById(R.id.txt_thu_start);
        txtThuEnd = (TextView) findViewById(R.id.txt_thu_end);
        txtFriStart = (TextView) findViewById(R.id.txt_fri_start);
        txtFriEnd = (TextView) findViewById(R.id.txt_fri_end);
        txtSatStart = (TextView) findViewById(R.id.txt_sat_start);
        txtSatEnd = (TextView) findViewById(R.id.txt_sat_end);
        txtSunStart = (TextView) findViewById(R.id.txt_sun_start);
        txtSunEnd = (TextView) findViewById(R.id.txt_sun_end);

        etxSubject = (EditText) findViewById(R.id.etx_subject);
        etxAbbreviation = (EditText) findViewById(R.id.etx_abbreviation);
        etxSchool = (EditText) findViewById(R.id.etx_school);
        etxRoom = (EditText) findViewById(R.id.etx_room);
        etxTeacher = (EditText) findViewById(R.id.etx_teacher);
        etxContact = (EditText) findViewById(R.id.etx_contact);

        tgl_btnMon = (ToggleButton) findViewById(R.id.tgl_btn_mon);
        tgl_btnTue = (ToggleButton) findViewById(R.id.tgl_btn_tue);
        tgl_btnWed = (ToggleButton) findViewById(R.id.tgl_btn_wed);
        tgl_btnThu = (ToggleButton) findViewById(R.id.tgl_btn_thu);
        tgl_btnFri = (ToggleButton) findViewById(R.id.tgl_btn_fri);
        tgl_btnSat = (ToggleButton) findViewById(R.id.tgl_btn_sat);
        tgl_btnSun = (ToggleButton) findViewById(R.id.tgl_btn_sun);

        //txtMonStart.setEnabled(false);

        final Calendar calendar = Calendar.getInstance();
        Hour = calendar.get(Calendar.HOUR_OF_DAY);
        Min = calendar.get(Calendar.MINUTE);

        txtMonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 timePickerDialog = new TimePickerDialog(AddScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        Hour = hourOfDay;
                        Min = minute;
                        if (Hour < 10 && Min < 10){
                            txtMonStart.setText( "0" + Hour + ":0"+ Min);

                        } else if (Min < 10){

                            txtMonStart.setText(Hour + ":" + "0"+ Min );

                        } else if (Hour < 10){

                            txtMonStart.setText("0" + Hour + ":" + Min );

                        } else {

                            txtMonStart.setText(Hour + ":" + Min);

                        }
                    }
                }, Hour, Min, false);

                timePickerDialog.show();

            }
        });

        txtMonEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePickerDialog = new TimePickerDialog(AddScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        Hour = hourOfDay;
                        Min = minute;
                        if (Hour < 10 && Min < 10){
                            txtMonEnd.setText( "0" + Hour + ":0"+ Min);

                        } else if (Min < 10){

                            txtMonEnd.setText(Hour + ":" + "0"+ Min );

                        } else if (Hour < 10){

                            txtMonEnd.setText("0" + Hour + ":" + Min );

                        } else {

                            txtMonEnd.setText(Hour + ":" + Min);

                        }
                    }
                }, Hour, Min, false);

                timePickerDialog.show();

            }
        });

        txtTueStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePickerDialog = new TimePickerDialog(AddScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        Hour = hourOfDay;
                        Min = minute;
                        if (Hour < 10 && Min < 10){
                            txtTueStart.setText( "0" + Hour + ":0"+ Min);

                        } else if (Min < 10){

                            txtTueStart.setText(Hour + ":" + "0"+ Min );

                        } else if (Hour < 10){

                            txtTueStart.setText("0" + Hour + ":" + Min );

                        } else {

                            txtTueStart.setText(Hour + ":" + Min);

                        }                     }
                }, Hour, Min, false);

                timePickerDialog.show();
            }
        });

        txtTueEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePickerDialog = new TimePickerDialog(AddScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        Hour = hourOfDay;
                        Min = minute;
                        if (Hour < 10 && Min < 10){
                            txtTueEnd.setText( "0" + Hour + ":0"+ Min);

                        } else if (Min < 10){

                            txtTueEnd.setText(Hour + ":" + "0"+ Min );

                        } else if (Hour < 10){

                            txtTueEnd.setText("0" + Hour + ":" + Min );

                        } else {

                            txtTueEnd.setText(Hour + ":" + Min);

                        }                     }
                }, Hour, Min, false);

                timePickerDialog.show();

                }
        });

        txtWedStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePickerDialog = new TimePickerDialog(AddScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        Hour = hourOfDay;
                        Min = minute;
                        if (Hour < 10 && Min < 10){
                            txtWedStart.setText( "0" + Hour + ":0"+ Min);

                        } else if (Min < 10){

                            txtWedStart.setText(Hour + ":" + "0"+ Min );

                        } else if (Hour < 10){

                            txtWedStart.setText("0" + Hour + ":" + Min );

                        } else {

                            txtWedStart.setText(Hour + ":" + Min);

                        }            }
                }, Hour, Min, false);

                timePickerDialog.show();


            }
        });

        txtWedEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePickerDialog = new TimePickerDialog(AddScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        Hour = hourOfDay;
                        Min = minute;
                        if (Hour < 10 && Min < 10){
                            txtWedEnd.setText( "0" + Hour + ":0"+ Min);

                        } else if (Min < 10){

                            txtWedEnd.setText(Hour + ":" + "0"+ Min );

                        } else if (Hour < 10){

                            txtWedEnd.setText("0" + Hour + ":" + Min );

                        } else {

                            txtWedEnd.setText(Hour + ":" + Min);

                        }                   }
                }, Hour, Min, false);

                timePickerDialog.show();

            }
        });

        txtThuStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePickerDialog = new TimePickerDialog(AddScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        Hour = hourOfDay;
                        Min = minute;
                        if (Hour < 10 && Min < 10){
                            txtThuStart.setText( "0" + Hour + ":0"+ Min);

                        } else if (Min < 10){

                            txtThuStart.setText(Hour + ":" + "0"+ Min );

                        } else if (Hour < 10){

                            txtThuStart.setText("0" + Hour + ":" + Min );

                        } else {

                            txtThuStart.setText(Hour + ":" + Min);

                        }          }

                }, Hour, Min, false);

                timePickerDialog.show();
            }
        });

        txtThuEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePickerDialog = new TimePickerDialog(AddScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        Hour = hourOfDay;
                        Min = minute;
                        if (Hour < 10 && Min < 10){
                            txtThuEnd.setText( "0" + Hour + ":0"+ Min);

                        } else if (Min < 10){

                            txtThuEnd.setText(Hour + ":" + "0"+ Min );

                        } else if (Hour < 10){

                            txtThuEnd.setText("0" + Hour + ":" + Min );

                        } else {

                            txtThuEnd.setText(Hour + ":" + Min);

                        }         }
                }, Hour, Min, false);

                timePickerDialog.show();
            }
        });

        txtFriStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePickerDialog = new TimePickerDialog(AddScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        Hour = hourOfDay;
                        Min = minute;

                        if (Hour < 10 && Min < 10){
                            txtFriStart.setText( "0" + Hour + ":0"+ Min);

                        } else if (Min < 10){

                            txtFriStart.setText(Hour + ":" + "0"+ Min );

                        } else if (Hour < 10){

                            txtFriStart.setText("0" + Hour + ":" + Min );

                        } else {

                            txtFriStart.setText(Hour + ":" + Min);

                        }        }
                }, Hour, Min, false);

                timePickerDialog.show();
            }
        });

        txtFriEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePickerDialog = new TimePickerDialog(AddScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        Hour = hourOfDay;
                        Min = minute;
                        if (Hour < 10 && Min < 10){
                            txtFriEnd.setText( "0" + Hour + ":0"+ Min);

                        } else if (Min < 10){

                            txtFriEnd.setText(Hour + ":" + "0"+ Min );

                        } else if (Hour < 10){

                            txtFriEnd.setText("0" + Hour + ":" + Min );

                        } else {

                            txtFriEnd.setText(Hour + ":" + Min);

                        }
                    }
                }, Hour, Min, false);

                timePickerDialog.show();

            }
        });

        txtSatStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePickerDialog = new TimePickerDialog(AddScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        Hour = hourOfDay;
                        Min = minute;
                        if (Hour < 10 && Min < 10){
                            txtSatStart.setText( "0" + Hour + ":0"+ Min);

                        } else if (Min < 10){

                            txtSatStart.setText(Hour + ":" + "0"+ Min );

                        } else if (Hour < 10){

                            txtSatStart.setText("0" + Hour + ":" + Min );

                        } else {

                            txtSatStart.setText(Hour + ":" + Min);

                        }
                    }
                }, Hour, Min, false);

                timePickerDialog.show();

            }
        });

        txtSatEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePickerDialog = new TimePickerDialog(AddScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        Hour = hourOfDay;
                        Min = minute;

                        if (Hour < 10 && Min < 10){
                            txtSatEnd.setText( "0" + Hour + ":0"+ Min);

                        } else if (Min < 10){

                            txtSatEnd.setText(Hour + ":" + "0"+ Min );

                        } else if (Hour < 10){

                            txtSatEnd.setText("0" + Hour + ":" + Min );

                        } else {

                            txtSatEnd.setText(Hour + ":" + Min);

                        }
                    }
                }, Hour, Min, false);

                timePickerDialog.show();
            }
        });

        txtSunStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePickerDialog = new TimePickerDialog(AddScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        Hour = hourOfDay;
                        Min = minute;
                        if (Hour < 10 && Min < 10){
                            txtSunStart.setText( "0" + Hour + ":0"+ Min);

                        } else if (Min < 10){

                            txtSunStart.setText(Hour + ":" + "0"+ Min );

                        } else if (Hour < 10){

                            txtSunStart.setText("0" + Hour + ":" + Min );

                        } else {

                            txtSunStart.setText(Hour + ":" + Min);

                        }         }
                }, Hour, Min, false);

                timePickerDialog.show();
            }
        });

        txtSunEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePickerDialog = new TimePickerDialog(AddScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        Hour = hourOfDay;
                        Min = minute;
                        if (Hour < 10 && Min < 10){
                            txtSunEnd.setText( "0" + Hour + ":0"+ Min);

                        } else if (Min < 10){

                            txtSunEnd.setText(Hour + ":" + "0"+ Min );

                        } else if (Hour < 10){

                            txtSunEnd.setText("0" + Hour + ":" + Min );

                        } else {

                            txtSunEnd.setText(Hour + ":" + Min);

                        }
                    }
                }, Hour, Min, false);

                timePickerDialog.show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tgl_btnMon.isChecked()){

                    Flag = 1;
                    Log.d("Button Monday", "Checked");
                    storeScheduletoDB(txtMonStart, txtMonEnd, "tblmondayschedule");

                }
                if(tgl_btnTue.isChecked()){

                    Flag = 1;
                    storeScheduletoDB(txtTueStart, txtTueEnd, "tbltuesdayschedule");
                    Log.d("Button Tuesday", "Checked");
                }

                if(tgl_btnWed.isChecked()){

                    Flag = 1;
                    storeScheduletoDB(txtWedStart, txtWedEnd, "tblwednesdayschedule");
                    Log.d("Button Wednesday", "Checked");
                }

                if(tgl_btnThu.isChecked()){

                    Flag = 1;
                    storeScheduletoDB(txtThuEnd, txtThuEnd, "tblthursdayschedule");
                    Log.d("Button Thursday", "Checked");

                }

                if(tgl_btnFri.isChecked()){

                    Flag = 1;
                    storeScheduletoDB(txtFriStart, txtFriEnd, "tblfridayschedule");
                    Log.d("Button Friday", "Checked");

                }

                if(tgl_btnSat.isChecked()){

                    Flag = 1;
                    storeScheduletoDB(txtSatStart, txtSatEnd, "tblsaturdayschedule");
                    Log.d("Button Saturday", "Checked");

                }

                if(tgl_btnSun.isChecked()){

                    Flag = 1;
                    storeScheduletoDB(txtSunStart, txtSunEnd, "tblsundayschedule");

                }

                if (Flag == 0){

                    Toast.makeText(AddScheduleActivity.this, "Error", Toast.LENGTH_LONG).show();
                    return;
                }

                onBackPressed();

            }
        });

        btnDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void storeScheduletoDB(TextView txtStart, TextView txtEnd, String TableName){

        DbManager Manager = DbManager.getInstance(AddScheduleActivity.this);
        String Subject = etxSubject.getText().toString();
        String Abbreviation = etxAbbreviation.getText().toString();
        String School = etxSchool.getText().toString();
        String Room = (etxRoom.getText().toString());
        String Teacher = etxTeacher.getText().toString();
        String Contact = (etxContact.getText().toString());
        String Start = txtStart.getText().toString();
        String End = txtEnd.getText().toString();

        Manager.insertSchedule(TableName, Subject, Abbreviation, School, Room, Teacher, Contact, Start, End);
        Intent startservice = new Intent(this, NotificationChecker.class);
        stopService(startservice);
        startService(startservice);
    }


//    @Override
//    protected Dialog onCreateDialog(int id) {
//        if(id == DIALOG_ID){
//
//         return new TimePickerDialog(this, timePicker, Hour, Min, android.text.format.DateFormat.is24HourFormat(this));
//
//        }
//        return null;
//    }
//
//
//    private TimePickerDialog.OnTimeSetListener
//            timePicker = new TimePickerDialog.OnTimeSetListener() {
//        @Override
//        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//
//            Hour = hourOfDay;
//            Min = minute;
//
//
//            txtMonStart.setText(Hour + ":" + Min);
//        }
//
//    };



}
