package com.example.myapplication.database.downloads

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert

@Dao
interface DownloadDao {
    @Insert()
    fun insert(download: DownloadEntity): Long

    @Query("SELECT * from Downloads ORDER By created DESC")
    fun getAll(): List<DownloadEntity>

    @Query("DELETE from Downloads where id =:id")
    fun deleteFromId(id: Long)

    @Update()
    fun update(download: DownloadEntity)

    @Upsert()
    fun upsert(download: DownloadEntity)

    @Query("SELECT * from Downloads where id=:id LIMIT 1 ")
    fun getFromId(id: Int): DownloadEntity?
}