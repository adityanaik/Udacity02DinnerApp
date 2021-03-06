/*
 * Copyright (C) 2015 Google Inc. All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.example.android.dinnerapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.TagManager;

import java.util.concurrent.TimeUnit;


public class MainActivity extends Activity {

    TagManager mTagManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Make sure that Analytics tracking has started
        ((MyApp) getApplication()).startTracking();

        loadGTMContainer();
    }

    /*
     * Show a pop up menu of food preferences.
     * Menu items are defined in menus/food_prefs_menu.xml
     */
    public void showFoodPrefsMenu(View view) {
        // Utility.showMyToast("I will show you a menu", this);
        android.widget.PopupMenu popup = new android.widget.PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.food_prefs_menu, popup.getMenu());

        // Set the action of the menu
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                getDinnerSuggestion(item.getItemId());
                return true;
            }
        });
        // Show the popup menu
        popup.show();
    }

    /*
     * Suggest dinner for tonight.
     * This method is invoked by the button click action of the food prefs menu.
     * We use the Dinner class to figure out the dinner, based on the food pref.

     */
    public String getDinnerSuggestion(int item) {

        // Get a new Dinner, and use it to get tonight's dinner
        Dinner dinner = new Dinner(this, item);
        String dinnerChoice = dinner.getDinnerTonight();
        // Utility.showMyToast("dinner suggestion: " + dinnerChoice, this);

        // Start an intent to show the dinner suggestion
        // Put the suggested dinner in the Intent's Extras bundle
        Intent dinnerIntent = new Intent(this, ShowDinnerActivity.class);

        dinnerIntent.putExtra(String.valueOf(R.string.selected_dinner), dinnerChoice);
        startActivity(dinnerIntent);

        return dinnerChoice;
    }

    public void showDinnerList(View view) {
        // Start an intent to show ShowAllDinnersActivity
        Intent allDinnerIntent = new Intent(this, ShowAllDinnersActivity.class);
        startActivity(allDinnerIntent);
    }

    public void showDailySpecial(View view) {
        // Show the food pref menu
        android.widget.PopupMenu popup = new android.widget.PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.food_prefs_menu, popup.getMenu());

        // Set the action of the menu
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Save the food pref in the data layer
                putFoodPrefinDatalayer(item);
                // Show the daily special
                startShowDailySpecialActivity();
                return true;
            }
        });
        // Show the popup menu
        popup.show();
    }

    // Load a TagManager container
    public void loadGTMContainer() {
        // Get a TagManager instance
        mTagManager = ((MyApp) getApplication()).getmTagManager();

        // Enable verbose logging
        mTagManager.setVerboseLoggingEnabled(true);

        // Load the container
        PendingResult pendingResult =
                mTagManager.loadContainerPreferFresh("GTM-MFRD65", R.raw.gtm_default);

        // Define the callback to store the loaded container
        pendingResult.setResultCallback(new ResultCallback<ContainerHolder>() {

            @Override
            public void onResult(ContainerHolder containerHolder) {
                // if unsuccessful, return
                if (!containerHolder.getStatus().isSuccess()) {
                    // TODO: 15/8/2015 Deal with failure
                    return;
                }

                // Manually refresh the container holder
                // Can only do this every 15 minutes or so
                containerHolder.refresh();

                // Set the container holder, only want one per running app
                // We can retrieve it later as needed
                ((MyApp) getApplication()).setmContainerHolder(containerHolder);
            }
        }, 2, TimeUnit.SECONDS);
    }

    public void putFoodPrefinDatalayer(MenuItem item) {
        TagManager tagManager = ((MyApp) getApplication()).getmTagManager();

        DataLayer dataLayer = tagManager.getDataLayer();
        switch (item.getItemId()) {
            case R.id.vegan_pref:
                dataLayer.push("food-pref", "vegan");
                break;
            case R.id.vegetarian_pref:
                dataLayer.push("food-pref", "vegetarian");
                break;
            case R.id.fish_pref:
                dataLayer.push("food-pref", "fish");
                break;
            case R.id.meat_pref:
                dataLayer.push("food-pref", "meat");
                break;
            case R.id.unrestricted_pref:
                dataLayer.push("food-pref", "unrestricted");
                break;
            default:
                break;
        }
    }

    public void startShowDailySpecialActivity() {
        // Start an activity to show the daily special
        startActivity(new Intent(this, ShowDailySpecialActivity.class));

        // Get the food pref out of the DataLayer
        DataLayer dataLayer = mTagManager.getDataLayer();

        // Push an event into the data layer
        // which will trigger sending a hit to Google Analytics
        dataLayer.pushEvent("openScreen",
                DataLayer.mapOf(
                        "screen-name", "Show Daily Special"
                ));
    }
}

