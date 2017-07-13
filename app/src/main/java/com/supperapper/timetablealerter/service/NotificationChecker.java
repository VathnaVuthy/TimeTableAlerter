package com.supperapper.timetablealerter.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.res.TypedArrayUtils;
import android.util.Log;

import com.supperapper.timetablealerter.database.DbManager;
import com.supperapper.timetablealerter.dataset.Schedule;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by User on 7/13/2017.
 */

public class NotificationChecker extends Service {

    private Handler eventCheckerHandler;
    private EventCheckerRunnable eventCheckerRunnable;
    private Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        eventCheckerHandler = new Handler();
        eventCheckerRunnable = new EventCheckerRunnable();
        eventCheckerHandler.post(eventCheckerRunnable);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    class EventCheckerRunnable implements Runnable{

        DbManager dbManager ;


        @Override
        public void run() {
            Log.d("TTA","EventCheckerRunnable");
            dbManager = new DbManager(context);
            ArrayList<Schedule> schedules = dbManager.getListSchdule("tblmondayschedule");

            schedules.addAll(dbManager.getListSchdule("tbltuedayschedule"));
            schedules.addAll(dbManager.getListSchdule("tblwednesdayschedule"));
            schedules.addAll(dbManager.getListSchdule("tblthursdayschedule"));
            schedules.addAll(dbManager.getListSchdule("tblfridayschedule"));
            schedules.addAll(dbManager.getListSchdule("tblsaturdayschedule"));
            schedules.addAll(dbManager.getListSchdule("tblsundayschedule"));

            for(Schedule schedule : schedules){

            }

            eventCheckerHandler.postDelayed(eventCheckerRunnable,10000 );
        }

    }
}
