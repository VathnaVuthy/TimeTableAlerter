package com.supperapper.timetablealerter.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.supperapper.timetablealerter.R;

import java.util.Calendar;

/**
 * Created by User on 4/21/2017.
 */

public class AddNewTaskActivity extends AppCompatActivity{
    private int mYear,mMoth,mDay;
    private final int DAILOG_ID = 0;
    TextView textView;
    Button btn_discard, btn_done;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Spinner spinner = (Spinner) findViewById(R.id.sp_taskType);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.taskType, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        final Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMoth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        showDatePickerDailog();
        buttonDiscardClicked();
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
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            mDay = i2;
            mMoth=i1+1;
            mYear=i;
            textView.setText(mDay + "-" + mMoth + "-" + mYear);
        }
    };
}


















