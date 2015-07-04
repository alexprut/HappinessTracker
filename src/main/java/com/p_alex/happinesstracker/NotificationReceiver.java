package com.p_alex.happinesstracker;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class NotificationReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        Log.d("shuffTest", "I Arrived!!!!");
        String action = intent.getAction();

        String s = context.NOTIFICATION_SERVICE;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(s);
        notificationManager.cancel(1);

        Toast toast = new Toast(context).makeText(context, "asd", Toast.LENGTH_SHORT);
        toast.show();

        if("SAD_ACTION".equals(action))
        {
            Log.d("shuffTest", "Pressed YES");
        }
        else if("NORMAL_ACTION".equals(action))
        {
            Log.d("shuffTest", "Pressed NO");
        }
        else if("HAPPY_ACTION".equals(action))
        {
            Log.d("shuffTest", "Pressed MAYBE");
        }
    }
}
