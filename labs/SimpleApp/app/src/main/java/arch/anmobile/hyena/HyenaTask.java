package arch.anmobile.hyena;

import android.util.Log;

import androidx.annotation.GuardedBy;

import arch.anmobile.hyena.support.DefaultRetryPolicy;
import arch.anmobile.hyena.support.RetryPolicy;

/**
 * Created by Albert Zhao on 2019-10-28.
 * Copyright (c) 2019 Android Mobile ActiveNetwork. All rights reserved.
 */
public abstract class HyenaTask<T> implements Comparable<HyenaTask> {

    private Integer mSequence;
    private Object mTag;
    private RetryPolicy mRetryPolicy;

    private HyenaQueue mHyenaQueue;

    private HyenaResults.Listener<T> mListener;

    private final Object mLock = new Object();
    @GuardedBy("mLock") private boolean mCanceled = false;
    @GuardedBy("mLock") private boolean mTaskDelivered = false;

    public HyenaTask(HyenaResults.Listener<T> listener) {
        setRetryPolicy(new DefaultRetryPolicy());
        mListener = listener;
    }

    public HyenaTask setHyenaQueue(HyenaQueue queue) {
        mHyenaQueue = queue;
        return this;
    }

    protected abstract HyenaResults<T> execute() throws HyenaError;

    void finish(String tag) {
        Log.d(Hyena.TAG, "finish " + tag);
        if (null != mHyenaQueue) {
            mHyenaQueue.finish(this);
        }
    }

    public void cancel() {
        synchronized (mLock) {
            mCanceled = true;
            mListener = null;
        }
    }

    public boolean isCanceled() {
        synchronized (mLock) {
            return mCanceled;
        }
    }

    void deliverResults(HyenaResults<T> results) {
        HyenaResults.Listener<T> listener;
        synchronized (mLock) {
            listener = mListener;
        }
        if (null != listener) {
            listener.onResults(results.getResults());
        }
    }

    void deliverError(HyenaError error) {
        HyenaResults.Listener<T> listener;
        synchronized (mLock) {
            listener = mListener;
        }
        if (null != listener) {
            listener.onErrorResults(error);
        }
    }

    public void markDelivered() {
        synchronized (mLock) {
            mTaskDelivered = true;
        }
    }

    public boolean hasHadTaskDelivered() {
        synchronized (mLock) {
            return mTaskDelivered;
        }
    }

    void sendEvent(@HyenaQueue.TaskEvent int event) {
        if (null != mHyenaQueue) {
            mHyenaQueue.sendTaskEvent(this, event);
        }
    }

    public HyenaTask setRetryPolicy(RetryPolicy retryPolicy) {
        mRetryPolicy = retryPolicy;
        return this;
    }

    public RetryPolicy getRetryPolicy() {
        return mRetryPolicy;
    }

    public HyenaTask setTag(Object tag) {
        mTag = tag;
        return this;
    }

    public Object getTag() {
        return mTag;
    }

    /**
     * Priority values. Requests will be processed from higher priorities to lower priorities, in
     * FIFO order.
     */
    public enum Priority {
        LOW,
        NORMAL,
        HIGH,
        IMMEDIATE
    }

    /** Returns the {@link Priority} of this task; {@link Priority#NORMAL} by default. */
    public Priority getPriority() {
        return Priority.NORMAL;
    }

    /**
     * Sets the sequence number of this task. Used by {@link HyenaQueue}.
     * @return This Task object to allow for chaining.
     */
    public final HyenaTask setSequence(int sequence) {
        mSequence = sequence;
        return this;
    }

    @Override
    public int compareTo(HyenaTask other) {
        Priority left = this.getPriority();
        Priority right = other.getPriority();

        // High-priority requests are "lesser" so they are sorted to the front.
        // Equal priorities are sorted by sequence number to provide FIFO ordering.
        return left == right ? this.mSequence - other.mSequence : right.ordinal() - left.ordinal();
    }
}
