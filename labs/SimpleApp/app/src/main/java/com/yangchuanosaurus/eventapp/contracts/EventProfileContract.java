package com.yangchuanosaurus.eventapp.contracts;

/**
 * Created by Albert Zhao on 2019-11-14.
 * Copyright (c) 2019 Android Mobile Yangchuanosaurus. All rights reserved.
 */
public interface EventProfileContract {
    interface View extends arch.anmobile.mvp.View {
        void renderEventProfile(String owner);
        void renderError(String error);
    }

    interface Presenter {
        void loadEventProfile();
    }
}
