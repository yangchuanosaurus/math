package func.messaging;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by Albert Zhao on 2019-11-19.
 * Copyright (c) 2019 Android Mobile Yangchuanosaurus. All rights reserved.
 */
public class AppFirebaseMessagingService extends FirebaseMessagingService {

    /*
    Post: https://fcm.googleapis.com/fcm/send

    Header:
    Content-Type: application/json
    authorization: key=AAAAx4TlLac:APA91bG_Hg7D8xndpeVmDBE9eTWBpR-PpeOu5wbkXTlgLVVrAlvoTXsCifYU1vpv0gIOQbatL2sAHptwvBL8bt7P3B6lZtFSiU3Z2dXyrymCPs_WTFVKVSZn6to6RCZvKijZQ_baRYNV
    Body:
    "to": "csOVXAWSIBc:APA91bER8--xi-m2bN_sJl8yxHnoo6X4GRTxWKY7njTHiIUaZHMRiOJEP1iVXqAz7mPoRTM5kGFSgyoHvI2_IJV7danCo5kYQ2ac67VJIdNmv5Owx1SW6Y60rnKLV54IR1gTRrQz7Qt-"

    {
      "notification":{
        "title":"message title",
        "body":"great match!"
      },
      "data":{
        "title" : "message title",
        "message" : "great match!"
      },
      "registration_ids": ["csOVXAWSIBc:APA91bER8--xi-m2bN_sJl8yxHnoo6X4GRTxWKY7njTHiIUaZHMRiOJEP1iVXqAz7mPoRTM5kGFSgyoHvI2_IJV7danCo5kYQ2ac67VJIdNmv5Owx1SW6Y60rnKLV54IR1gTRrQz7Qt-"]
    }
    * */
    private static final String TAG = AppFirebaseMessagingService.class.getSimpleName();

    private static final String CHANNEL_ID_DEFAULT = "simple.notification.channel.default";
    private static final int NOTIFICATION_ID_DEFAULT = 1;
    private static final String NOTIFICATION_TAG_DEFAULT = "simple.notification.tag.default";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Bundle data = bundleData(remoteMessage);
        NotificationCreator creator = new NotificationCreator.Builder()
                .setData(data).build();
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (null != notificationManager) {
            Log.d(TAG, "show foreground app notification.");
            notificationManager.notify(NOTIFICATION_TAG_DEFAULT, NOTIFICATION_ID_DEFAULT,
                    creator.create(this, CHANNEL_ID_DEFAULT));
        }
    }

    private @NonNull Bundle bundleData(@NonNull RemoteMessage remoteMessage) {
        Bundle data = new Bundle();
        Map<String, String> payload = remoteMessage.getData();
        if (payload.size() > 0) {
            // Check if message contains a data payload.
            Log.d(TAG, "Message data payload: " + payload);

            for (Map.Entry<String, String> entry : payload.entrySet()) {
                data.putString(entry.getKey(), entry.getValue());
            }
        }
        return data;
    }

    @Override
    public void onNewToken(@NonNull String token) {
        // csOVXAWSIBc:APA91bER8--xi-m2bN_sJl8yxHnoo6X4GRTxWKY7njTHiIUaZHMRiOJEP1iVXqAz7mPoRTM5kGFSgyoHvI2_IJV7danCo5kYQ2ac67VJIdNmv5Owx1SW6Y60rnKLV54IR1gTRrQz7Qt-
        Log.d(TAG, "onNewToken " + token);
    }
}
