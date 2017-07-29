package com.supperapper.timetablealerter.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.supperapper.timetablealerter.R;
import com.supperapper.timetablealerter.service.NotificationChecker;

public class StartupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        boolean isFirst = sharedPreferences.getBoolean("isFirst",true);
        if(isFirst==true){
            Log.d("First Run","Run");
            //Setup Default Setting
            editor.putBoolean("allowNotify",true);
            editor.putBoolean("isFirst",false);
            editor.putInt("alertTime",10);
            editor.commit();
        }

        boolean allowNotification = sharedPreferences.getBoolean("allowNotify",true);
        if(allowNotification==true){
            //Start Service
            Intent startservice = new Intent(this, NotificationChecker.class);
            startService(startservice);
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startMainActiviy();
                finish();
            }
        }, 2000);
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void startMainActiviy(){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
