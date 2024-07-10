package com.awesomeproject;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import androidx.core.app.NotificationCompat;
import android.app.KeyguardManager;

public class OverlayService extends Service {

    private WindowManager windowManager;
    private View overlayView;
    private WindowManager.LayoutParams layoutParams;
    private BroadcastReceiver screenReceiver;
    private Handler handler;

    private static final String TAG = "OverlayService";
    private static final String CHANNEL_ID = "OverlayServiceChannel";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Service created");

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        overlayView = LayoutInflater.from(this).inflate(R.layout.overlay, null);

        layoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ?
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY :
                        WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                -3
        );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = CHANNEL_ID;
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Overlay Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
            Notification notification = new NotificationCompat.Builder(this, channelId)
                    .setContentTitle("Overlay Service")
                    .setContentText("Running...")
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .build();
            startForeground(1, notification);
        }

        screenReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (Intent.ACTION_SCREEN_OFF.equals(action) || Intent.ACTION_SCREEN_ON.equals(action) || Intent.ACTION_USER_PRESENT.equals(action)) {
                    checkAndShowOverlay();
                }
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(screenReceiver, filter);

        handler = new Handler();
        handler.post(checkOverlayRunnable);

        checkAndShowOverlay(); // Check initial state
    }

    private Runnable checkOverlayRunnable = new Runnable() {
        @Override
        public void run() {
            checkAndShowOverlay();
            handler.postDelayed(this, 1000); // Repeat every second
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (overlayView != null) {
            windowManager.removeView(overlayView);
        }
        if (screenReceiver != null) {
            unregisterReceiver(screenReceiver);
        }
        if (handler != null) {
            handler.removeCallbacks(checkOverlayRunnable);
        }
        Log.d(TAG, "Service destroyed");
    }

    private void checkAndShowOverlay() {
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        if (keyguardManager.isKeyguardLocked() || isScreenOn()) {
            showOverlay();
        } else {
            hideOverlay();
        }
    }

    private void showOverlay() {
        if (overlayView.getWindowToken() == null) {
            windowManager.addView(overlayView, layoutParams);
        }
    }

    private void hideOverlay() {
        if (overlayView.getWindowToken() != null) {
            windowManager.removeView(overlayView);
        }
    }

    private boolean isScreenOn() {
        return ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getState() == android.view.Display.STATE_ON;
    }
}
