package com.example.myapplication.database.downloads

import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.myapplication.database.history.DateConverter
import com.example.myapplication.database.tabInfo.BitmapConverter
import com.tonyodev.fetch2.Download
import com.tonyodev.fetch2.Priority
import java.util.Date

@Entity(tableName = "Downloads")
@TypeConverters(DateConverter::class, BitmapConverter::class, PriorityConverter::class)
data class DownloadEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "file") var file: String?,
    @ColumnInfo(name = "fileUri") var fileUri: String?,
    @ColumnInfo(name = "url") var url: String?,
    @ColumnInfo(name = "priority") var priority: Priority,
    @ColumnInfo(name = "created") var created: Date,
    @ColumnInfo(name = "progress") var progress: Int,
    @ColumnInfo(name = "total") var total: Long,
    @ColumnInfo(name = "eta") var eta: Long,
    @ColumnInfo(name = "error") var error: Boolean = false,
    @ColumnInfo(name = "cancelled") var cancelled: Boolean = false,
    @ColumnInfo(name = "paused") var paused: Boolean = false,
    @ColumnInfo(name = "bps") var bps: Long,
    @ColumnInfo(name = "waiting") var waiting: Boolean,
) {
    companion object {
        fun fromDownload(download: Download): DownloadEntity {
            Log.d("download", "extra info ${download.extras.toJSONString()}")
            return DownloadEntity(
                id = download.id.toLong(),
                error = false,
                file = download.file,
                url = download.url,
                fileUri = download.fileUri.toString(),
                progress = download.progress,
                eta = download.etaInMilliSeconds,
                total = download.total,
                paused = false,
                created = Date(download.created),
                priority = download.priority,
                cancelled = false,
                bps = download.downloadedBytesPerSecond,
                waiting = false,
            )
        }
    }
}


class PriorityConverter {
    @TypeConverter
    fun toPriority(value: String) = enumValueOf<Priority>(value)

    @TypeConverter
    fun fromPriority(value: Priority) = value.name
}
