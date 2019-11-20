package com.yangchuanosaurus.eventapp.messaging;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by Albert Zhao on 2019-11-19.
 * Copyright (c) 2019 Android Mobile Yangchuanosaurus. All rights reserved.
 */
public class Messaging {

    private static final String TAG = Messaging.class.getSimpleName();

    public static void registerDeviceToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    Log.w(TAG, "GetInstanceId Failed", task.getException());
                    return;
                }

                if (null == task.getResult()) {
                    return;
                }

                String token = task.getResult().getToken();
                Log.d(TAG, "registerDeviceToken device token: " + token);
            }
        });
    }

    public static void enableFcm() {
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
    }
}
