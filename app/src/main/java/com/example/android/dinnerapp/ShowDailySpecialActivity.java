package com.example.android.dinnerapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Aditya on 13/8/2015.
 */
public class ShowDailySpecialActivity extends Activity {
    String selectedDinnerExtrasKey = String.valueOf(R.string.selected_dinner);
    public String mDailySpecial = "Fried egg with kit kat rashers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_daily_special);

        // Get the text view we are using to show today' special
        TextView textViewTodaysSpecial = (TextView) findViewById(R.id.textView_todays_special);
        // Set the text, currently hard wire it to a constant string
        textViewTodaysSpecial.setText(mDailySpecial);
    }

    public void orderOnline(View view) {
        // Start an intent to allow the user to order dinner online
        Intent intent = new Intent(this, OrderDinnerActivity.class);
        intent.putExtra(selectedDinnerExtrasKey, mDailySpecial);
        startActivity(intent);
    }
}
