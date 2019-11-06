package arch.anmobile.story;

import android.util.Log;

/**
 * Created by Albert Zhao on 2019-11-06.
 * Copyright (c) 2019 Android Mobile ActiveNetwork. All rights reserved.
 */
public class DefaultStoryEditor implements StoryEditor {

    private static final String TAG = "STORY";

    @Override
    public void write(Class clazz, String msg) {
        Log.d(TAG, "[" + clazz.getSimpleName() + "] - " + msg);
    }
}
