package com.yangchuanosaurus.eventapp;

import android.app.Application;

/**
 * Created by Albert Zhao on 2019-11-06.
 * Copyright (c) 2019 Android Mobile Yangchuanosaurus. All rights reserved.
 */
public class EventApplication extends Application {

    private static final int SIZE_FOREGROUND_QUEUE = 2;
    private static final int SIZE_BACKGROUND_QUEUE = 4;

    @Override
    public void onCreate() {
        super.onCreate();

//        new AnFramework.AnFrameworkBuilder()
//                .setQueueSize(SIZE_FOREGROUND_QUEUE, SIZE_BACKGROUND_QUEUE)
//                .build(this);
    }

    @Override
    public void onTerminate() {
//        AnFramework.terminate();
        super.onTerminate();
    }
}
