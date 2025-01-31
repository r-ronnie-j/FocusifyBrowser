package com.example.myapplication.database.bookmark

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.myapplication.database.history.DateConverter
import java.util.Date

@Entity(tableName = "Bookmarks")
@TypeConverters(DateConverter::class)
data class BookmarkEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "time") val time: Date
)