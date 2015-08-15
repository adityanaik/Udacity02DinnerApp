package com.example.android.dinnerapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tagmanager.ContainerHolder;

/**
 * Created by Aditya on 13/8/2015.
 */
public class ShowDailySpecialActivity extends Activity {
    String selectedDinnerExtrasKey = String.valueOf(R.string.selected_dinner);
    public String mDailySpecial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_daily_special);

        // Get the text view we are using to show today' special
        TextView textViewTodaysSpecial = (TextView) findViewById(R.id.textView_todays_special);

        updateDailySpecial();

        // Set the text to mDailySpecial
        textViewTodaysSpecial.setText(mDailySpecial);
    }

    public void orderOnline(View view) {
        // Start an intent to allow the user to order dinner online
        Intent intent = new Intent(this, OrderDinnerActivity.class);
        intent.putExtra(selectedDinnerExtrasKey, mDailySpecial);
        startActivity(intent);
    }

    /**
     * Updates the value of mDailySpecial to that of containerHolder
     */
    public void updateDailySpecial() {
        // Get the containerHolder
        ContainerHolder containerHolder = ((MyApp) getApplication()).getmContainerHolder();
        // Get the value of the "daily-special" key from the containerHolder
        // The keys need to match exactly the keys you set in the Tag Manager
        mDailySpecial = containerHolder.getContainer().getString("daily-special");
    }
}
