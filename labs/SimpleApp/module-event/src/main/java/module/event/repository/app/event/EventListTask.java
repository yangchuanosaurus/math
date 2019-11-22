package module.event.repository.app.event;

import java.util.ArrayList;
import java.util.List;

import arch.anmobile.hyena.HyenaError;
import arch.anmobile.hyena.HyenaResults;
import arch.anmobile.hyena.HyenaTask;
import module.event.repository.app.data.Event;

/**
 * Created by Albert Zhao on 2019-11-06.
 * Copyright (c) 2019 Android Mobile Yangchuanosaurus. All rights reserved.
 */
public class EventListTask extends HyenaTask<List<Event>> {

    public EventListTask(HyenaResults.Listener<List<Event>> listener) {
        super(listener);
    }

    @Override
    protected HyenaResults<List<Event>> execute() throws HyenaError {
        List<Event> eventList = new ArrayList<>();
        return HyenaResults.success(eventList);
    }

}
