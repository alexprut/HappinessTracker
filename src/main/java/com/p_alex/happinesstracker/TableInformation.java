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
        public static final int SAMPLE_VALUE_SAD = 1;
        public static final int SAMPLE_VALUE_NORMAL = 2;
        public static final int SAMPLE_VALUE_HAPPY = 3;
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

