package com.example.myapplication

import android.app.Application
import androidx.room.Room
import com.example.myapplication.dataClass.categoryList
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.WebFilterEntity
import com.example.myapplication.database.db
import com.tonyodev.fetch2.Fetch
import com.tonyodev.fetch2.FetchConfiguration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

var fetch: Fetch? = null

class FocusifyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        db = Room
            .databaseBuilder(this.applicationContext, AppDatabase::class.java, "Focusify")
            .build()

        val categoryDao = db!!.webCategoryDao()
        CoroutineScope(Dispatchers.IO).launch {
            if (categoryDao.getSize() == 0) {
                categoryDao.insertAll(categoryList.map {
                    WebFilterEntity(
                        filterCategory = it.filter,
                        blocked = false
                    )
                })
            }
        }
        val fetchConfiguration =
            FetchConfiguration.Builder(this.applicationContext).setDownloadConcurrentLimit(3)
                .build()
        fetch = Fetch.Impl.getInstance(fetchConfiguration)
    }
}