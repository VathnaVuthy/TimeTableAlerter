package com.supperapper.timetablealerter.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.supperapper.timetablealerter.R;

public class LoginActivity extends AppCompatActivity {
    private final String USERNAME = "admin";
    private final String PASSWORD = "admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button button = (Button) findViewById(R.id.btn_submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView userName = (TextView) findViewById(R.id.et_user);
                TextView passWord = (TextView) findViewById(R.id.et_password);
                if(userName.getText().toString().equals(USERNAME)&&passWord.getText().toString().equals(PASSWORD)){
                    startMainActivity();
                }
            }
        });
    }
    private void startMainActivity(){
        Intent intent  = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
