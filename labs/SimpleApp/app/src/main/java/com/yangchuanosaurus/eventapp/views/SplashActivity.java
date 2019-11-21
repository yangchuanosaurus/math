package com.yangchuanosaurus.eventapp.views;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.yangchuanosaurus.eventapp.R;
import com.yangchuanosaurus.eventapp.messaging.Messaging;

/**
 * Created by Albert Zhao on 2019-11-20.
 * Copyright (c) 2019 Android Mobile Yangchuanosaurus. All rights reserved.
 */
public class SplashActivity extends AppCompatActivity {

    private static final long DELAY = 1400;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Messaging.enableFcm();
        new Handler().postDelayed(this::actionOfSplash, DELAY);
    }

    private void actionOfSplash() {
        EventListActivity.start(this);
        finish();
    }

}
