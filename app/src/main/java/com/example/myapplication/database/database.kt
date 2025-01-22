package com.example.myapplication.database

import androidx.compose.runtime.compositionLocalOf
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.database.dao.WebCategoryDao
import com.example.myapplication.database.entity.WebFilterEntity

@Database(
    entities = [
        WebFilterEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun webCategoryDao(): WebCategoryDao
}

val LocalDatabase = compositionLocalOf {
    error("No database created or provided")
}