package com.supperapper.timetablealerter.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.gson.JsonObject;
import com.supperapper.timetablealerter.R;
import com.supperapper.timetablealerter.dataset.App;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by USER on 5/16/2017.
 */

public class SettingActivity extends AppCompatActivity  {

    Button btnLogin, btnLogout;
    EditText etxUsername, etxEmail;
    NetworkImageView imgProfile;
    Spinner spinner;
    SharedPreferences sharedPreferences;
    Switch allowNotification;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = (Toolbar)findViewById(R.id.setting_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Setting");

        spinner = (Spinner) findViewById(R.id.set_time);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.time, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Log.d("name:", "i am" + name);
        etxUsername = (EditText) findViewById(R.id.etx_username);
        etxEmail = (EditText) findViewById(R.id.etx_email);

        etxUsername.setText(name);
        etxEmail.setText(email);

        final CircleImageView circleImageView = (CircleImageView) findViewById(R.id.img_profile_picture);


        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogout = (Button) findViewById(R.id.btn_logout);
        allowNotification = (Switch) findViewById(R.id.switch_allow_notification);


        if(App.getInstance(SettingActivity.this).getLogin() == App.IS_LOGIN){


            btnLogin.setVisibility(View.INVISIBLE);
            btnLogout.setVisibility(View.VISIBLE);

            if(App.getInstance(SettingActivity.this).getLoginMethod() == App.LOGIN_METHOD_USERNAME_PASSWORD){


                circleImageView.setImageResource(R.drawable.profile_larrypage);
            } else if (App.getInstance(SettingActivity.this).getLoginMethod() == App.LOGIN_METHOD_FACEBOOK){


                String profilePicUrl = Profile.getCurrentProfile().getProfilePictureUri(230,230).toString();

                ImageRequest imageRequest = new ImageRequest(profilePicUrl, new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        circleImageView.setImageBitmap(response);
                    }
                }, 230, 230, ImageView.ScaleType.FIT_CENTER, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                App.getInstance(this).addRequest(imageRequest);


            }


        } else {

            btnLogin.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.INVISIBLE);

        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                App.getInstance(SettingActivity.this).setLogin(App.IS_LOGIN);
                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.getInstance(SettingActivity.this).setLogin(App.NOT_LOGIN);

                if(App.getInstance(SettingActivity.this).getLoginMethod() == App.LOGIN_METHOD_USERNAME_PASSWORD){


                } else {

                    LoginManager.getInstance().logOut();
                }

                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

//        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                boolean allow;
//                String time = spinner.getSelectedItem().toString().replace("mn","");
//                int setTime = Integer.parseInt(time);
//                if (allowNotification.isChecked()){
//
//                    allow = true;
//                } else {
//
//                    allow =false;
//                }
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putBoolean("allowNotification", allow);
//                editor.putInt("setTime", setTime);
//            }
//        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 1){

                    Log.d("Positon:", "1");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Log.d("here", "Not click");
            }
        });

    }




     private void loadProfileFromFacebook() {
        Log.d("ckcc", "loadProfileFromFacebook");
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        final GraphRequest request = GraphRequest.newGraphPathRequest(
                accessToken,
                "/me/",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        Log.d("ckcc", "loadProfileFromFacebook completed");
                        JSONObject result = response.getJSONObject();

                        try {
                            String name = result.getString("name");
                            String email = result.getString("email");
                            etxUsername.setText(name);
                            etxEmail.setText(email);

                           String imageUrl = Profile.getCurrentProfile().getProfilePictureUri(230, 230).toString();
                            disPlayFacebookProfilePicture(imageUrl);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private  void disPlayFacebookProfilePicture(String Url){

        ImageRequest request = new ImageRequest(Url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imgProfile.setImageBitmap(response);

            }
        }, 512, 512, ImageView.ScaleType.FIT_CENTER, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SettingActivity.this, "Error while loading profile image.", Toast.LENGTH_LONG).show();
            }
        });

        App.getInstance(SettingActivity.this).addRequest(request);
    }
}
