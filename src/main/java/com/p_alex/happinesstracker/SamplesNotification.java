/*
 * Copyright (c) 2015, P.Alex <web.is.art@p-alex.com>, All rights reserved.
 *
 * This file is part of "Happiness Tracker".
 *
 * Happiness Tracker is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *
 * Happiness Tracker is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * and a copy of the GNU Lesser General Public License
 * along with Happiness Tracker.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.p_alex.happinesstracker;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;

public class SamplesNotification {
    public static final int NOTIFICATION_ID = 1;
    public static final int DEFAULT_NOTIFICATION_NUMBER = 4;

    public static void startJob(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isNotificationsEnabled = preferences.getBoolean("pref_notifications", true);

        if (isNotificationsEnabled) {
            int samplesNumber = Integer.parseInt(
                    preferences.getString("pref_samples_number", DEFAULT_NOTIFICATION_NUMBER + "")
            );

            Log.d("pref_samples_number", samplesNumber + "");

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(
                    context,
                    0,
                    new Intent(context, SamplesReceiver.class),
                    0
            );
            alarmManager.set(
                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + (24 / samplesNumber) * 3600 * 1000,
                    alarmIntent
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

    public static void fireNotification(Context context) {
        android.app.Notification.Builder notification = new android.app.Notification.Builder(context);
        notification.setSmallIcon(R.mipmap.logo_notification)
                .setContentTitle(context.getString(R.string.notification_title))
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

        notification.addAction(
                R.drawable.smile_sad,
                context.getString(R.string.text_smile_sad),
                sadPendingIntent
        );
        notification.addAction(
                R.drawable.smile_normal,
                context.getString(R.string.text_smile_normal),
                normalPendingIntent
        );
        notification.addAction(
                R.drawable.smile_happy,
                context.getString(R.string.text_smile_happy),
                happyPendingIntent
        );

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification.build());
    }

    public static void cancelNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);
    }
}
