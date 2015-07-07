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

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


public class SamplesService extends Service {
    public final String SAD_ACTION = "com.p_alex.happinesstracker.SAD_ACTION";
    public final String NORMAL_ACTION = "com.p_alex.happinesstracker.NORMAL_ACTION";
    public final String HAPPY_ACTION = "com.p_alex.happinesstracker.HAPPY_ACTION";

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        String action = intent.getAction();

        SamplesNotification.cancelNotification(this);
        DatabaseOperations database = DatabaseOperations.getInstance(this);

        switch (action) {
            case SAD_ACTION:
                database.insertSmileSample(TableInformation.Table.SAMPLE_VALUE_SAD);
                Log.d("shuffTest", "Pressed SAD");
                break;
            case NORMAL_ACTION:
                database.insertSmileSample(TableInformation.Table.SAMPLE_VALUE_NORMAL);
                Log.d("shuffTest", "Pressed NORMAL");
                break;
            case HAPPY_ACTION:
                database.insertSmileSample(TableInformation.Table.SAMPLE_VALUE_HAPPY);
                Log.d("shuffTest", "Pressed HAPPY");
                break;
        }

        Log.d("SamplesService", "enter SamplesService");
        stopSelf();
    }
}
