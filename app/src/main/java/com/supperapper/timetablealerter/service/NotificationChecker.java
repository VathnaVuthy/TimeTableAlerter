package com.supperapper.timetablealerter.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
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
import com.supperapper.timetablealerter.activity.SchoolDetailActivity;
import com.supperapper.timetablealerter.activity.TaskDetailActivity;
import com.supperapper.timetablealerter.database.DbManager;
import com.supperapper.timetablealerter.dataset.MapClass;
import com.supperapper.timetablealerter.dataset.Schedule;
import com.supperapper.timetablealerter.dataset.ScheduleNotify;
import com.supperapper.timetablealerter.dataset.Task;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by User on 7/13/2017.
 */

public class NotificationChecker extends Service {

    private Handler eventCheckerHandler;
    private EventCheckerRunnable eventCheckerRunnable;
    private Context context;
    int id = 0;
    DbManager dbManager;
    SharedPreferences preferences;
    long alertBefore;

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        alertBefore = (long) preferences.getInt("alertTime", 10);

        Log.d("GET TIME: ", "TIME: IS " + alertBefore);
        context = this;
        eventCheckerHandler = new Handler();
        eventCheckerRunnable = new EventCheckerRunnable();
        eventCheckerHandler.post(eventCheckerRunnable);
        Log.d("TTA", "Service RUN");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    class EventCheckerRunnable implements Runnable {

        @Override
        public void run() {
            checkSchdeduleAlert();
            eventCheckerHandler.postDelayed(eventCheckerRunnable, 3600000);
        }

    }


    private void checkSchdeduleAlert() {
        Calendar c = Calendar.getInstance();
        dbManager = DbManager.getInstance(context);
        ArrayList<ScheduleNotify> schedules = new ArrayList<>();
        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                schedules.addAll(dbManager.getListSchdule("tblsundayschedule"));
                break;
            case 2:
                schedules.addAll(dbManager.getListSchdule("tblmondayschedule"));
                break;
            case 3:
                schedules.addAll(dbManager.getListSchdule("tbltuesdayschedule"));
                break;
            case 4:
                schedules.addAll(dbManager.getListSchdule("tblwednesdayschedule"));
                break;
            case 5:
                schedules.addAll(dbManager.getListSchdule("tblthursdayschedule"));
                break;
            case 6:
                schedules.addAll(dbManager.getListSchdule("tblfridayschedule"));
                break;
            case 7:
                schedules.addAll(dbManager.getListSchdule("tblsaturdayschedule"));
                break;
        }


