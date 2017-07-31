package com.supperapper.timetablealerter.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.supperapper.timetablealerter.service.NotificationChecker;

/**
 * Created by User on 7/31/2017.
 */

public class BootCompletedIntentReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Intent pushIntent = new Intent(context, NotificationChecker.class);
            context.startService(pushIntent);
            Log.d("OnReceive","Boot Complete Start Service");
        }
    }
}

