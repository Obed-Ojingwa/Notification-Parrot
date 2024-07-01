package com.obedhack.myparot

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.content.Intent

class MyNotificationListenerService : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        sbn?.notification?.let {
            val extras = it.extras
            val notificationTitle = extras.getString("android.title")
            val notificationText = extras.getCharSequence("android.text").toString()

            // Only capture notifications from specific apps
            val packageName = sbn.packageName
            if (packageName == "com.whatsapp" || packageName == "com.whatsapp.w4b" ||
                packageName == "org.telegram.messenger" || packageName == "com.facebook.katana" ||
                packageName == "com.facebook.orca") {
                // Send broadcast to the receiver
                val intent = Intent("com.example.NOTIFICATION_LISTENER")
                intent.putExtra("title", notificationTitle)
                intent.putExtra("text", notificationText)
                sendBroadcast(intent)
            }
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        // Handle notification removal if needed
    }
}
