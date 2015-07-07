package com.p_alex.happinesstracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AutoStartReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        SamplesNotification.startJob(context);

        Log.d("AutoStartReceiver", "enter AutoStartReceiver");
    }
}
