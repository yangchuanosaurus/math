package repository.app.ds;

import java.util.List;

import repository.app.Callback;
import repository.app.data.Event;

/**
 * Created by Albert Zhao on 2019-11-06.
 * Copyright (c) 2019 Android Mobile Yangchuanosaurus. All rights reserved.
 */
public class EventDataSourceImpl implements EventDataSource {

    @Override
    public void getEventList(Callback<List<Event>> callback) {
        // todo download from restful service

        // todo persistence local

        // todo render to callback
    }
}
