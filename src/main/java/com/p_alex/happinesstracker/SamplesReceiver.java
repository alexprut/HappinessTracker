package com.p_alex.happinesstracker;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;

public class SamplesReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        NotificationJob.start(context);
        fireNotification(context);

        Log.d("SamplesReceiver", "enter SamplesReceiver");
    }

    public void fireNotification(Context context) {
        Notification.Builder notification = new Notification.Builder(context);
        notification.setSmallIcon(R.drawable.smile_happy)
                .setContentTitle("How do you feel now?")
                .setAutoCancel(true);

        Intent intent = new Intent(context, SamplesService.class);

        PendingIntent sadPendingIntent = PendingIntent.getService(
                context,
                2,
                intent.setAction("com.p_alex.happinesstracker.SAD_ACTION"),
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        PendingIntent normalPendingIntent = PendingIntent.getService(
                context,
                2,
                intent.setAction("com.p_alex.happinesstracker.NORMAL_ACTION"),
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        PendingIntent happyPendingIntent = PendingIntent.getService(
                context,
                2,
                intent.setAction("com.p_alex.happinesstracker.HAPPY_ACTION"),
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        notification.addAction(R.drawable.smile_sad, "Sad", sadPendingIntent);
        notification.addAction(R.drawable.smile_normal, "Normal", normalPendingIntent);
        notification.addAction(R.drawable.smile_happy, "Happy", happyPendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification.build());
    }
}
