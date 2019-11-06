package arch.anmobile.story;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;

import arch.anmobile.hyena.HyenaQueue;
import arch.anmobile.hyena.HyenaTask;

/**
 * Created by Albert Zhao on 2019-11-06.
 * Copyright (c) 2019 Android Mobile ActiveNetwork. All rights reserved.
 */
public class DefaultStoryEditor implements StoryEditor {

    private static final String TAG = "STORY";
    private final HyenaQueue mStoryQueue;
    private final StoryWriter mStoryWriter;

    public DefaultStoryEditor(HyenaQueue queue, @NonNull File appDir) {
        mStoryQueue = queue;
        mStoryWriter = new DefaultStoryWriter(appDir);
        mStoryQueue.add((HyenaTask) mStoryWriter);
    }

    @Override
    public void write(Class clazz, String msg) {
        // adb exec-out run-as com.yangchuanosaurus.eventapp cat files/story.log > story.log
        mStoryWriter.addItem(clazz, msg);
        Log.d(TAG, "[" + clazz.getSimpleName() + "] - " + msg);
    }
}
