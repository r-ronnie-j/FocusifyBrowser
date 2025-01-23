package com.example.myapplication.viewModel

import android.content.Context
import androidx.compose.runtime.compositionLocalOf
import com.example.myapplication.webkit.AayamWebView
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.dataClass.BlocksiCategory
import com.example.myapplication.dataClass.SpinWebCategory
import com.example.myapplication.dataClass.TabInfo
import com.example.myapplication.dataClass.WebCategoryStatus
import com.example.myapplication.dataClass.categoryList
import com.example.myapplication.database.db
import com.example.myapplication.database.entity.WebFilterEntity
import com.example.myapplication.utilities.enums.SearchEngines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import java.net.URLEncoder

class WebTabViewModel : ViewModel() {
    val webViewTabs = mutableStateListOf<AayamWebView>()
    var activeIndex = mutableIntStateOf(0)
    var tabInfo = mutableStateListOf<TabInfo>()
    var isIncognito by mutableStateOf(false)

    private val spinBlockCategories = mutableStateListOf<SpinWebCategory>()
    private val blocksiBlockCategory = mutableStateListOf<BlocksiCategory>()
    val webCategoryStatusList = mutableStateListOf<WebCategoryStatus>()

    init {
        db?.let { db ->
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    val webCategoryDao = db.webCategoryDao()
                    val spinFilters = mutableListOf<SpinWebCategory>()
                    val blocksiFilters = mutableListOf<BlocksiCategory>()
                    val category = categoryList.map {
                        val filterStatus = webCategoryDao.getById(it.filter)
                        if (filterStatus.blocked) {
                            spinFilters.addAll(it.spin)
                            blocksiFilters.addAll(it.blocksi)
                        }
                        return@map WebCategoryStatus(
                            category = it,
                            blocked = filterStatus.blocked
                        )
                    }
                    withContext(Dispatchers.Main) {
                        webCategoryStatusList.addAll(category)
                        spinBlockCategories.addAll(spinFilters)
                        blocksiBlockCategory.addAll(blocksiFilters)
                    }
                }
            }
        }
    }

    fun changeStatus(index: Int) {
        val initialStatus = webCategoryStatusList[index].blocked
        webCategoryStatusList[index] = webCategoryStatusList[index].copy(
            blocked = !initialStatus
        )
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db?.let {
                    val webCategoryDao = it.webCategoryDao()
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

    fun performSearch(
        text: String,
        searchEngine: SearchEngines
    ) {
        val webView = webViewTabs[activeIndex.intValue]

        val isValidUrl = try {
            val url = URL(text)
            url.protocol == "http" || url.protocol == "https"
        } catch (e: Exception) {
            false
        }

        val url = if (isValidUrl) {
            text
        } else {
            val query = URLEncoder.encode(text, "UTF-8")
            when (searchEngine) {
                SearchEngines.Google -> "https://www.google.com/search?q=$query"
                SearchEngines.Duckduckgo -> "https://duckduckgo.com/?q=$query"
                SearchEngines.Yandex -> "https://yandex.com/search/?text=$query"
                SearchEngines.Bing -> "https://www.bing.com/search?q=$query"
            }
        }

        webView.loadUrl(url)
    }


    fun createWebView(context: Context): AayamWebView {
        val index = webViewTabs.size
        val webView = AayamWebView(
            context = context,
            onTitleReceive = {
                if (index == tabInfo.size) {
                    tabInfo.add(
                        TabInfo(
                            title = it ?: "No title found",
                            incognito = isIncognito,
                            favIcon = null,
                            progress = 0
                        )
                    )
                } else if (index < tabInfo.size) {
                    tabInfo[index] = tabInfo[index].copy(
                        title = it ?: "No title found"
                    )
                }
            },
            onIconReceive = {
                if (index == tabInfo.size) {
                    tabInfo.add(
                        TabInfo(
                            title = "No title found",
                            incognito = isIncognito,
                            favIcon = it,
                            progress = 0
                        )
                    )
                } else if (index < tabInfo.size) {
                    tabInfo[index] = tabInfo[index].copy(
                        favIcon = it
                    )
                }
            },
            onProgress = {
                if (index == tabInfo.size) {
                    tabInfo.add(
                        TabInfo(
                            title = "Loading...",
                            incognito = isIncognito,
                            favIcon = null,
                            progress = it
                        )
                    )
                } else if (index < tabInfo.size) {
                    tabInfo[index] = tabInfo[index].copy(
                        progress = it
                    )
                }
            },
            shouldBlock = { blocksi, spin ->
                return@AayamWebView blocksi.any { blocksiBlockCategory.contains(it) } ||
                        spin.any { spinBlockCategories.contains(it) }
            }
        )
        webView.loadUrl("file:///android_asset/home/home.html")
        webViewTabs.add(index, webView)
        return webView
    }
}

val LocalWebTabViewModel = compositionLocalOf { WebTabViewModel() }