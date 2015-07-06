package com.p_alex.happinesstracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;

public class NotificationJob {
    public static void start(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isNotificationsEnabled = preferences.getBoolean("pref_notifications", true);

        if (isNotificationsEnabled) {
            int samplesNumber = Integer.parseInt(preferences.getString("pref_samples_number", "4"));

            Log.d("pref_samples_number", samplesNumber + "");

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent i = new Intent(context, SamplesReceiver.class);
            PendingIntent alarmI = PendingIntent.getBroadcast(context, 0, i, 0);
            alarmManager.set(
                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + (24 / samplesNumber) * 3600 * 1000,
                    alarmI
            );
        }
    }

    public static boolean isNotificationJobStarted(Context context) {
        boolean isAlarm = (PendingIntent.getBroadcast(context, 0,
                new Intent(context, SamplesReceiver.class),
                PendingIntent.FLAG_NO_CREATE) != null);

        Log.d("is allarm set", (isAlarm) ? "yes" : "no");

        return isAlarm;
    }
}