        int currentTime;
        Log.d("Schedule size", schedules.size() + "");
        for (final ScheduleNotify schedule : schedules) {
            final String[] time = schedule.getmStartTime().split(":");
            int scheduleTime = Integer.parseInt(time[0]);
            currentTime = c.get(Calendar.HOUR_OF_DAY);
            //if the schedule found
            Log.d("RUN RUN", "RUN RUN");
            if (scheduleTime >= currentTime && scheduleTime <= currentTime + 1) {
                long scheduleml = TimeUnit.MINUTES.toMillis(Long.parseLong(time[1])) + TimeUnit.HOURS.toMillis(Long.parseLong(time[0]));
                long currenttimeml = TimeUnit.HOURS.toMillis(c.get(Calendar.HOUR_OF_DAY)) + TimeUnit.MINUTES.toMillis(c.get(Calendar.MINUTE));
                Log.d("schedule", time[0] + " - " + time[1]);
                Log.d("current", c.get(Calendar.HOUR_OF_DAY) + " - " + c.get(Calendar.MINUTE));
                Log.d("SCHEDULE TIME: ", scheduleml + "");
                Log.d("Current TIME: ", currenttimeml + "");
                if (scheduleml < currenttimeml) {
                    Log.d("Meet Return : ", "RETURN");
                    return;
                }
                long scheduleTimeToRun = (scheduleml - currenttimeml);
                long timeToAlertBeforeSchedule = ((alertBefore * 360000) / 6);
                long duration = scheduleTimeToRun - timeToAlertBeforeSchedule;
                if (scheduleTimeToRun < timeToAlertBeforeSchedule) {
                    eventCheckerHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            String title = schedule.getmSubject() + " (" + schedule.getmAbbreviation() + ")";
                            String detail = schedule.getmStartTime() + " - " + schedule.getmEndTime();
                            showScheduleNotification(id, title, detail, schedule);
                            id++;
                        }
                    });
                } else {
                    eventCheckerHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            String title = schedule.getmSubject() + " (" + schedule.getmAbbreviation() + ")";
                            String detail = schedule.getmStartTime() + " - " + schedule.getmEndTime();
                            showScheduleNotification(id, title, detail, schedule);
                            id++;
                        }
                    }, duration);
                }
            }
        }

        ArrayList<Task> tasks = dbManager.getListTask(new String[]{"EXAM"});
        tasks.addAll(dbManager.getListTask(new String[]{"MEETING"}));
        tasks.addAll(dbManager.getListTask(new String[]{"OTHERS"}));
        tasks.addAll(dbManager.getListTask(new String[]{"R.ACTIVITY"}));
        tasks.addAll(dbManager.getListTask(new String[]{"WEDDING"}));
        SimpleDateFormat df = new SimpleDateFormat("EEEE-d-M-yyyy");
        Date currentDate = new Date();

        for (final Task task : tasks) {
            final String taskDate = task.getmDate();
            if (taskDate.equals(df.format(currentDate))) {
                eventCheckerHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        String title = task.getmTaskType();
                        String detail = task.getmTopic() + " - " + task.getmDate();
                        showTaskNotification(id, title, detail, task);

                        id++;
                    }
                });
            }
        }
    }


    private void showScheduleNotification(int id, String title, String detail, ScheduleNotify schedule) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle(title);
        mBuilder.setSmallIcon(R.drawable.notifi);
        mBuilder.setContentText(detail);
        mBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000});
        mBuilder.setLights(Color.BLUE, 3000, 3000);

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setSound(sound);

        Intent intent = new Intent(this, SchoolDetailActivity.class);
        intent.putExtra("id", String.valueOf(schedule.getId()));
        intent.putExtra("subject", schedule.getmSubject());
        intent.putExtra("abb", schedule.getmAbbreviation());
        intent.putExtra("school", schedule.getmSchool());
        intent.putExtra("room", schedule.getmRoom());
        intent.putExtra("teacher", schedule.getmTeacher());
        intent.putExtra("contact", schedule.getmContact());
        intent.putExtra("timestart", schedule.getmStartTime());
        intent.putExtra("timeend", schedule.getmEndTime());
        intent.putExtra("date", schedule.getmDay());

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(contentIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, mBuilder.build());
    }

    private void showTaskNotification(int id, String title, String detail, Task task) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle(title);
        mBuilder.setSmallIcon(R.drawable.notifi);
        mBuilder.setContentText(detail);
        mBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000});
        mBuilder.setLights(Color.BLUE, 3000, 3000);

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setSound(sound);

        Intent intent = new Intent(context, TaskDetailActivity.class);
        intent.putExtra("topic", task.getmTopic());
        intent.putExtra("subject", task.getmSubject());
        intent.putExtra("tasktype", task.getmTaskType());
        intent.putExtra("date", task.getmDate());
        intent.putExtra("note", task.getmNote());
        intent.putExtra("id", task.getmID());
        Log.d("TTA", task.getmID() + "");
        String ID = task.getmID();
        DbManager dbManager = DbManager.getInstance(context);
        int mapId = dbManager.getMapIdByTaskId(Integer.parseInt(ID));
        MapClass mapClass = dbManager.getMapFromDb(mapId);
        intent.putExtra("mapId", mapClass.getId());
        intent.putExtra("mapName", mapClass.getName());
        intent.putExtra("mapAddress", mapClass.getAddress());
        intent.putExtra("mapPhone", mapClass.getPhone());
        intent.putExtra("mapWebsite", mapClass.getWebsite());
        intent.putExtra("mapLat", mapClass.getLat());
        intent.putExtra("mapLang", mapClass.getLang());
        dbManager.close();


        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(contentIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, mBuilder.build());

    }

}
