package com.gtasoft.jeveuxmonvaccin.worker;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import androidx.annotation.NonNull;
import com.gtasoft.jeveuxmonvaccin.AndroidPlatform;


public class AlertManager {
    @NonNull
    public static String CHANNEL_ID = AndroidPlatform.TAGID;
    Activity myContext;

    public AlertManager(@NonNull Activity context) {


        myContext = context;


    }

    public void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = CHANNEL_ID;
            String description = "Les alertes pour application jeveuxmonvaccin sont disponibles ici";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = myContext.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
