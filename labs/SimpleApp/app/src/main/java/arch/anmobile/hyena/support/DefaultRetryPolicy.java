package arch.anmobile.hyena.support;

import arch.anmobile.hyena.HyenaError;

/**
 * Created by Albert Zhao on 2019-10-28.
 * Copyright (c) 2019 Android Mobile ActiveNetwork. All rights reserved.
 */
public class DefaultRetryPolicy implements RetryPolicy {

    private int mCurrentRetryCount;
    private final int mMaxNumRetries;

    public static final int DEFAULT_MAX_RETRIES = 1;

    public DefaultRetryPolicy() {
        this(DEFAULT_MAX_RETRIES);
    }

    public DefaultRetryPolicy(int maxNumRetries) {
        mMaxNumRetries = maxNumRetries;
    }

    @Override
    public int getCurrentRetryCount() {
        return mCurrentRetryCount;
    }

    @Override
    public void retry(HyenaError error) throws HyenaError {
        mCurrentRetryCount++;
        if (!hasAttemptRemaining()) {
            throw error;
        }
    }

    protected boolean hasAttemptRemaining() {
        return mCurrentRetryCount <= mMaxNumRetries;
    }
}
