package module.event.repository.app;

/**
 * Created by Albert Zhao on 2019-11-06.
 * Copyright (c) 2019 Android Mobile Yangchuanosaurus. All rights reserved.
 */
public interface Callback<T> {
    void onCallback(T results);
    void onError(String error);
}
