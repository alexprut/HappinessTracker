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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatabaseOperations extends SQLiteOpenHelper {
    private static DatabaseOperations instance;
    private Context context;
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Samples.db";

    private DatabaseOperations(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public static DatabaseOperations getInstance(Context context) {
        if (instance == null) {
            synchronized (DatabaseOperations.class) {
                if (instance == null) {
                    instance = new DatabaseOperations(context);
                }
            }
        }

        return instance;
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TableInformation.SQL_CREATE_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(TableInformation.SQL_DELETE_TABLE);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public long insertSmileSample(int sampleType) {
        SQLiteDatabase db = this.getWritableDatabase();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        ContentValues contentValues = new ContentValues();
        contentValues.put(TableInformation.Table.COLUMN_NAME_VALUE, sampleType);
        contentValues.put(TableInformation.Table.COLUMN_NAME_DATE, dateFormat.format(new Date()));

        return db.insert(TableInformation.Table.TABLE_NAME, null, contentValues);
    }

    private Date getPreviousDateFromToday(int offset) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, offset);
        return cal.getTime();
    }

    private String last30DaysDate() {
        return new SimpleDateFormat("yyyy/MM/dd")
                .format(getPreviousDateFromToday(-30)) + " 00:00:00";
    }

    private String last7DaysDate() {
        return new SimpleDateFormat("yyyy/MM/dd")
                .format(getPreviousDateFromToday(-7)) + " 00:00:00";
    }

    private String todayDate() {
        return new SimpleDateFormat("yyyy/MM/dd")
                .format(getPreviousDateFromToday(0)) + " 00:00:00";
    }

    public Cursor getMonthSmileSamples() {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {
                TableInformation.Table.COLUMN_NAME_DATE,
                TableInformation.Table.COLUMN_NAME_VALUE
        };

        Log.d("last 30 days date", last30DaysDate());

        return db.query(
                TableInformation.Table.TABLE_NAME,
                columns,
                "date >= '" + last30DaysDate() + "' AND date < '" + todayDate() + "'",
                null, null, null, null);
    }

    public Cursor getWeekSmileSamples() {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {
                TableInformation.Table.COLUMN_NAME_DATE,
                TableInformation.Table.COLUMN_NAME_VALUE
        };

        Log.d("last 7 days date", last7DaysDate());

        return db.query(
                TableInformation.Table.TABLE_NAME,
                columns,
                "date >= '" + last7DaysDate() + "' AND date < '" + todayDate() + "'",
                null, null, null, null);
    }

    public Cursor getTodaySmileSamples() {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {
                TableInformation.Table.COLUMN_NAME_DATE,
                TableInformation.Table.COLUMN_NAME_VALUE
        };

        return db.query(
                TableInformation.Table.TABLE_NAME,
                columns,
                "date >= '" + todayDate() + "'",
                null, null, null, null);
    }

    public Cursor getSmileSamples() {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {
                TableInformation.Table.COLUMN_NAME_DATE,
                TableInformation.Table.COLUMN_NAME_VALUE
        };

        return db.query(TableInformation.Table.TABLE_NAME, columns, null, null, null, null, null);
    }
}
