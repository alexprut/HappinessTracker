package com.p_alex.happinesstracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public final class TableInformation {
    public TableInformation() {
    }

    public static abstract class Table implements BaseColumns {
        public static final String TABLE_NAME = "samples";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_VALUE = "value";
    }

    public static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + Table.TABLE_NAME + " (" +
                    Table._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Table.COLUMN_NAME_DATE + " DATE" + COMMA_SEP +
                    Table.COLUMN_NAME_VALUE + " INTEGER" +
                    ")";

    public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + Table.TABLE_NAME;
}

