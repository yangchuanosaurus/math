package com.yangchuanosaurus.eventapp.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.yangchuanosaurus.eventapp.R;
import com.yangchuanosaurus.eventapp.contracts.EventListContract;
import com.yangchuanosaurus.eventapp.messaging.Messaging;
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

    public static void start(Activity source) {
        Intent intent = new Intent(source, EventListActivity.class);
        source.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        storyEditor().write(EventListActivity.class, "onCreate(Bundle)");
        mPresenter = ActivityMvp.of(this).create(EventListPresenter.class);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_event_list);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, FragmentMvp.createInstance(this,
                EventOwnerFragment.class, "top.owner"));
        ft.add(R.id.fragment_container_down, FragmentMvp.createInstance(this,
                EventOwnerFragment.class, "bottom.owner"));
        ft.commit();

        Messaging.registerDeviceToken();
    }

    @Override
    public void renderEventList(List<Event> eventList) {
        storyEditor().write(EventListActivity.class, "renderEventList(List<Event>)");
    }

    @Override
    public void renderError(String error) {
        storyEditor().write(EventListActivity.class, "renderError(String)");
    }

    public void onClickEvent(View view) {
        EventHomeActivity.start(this);
        finish();
    }
}
