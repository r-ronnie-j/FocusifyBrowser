package com.example.myapplication.database.history

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.myapplication.database.tabInfo.BitmapConverter
import java.util.Date

@Entity(tableName = "History")
@TypeConverters(DateConverter::class, BitmapConverter::class)
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "time") val time: Date,
    @ColumnInfo(name = "fav_icon") val favIcon: Bitmap?,
)

class DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
