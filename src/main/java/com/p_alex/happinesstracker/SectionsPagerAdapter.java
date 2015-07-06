package com.p_alex.happinesstracker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Locale;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        Fragment fragment;

        switch (position) {
            case 1:
                fragment = WeekFragment.newInstance(position + 1);
                break;
            case 2:
                fragment = MonthFragment.newInstance(position + 1);
                break;
            default:
                fragment = TodayFragment.newInstance(position + 1);
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return "Today";
            case 1:
                return "Week";
            case 2:
                return "Month";
        }
        return null;
    }
}
