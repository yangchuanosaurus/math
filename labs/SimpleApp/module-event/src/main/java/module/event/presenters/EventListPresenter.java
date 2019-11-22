package module.event.presenters;

import module.event.contracts.EventListContract;

import java.util.List;

import arch.anmobile.framework.AnFramework;
import arch.anmobile.hyena.HyenaQueue;
import arch.anmobile.mvp.Presenter;
import module.event.repository.app.Callback;
import module.event.repository.app.EventRepository;
import module.event.repository.app.RepositoryManager;
import module.event.repository.app.data.Event;

import static arch.anmobile.framework.AnFramework.storyEditor;

/**
 * Created by Albert Zhao on 2019-11-06.
 * Copyright (c) 2019 Android Mobile Yangchuanosaurus. All rights reserved.
 */
public class EventListPresenter extends Presenter<EventListContract.View>
        implements EventListContract.Presenter {

    private EventRepository eventRepository;

    @Override
    public void loadEventList() {
        storyEditor().write(EventListPresenter.class, "loadEventList()");

        HyenaQueue foregroundQueue = AnFramework.instance().getForegroundQueue();

        eventRepository = RepositoryManager.getEventRepository(foregroundQueue);
        eventRepository.getEventList(new Callback<List<Event>>() {
            @Override
            public void onCallback(List<Event> results) {
                storyEditor().write(EventListPresenter.class, "onCallback(List<Event>)");
                if (isViewSafe()) {
                    getView().renderEventList(results);
                }
            }

            @Override
            public void onError(String error) {
                storyEditor().write(EventListPresenter.class, "onError(String)");
                if (isViewSafe()) {
                    getView().renderError(error);
                }
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
        if (null != eventRepository) {
            eventRepository.cancelGetEventList();
        }
        storyEditor().write(EventListPresenter.class, "onDestroy()");
    }
}
