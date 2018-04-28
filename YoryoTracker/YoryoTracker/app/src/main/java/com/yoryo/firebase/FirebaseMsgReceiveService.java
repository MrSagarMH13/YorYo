package com.yoryo.firebase;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMsgReceiveService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String mapData = remoteMessage.getData().get("body");
        notifyMainActivity(mapData);
    }

    private void notifyMainActivity(String mapData) {
        Intent intent = new Intent();
        intent.setAction("BROADCAST_MESSAGE_NOTIFICATION_RECEIVED");
        intent.putExtra("mapData", mapData);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}