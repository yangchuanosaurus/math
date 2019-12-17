package app.module.pagination;

import android.util.Log;

/**
 * Created by Albert Zhao on 2019-12-17.
 * Copyright (c) 2019 Android Mobile ActiveNetwork. All rights reserved.
 */
public class PaginationLog {

    private static final String TAG = "PaginationLog";
    private static final String DEBUG = "[DEBUG]";

    public static void d(String msg) {
        Log.d(TAG, DEBUG + " - " + msg);
    }
}
