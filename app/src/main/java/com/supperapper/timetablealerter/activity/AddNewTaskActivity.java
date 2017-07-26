package com.supperapper.timetablealerter.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
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
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
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
    boolean hasSetMap = false;
    private GoogleApiClient mGoogleApiClient;


    private final int REQUEST_CODE_PLACEPICKER = 1;

    MainActivity mainActivity;
    Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        context=this;

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
               if(spinner.getSelectedItem().toString().toLowerCase().equals("task")){
                   Toast.makeText(context,"Please Select One Task", Toast.LENGTH_LONG).show();
               }else{
                   DbManager Manager = DbManager.getInstance(AddNewTaskActivity.this);
                   String mapId;
                   if(hasSetMap==true){
                       mapId = Manager.getLastMapId();
                       Log.d("Last Map ID", mapId);
                       hasSetMap = false;
                   }else{
                       mapId = null;
                   }
                   String Topic = etxTopic.getText().toString();
                   String Subject = etxSubject.getText().toString();
                   String TaskType = spinner.getSelectedItem().toString();
                   String Date = textView.getText().toString();
                   String Note = etxNote.getText().toString();

                   Log.d("ckcc", String.valueOf(etxTopic.getText()));

                   Manager.insertTask(Topic, Subject, TaskType, Date, Note,mapId);
                   Manager.close();
                   Log.d("Add TO DB Successfully","Success!");
                   onBackPressed();
               }

            }
        });
    }

    private void onSetLocationClick(){

        txtLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlacePickerActivity();
            }
        });
    }

    private void startPlacePickerActivity(){
        PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
        try{
            Intent intent = intentBuilder.build(this);
            startActivityForResult(intent,REQUEST_CODE_PLACEPICKER);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void displaySelectedPlaceFromPlacePicker(Intent data) {
        Place placeSelected = PlacePicker.getPlace(this, data);
        String name = placeSelected.getName().toString();
        String address = placeSelected.getAddress().toString();
        String phone = placeSelected.getPhoneNumber().toString();
        String website = placeSelected.getWebsiteUri().toString();
        String latLang = placeSelected.getLatLng().toString().replace("(","").replace(")","").replace("lat/lng: ","");
        String [] LL = latLang.split(",");
        DbManager dbManager = DbManager.getInstance(this);
        dbManager.insertMap(name,address,phone,website,LL[0],LL[1]);

        txtLocation.setText(name);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PLACEPICKER && resultCode == RESULT_OK) {
            hasSetMap = true;
            displaySelectedPlaceFromPlacePicker(data);
        }
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
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
            Date date = new Date(year, month, dayOfMonth-1);
            String dayOfWeek = simpledateformat.format(date);
            textView.setText(dayOfWeek + "-" + dayOfMonth + "-" + (month+1)+ "-" + year);
        }
    };
}


















