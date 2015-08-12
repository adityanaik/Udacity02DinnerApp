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
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;


public class OrderDinnerActivity extends Activity {
    String selectedDinnerExtrasKey = String.valueOf(R.string.selected_dinner);

    // Declare global variables for the selected dinner and its id,
    // which are initialized in onStart().
    String thisDinner;
    String thisDinnerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_dinner);
    }

    protected void onStart() {
        super.onStart();

        // Set the heading
        TextView heading_tv = (TextView) findViewById(R.id.textView_info_heading);
        heading_tv.setText(getResources().getText(R.string.order_online_heading));

        // Set the text
        TextView tv = (TextView) findViewById(R.id.textView_info);

        String dinner = getIntent().getStringExtra(selectedDinnerExtrasKey);
        tv.setText("This is where you will order the selected dinner: \n\n" +
                dinner);
        String dinnerId = Utility.getDinnerId(dinner);

        thisDinner = dinner;
        thisDinnerId = dinnerId;

        sendViewProductHit();
    }

    /**
     * G-Analytics code to track an event of product and product action details
     * for e-commerce analysis.
     */
    public void sendViewProductHit() {
        Product product = new Product()
                .setName("Dinner")
                .setPrice(5)
                .setVariant(thisDinner)
                .setId(thisDinnerId)
                .setQuantity(1);

        ProductAction productAction = new ProductAction(ProductAction.ACTION_DETAIL);

        Tracker tracker = ((MyApp) getApplication()).getTracker();
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("Shopping steps")
                .setAction("View Order Dinner screen")
                .setLabel(thisDinner)
                .addProduct(product)
                .setProductAction(productAction)
                .build());
    }

    public void addDinnerToCart(View view) {
        /* Code to implement the selected dinner to cart goes here.
        In this project, we skip implementing that functionality
        and instead focus on sending the "Add to Cart" hit to the Analytics.*/
        Utility.showMyToast(thisDinner + " \"may\" have been added to the cart. :P", this);

        sendAddToCartHit();

        // Show the Start Checkout button
        Button button = (Button) findViewById(R.id.start_checkout_button);
        button.setVisibility(View.VISIBLE);

        // Hide the Add to Cart button
        button = (Button) findViewById(R.id.add_to_cart_button);
        button.setVisibility(View.INVISIBLE);

    }

    /**
     * G-Analytics code to track an event of "Add to cart" hit for e-commerce analysis.
     */
    public void sendAddToCartHit() {
        Product product = new Product()
                .setName("Dinner")
                .setPrice(5)
                .setVariant(thisDinner)
                .setId(thisDinnerId)
                .setQuantity(1);

        ProductAction productAction = new ProductAction(ProductAction.ACTION_ADD);

        Tracker tracker = ((MyApp) getApplication()).getTracker();
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("Shopping steps")
                .setAction("Add to Cart hit")
                .setLabel(thisDinner)
                .addProduct(product)
                .setProductAction(productAction)
                .build());
    }

    public void startCheckout(View view) {
        /* Code to implement the checkout process goes here.
        In this project, we skip implementing that functionality
        and instead focus on sending the Checkout hit to the Analytics.*/
        Utility.showMyToast("Checkout process begins...", this);

        sendStartCheckoutHit();
    }

    /**
     * G-Analytics code to track an event of Checkout hit for e-commerce analysis.
     */
    private void sendStartCheckoutHit() {
        // We just assume that the currently selected dinner is in the cart.
        Product product = new Product()
                .setName("Dinner")
                .setPrice(5)
                .setVariant(thisDinner)
                .setId(thisDinnerId)
                .setQuantity(1);

        ProductAction productAction = new ProductAction(ProductAction.ACTION_CHECKOUT);

        Tracker tracker = ((MyApp) getApplication()).getTracker();
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("Shopping steps")
                .setAction("Start checkout")
                .setLabel(thisDinner)
                .addProduct(product)
                .setProductAction(productAction)
                .build());
    }
}
