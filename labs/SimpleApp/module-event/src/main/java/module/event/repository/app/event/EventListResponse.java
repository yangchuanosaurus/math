package module.event.repository.app.event;

import java.util.List;

import module.event.repository.app.data.Event;

/**
 * Created by Albert Zhao on 2019-11-07.
 * Copyright (c) 2019 Android Mobile Yangchuanosaurus. All rights reserved.
 */
public class EventListResponse extends RsResponse {
    private List<Event> data;

    public List<Event> getData() {
        return data;
    }

    public void setData(List<Event> data) {
        this.data = data;
    }
}
