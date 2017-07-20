package com.supperapper.timetablealerter.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.constraint.solver.SolverVariable;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.supperapper.timetablealerter.R;
import com.supperapper.timetablealerter.database.DbManager;
import com.supperapper.timetablealerter.dataset.Schedule;
import com.supperapper.timetablealerter.dataset.ScheduleNotify;
import com.supperapper.timetablealerter.dataset.Task;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by User on 7/13/2017.
 */

public class NotificationChecker extends Service {

    private Handler eventCheckerHandler;
    private EventCheckerRunnable eventCheckerRunnable;
    private Context context;
    int id =0;
    DbManager dbManager ;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        eventCheckerHandler = new Handler();
        eventCheckerRunnable = new EventCheckerRunnable();
        eventCheckerHandler.post(eventCheckerRunnable);
        Log.d("TTA","Service RUN");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    class EventCheckerRunnable implements Runnable{

        @Override
        public void run() {
            checkSchdeduleAlert();
            eventCheckerHandler.postDelayed(eventCheckerRunnable,3600000);
        }

    }



    private void checkSchdeduleAlert(){
        Log.d("TTA","CheckForSchedule");
        dbManager = DbManager.getInstance(context);
        ArrayList<ScheduleNotify> schedules = dbManager.getListSchdule("tblmondayschedule");
        schedules.addAll(dbManager.getListSchdule("tbltuedayschedule"));
        schedules.addAll(dbManager.getListSchdule("tblwednesdayschedule"));
        schedules.addAll(dbManager.getListSchdule("tblthursdayschedule"));
        schedules.addAll(dbManager.getListSchdule("tblfridayschedule"));
        schedules.addAll(dbManager.getListSchdule("tblsaturdayschedule"));
        schedules.addAll(dbManager.getListSchdule("tblsundayschedule"));



        Log.d("TTA","Schedule Size"+schedules.size());

        Calendar c = Calendar.getInstance();
        int currentTime = c.get(Calendar.HOUR_OF_DAY);
        for(final ScheduleNotify schedule : schedules){
            Log.d("TTA","Final: " + schedule.getmStartTime());
            final String[] time = schedule.getmStartTime().split(":");
            int scheduleTime = Integer.parseInt(time[0]);
            //if the schedule found
            if(scheduleTime>=currentTime && scheduleTime <=currentTime+1){
                Log.d("TTA","Schedule Found");
                long scheduleml = TimeUnit.MINUTES.toMillis(Long.parseLong(time[1])) + TimeUnit.MINUTES.toMillis(Long.parseLong(time[0]));
                long currenttimeml = TimeUnit.MINUTES.toMillis(c.get(Calendar.HOUR_OF_DAY)) + TimeUnit.MINUTES.toMillis(c.get(Calendar.MINUTE));
                long duration = scheduleml - currenttimeml  ;
                Log.d("TTA","Schedule Found And Wait for " + duration);
                if (duration > (-60000)){
                    eventCheckerHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            String title = schedule.getmSubject() + " (" + schedule.getmAbbreviation()+ ")";
                            String detail = schedule.getmStartTime() + " - " + schedule.getmEndTime();
                            showNotification(id,title,detail);
                            id++;
                        }
                    },duration);
                }
            }
        }
    }

    private void showNotification(int id, String title, String detail){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle(title);
        mBuilder.setSmallIcon(R.drawable.notifi);
        mBuilder.setContentText(detail);
        mBuilder.setVibrate(new long[]{1000,1000,1000,1000});
        mBuilder.setLights(Color.RED,3000,3000);

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setSound(sound);

//        Intent noficationIntent = new Intent(this,LoginActivity.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(this,0,noficationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
//
//        mBuilder.setContentIntent(contentIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id,mBuilder.build());

    }

}
