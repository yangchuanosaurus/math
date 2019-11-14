package com.yangchuanosaurus.eventapp.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.yangchuanosaurus.eventapp.R;
import com.yangchuanosaurus.eventapp.contracts.EventListContract;
import com.yangchuanosaurus.eventapp.presenters.EventListPresenter;

import java.util.List;

import arch.anmobile.mvp.ActivityMvp;
import arch.anmobile.mvp.FragmentMvp;
import repository.app.data.Event;

import static arch.anmobile.framework.AnFramework.storyEditor;

/**
 * Created by Albert Zhao on 2019-11-06.
 * Copyright (c) 2019 Android Mobile Yangchuanosaurus. All rights reserved.
 */
public class EventListActivity extends AppCompatActivity implements EventListContract.View {

    private EventListContract.Presenter mPresenter;

    public void onCreate(Bundle savedInstanceState) {
        storyEditor().write(EventListActivity.class, "onCreate(Bundle)");
        mPresenter = ActivityMvp.of(this).create(EventListPresenter.class);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        storyEditor().write(EventListActivity.class, "EventListContract.Presenter#loadEventList()");
        mPresenter.loadEventList();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, FragmentMvp.createInstance(this,
                EventOwnerFragment.class, "top.owner"));
        ft.add(R.id.fragment_container_down, FragmentMvp.createInstance(this,
                EventOwnerFragment.class, "bottom.owner"));
        ft.commit();
    }

    @Override
    public void renderEventList(List<Event> eventList) {
        storyEditor().write(EventListActivity.class, "renderEventList(List<Event>)");
    }

    @Override
    public void renderError(String error) {
        storyEditor().write(EventListActivity.class, "renderError(String)");
    }
}
