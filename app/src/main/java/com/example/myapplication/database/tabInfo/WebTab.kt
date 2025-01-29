package com.example.myapplication.database.tabInfo

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.io.ByteArrayOutputStream

@Entity(tableName = "webTab")
@TypeConverters(BitmapConverter::class)
data class WebTabEntity(
    @PrimaryKey val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "fav_icon") val favIcon: Bitmap?,
    @ColumnInfo(name = "url") val url: String?,
    @ColumnInfo(name = "incognito") val incognito: Boolean
)

class BitmapConverter {

    @TypeConverter
    fun fromBitmap(bitmap: Bitmap?): ByteArray? {
        return bitmap?.let {
            val stream = ByteArrayOutputStream()
            it.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.toByteArray()
        }
    }

    @TypeConverter
    fun toBitmap(byteArray: ByteArray?): Bitmap? {
        return byteArray?.let {
            BitmapFactory.decodeByteArray(it, 0, it.size)
        }
    }
}
