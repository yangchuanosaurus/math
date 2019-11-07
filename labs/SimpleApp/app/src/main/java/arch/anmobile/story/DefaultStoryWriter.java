package arch.anmobile.story;

import android.util.Log;

import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import arch.anmobile.hyena.HyenaError;
import arch.anmobile.hyena.HyenaResults;
import arch.anmobile.hyena.HyenaTask;

/**
 * Created by Albert Zhao on 2019-11-06.
 * Copyright (c) 2019 Android Mobile Yangchuanosaurus. All rights reserved.
 */
class DefaultStoryWriter extends HyenaTask<Boolean> implements StoryWriter {

    private static final String STORY_FILE_NAME = "story.log";

    private @GuardedBy("mLock") List<StoryItem> storyItemList;
    private static final long DELAY = 10 * 1000;
    private static final int WRITE_CACHE_SIZE = 2;
    private final File mAppDir;

    private final Object mLock = new Object();

    DefaultStoryWriter(@NonNull File appDir) {
        super(null);
        storyItemList = new ArrayList<>();
        mAppDir = appDir;
    }

    @Override
    protected HyenaResults<Boolean> execute() throws HyenaError {
        while (true) {

            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {

            }

            Log.d("E", "Check story size: " + storyItemList.size());
            if (storyItemList.size() >= WRITE_CACHE_SIZE) {
                List<StoryItem> storyCache;

                synchronized (mLock) {
                    storyCache = new ArrayList<>(storyItemList);
                    storyItemList.clear();
                }
                writeStory(storyCache);
            }
        }

//        return HyenaResults.success(true);
    }

    private void writeStory(List<StoryItem> storyCache) {
        File file = new File(mAppDir, STORY_FILE_NAME);

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file, true);
            for (StoryItem item : storyCache) {
                fileWriter.write(item.getStory());
                fileWriter.write('\n');
            }
            fileWriter.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != fileWriter) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void addItem(Class clazz, String msg) {
        addItem(clazz, msg, true);
    }

    private void addItem(Class clazz, String msg, boolean immediately) {
        synchronized (mLock) {
            storyItemList.add(new StoryItem(clazz.getCanonicalName(), msg));
        }
    }

    private static class StoryItem {
        private String date;
        private String clazz;
        private String msg;

        private String story;

        StoryItem(String clazz, String msg) {
            this.clazz = clazz;
            this.msg = msg;

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.sss", Locale.US);
            this.date = df.format(new Date());
            this.story = date + " " + clazz + " - " + msg;
        }

        String getStory() {
            return story;
        }
    }
}
