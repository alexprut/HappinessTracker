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
