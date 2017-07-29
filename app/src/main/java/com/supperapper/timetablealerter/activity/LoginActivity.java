package com.supperapper.timetablealerter.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
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
import java.security.MessageDigest;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private final String USERNAME = "admin";
    private final String PASSWORD = "admin";

    public static String LAST_LOGIN_METHOD;

    LoginButton btnFacebook;
    CallbackManager callbackManager;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if(AccessToken.getCurrentAccessToken() != null){

            Log.d("LoginActivity", "Login via Facebook");
       //     App.getInstance(this).setLoginMethod(App.LOGIN_METHOD_FACEBOOK);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(LAST_LOGIN_METHOD);
            editor.commit();
            setDefaults(LAST_LOGIN_METHOD, "facebook", this);
            startMainActivity();
            return;
        }


        Button button = (Button) findViewById(R.id.btn_submit);
        btnFacebook = (LoginButton) findViewById(R.id.btn_login_fb);

        btnFacebook.setReadPermissions("email");

        callbackManager = CallbackManager.Factory.create();
        btnFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            ProfileTracker profileTracker;

            @Override
            public void onSuccess(LoginResult loginResult) {

//                App.getInstance(LoginActivity.this).setLoginMethod(App.LOGIN_METHOD_FACEBOOK);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(LAST_LOGIN_METHOD);
                editor.commit();
                setDefaults(LAST_LOGIN_METHOD, "facebook", LoginActivity.this);
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
                 //   App.getInstance(LoginActivity.this).setLoginMethod(App.LOGIN_METHOD_USERNAME_PASSWORD);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove(LAST_LOGIN_METHOD);
                    editor.commit();
                    setDefaults(LAST_LOGIN_METHOD, "username", LoginActivity.this);
                    startMainActivity();
                }
            }
        });
    }

    public static void setDefaults(String key, String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    private String getHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
             for (Signature signature : info.signatures) {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA");
                messageDigest.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(messageDigest.digest(), 0));
                    if (!hashKey.isEmpty()) {
                     return hashKey;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();

        }
         return "";
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
