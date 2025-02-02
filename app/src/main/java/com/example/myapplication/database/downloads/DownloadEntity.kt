package com.example.myapplication.database.downloads

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.myapplication.database.history.DateConverter
import com.example.myapplication.database.tabInfo.BitmapConverter
import java.util.Date

@Entity(tableName = "Downloads")
@TypeConverters(DateConverter::class, BitmapConverter::class)
data class BookmarkEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "url") val url: String?,
    @ColumnInfo(name = "time") val time: Date,
    @ColumnInfo(name = "progress") val progress: Int,
)