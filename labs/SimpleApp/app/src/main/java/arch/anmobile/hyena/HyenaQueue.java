package arch.anmobile.hyena;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import arch.anmobile.hyena.support.HyenaTaskFilter;

/**
 * Created by Albert Zhao on 2019-10-28.
 * Copyright (c) 2019 Android Mobile Yangchuanosaurus. All rights reserved.
 */
public class HyenaQueue {

    /** Used for generating monotonically-increasing sequence numbers for tasks. */
    private final AtomicInteger mSequenceGenerator = new AtomicInteger();

    private PriorityBlockingQueue<HyenaTask<?>> mTasksQueue;
    private final Set<HyenaTask<?>> mCurrentTasks = new HashSet<>();
    private HyenaDispatcher[] mTaskDispatchers;
    private final HyenaResultsDelivery mDelivery;

    private final List<TaskEventListener> mTaskEventListeners = new ArrayList<>();

    /*package*/ HyenaQueue(int poolSize) {
        this(poolSize, new HyenaResultsExecutorDelivery(new Handler(Looper.getMainLooper())));
    }

    /*package*/ HyenaQueue(int poolSize, HyenaResultsDelivery delivery) {
        mTaskDispatchers = new HyenaDispatcher[poolSize];
        mTasksQueue = new PriorityBlockingQueue<>();
        mDelivery = delivery;
    }

    public void start() {
        stop();

        for (int i = 0; i < mTaskDispatchers.length; i++) {
            HyenaDispatcher dispatcher = new HyenaDispatcher(mTasksQueue, mDelivery);
            mTaskDispatchers[i] = dispatcher;
            dispatcher.start();
        }
    }

    public void stop() {
        for (HyenaDispatcher dispatcher : mTaskDispatchers) {
            if (null != dispatcher) dispatcher.quit();
        }
    }

    public HyenaQueue add(HyenaTask task) {
        task.setHyenaQueue(this);
        synchronized (mCurrentTasks) {
            mCurrentTasks.add(task);
        }

        task.setSequence(getSequenceNumber());
        sendTaskEvent(task, TaskEvent.TASK_QUEUED);

        mTasksQueue.add(task);
        return this;
    }

    void finish(HyenaTask task) {
        synchronized (mCurrentTasks) {
            mCurrentTasks.remove(task);
        }

        sendTaskEvent(task, TaskEvent.TASK_FINISHED);
    }

    public void cancelAll(HyenaTaskFilter filter) {
        synchronized (mCurrentTasks) {
            for (HyenaTask task : mCurrentTasks) {
                if (filter.apply(task)) {
                    task.cancel();
                }
            }
        }
    }

    public void cancelAll(final Object tag) {
        if (tag == null) {
            throw new IllegalArgumentException("Cannot cancelAll with a null tag");
        }
        cancelAll(new HyenaTaskFilter() {
                    @Override
                    public boolean apply(HyenaTask task) {
                        return task.getTag() == tag;
                    }
                });
    }

    void sendTaskEvent(HyenaTask task, @TaskEvent int event) {
        synchronized (mTaskEventListeners) {
            Log.d(Hyena.TAG, "send task event " + event + " of [" + task + "]");
            for (TaskEventListener taskEventListener : mTaskEventListeners) {
                taskEventListener.onTaskEvent(task, event);
            }
        }
    }

    public HyenaQueue addTaskEventListener(TaskEventListener taskEventListener) {
        synchronized (mTaskEventListeners) {
            mTaskEventListeners.add(taskEventListener);
        }
        return this;
    }

    public HyenaQueue removeTaskEventListener(TaskEventListener taskEventListener) {
        synchronized (mTaskEventListeners) {
            mTaskEventListeners.remove(taskEventListener);
        }
        return this;
    }

    public int getSequenceNumber() {
        return mSequenceGenerator.incrementAndGet();
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            TaskEvent.TASK_QUEUED,
            TaskEvent.TASK_DISPATCH_RETRY,
            TaskEvent.TASK_DISPATCH_STARTED,
            TaskEvent.TASK_DISPATCH_FINISHED,
            TaskEvent.TASK_DISPATCH_ERROR,
            TaskEvent.TASK_FINISHED
    })
    public @interface TaskEvent {
        int TASK_QUEUED = 0;
        int TASK_DISPATCH_RETRY = 1;
        int TASK_DISPATCH_STARTED = 2;
        int TASK_DISPATCH_FINISHED = 3;
        int TASK_DISPATCH_ERROR = 4;
        int TASK_FINISHED = 5;
    }

    /** Callback interface for task life cycle events. */
    public interface TaskEventListener {
        /**
         * Called on every task lifecycle event. Can be called from different threads. The call
         * is blocking task processing, so any processing should be kept at minimum or moved to
         * another thread.
         */
        void onTaskEvent(HyenaTask task, @TaskEvent int event);
    }
}
