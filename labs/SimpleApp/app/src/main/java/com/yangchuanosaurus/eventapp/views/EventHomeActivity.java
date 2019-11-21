package com.yangchuanosaurus.eventapp.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.yangchuanosaurus.eventapp.R;

/**
 * Created by Albert Zhao on 2019-11-20.
 * Copyright (c) 2019 Android Mobile Yangchuanosaurus. All rights reserved.
 */
public class EventHomeActivity extends AppCompatActivity {

    public static void start(Activity source) {
        Intent intent = new Intent(source, EventHomeActivity.class);
        source.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_event_home);
    }
}
