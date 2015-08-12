package com.example.android.dinnerapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class ShowAllDinnersActivity extends ListActivity {

    String selectedDinnerExtrasKey = String.valueOf(R.string.selected_dinner);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_all_dinners);

        // Start timing how long it takes to display the list of products
        long startTime = System.nanoTime();

        // Get the array of all the dinners
        Dinner dinner = new Dinner();
        String[] allDinners = dinner.getAllDinners(this);
        // Create an array adapter
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                R.layout.show_dinner_in_row,
                R.id.textview_dinner_row,
                allDinners);
        // Attach the ArrayAdapter to the list view
        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(arrayAdapter);

        // Stop timing how long it takes to display the list of products
        long stopTime = System.nanoTime();
        long elapsedTime = (stopTime - startTime) / 1000000;

        // Display a toast to show the elapsed time
        Utility.showMyToast("Elapsed time: " + Long.toString(elapsedTime) + " milliseconds", this);

        //Report to analytics how long it took to display this list
        sendAnalyticsTimingHit(elapsedTime);
    }

    private void sendAnalyticsTimingHit(long elapsedTime) {
        Tracker tracker = ((MyApp) getApplication()).getTracker();

        // Build and send timing data
        tracker.send(new HitBuilders.TimingBuilder()
                .setCategory("list of dinners")
                .setValue(elapsedTime)
                .setLabel("Display duration")
                .setVariable("duration")
                .build());
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        // Do something when a list item is clicked
        super.onListItemClick(l, v, position, id);

        String value = (String) getListView().getItemAtPosition(position);


        Utility.showMyToast("selected dinner is " + value, this);
        Intent intent = new Intent(this, OrderDinnerActivity.class);
        intent.putExtra(selectedDinnerExtrasKey, value);

        startActivity(intent);
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all_dinners, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
