package com.example.myapplication.viewModel

import android.util.Log
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.dataClass.BlocksiCategory
import com.example.myapplication.dataClass.SpinWebCategory
import com.example.myapplication.dataClass.WebCategoryStatus
import com.example.myapplication.dataClass.categoryList
import com.example.myapplication.database.db
import com.example.myapplication.database.entity.WebFilterEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FilterViewModel : ViewModel() {
    val spinBlockCategories = mutableStateListOf<SpinWebCategory>()
    val blocksiBlockCategory = mutableStateListOf<BlocksiCategory>()

    val webCategoryStatusList = mutableStateListOf<WebCategoryStatus>()

    init {
        db?.let { db ->
            Log.d("database", "database has been initialized")
            viewModelScope.launch {
                Log.d("database", "view model scope is called")
                withContext(Dispatchers.IO) {
                    Log.d("database", "dispatcher is called")
                    val webCategoryDao = db.webCategoryDao()
                    Log.d("database", "dao is received ${webCategoryStatusList.size}")
                    val spinFilters = mutableListOf<SpinWebCategory>()
                    val blocksiFilters = mutableListOf<BlocksiCategory>()
                    val categoryList = categoryList.map {
                        Log.d("database", "Are we called")
                        val filterStatus = webCategoryDao.getById(it.filter)
                        Log.d(
                            "database",
                            "filter ${filterStatus.blocked} ${filterStatus.filterCategory}"
                        )
                        if (filterStatus.blocked) {
                            spinFilters.addAll(it.spin)
                            blocksiFilters.addAll(it.blocksi)
                        }
                        return@map WebCategoryStatus(
                            category = it,
                            blocked = filterStatus.blocked
                        )
                    }
                    webCategoryStatusList.removeAll(webCategoryStatusList)
                    webCategoryStatusList.addAll(categoryList)
                    spinBlockCategories.removeAll(spinBlockCategories)
                    spinBlockCategories.addAll(spinFilters)
                    blocksiBlockCategory.removeAll(blocksiBlockCategory)
                    blocksiBlockCategory.addAll(blocksiFilters)
                }
            }
        }
    }

    fun changeStatus(index: Int) {
        Log.d("database", "Are we called for database update")
        val initialStatus = webCategoryStatusList[index].blocked
        webCategoryStatusList[index] = webCategoryStatusList[index].copy(
            blocked = !initialStatus
        )
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db?.let {
                    val webCategoryDao = it.webCategoryDao()
                    Log.d("database", "Update to database has been called")
                    webCategoryDao.update(
                        WebFilterEntity(
                            blocked = !initialStatus,
                            filterCategory = webCategoryStatusList[index].category.filter
                        )
                    )
                }
            }
        }
        val spin = webCategoryStatusList[index].category.spin
        val blocksi = webCategoryStatusList[index].category.blocksi
        if (initialStatus) {
            spin.forEach { spinBlockCategories.remove(it) }
            blocksi.forEach { blocksiBlockCategory.remove(it) }
        } else {
            spin.forEach { spinBlockCategories.add(it) }
            blocksi.forEach { blocksiBlockCategory.add(it) }
        }
    }
}

val LocalFilterModelProvider = compositionLocalOf {
    FilterViewModel()
}