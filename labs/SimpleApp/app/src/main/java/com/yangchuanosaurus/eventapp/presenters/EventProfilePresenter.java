package com.yangchuanosaurus.eventapp.presenters;

import android.util.Log;

import com.yangchuanosaurus.eventapp.contracts.EventProfileContract;

import arch.anmobile.mvp.Presenter;

/**
 * Created by Albert Zhao on 2019-11-14.
 * Copyright (c) 2019 Android Mobile Yangchuanosaurus. All rights reserved.
 */
public class EventProfilePresenter extends Presenter<EventProfileContract.View>
        implements EventProfileContract.Presenter {

    private static final String TAG = EventProfilePresenter.class.getSimpleName();

    @Override
    protected void onCreated() {
        Log.d(TAG, "onCreated " + this);
    }

    @Override
    protected void onViewSafe() {
        Log.d(TAG, "onViewSafe");
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void loadEventProfile() {

    }
}
