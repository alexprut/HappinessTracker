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
