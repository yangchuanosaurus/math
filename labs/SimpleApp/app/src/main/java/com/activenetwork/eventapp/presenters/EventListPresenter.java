package com.activenetwork.eventapp.presenters;

import com.activenetwork.eventapp.contracts.EventListContract;

import java.util.List;

import arch.anmobile.mvp.Presenter;
import repository.app.Callback;
import repository.app.EventRepository;
import repository.app.RepositoryManager;
import repository.app.data.Event;

/**
 * Created by Albert Zhao on 2019-11-06.
 * Copyright (c) 2019 Android Mobile ActiveNetwork. All rights reserved.
 */
public class EventListPresenter extends Presenter<EventListContract.View> implements EventListContract.Presenter {

    private EventRepository eventRepository;

    @Override
    public void loadEventList() {
        eventRepository = RepositoryManager.getEventRepository();
        eventRepository.getEventList(new Callback<List<Event>>() {
            @Override
            public void onCallback(List<Event> results) {
                getView().renderEventList(results);
            }

            @Override
            public void onError(String error) {
                getView().renderError(error);
            }
        });
    }

    @Override
    protected void onCreated() {

    }

    @Override
    protected void onViewSafe() {

    }

    /**
     * called when activity destroy
     * */
    @Override
    protected void onDestroy() {
        eventRepository.cancelGetEventList();
    }
}
