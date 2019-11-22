package arch.anmobile.hyena;

import android.os.Handler;

import java.util.concurrent.Executor;

/**
 * Created by Albert Zhao on 2019-10-28.
 * Copyright (c) 2019 Android Mobile Yangchuanosaurus. All rights reserved.
 */
public class HyenaResultsExecutorDelivery implements HyenaResultsDelivery {

    private final Executor mResultsPoster;

    public HyenaResultsExecutorDelivery(final Handler handler) {
        mResultsPoster = new Executor() {
            @Override
            public void execute(Runnable runnable) {
                handler.post(runnable);
            }
        };
    }

    public HyenaResultsExecutorDelivery(Executor executor) {
        mResultsPoster = executor;
    }

    @Override
    public void postResults(HyenaTask<?> task, HyenaResults<?> results) {
        postResults(task, results, null);
    }

    @Override
    public void postResults(HyenaTask<?> task, HyenaResults<?> results, Runnable runnable) {
        task.markDelivered();
        mResultsPoster.execute(new ResultsDeliveryRunnable(task, results, runnable));
    }

    @Override
    public void postError(HyenaTask task, HyenaError error) {
        HyenaResults<?> errorResults = HyenaResults.error(error);
        mResultsPoster.execute(new ResultsDeliveryRunnable(task, errorResults, null));
    }

    private static class ResultsDeliveryRunnable implements Runnable {
        private final HyenaTask mTask;
        private final HyenaResults mTaskResults;
        private final Runnable mRunnable;

        public ResultsDeliveryRunnable(HyenaTask task, HyenaResults results, Runnable runnable) {
            mTask = task;
            mTaskResults = results;
            mRunnable = runnable;
        }

        @SuppressWarnings("unchecked")
        @Override
        public void run() {
            if (mTask.isCanceled()) {
                mTask.finish("canceled-delivery");
                return;
            }

            boolean isSuccess = null == mTaskResults.mError;
            if (isSuccess) {
                mTask.deliverResults(mTaskResults);
            } else {
                mTask.deliverError(mTaskResults.mError);
            }

            if (null != mRunnable) {
                mRunnable.run();
            }
        }
    }
}
