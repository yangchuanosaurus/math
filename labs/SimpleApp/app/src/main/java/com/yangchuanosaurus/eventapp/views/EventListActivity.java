package com.yangchuanosaurus.eventapp.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.yangchuanosaurus.eventapp.contracts.EventListContract;
import com.yangchuanosaurus.eventapp.presenters.EventListPresenter;

import java.util.List;

import arch.anmobile.mvp.ActivityMvp;
import repository.app.data.Event;

/**
 * Created by Albert Zhao on 2019-11-06.
 * Copyright (c) 2019 Android Mobile Yangchuanosaurus. All rights reserved.
 */
public class EventListActivity extends AppCompatActivity implements EventListContract.View {

    private EventListContract.Presenter mPresenter;

    public void onCreate(Bundle savedInstanceState) {
        mPresenter = ActivityMvp.of(this).create(EventListPresenter.class);

        super.onCreate(savedInstanceState);
    }

    @Override
    public void renderEventList(List<Event> eventList) {

    }

    @Override
    public void renderError(String error) {

    }
}
