package com.obedhack.myparot

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.work.Worker
import androidx.work.WorkerParameters

class EmailWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        if (isNetworkAvailable(applicationContext)) {
            val sharedPreferences = applicationContext.getSharedPreferences("notifications", Context.MODE_PRIVATE)
            val notifications = sharedPreferences.getString("notifications", "")

            if (!notifications.isNullOrEmpty()) {
                EmailSender.sendEmail("Notification Summary", notifications, "recipient@example.com")
                sharedPreferences.edit().putString("notifications", "").apply()
            }
        }
        return Result.success()
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
