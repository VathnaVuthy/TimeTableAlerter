package com.supperapper.timetablealerter.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.supperapper.timetablealerter.R;
import com.supperapper.timetablealerter.dataset.App;

import java.lang.reflect.Array;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private final String USERNAME = "admin";
    private final String PASSWORD = "admin";

    LoginButton btnFacebook;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(AccessToken.getCurrentAccessToken() != null){

            Log.d("TTA", "Login via Facebook");
            App.getInstance(this).setLoginMethod(App.LOGIN_METHOD_FACEBOOK);
            startMainActivity();
            return;
        }

        Button button = (Button) findViewById(R.id.btn_submit);
        btnFacebook = (LoginButton) findViewById(R.id.btn_login_fb);

        btnFacebook.setReadPermissions("email", "public_profile");

        callbackManager = CallbackManager.Factory.create();
        btnFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            ProfileTracker profileTracker;

            @Override
            public void onSuccess(LoginResult loginResult) {

                App.getInstance(LoginActivity.this).setLoginMethod(App.LOGIN_METHOD_FACEBOOK);
                if (Profile.getCurrentProfile() != null){

                    startMainActivity();
                } else {

                    profileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

                            profileTracker.stopTracking();
                            startMainActivity();
                        }
                    };
                }
            }

            @Override
            public void onCancel() {

                Log.d("Facebook", "Cancel");
            }

            @Override
            public void onError(FacebookException error) {

                Log.d("Facebook", "Error" + error.getMessage());
            }
        });




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView userName = (TextView) findViewById(R.id.et_user);
                TextView passWord = (TextView) findViewById(R.id.et_password);

                if(userName.getText().toString().equals(USERNAME)&&passWord.getText().toString().equals(PASSWORD)){
                    App.getInstance(LoginActivity.this).setLoginMethod(App.LOGIN_METHOD_USERNAME_PASSWORD);
                    startMainActivity();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void startMainActivity(){
        Intent intent  = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
