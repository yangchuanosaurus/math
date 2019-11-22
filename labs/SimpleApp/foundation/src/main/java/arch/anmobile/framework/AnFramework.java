package arch.anmobile.framework;

import android.app.Application;

import androidx.annotation.NonNull;

import arch.anmobile.hyena.Hyena;
import arch.anmobile.hyena.HyenaQueue;
import arch.anmobile.story.DefaultStoryEditor;
import arch.anmobile.story.StoryEditor;

/**
 * Created by Albert Zhao on 2019-11-06.
 * Copyright (c) 2019 Android Mobile Yangchuanosaurus. All rights reserved.
 */
public class AnFramework {
    private Application mApplication;
    private HyenaQueue mForegroundQueue;
    private HyenaQueue mBackgroundQueue;
    private StoryEditor mStoryEditor;
    private HyenaQueue mStoryQueue;

    private AnFramework() {}

    private AnFramework createQueues(int foregroundQueueSize, int backgroundQueueSize) {
        if (foregroundQueueSize > 0 && null == mForegroundQueue) {
            mForegroundQueue = Hyena.create(foregroundQueueSize);
            mForegroundQueue.start();
        }

        if (backgroundQueueSize > 0 && null == mBackgroundQueue) {
            mBackgroundQueue = Hyena.create(backgroundQueueSize);
            mBackgroundQueue.start();
        }

        if (null == mStoryQueue) {
            mStoryQueue = Hyena.create(1);
            mStoryQueue.start();
        }

        return this;
    }

    private interface LazyLoader {
        AnFramework SINGLETON = new AnFramework();
    }

    public static class AnFrameworkBuilder {
        private int foregroundQueueSize;
        private int backgroundQueueSize;
        private StoryEditor storyEditor;

        public AnFrameworkBuilder setQueueSize(int foregroundQueueSize, int backgroundQueueSize) {
            this.foregroundQueueSize = foregroundQueueSize;
            this.backgroundQueueSize = backgroundQueueSize;
            return this;
        }

        public AnFrameworkBuilder setStoryEditor(StoryEditor storyEditor) {
            this.storyEditor = storyEditor;
            return this;
        }

        public AnFramework build(@NonNull Application application) {
            LazyLoader.SINGLETON.mApplication = application;

            LazyLoader.SINGLETON
                    .createQueues(foregroundQueueSize, backgroundQueueSize);
            if (null == storyEditor) {
                LazyLoader.SINGLETON.setStoryEditor(null);
            } else {
                LazyLoader.SINGLETON.setStoryEditor(storyEditor);
            }

            return LazyLoader.SINGLETON;
        }
    }

    public static AnFramework instance() {
        return LazyLoader.SINGLETON;
    }

    public static void terminate() {
        LazyLoader.SINGLETON.killForegroundQueue();
        LazyLoader.SINGLETON.killBackgroundQueue();
        LazyLoader.SINGLETON.killStoryQueue();
    }

    public HyenaQueue getForegroundQueue() {
        return mForegroundQueue;
    }

    public HyenaQueue getBackgroundQueue() {
        return mBackgroundQueue;
    }

    private void setStoryEditor(StoryEditor editor) {
        if (null != editor) {
            mStoryEditor = editor;
        } else {
            mStoryEditor = new DefaultStoryEditor(mStoryQueue, mApplication.getFilesDir());
        }
    }

    public static StoryEditor storyEditor() {
        return LazyLoader.SINGLETON.getStoryEditor();
    }

    public StoryEditor getStoryEditor() {
        return mStoryEditor;
    }

    private void killForegroundQueue() {
        killHyenaQueue(mForegroundQueue);
    }

    private void killBackgroundQueue() {
        killHyenaQueue(mBackgroundQueue);
    }

    private void killStoryQueue() {
        killHyenaQueue(mStoryQueue);
    }

    private void killHyenaQueue(HyenaQueue queue) {
        if (null != queue) {
            queue.stop();
        }
    }
}
