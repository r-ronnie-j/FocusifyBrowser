package com.example.myapplication.database.history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoryDao {
    @Query("SELECT * from History")
    fun getAll(): List<HistoryEntity>

    @Query("DELETE from History")
    fun clearAll()

    @Query("DELETE from History WHERE id= :id")
    fun delete(id: Int)

    @Insert()
    fun add(history: HistoryEntity)
}