package module.event.contracts;

import java.util.List;

import module.event.repository.app.data.Event;

/**
 * Created by Albert Zhao on 2019-11-06.
 * Copyright (c) 2019 Android Mobile Yangchuanosaurus. All rights reserved.
 */
public interface EventListContract {
    interface View extends arch.anmobile.mvp.View {
        void renderEventList(List<Event> eventList);
        void renderError(String error);
    }

    interface Presenter {
        void loadEventList();
    }
}
