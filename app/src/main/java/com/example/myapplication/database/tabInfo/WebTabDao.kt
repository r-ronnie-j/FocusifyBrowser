package com.example.myapplication.database.tabInfo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface WebTabDao {
    @Query("SELECT * from webTab")
    fun getAll(): List<WebTabEntity>

    @Delete()
    fun delete(tab: WebTabEntity)

    @Insert()
    fun insert(tab: WebTabEntity)

    @Query("SELECT COUNT(*) FROM webTab")
    fun getSize(): Int

    @Insert()
    fun insertAll(tab: List<WebTabEntity>)

    @Upsert()
    fun insertOrUpdate(tab: WebTabEntity)

    @Query("DELETE FROM webTab WHERE id= :index")
    fun deleteAtIndex(index: Int)
}