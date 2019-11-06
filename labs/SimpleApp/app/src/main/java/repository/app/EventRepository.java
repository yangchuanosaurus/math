package repository.app;

import java.util.List;

import repository.app.data.Event;

/**
 * Created by Albert Zhao on 2019-11-06.
 * Copyright (c) 2019 Android Mobile Yangchuanosaurus. All rights reserved.
 */
public interface EventRepository {
    void getEventList(Callback<List<Event>> callback);
    void cancelGetEventList();
}
