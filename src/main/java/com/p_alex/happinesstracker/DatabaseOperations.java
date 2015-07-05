package com.p_alex.happinesstracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseOperations extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Samples.db";

    public DatabaseOperations(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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

    public Cursor getTodaySmileSamples() {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {
                TableInformation.Table.COLUMN_NAME_DATE,
                TableInformation.Table.COLUMN_NAME_VALUE
        };

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();

        return db.query(
                TableInformation.Table.TABLE_NAME,
                columns,
                "date >= '" + dateFormat.format(date) + " 00:00:00'",
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
