package com.example.myapplication.database.bookmark

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.database.history.HistoryEntity

@Dao
interface BookmarkDao {
    @Query("SELECT * from Bookmarks")
    fun getAll(): List<HistoryEntity>

    @Query("DELETE from Bookmarks WHERE id= :id")
    fun delete(id: Int)

    @Insert()
    fun insert(bookmark: BookmarkEntity)
}