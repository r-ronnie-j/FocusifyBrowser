package com.example.myapplication.utilities.download

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.webkit.URLUtil
import android.widget.Toast
import java.io.File


@SuppressLint("UnspecifiedRegisterReceiverFlag")
fun downloadFile(
    context: Context,
    url: String,
    fileName: String? = null,
    mimeType: String? = null
) {
    try {
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val uri = Uri.parse(url)

        val request = DownloadManager.Request(uri)
            .setMimeType(mimeType)
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)
            .setTitle(fileName ?: URLUtil.guessFileName(url, null, mimeType))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

        val downloadsDir = File(
            context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
            "YourDownloadFolderName"
        )
        if (!downloadsDir.exists()) {
            downloadsDir.mkdirs()
        }

        val file = File(downloadsDir, fileName ?: URLUtil.guessFileName(url, null, mimeType))

        request.setDestinationUri(Uri.fromFile(file))

        val downloadId = downloadManager.enqueue(request) // Store the download ID

        Toast.makeText(context, "Download started...", Toast.LENGTH_SHORT).show()

        // Register a BroadcastReceiver to listen for download completion
        val onComplete = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (id == downloadId) { // Check if it's the correct download
                    Toast.makeText(context, "Download completed!", Toast.LENGTH_SHORT).show()
                    context.unregisterReceiver(this) // Unregister the receiver to avoid memory leaks
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.registerReceiver(
                    onComplete,
                    IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE),
                    Context.RECEIVER_NOT_EXPORTED
                )
            }
        } else {
            context.registerReceiver(
                onComplete,
                IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
            )
        }

    } catch (e: Exception) {
        Toast.makeText(context, "Download failed: ${e.message}", Toast.LENGTH_SHORT).show()
        e.printStackTrace()
    }
}