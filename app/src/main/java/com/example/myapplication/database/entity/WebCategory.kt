package com.example.myapplication.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.myapplication.dataClass.FilterCategory

@Entity(tableName = "webFilter")
@TypeConverters(FilterCategoryConverter::class)
data class WebFilterEntity(
    @PrimaryKey val filterCategory: FilterCategory,
    val blocked: Boolean,
)

class FilterCategoryConverter {
    @TypeConverter
    fun toFilterCategory(value: String) = enumValueOf<FilterCategory>(value)

    @TypeConverter
    fun fromFilterCategory(value: FilterCategory) = value.name
}
