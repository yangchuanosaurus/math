package repository.app;

import arch.anmobile.hyena.HyenaQueue;

/**
 * Created by Albert Zhao on 2019-11-06.
 * Copyright (c) 2019 Android Mobile Yangchuanosaurus. All rights reserved.
 */
public class RepositoryManager {

    public static EventRepository getEventRepository(HyenaQueue queue) {
        return new EventRepositoryImpl(queue);
    }

}
