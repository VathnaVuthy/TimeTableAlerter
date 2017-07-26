package com.supperapper.timetablealerter.dataset;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by dell on 7/25/2017.
 */

public class App {

    public static final int LOGIN_METHOD_USERNAME_PASSWORD = 0;
    public static final int LOGIN_METHOD_FACEBOOK = 1;

    private static App instance;

    private int loginMethod;

    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    private App(Context context){

        requestQueue = Volley.newRequestQueue(context);
        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {

            }
        });

    }

    public static App getInstance(Context context){

        if(instance == null){

            instance = new App(context);
        }
        return instance;
    }

    public void addRequest(Request request){
        requestQueue.add(request);
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }


    public int getLoginMethod() {
        return loginMethod;
    }

    public void setLoginMethod(int loginMethod) {
        this.loginMethod = loginMethod;
    }
}
