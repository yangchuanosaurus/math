package module.event.presenters;

import android.util.Log;

import module.event.contracts.EventOwnerContract;

import arch.anmobile.mvp.Presenter;

/**
 * Created by Albert Zhao on 2019-11-14.
 * Copyright (c) 2019 Android Mobile Yangchuanosaurus. All rights reserved.
 */
public class EventOwnerPresenter extends Presenter<EventOwnerContract.View> implements EventOwnerContract.Presenter {

    private static final String TAG = EventOwnerPresenter.class.getSimpleName();

    @Override
    protected void onCreated() {
        Log.d(TAG, "onCreated " + this);
    }

    @Override
    protected void onViewSafe() {
        Log.d(TAG, "onViewSafe " + this);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void loadEventOwner() {
        Log.d(TAG, "loadEventOwner");
    }
}
