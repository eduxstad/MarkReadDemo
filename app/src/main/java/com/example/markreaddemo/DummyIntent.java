package com.example.markreaddemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.Person;
import androidx.core.app.RemoteInput;

import java.util.ResourceBundle;

import static com.example.markreaddemo.R.drawable.ic_stat_notification;

public class DummyIntent extends BroadcastReceiver {
    //Do nothing
    @Override
    public void onReceive(Context context, Intent intent) {
        //Bundle replyInput = RemoteInput.getResultsFromIntent(intent);
        return;
    }
}