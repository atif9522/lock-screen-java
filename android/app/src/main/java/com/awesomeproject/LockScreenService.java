package com.awesomeproject;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

public class LockScreenService extends Service {

    private static final String CHANNEL_ID = "LockScreenServiceChannel";
    private static final String TAG = "LockScreenService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Service created");

        // Register for screen state broadcasts
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(new ScreenReceiver(), filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Service started");

        // Create notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "Lock Screen Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

            Notification notification = new Notification.Builder(this, CHANNEL_ID)
                .setContentTitle("Lock Screen Service")
                .setContentText("Running")
                .setSmallIcon(R.drawable.ic_notification)
                .build();
            startForeground(1, notification);
        }

        // Start LockScreenActivity
        Intent lockScreenIntent = new Intent(this, LockScreenActivity.class);
        lockScreenIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(lockScreenIntent);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Service destroyed");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
