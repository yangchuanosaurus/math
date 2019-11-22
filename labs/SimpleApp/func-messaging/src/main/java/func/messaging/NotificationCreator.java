package func.messaging;

import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import java.io.Serializable;

/**
 * Created by Albert Zhao on 2019-11-20.
 * Copyright (c) 2019 Android Mobile Yangchuanosaurus. All rights reserved.
 */
public class NotificationCreator {
    private static final String DATA_TITLE = "title";
    private static final String DATA_MESSAGE = "message";

    private final String title;
    private final String message;

    public NotificationCreator(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public android.app.Notification create(@NonNull Context context, String channelId) {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_default_notification)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri);

        return builder.build();
    }

    public static class Builder {
        private @NonNull Bundle data;

        public Builder setData(@NonNull Bundle data) {
            this.data = data;
            return this;
        }

        public NotificationCreator build() {
            return new NotificationCreator(data.getString(DATA_TITLE),
                    data.getString(DATA_MESSAGE));
        }
    }

    public static class Notification implements Serializable {
        private String icon;
        private String title;
        private Intent intent;

        public Notification(String icon, String title, Intent intent) {
            this.icon = icon;
            this.title = title;
            this.intent = intent;
        }

        public String getIcon() {
            return icon;
        }

        public String getTitle() {
            return title;
        }

        public Intent getIntent() {
            return intent;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Notification that = (Notification) o;

            if (icon != null ? !icon.equals(that.icon) : that.icon != null) return false;
            if (title != null ? !title.equals(that.title) : that.title != null) return false;
            return intent != null ? intent.equals(that.intent) : that.intent == null;
        }

        @Override
        public int hashCode() {
            int result = icon != null ? icon.hashCode() : 0;
            result = 31 * result + (title != null ? title.hashCode() : 0);
            result = 31 * result + (intent != null ? intent.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Notification{" +
                    "icon='" + icon + '\'' +
                    ", title='" + title + '\'' +
                    ", intent=" + intent +
                    '}';
        }
    }
}
