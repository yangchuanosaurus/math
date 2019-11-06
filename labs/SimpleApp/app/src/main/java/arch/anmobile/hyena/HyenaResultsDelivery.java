package arch.anmobile.hyena;

/**
 * Created by Albert Zhao on 2019-10-28.
 * Copyright (c) 2019 Android Mobile Yangchuanosaurus. All rights reserved.
 */
public interface HyenaResultsDelivery {
    void postResults(HyenaTask<?> task, HyenaResults<?> results);
    void postResults(HyenaTask<?> task, HyenaResults<?> results, Runnable runnable);
    void postError(HyenaTask<?> task, HyenaError error);
}
