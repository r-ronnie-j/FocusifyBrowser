package com.example.myapplication.viewModel

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.myapplication.dataClass.BlocksiCategory
import com.example.myapplication.dataClass.SpinWebCategory
import com.example.myapplication.dataClass.WebCategoryStatus
import com.example.myapplication.dataClass.categoryList

class FilterViewModel : ViewModel() {
    val spinBlockCategories = mutableStateListOf<SpinWebCategory>()
    val blocksiBlockCategory = mutableStateListOf<BlocksiCategory>()

    val webCategoryStatusList = mutableStateListOf<WebCategoryStatus>().apply {
        addAll(categoryList.map {
            WebCategoryStatus(
                category = it,
                blocked = false
            )
        })
    }

    fun changeStatus(index: Int) {
        webCategoryStatusList[index] = webCategoryStatusList[index].copy(
            blocked = !webCategoryStatusList[index].blocked
        )
    }
}

val LocalFilterModelProvider = compositionLocalOf {
    FilterViewModel()
}