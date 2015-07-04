package com.p_alex.happinesstracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AutoStartReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, SamplesService.class);
        context.startService(i);
        Log.d("service autostart", "started"); // TODO remove me
    }
}
