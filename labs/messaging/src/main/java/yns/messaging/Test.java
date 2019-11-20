package yns.messaging;

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;

import java.io.FileInputStream;

public class Test {

    public static void main(String args[]) throws Exception {

        Storage storage = StorageOptions.getDefaultInstance().getService();

        Page<Bucket> buckets = storage.list();
        for (Bucket bucket : buckets.iterateAll()) {
            // do something with the info
            System.out.println(bucket);
        }

//        FirebaseApp.initializeApp();
//        sendMessage();
    }

    private static void sendMessage() throws Exception {
        // This registration token comes from the client FCM SDKs.
        String registrationToken = "csOVXAWSIBc:APA91bER8--xi-m2bN_sJl8yxHnoo6X4GRTxWKY7njTHiIUaZHMRiOJEP1iVXqAz7mPoRTM5kGFSgyoHvI2_IJV7danCo5kYQ2ac67VJIdNmv5Owx1SW6Y60rnKLV54IR1gTRrQz7Qt-";

        // See documentation on defining a message payload.
        Message message = Message.builder()
                .putData("score", "850")
                .putData("time", "2:45")
                .setToken(registrationToken)
                .build();

        // Send a message to the device corresponding to the provided
        // registration token.
        String response = FirebaseMessaging.getInstance().send(message);
        // Response is a message ID string.
        System.out.println("Successfully sent message: " + response);
    }
}
