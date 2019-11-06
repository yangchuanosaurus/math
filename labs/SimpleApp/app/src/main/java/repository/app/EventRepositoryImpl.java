package repository.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import arch.anmobile.hyena.HyenaError;
import arch.anmobile.hyena.HyenaQueue;
import arch.anmobile.hyena.HyenaResults;
import repository.app.data.Event;
import repository.app.event.EventListTask;

import static arch.anmobile.framework.AnFramework.storyEditor;

/**
 * Created by Albert Zhao on 2019-11-06.
 * Copyright (c) 2019 Android Mobile Yangchuanosaurus. All rights reserved.
 */
/*package*/class EventRepositoryImpl implements EventRepository {

    private final HyenaQueue mHyenaQueue;
    private EventListTask mDownloadEventListTask;

    EventRepositoryImpl(@NonNull HyenaQueue hyenaQueue) {
        mHyenaQueue = hyenaQueue;
    }

    @Override
    public void getEventList(@NonNull Callback<List<Event>> callback) {
        downloadEventList(callback);
    }

    private void downloadEventList(@NonNull Callback<List<Event>> callback) {
        storyEditor().write(EventRepositoryImpl.class, "downloadEventList(Callback<List<Event>>)");
        cancelGetEventList();

        mDownloadEventListTask = new EventListTask(new HyenaResults.Listener<List<Event>>() {
            @Override
            public void onResults(List<Event> results) {
                storyEditor().write(EventRepositoryImpl.class, "HyenaResults.Listener#onResults(List<Event>)");
                persistentEventList(results, callback);
            }

            @Override
            public void onErrorResults(HyenaError error) {
                storyEditor().write(EventRepositoryImpl.class, "HyenaResults.Listener#onErrorResults(HyenaError)");
                callback.onError(error.toString());
            }
        });
        mHyenaQueue.add(mDownloadEventListTask);
    }

    private void persistentEventList(@Nullable List<Event> events, @NonNull Callback<List<Event>> callback) {
        storyEditor().write(EventRepositoryImpl.class, "persistentEventList(List<Event>, Callback<List<Event>>)");
        // todo persistence local
        callback.onCallback(loadEventList());
    }

    private @Nullable List<Event> loadEventList() {
        return null;
    }

    @Override
    public void cancelGetEventList() {
        if (null != mDownloadEventListTask) {
            storyEditor().write(EventRepositoryImpl.class, "cancelGetEventList()");
            mDownloadEventListTask.cancel();
            mDownloadEventListTask = null;
        }
    }
}
