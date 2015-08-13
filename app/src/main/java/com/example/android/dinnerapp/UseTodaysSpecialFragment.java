package com.example.android.dinnerapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Aditya on 13/8/2015.
 */
/*
 * This fragment displays a Today's Special box with a solid heading
 */
public class UseTodaysSpecialFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.todays_special, container, false);
    }
}
