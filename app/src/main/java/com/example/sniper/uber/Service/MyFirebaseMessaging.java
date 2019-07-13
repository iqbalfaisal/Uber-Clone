package com.example.sniper.uber.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.sniper.uber.CustommerCall;
import com.example.sniper.uber.R;
import com.example.sniper.uber.RateActivity;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.util.logging.Handler;

/**
 * Created by Sniper on 12/26/2017.
 */

public class MyFirebaseMessaging extends FirebaseMessagingService {

    public void onMessageReceived(final RemoteMessage remoteMessage) {

        if (remoteMessage.getNotification().getTitle().equals("Cancel")) {
            showCancelNotification(remoteMessage.getNotification().getBody());
            
        } else if (remoteMessage.getNotification().getTitle().equals("Arrived")) {

            showArrivedNotification(remoteMessage.getNotification().getBody());
        }
        else if (remoteMessage.getNotification().getTitle().equals("DropOff")) {

            openRateActivity(remoteMessage.getNotification().getBody());
        }

        else {

            LatLng customer_location = new Gson().fromJson(remoteMessage.getNotification().getBody(), LatLng.class);

            Intent intent = new Intent(getBaseContext(), CustommerCall.class);
            intent.putExtra("lat", customer_location.latitude);
            intent.putExtra("lng", customer_location.longitude);
            intent.putExtra("customerId", remoteMessage.getNotification().getTitle());

            startActivity(intent);

        }
    }

    private void showCancelNotification(String body) {
        PendingIntent contentIntent=PendingIntent.getActivity(getBaseContext(),0,new Intent(),PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(getBaseContext());
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Cancel")
                .setContentText(body)
                .setContentIntent(contentIntent);
        NotificationManager manager=(NotificationManager)getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1,builder.build());
    }

    private void openRateActivity(String body) {

        Intent intent=new Intent(this, RateActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void showArrivedNotification(String body) {
        PendingIntent contentIntent=PendingIntent.getActivity(getBaseContext(),0,new Intent(),PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(getBaseContext());
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Arrived")
                .setContentText(body)
                .setContentIntent(contentIntent);
        NotificationManager manager=(NotificationManager)getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1,builder.build());
    }


}
