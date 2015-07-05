package com.p_alex.happinesstracker;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


public class SamplesService extends Service {
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        String action = intent.getAction();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(1);

        DatabaseOperations database = new DatabaseOperations(this);

        switch (action) {
            case "com.p_alex.happinesstracker.SAD_ACTION":
                database.insertSmileSample(1);
                Log.d("shuffTest", "Pressed SAD");
                break;
            case "com.p_alex.happinesstracker.NORMAL_ACTION":
                database.insertSmileSample(2);
                Log.d("shuffTest", "Pressed NORMAL");
                break;
            case "com.p_alex.happinesstracker.HAPPY_ACTION":
                database.insertSmileSample(3);
                Log.d("shuffTest", "Pressed HAPPY");
                break;
        }

        Log.d("SamplesService", "enter SamplesService");
        stopSelf();
    }
}
