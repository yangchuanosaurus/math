package repository.app;

import repository.app.ds.EventDataSourceImpl;

/**
 * Created by Albert Zhao on 2019-11-06.
 * Copyright (c) 2019 Android Mobile ActiveNetwork. All rights reserved.
 */
public class RepositoryManager {
    public static EventRepository getEventRepository() {
        return new EventRepositoryImpl(new EventDataSourceImpl());
    }
}
