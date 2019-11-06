package repository.app.ds;

import java.util.List;

import repository.app.Callback;
import repository.app.data.Event;

/**
 * Created by Albert Zhao on 2019-11-06.
 * Copyright (c) 2019 Android Mobile Yangchuanosaurus. All rights reserved.
 */
public interface EventDataSource {
    void getEventList(Callback<List<Event>> callback);
}
