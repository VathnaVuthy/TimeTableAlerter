package com.supperapper.timetablealerter.dataset;

/**
 * Created by User on 7/18/2017.
 */

public class MyApp {
    private static MyApp instance;

    public static MyApp getInstance(){
        if(instance==null)
            instance = new MyApp();
        return instance;
    }
}
