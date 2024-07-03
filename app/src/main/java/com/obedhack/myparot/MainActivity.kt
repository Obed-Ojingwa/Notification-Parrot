package com.obedhack.myparot

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val notificationReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                val title = it.getStringExtra("title")
                val text = it.getStringExtra("text")

                val sharedPreferences = getSharedPreferences("notifications", Context.MODE_PRIVATE)
                val storedNotifications = sharedPreferences.getString("notifications", "")
                val newNotifications = "$storedNotifications\n$title: $text"
                sharedPreferences.edit().putString("notifications", newNotifications).apply()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val filter = IntentFilter("com.example.NOTIFICATION_LISTENER")
        registerReceiver(notificationReceiver, filter)

        val emailWorkRequest = PeriodicWorkRequestBuilder<EmailWorker>(1, TimeUnit.HOURS).build()
        WorkManager.getInstance(this).enqueue(emailWorkRequest)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(notificationReceiver)
    }
}

