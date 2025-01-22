package com.example.myapplication.viewModel

import android.content.Context
import androidx.compose.runtime.compositionLocalOf
import com.example.myapplication.webkit.AayamWebView
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.myapplication.dataClass.TabInfo
import com.example.myapplication.utilities.enums.SearchEngines
import java.net.URLEncoder

class WebTabViewModel : ViewModel() {
    val webViewTabs = mutableStateListOf<AayamWebView>()
    var activeIndex = mutableIntStateOf(0)
    var tabInfo = mutableStateListOf<TabInfo>()
    var isIncognito by mutableStateOf(false)

    fun performSearch(
        text: String,
        searchEngine: SearchEngines
    ) {
        val webView = webViewTabs[activeIndex.intValue]
        val query = URLEncoder.encode(text, "UTF-8")
        val url = when (searchEngine) {
            SearchEngines.Google -> "https://www.google.com/search?q=$query"
            SearchEngines.Duckduckgo -> "https://duckduckgo.com/?q=$query"
            SearchEngines.Yandex -> "https://yandex.com/search/?text=$query"
            SearchEngines.Bing -> "https://www.bing.com/search?q=$query"
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
                            favIcon = null
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
                            favIcon = it
                        )
                    )
                } else if (index < tabInfo.size) {
                    tabInfo[index] = tabInfo[index].copy(
                        favIcon = it
                    )
                }
            }
        )
        webView.loadUrl("file:///android_asset/home/home.html")
        webViewTabs.add(index, webView)
        return webView
    }
}

val LocalWebTabViewModel = compositionLocalOf { WebTabViewModel() }