package com.example.myapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.database.dao.WebCategoryDao
import com.example.myapplication.database.entity.WebFilterEntity
import com.example.myapplication.database.tabInfo.WebTabDao
import com.example.myapplication.database.tabInfo.WebTabEntity

@Database(
    entities = [
        WebFilterEntity::class,
        WebTabEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun webCategoryDao(): WebCategoryDao
    abstract fun webTabDao(): WebTabDao
}

var db: AppDatabase? = null