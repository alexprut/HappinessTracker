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

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupWindow;

import com.melnykov.fab.FloatingActionButton;


public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {
    private PopupWindow popup;
    private DatabaseOperations database;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.database = DatabaseOperations.getInstance(this);

        SamplesNotification.startJob(this);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
                updateBackgroundColor(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }

        updateBackgroundColor(mViewPager.getCurrentItem());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_menu_settings:
                openSettings();
                break;
            default:
                openSettings();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    public void clickButtonSad(View view) {
        clickSmileButton(TableInformation.Table.SAMPLE_VALUE_SAD);
    }

    public void clickButtonNormal(View view) {
        clickSmileButton(TableInformation.Table.SAMPLE_VALUE_NORMAL);
    }

    public void clickButtonHappy(View view) {
        clickSmileButton(TableInformation.Table.SAMPLE_VALUE_HAPPY);
    }

    public void clickSmileButton(int smileType) {
        popup.dismiss();
        database.insertSmileSample(smileType);
        updateBackgroundColor(mViewPager.getCurrentItem());
    }

    public void updateBackgroundColor(int position) {
        Log.d("Current tab item", position + "");
        View relativeLayout = (View) findViewById(R.id.pager);

        Cursor cursor;

        switch (position) {
            case 1:
                cursor = database.getSmileSamples();
                break;
            case 2:
                cursor = database.getSmileSamples();
                break;
            default:
                cursor = database.getTodaySmileSamples();
                break;
        }


        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Double countSadSmiles = 0.0;
            Double countNormalSmiles = 0.0;
            Double countHappySmiles = 0.0;

            while (!cursor.isLast()) {
                switch (cursor.getInt(1)) {
                    case 1:
                        countSadSmiles++;
                        break;
                    case 2:
                        countNormalSmiles++;
                        break;
                    case 3:
                        countHappySmiles++;
                        break;
                }

                cursor.moveToNext();
            }

            Double max = Math.max(countSadSmiles, Math.max(countNormalSmiles, countHappySmiles));

            if (Double.compare(max, countSadSmiles) == 0) {
                relativeLayout.setBackgroundColor(getResources().getColor(R.color.color_sad));
            }

            if (Double.compare(max, countNormalSmiles) == 0) {
                relativeLayout.setBackgroundColor(getResources().getColor(R.color.color_normal));
            }

            if (Double.compare(max, countHappySmiles) == 0) {
                relativeLayout.setBackgroundColor(getResources().getColor(R.color.color_happy));
            }
        }
    }

    public void openSamplesPopup(View view) {
        Log.d("Popup", "show popup");

        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.activity_popup, null);

        popup = new PopupWindow(popupView, 860, 300, true);
        popup.showAsDropDown((FloatingActionButton) findViewById(R.id.fab), -430, 40);
    }

    public void openSettings() {
        startActivity(new Intent(this, SettingsActivity.class));
    }
}
