package com.example.myapplication.utilities.download

import java.text.DecimalFormat

object DownloadSizeConverter {

    fun formatSpeed(bytes: Long): String {
        if (bytes < 0) return ""
        val df = DecimalFormat("#.##")
        val kbps = bytes / 1000.0
        val mbps = kbps / 1000.0
        val gbps = mbps / 1000.0

        return when {
            gbps >= 1 -> "${df.format(gbps)} Gbps"
            mbps >= 1 -> "${df.format(mbps)} Mbps"
            kbps >= 1 -> "${df.format(kbps)} Kbps"
            else -> "$bytes bps"
        }
    }

    fun formatSize(bytes: Long): String {
        if (bytes < 0) return ""
        val df = DecimalFormat("#.##")
        val kb = bytes / 1024.0
        val mb = kb / 1024.0
        val gb = mb / 1024.0

        return when {
            gb >= 1 -> "${df.format(gb)}gb"
            mb >= 1 -> "${df.format(mb)}mb"
            kb >= 1 -> "${df.format(kb)}kb"
            else -> "$bytes B"
        }
    }

    fun formatTime(milliseconds: Long): String {
        if (milliseconds < 0) return ""
        val df = DecimalFormat("#.##")
        val seconds = milliseconds / 1000.0
        val minutes = seconds / 60.0
        val hours = minutes / 60.0
        val days = hours / 24.0

        return when {
            days >= 1 -> "${df.format(days)} days"
            hours >= 1 -> "${df.format(hours)} h"
            minutes >= 1 -> "${df.format(minutes)} min"
            seconds >= 1 -> "${df.format(seconds)} s"
            else -> "$milliseconds ms"
        }
    }
}