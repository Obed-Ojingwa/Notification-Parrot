package com.obedhack.myparot

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class EmailWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        // Get the stored notifications
        val sharedPreferences = applicationContext.getSharedPreferences("notifications", Context.MODE_PRIVATE)
        val notifications = sharedPreferences.getString("notifications", "")

        // Send the email
        if (!notifications.isNullOrEmpty()) {
            EmailSender.sendEmail("Notification Summary", notifications, "obedchukwunenye@gmail.com")

            // Clear the stored notifications
            sharedPreferences.edit().putString("notifications", "").apply()
        }

        return Result.success()
    }
}
