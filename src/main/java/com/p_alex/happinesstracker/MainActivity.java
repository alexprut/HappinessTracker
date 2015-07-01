package com.p_alex.happinesstracker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends ActionBarActivity {
    public final static String GRATIFICATION_MESSAGE = "com.p_alex.happinesstracker.GRATIFICATION_MESSAGE";
    public final static int SAMPLE_SAD = 1;
    public final static int SAMPLE_NORMAL = 2;
    public final static int SAMPLE_HAPPY = 3;
    public final static int COLOR_SAD = 0xFFFFF59D;
    public final static int COLOR_NORMAL = 0xFFFFF176;
    public final static int COLOR_HAPPY = 0xFFFFEB3B;

    private DatabaseOperations database = new DatabaseOperations(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateBackgroundColor();
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
        }

        return super.onOptionsItemSelected(item);
    }

    public void clickButtonSad(View view) {
        clickSmileButton(SAMPLE_SAD);
    }

    public void clickButtonNormal(View view) {
        clickSmileButton(SAMPLE_NORMAL);
    }

    public void clickButtonHappy(View view) {
        clickSmileButton(SAMPLE_HAPPY);
    }

    public void clickSmileButton(int smileType) {
        insertSmileSample(smileType);
        updateBackgroundColor();
    }

    public void updateBackgroundColor() {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relative_layout);

        Cursor cursor = getTodaySmileSamples();

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

    public void openAbout() {
        startActivity(new Intent(this, AboutActivity.class));
    }

    public void openSettings() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    public long insertSmileSample(int sampleType) {
        SQLiteDatabase db = database.getWritableDatabase();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        Log.d("date time now", dateFormat.format(new Date()));

        ContentValues contentValues = new ContentValues();
        contentValues.put(TableInformation.Table.COLUMN_NAME_VALUE, sampleType);
        contentValues.put(TableInformation.Table.COLUMN_NAME_DATE, dateFormat.format(new Date()));

        return db.insert(TableInformation.Table.TABLE_NAME, null, contentValues);
    }

    public Cursor getSmileSamples() {
        SQLiteDatabase db = database.getReadableDatabase();

        String[] columns = {
                TableInformation.Table.COLUMN_NAME_DATE,
                TableInformation.Table.COLUMN_NAME_VALUE
        };

        return db.query(TableInformation.Table.TABLE_NAME, columns, null, null, null, null, null);
    }

    public Cursor getTodaySmileSamples() {
        SQLiteDatabase db = database.getReadableDatabase();

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
}
