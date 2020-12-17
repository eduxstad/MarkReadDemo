package com.example.markreaddemo;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationManagerCompat;

import static androidx.core.app.ServiceCompat.stopForeground;

public class ClearReceiver  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("ClearReceiver", "Clear was pressed");
        int id = intent.getIntExtra(MainActivity.NOTIFICATION_ID_EXTRA, -1);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.cancel(id);
    }
}
