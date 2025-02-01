package com.example.myapplication.viewModel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.dataClass.BlocksiCategory
import com.example.myapplication.dataClass.SpinWebCategory
import com.example.myapplication.dataClass.TabInfo
import com.example.myapplication.dataClass.WebCategoryStatus
import com.example.myapplication.dataClass.categoryList
import com.example.myapplication.database.WebFilterEntity
import com.example.myapplication.database.bookmark.BookmarkEntity
import com.example.myapplication.database.db
import com.example.myapplication.database.history.HistoryEntity
import com.example.myapplication.database.tabInfo.WebTabEntity
import com.example.myapplication.utilities.Home_Url
import com.example.myapplication.utilities.enums.SearchEngines
import com.example.myapplication.webkit.AayamWebView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import java.net.URLEncoder
import java.util.Date


class WebTabViewModel() : ViewModel() {
    val webViewTabs = mutableStateListOf<AayamWebView>()
    var activeIndex = mutableIntStateOf(0)
    var tabInfo = MutableStateFlow<List<TabInfo>>(emptyList())
    var isIncognito by mutableStateOf(false)
    var restored by mutableStateOf(false)
    var history = mutableStateListOf<HistoryEntity>()
    var bookmarks = mutableStateListOf<BookmarkEntity>()

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
                    val allHistory = db.historyDao().getAll()
                    val allBookmark = db.bookmarkDao().getAll()
                    withContext(Dispatchers.Main) {
                        webCategoryStatusList.addAll(category)
                        spinBlockCategories.addAll(spinFilters)
                        blocksiBlockCategory.addAll(blocksiFilters)
                        history.addAll(allHistory)
                        bookmarks.addAll(allBookmark)
                    }
                }
            }
        }
        viewModelScope.launch {
            tabInfo.collectLatest {
                withContext(Dispatchers.IO) {
                    if (restored) {
                        db?.let { db ->
                            val tabInfoDao = db.webTabDao()
                            val tabSize = tabInfoDao.getSize()
                            it.forEachIndexed { index, tab ->
                                tabInfoDao.insertOrUpdate(
                                    WebTabEntity(
                                        title = tab.title,
                                        favIcon = tab.favIcon,
                                        id = index,
                                        url = tab.url,
                                        incognito = tab.incognito
                                    )
                                )
                            }
                            if (tabSize > it.size) {
                                for (x in it.size..<tabSize) tabInfoDao.deleteAtIndex(x)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun addHistory(h: HistoryEntity) {
        Log.d("history", "check restored $restored")
        if (restored) {
            history.add(h)
            db?.let { db ->
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        val historyDow = db.historyDao()
                        historyDow.add(h)
                    }
                }
            }
        }
    }

    fun deleteHistoryById(id: Int) {
        val h = history.removeIf { it.id == id }
        if (h) {
            db?.let { db ->
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        val historyDao = db.historyDao()
                        historyDao.delete(id)
                    }
                }
            }
        }
    }

    fun addBookmark(b: BookmarkEntity) {
        bookmarks.add(b)
        db?.let { db ->
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    val bookmarkDao = db.bookmarkDao()
                    bookmarkDao.insert(b)
                }
            }
        }
    }

    fun deleteBookmarkById(id: Int) {
        val b = bookmarks.removeIf { it.id == id }
        if (b) {
            db?.let { db ->
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        val bookmarkDao = db.bookmarkDao()
                        bookmarkDao.delete(id)
                    }
                }
            }
        }
    }

    fun restore(context: Context) {
        db?.let { db ->
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    val tabDao = db.webTabDao()
                    val tabs = tabDao.getAll()
                    val notIncognitoTabs = tabs.filter { !it.incognito }
                    tabs.forEach { tabDao.delete(it) }
                    withContext(Dispatchers.Main) {
                        notIncognitoTabs.forEach { tab ->
                            if (tab.url == null) {
                                createWebView(context, historyRestore = false)
                            } else {
                                createWebView(context, tab.url, historyRestore = false)
                            }
                        }
                        restored = true
                    }
                }
            }
        }
    }

    fun deleteTabAtIndex(index: Int) {
        tabInfo.update { list ->
            if (index in list.indices) {
                Log.d("tabs", "$index is in list ${list.indices}")
                list.toMutableList().apply {
                    removeAt(index)
                }.toList()
            } else {
                list
            }
        }
        if (index == 0 && activeIndex.intValue == 0) activeIndex.intValue = 0
        else if (index <= activeIndex.intValue) activeIndex.intValue--
        webViewTabs.removeAt(index)
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


    fun createWebView(
        context: Context,
        url: String = Home_Url,
        historyRestore: Boolean = true
    ): AayamWebView {
        var historyR = historyRestore
        val index = webViewTabs.size
        val webView = AayamWebView(
            context = context,
            onTitleReceive = { title ->
                tabInfo.update { list ->
                    val newList = list.toMutableList()
                    if (index >= newList.size) {
                        newList.add(
                            TabInfo(
                                title = title ?: "No title found",
                                incognito = isIncognito,
                                favIcon = null,
                                progress = 0,
                                url = null
                            )
                        )
                    } else {
                        newList[index] = newList[index].copy(title = title ?: "No title found")
                    }
                    newList
                }
            },
            onIconReceive = { icon ->
                tabInfo.update { list ->
                    try {
                        val history = this.webViewTabs[index].copyBackForwardList()
                        val currentItem = history.currentItem
                        if (currentItem != null && historyR) {
                            addHistory(
                                HistoryEntity(
                                    url = currentItem.url,
                                    title = currentItem.title,
                                    favIcon = currentItem.favicon,
                                    time = Date(),
                                )
                            )
                        }
                        historyR = true
                    } catch (_: Exception) {
                    }
                    val newList = list.toMutableList()
                    if (index >= newList.size) {
                        newList.add(
                            TabInfo(
                                title = "No title found",
                                incognito = isIncognito,
                                favIcon = icon,
                                progress = 0,
                                url = null
                            )
                        )
                    } else {
                        newList[index] = newList[index].copy(favIcon = icon)
                    }
                    newList
                }
            },
            onProgress = { progress ->
                tabInfo.update { list ->
                    val newList = list.toMutableList()
                    if (index >= newList.size) {
                        newList.add(
                            TabInfo(
                                title = "Loading...",
                                incognito = isIncognito,
                                favIcon = null,
                                progress = progress,
                                url = null
                            )
                        )
                    } else {
                        newList[index] = newList[index].copy(progress = progress)
                    }
                    newList
                }
            },
            onUrlChange = { loadedUrl ->
                tabInfo.update { list ->
                    val newList = list.toMutableList()
                    if (index >= newList.size) {
                        newList.add(
                            TabInfo(
                                title = "Loading...",
                                incognito = isIncognito,
                                favIcon = null,
                                progress = 0,
                                url = loadedUrl
                            )
                        )
                    } else {
                        newList[index] = newList[index].copy(url = loadedUrl)
                    }
                    newList
                }
            },
            shouldBlock = { blocksi, spin ->
                return@AayamWebView blocksi.any { blocksiBlockCategory.contains(it) } ||
                        spin.any { spinBlockCategories.contains(it) }
            },
        )

        webView.loadUrl(url)
        webViewTabs.add(webView)
        return webView
    }
}

val LocalWebTabViewModel = compositionLocalOf { WebTabViewModel() }