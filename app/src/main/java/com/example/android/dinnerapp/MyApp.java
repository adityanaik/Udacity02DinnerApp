package com.example.android.dinnerapp;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by Aditya on 11/8/2015.
 */
public class MyApp extends Application {
    //public static GoogleAnalytics analytics;
    public Tracker mTracker;

    // Get the tracker associated with this app
    public void startTracking() {
        // Does the Tracker already exist?
        // If not, create it
        if (mTracker == null) {
            // Get GoogleAnalytics instance
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // Create a new tracker
            mTracker = analytics.newTracker(R.xml.tracker_app);
            // Enable tracking of activities
            analytics.enableAutoActivityReports(this);
            // Set the log level to verbose
            analytics.getLogger().setLogLevel(Logger.LogLevel.VERBOSE);
        }
    }

    public Tracker getTracker() {
        // Make sure the tracker exists
        startTracking();
        // Then return the tracker
        return mTracker;
    }
}
