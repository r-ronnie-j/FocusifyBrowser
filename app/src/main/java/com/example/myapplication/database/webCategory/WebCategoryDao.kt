package com.example.myapplication.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.dataClass.FilterCategory

@Dao
interface WebCategoryDao {

    @Query("SELECT * FROM webFilter")
    fun getAll(): List<WebFilterEntity>

    @Query("SELECT COUNT(*) FROM webFilter")
    fun getSize(): Int

    @Query("SELECT * FROM webFilter WHERE filterCategory = :filterCategory")
    fun getById(filterCategory: FilterCategory): WebFilterEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(webFilterEntity: WebFilterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(webFilterEntity: List<WebFilterEntity>)

    @Update()
    fun update(webFilterEntity: WebFilterEntity)
}
