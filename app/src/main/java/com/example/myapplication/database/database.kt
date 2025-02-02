package com.example.myapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.database.bookmark.BookmarkDao
import com.example.myapplication.database.bookmark.BookmarkEntity
import com.example.myapplication.database.downloads.DownloadDao
import com.example.myapplication.database.downloads.DownloadEntity
import com.example.myapplication.database.history.HistoryDao
import com.example.myapplication.database.history.HistoryEntity
import com.example.myapplication.database.tabInfo.WebTabDao
import com.example.myapplication.database.tabInfo.WebTabEntity

@Database(
    entities = [
        WebFilterEntity::class,
        WebTabEntity::class,
        BookmarkEntity::class,
        HistoryEntity::class,
        DownloadEntity::class,
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun webCategoryDao(): WebCategoryDao
    abstract fun webTabDao(): WebTabDao
    abstract fun bookmarkDao(): BookmarkDao
    abstract fun historyDao(): HistoryDao
    abstract fun downloadDao(): DownloadDao
}

var db: AppDatabase? = null