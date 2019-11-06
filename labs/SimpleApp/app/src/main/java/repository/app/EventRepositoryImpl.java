package repository.app;

import java.util.List;

import repository.app.data.Event;
import repository.app.ds.EventDataSource;

/**
 * Created by Albert Zhao on 2019-11-06.
 * Copyright (c) 2019 Android Mobile Yangchuanosaurus. All rights reserved.
 */
/*package*/class EventRepositoryImpl implements EventRepository {
    private final EventDataSource _eventDataSource;

    EventRepositoryImpl(EventDataSource eventDataSource) {
        _eventDataSource = eventDataSource;
    }

    @Override
    public void getEventList(Callback<List<Event>> callback) {
        _eventDataSource.getEventList(callback);
    }

    @Override
    public void cancelGetEventList() {

    }
}
