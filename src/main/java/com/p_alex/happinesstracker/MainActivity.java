package com.p_alex.happinesstracker;

import java.util.Locale;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;


public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {
    public final static String GRATIFICATION_MESSAGE = "com.p_alex.happinesstracker.GRATIFICATION_MESSAGE";
    public final static int SAMPLE_SAD = 1;
    public final static int SAMPLE_NORMAL = 2;
    public final static int SAMPLE_HAPPY = 3;
    public final static int COLOR_SAD = 0xFFFFF59D;
    public final static int COLOR_NORMAL = 0xFFFFF176;
    public final static int COLOR_HAPPY = 0xFFFFEB3B;
    private PopupWindow popup;

    private DatabaseOperations database = new DatabaseOperations(this);

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NotificationJob.start(this);

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

        updateBackgroundColor(0);
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
        popup.dismiss();
        clickSmileButton(SAMPLE_SAD);
    }

    public void clickButtonNormal(View view) {
        popup.dismiss();
        clickSmileButton(SAMPLE_NORMAL);
    }

    public void clickButtonHappy(View view) {
        popup.dismiss();
        clickSmileButton(SAMPLE_HAPPY);
    }

    public void clickSmileButton(int smileType) {
        database.insertSmileSample(smileType);
        updateBackgroundColor(mViewPager.getCurrentItem());
    }

    public void updateBackgroundColor(int position) {
        Log.d("current item", position + "");
        View relativeLayout = (View) findViewById(R.id.pager);

        Cursor cursor;

        if (position == 0) {
            cursor = database.getTodaySmileSamples();
        } else {
            cursor = database.getSmileSamples();
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
                relativeLayout.setBackgroundColor(COLOR_SAD);
            }

            if (Double.compare(max, countNormalSmiles) == 0) {
                relativeLayout.setBackgroundColor(COLOR_NORMAL);
            }

            if (Double.compare(max, countHappySmiles) == 0) {
                relativeLayout.setBackgroundColor(COLOR_HAPPY);
            }
        }
    }

    public void openSamplesPopup(View view) {
        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.activity_popup, null);
        popup = new PopupWindow(
                popupView,
                860,
                300,
                true
        );

        Log.d("Popup", "show popup");

        popup.showAsDropDown((FloatingActionButton) findViewById(R.id.fab), -430, 40);
    }

    public void openSettings() {
        startActivity(new Intent(this, SettingsActivity.class));
    }
}
