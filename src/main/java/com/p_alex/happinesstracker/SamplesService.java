package com.p_alex.happinesstracker;

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
        Log.d("service", "startedssss");
        Toast toast = new Toast(this).makeText(this, "asdasdsad", Toast.LENGTH_SHORT);
        toast.show();
    }
}
