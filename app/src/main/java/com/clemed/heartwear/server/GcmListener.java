package com.clemed.heartwear.server;


import android.app.Notification;
import android.app.NotificationManager;
import android.os.Bundle;
import android.util.Log;

import com.clemed.heartwear.R;
import com.google.android.gms.gcm.GcmListenerService;

public class GcmListener extends GcmListenerService {
    public GcmListener() {
    }

    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);

        String message = data.getString("alert");
        //Log.d("new message", "Message: " + message);
        if(message == null){
            return;
        }

       // Intent intent = new Intent(this, NotificationReceiver.class);
        // use System.currentTimeMillis() to have a unique ID for the pending intent
       // PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        // build notification
        // the addAction re-use the same intent to keep the example short
        if(!message.equals("Registration complete.")) {
            Notification n = new Notification.Builder(this)
                    .setContentTitle("Alert")
                    .setContentText(message)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true).build();

            Log.d("new message", "Message: " + message);


            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            notificationManager.notify(0, n);
        }


    }

}
