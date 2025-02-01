package com.example.myapplication.database.bookmark

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.myapplication.database.history.DateConverter
import com.example.myapplication.database.tabInfo.BitmapConverter
import java.util.Date

@Entity(tableName = "Bookmarks")
@TypeConverters(DateConverter::class, BitmapConverter::class)
data class BookmarkEntity(
    @PrimaryKey val id: Int? = null,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "url") val url: String?,
    @ColumnInfo(name = "time") val time: Date,
    @ColumnInfo(name = "fav_icon") val favIcon: Bitmap?,
)