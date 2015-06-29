package com.p_alex.happinesstracker;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {
    public final static String MOTIVATION = "com.p_alex.happinesstracker.MOTIVATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            case R.id.action_about:
                openAbout();
            break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void clickButtonSad(View view) {
        Intent intent = new Intent(this, Stats.class);
        EditText text = (EditText) findViewById(R.id.motivation);
        String message = text.getText().toString();
        intent.putExtra(MOTIVATION, message);
        startActivity(intent);
    }

    public void openAbout() {
        // TODO implement method
    }
}
