package com.example.myapplication.database.bookmark

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookmarkDao {
    @Query("SELECT * from Bookmarks")
    fun getAll(): List<BookmarkEntity>

    @Query("DELETE from Bookmarks WHERE id= :id")
    fun delete(id: Int)

    @Insert()
    fun insert(bookmark: BookmarkEntity)
}