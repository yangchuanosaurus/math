package arch.anmobile.hyena;

import android.os.Process;

import java.util.concurrent.BlockingQueue;

import arch.anmobile.hyena.support.RetryPolicy;

/**
 * Created by Albert Zhao on 2019-10-28.
 * Copyright (c) 2019 Android Mobile ActiveNetwork. All rights reserved.
 */
public class HyenaDispatcher extends Thread {

    private volatile boolean mQuit = false;

    private BlockingQueue<HyenaTask<?>> mHyenaTasksQueue;
    private final HyenaResultsDelivery mDelivery;

    public HyenaDispatcher(BlockingQueue<HyenaTask<?>> taskQueue, HyenaResultsDelivery delivery) {
        mHyenaTasksQueue = taskQueue;
        mDelivery = delivery;
    }

    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        while (true) {
            try {
                processTask();
            } catch (InterruptedException e) {
                if (mQuit) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }

    private void processTask() throws InterruptedException {
        HyenaTask<?> hyenaTask = mHyenaTasksQueue.take();
        processHyenaTask(hyenaTask);
    }

    /**
     * retry the hyena task when error
     * @return retry the hyena task success or failed
     * */
    private boolean attemptRetryOnException(HyenaTask<?> task, HyenaError hyenaError) {
        task.sendEvent(HyenaQueue.TaskEvent.TASK_DISPATCH_ERROR);

        boolean retryInProgress = false;
        RetryPolicy retryPolicy = task.getRetryPolicy();
        try {
            retryPolicy.retry(hyenaError);
            // Can retry
            task.sendEvent(HyenaQueue.TaskEvent.TASK_DISPATCH_RETRY);
            processHyenaTask(task);
            retryInProgress = true;
        } catch (HyenaError error) {
            // Cannot retry
            retryInProgress = false;
        }
        return retryInProgress;
    }

    void processHyenaTask(HyenaTask<?> task) {
        task.sendEvent(HyenaQueue.TaskEvent.TASK_DISPATCH_STARTED);

        if (task.isCanceled()) {
            task.finish("hyena-task-cancelled");
            return;
        }

        if (task.hasHadTaskDelivered()) {
            task.finish("hyena-task-delivered");
            return;
        }

        try {
            HyenaResults<?> results = task.execute();
            task.markDelivered();
            mDelivery.postResults(task, results);
            task.finish("hyena-task-done");
        } catch (HyenaError hyenaError) {
            // retry
            if (!attemptRetryOnException(task, hyenaError)) {
                task.sendEvent(HyenaQueue.TaskEvent.TASK_DISPATCH_FINISHED);
            } else {
                parseAndDeliverError(task, hyenaError);
                task.sendEvent(HyenaQueue.TaskEvent.TASK_FINISHED);
            }
        } catch (Exception e) {
            // unhandled exception, finished with error
            HyenaError hyenaError = new HyenaError(e);
            parseAndDeliverError(task, hyenaError);
            task.sendEvent(HyenaQueue.TaskEvent.TASK_DISPATCH_FINISHED);
            task.sendEvent(HyenaQueue.TaskEvent.TASK_FINISHED);
        }
    }

    public void quit() {
        mQuit = true;
        interrupt();
    }

    private void parseAndDeliverError(HyenaTask<?> task, HyenaError error) {
        mDelivery.postError(task, error);
    }
}
