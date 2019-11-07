package repository.app.event;

import java.util.List;

import arch.anmobile.annotations.ParamClass;
import arch.anmobile.annotations.RsService;
import repository.app.data.Event;

/**
 * Created by Albert Zhao on 2019-11-07.
 * Copyright (c) 2019 Android Mobile Yangchuanosaurus. All rights reserved.
 */
@RsService(name="getEventList",
        method = "GET",
        action = "/service/json/v1/event",
        query = {
                @ParamClass(name = "uuid", type = String.class),
                @ParamClass(name = "appName", type = String.class),
                @ParamClass(name = "version", type = String.class)
        },
        body = Event.class
)
public class EventListResponse extends RsResponse {
    private List<Event> data;

    public List<Event> getData() {
        return data;
    }

    public void setData(List<Event> data) {
        this.data = data;
    }
}
