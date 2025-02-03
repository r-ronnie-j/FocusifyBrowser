package com.example.myapplication.utilities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.myapplication.FocusifyApplication

fun showNotification(title: String, text: String, progress: Int) {
    val context = FocusifyApplication.getInstance()
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val channelId = "fetch_download_channel"

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel =
            NotificationChannel(channelId, "Downloads", NotificationManager.IMPORTANCE_LOW).apply {
                description = "Download Progress Notifications"
            }
        notificationManager.createNotificationChannel(channel)
    }

    val iconRes = when {
        progress in 0..99 -> android.R.drawable.stat_sys_download
        progress == 100 -> android.R.drawable.stat_sys_download_done
        else -> android.R.drawable.stat_notify_error
    }

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(iconRes)
        .setContentTitle(title)
        .setContentText(text)
        .setPriority(NotificationCompat.PRIORITY_LOW)
        .setOngoing(progress in 0..99)

    if (progress in 0..99) {
        builder.setProgress(100, progress, false) // Show progress bar
    } else {
        builder.setProgress(0, 0, false) // Remove progress bar
    }

    notificationManager.notify(title.hashCode(), builder.build())
}
