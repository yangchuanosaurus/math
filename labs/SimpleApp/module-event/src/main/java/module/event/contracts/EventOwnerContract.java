package module.event.contracts;

/**
 * Created by Albert Zhao on 2019-11-14.
 * Copyright (c) 2019 Android Mobile Yangchuanosaurus. All rights reserved.
 */
public interface EventOwnerContract {
    interface View extends arch.anmobile.mvp.View {
        void renderEventOwner(String owner);
        void renderError(String error);
    }

    interface Presenter {
        void loadEventOwner();
    }
}
