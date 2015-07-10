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

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.melnykov.fab.FloatingActionButton;

public class TodayFragment extends ApplicationFragment {
    private PopupWindow popup;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TodayFragment newInstance(int sectionNumber) {
        TodayFragment fragment = new TodayFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public void onStart() {
        super.onStart();
        updateBackgroundHappiness();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_today, container, false);

        FloatingActionButton button = (FloatingActionButton) rootView.findViewById(R.id.fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSamplesPopup(v);
            }
        });

        return rootView;
    }

    public void updateBackgroundHappiness() {
        DatabaseOperations database = DatabaseOperations.getInstance(getActivity());
        Cursor cursor;

        cursor = database.getTodaySmileSamples();

        int happinessType = calculateHappiness(cursor);

        updateBackgroundImage(happinessType);
        updateBackgroundColor(happinessType);
    }

    public void openSamplesPopup(View view) {
        Log.d("Popup", "show popup");

        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.activity_popup, null);

        ImageButton sadButton = (ImageButton) popupView.findViewById(R.id.button_smile_sad);
        sadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickButtonSad(v);
            }
        });

        ImageButton normalButton = (ImageButton) popupView.findViewById(R.id.button_smile_normal);
        normalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickButtonNormal(v);
            }
        });

        ImageButton happyButton = (ImageButton) popupView.findViewById(R.id.button_smile_happy);
        happyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickButtonHappy(v);
            }
        });

        popup = new PopupWindow(popupView, 800, 300, true);
        popup.setOutsideTouchable(true);
        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.showAsDropDown((FloatingActionButton) getView().findViewById(R.id.fab), -400, 40);
    }

    public RelativeLayout getRelativeLayout() {
        return (RelativeLayout) getView().findViewById(R.id.fragment_today);
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
        DatabaseOperations.getInstance(getActivity()).insertSmileSample(smileType);
        updateBackgroundHappiness();
    }
}
