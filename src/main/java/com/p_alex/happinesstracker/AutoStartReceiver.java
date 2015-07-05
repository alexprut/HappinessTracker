package com.p_alex.happinesstracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class AutoStartReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        NotificationJob.start(context);

        Log.d("AutoStartReceiver", "enter AutoStartReceiver");
    }
}
