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

import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceActivity;

public class SettingsActivity extends PreferenceActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
