package com.example.markreaddemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.Person;
import androidx.core.app.RemoteInput;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.app.Service;

import static androidx.core.app.ServiceCompat.stopForeground;
import static com.example.markreaddemo.R.drawable.ic_stat_notification;

public class MainActivity extends AppCompatActivity {

    private NotificationCompat.Builder notificationBuilder;
    public static final String CHANNEL_ID = "defaultNotificationChannel";
    private Intent notifyIntent;
    private Intent clearIntent;
    private PendingIntent pendingIntent;
    private PendingIntent clearPendingIntent;
    private NotificationManagerCompat notificationManager;
    private Person demoPerson;
    private NotificationCompat.MessagingStyle style;
    int notificationId = 50000;
    public static final  String NOTIFICATION_ID_EXTRA = "notification_id";

    // Key for the string that's delivered in the action's intent.
    public static final String KEY_TEXT_REPLY = "key_text_reply";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
        notificationManager = NotificationManagerCompat.from(this);

        //prime the bug by sending and cancelling 5 notifications
        sendNotification(null);
        cancelNotification(null);
        sendNotification(null);
        cancelNotification(null);
        sendNotification(null);
        cancelNotification(null);
        sendNotification(null);
        cancelNotification(null);
        sendNotification(null);
        cancelNotification(null);
    }
    //create and send the sample notification
    public void sendNotification(View view) {
        Log.i("MainActivity", "Sending notification");
        notificationId++;

        notifyIntent = new Intent(this, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        pendingIntent = PendingIntent.getActivity(this, 0, notifyIntent, 0);
        clearIntent = new Intent(this, ClearReceiver.class);
        clearIntent.setAction("CLEAR_ACTION");
        clearIntent.setData((Uri.parse("custom://"+System.currentTimeMillis())));
        clearIntent.putExtra(NOTIFICATION_ID_EXTRA, notificationId);
        clearPendingIntent = PendingIntent.getBroadcast(this, 0, clearIntent, 0);
        demoPerson = new Person.Builder().setName("Isaac Asimov").build();
        style = new NotificationCompat.MessagingStyle(demoPerson)
                .addMessage("Do you want to get something to eat?",System.currentTimeMillis(),demoPerson);
        RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
                .setLabel("Reply with a message")
                .build();
        //The intent for the reply button is a dummy intent that does nothing
        Intent dummyIntent = new Intent(this, DummyIntent.class);
        dummyIntent.setAction("REPLY_ACTION");
        dummyIntent.setData((Uri.parse("custom://"+System.currentTimeMillis())));
        dummyIntent.putExtra(NOTIFICATION_ID_EXTRA, notificationId);
        PendingIntent replyPendingIntent = PendingIntent.getBroadcast(this,0, dummyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(ic_stat_notification,
                        getString(R.string.reply), replyPendingIntent)
                        .addRemoteInput(remoteInput)
                        .build();

        notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
        notificationBuilder.setSmallIcon(ic_stat_notification);
        notificationBuilder.setContentTitle("Sample Notification");
        notificationBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notificationBuilder.addAction(ic_stat_notification, "Clear", clearPendingIntent);
        notificationBuilder.addAction(action);
        notificationBuilder.setStyle(style);
        notificationManager.notify(notificationId, notificationBuilder.build());
    }
    //cancel the latest notification using the current value of notificationId
    public void cancelNotification(View view) {
        notificationManager.cancel(notificationId);
    }
    //cancel all notifications handled by the notificationManager
    public void cancelAllNotification(View view) {
        notificationManager.cancelAll();
    }
    //create a notification channel
    private void createNotificationChannel() {
        CharSequence name = getString(R.string.channel_name);
        String description = getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}

