package com.example.myapplication.viewModel

import android.content.Context
import androidx.compose.runtime.compositionLocalOf
import com.example.myapplication.webkit.AayamWebView
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.myapplication.dataClass.TabInfo

class WebTabViewModel : ViewModel() {
    val webViewTabs = mutableStateListOf<AayamWebView>()
    var activeIndex = mutableIntStateOf(0)
    var tabInfo = mutableStateListOf<TabInfo>()
    var isIncognito by mutableStateOf(false)

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
                        title = "No title found"
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
        webView.loadUrl("https://www.duckduckgo.com")
        webViewTabs.add(index, webView)
        return webView
    }
}

val LocalWebTabViewModel = compositionLocalOf { WebTabViewModel() }