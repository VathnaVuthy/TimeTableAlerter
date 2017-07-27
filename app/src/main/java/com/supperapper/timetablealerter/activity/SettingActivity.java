package com.supperapper.timetablealerter.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by USER on 5/16/2017.
 */

public class SettingActivity extends AppCompatActivity  {

    Button btnLogin, btnLogout;
    EditText etxUsername, etxEmail;
    NetworkImageView imgProfile;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = (Toolbar)findViewById(R.id.setting_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Setting");

        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");

        Log.d("name:", "i am" + name);
        etxUsername = (EditText) findViewById(R.id.etx_username);
        etxEmail = (EditText) findViewById(R.id.etx_email);

        etxUsername.setText(name);
        etxEmail.setText(email);

        final CircleImageView circleImageView = (CircleImageView) findViewById(R.id.img_profile_picture);


        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogout = (Button) findViewById(R.id.btn_logout);

        if(App.getInstance(SettingActivity.this).getLoginMethod() == App.LOGIN_METHOD_USERNAME_PASSWORD){

            circleImageView.setImageResource(R.drawable.profile_larrypage);
        } else {

            String profilePicUrl = Profile.getCurrentProfile().getProfilePictureUri(230,230).toString();

            ImageRequest imageRequest = new ImageRequest(profilePicUrl, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    circleImageView.setImageBitmap(response);
                }
            }, 230, 230, ImageView.ScaleType.FIT_CENTER, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.d("Image", "null");
                }
            });
            App.getInstance(this).addRequest(imageRequest);

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                }
            });

        }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(App.getInstance(SettingActivity.this).getLoginMethod() == App.LOGIN_METHOD_USERNAME_PASSWORD){


                } else {

                    LoginManager.getInstance().logOut();
                }

                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
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
