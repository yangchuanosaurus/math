package arch.anmobile.hyena.support;

import arch.anmobile.hyena.HyenaError;

/**
 * Created by Albert Zhao on 2019-10-28.
 * Copyright (c) 2019 Android Mobile ActiveNetwork. All rights reserved.
 */
public interface RetryPolicy {
    int getCurrentRetryCount();
    void retry(HyenaError error) throws HyenaError;
}
