package com.example.myapplication.viewModel

import android.content.Context
import androidx.compose.runtime.compositionLocalOf
import com.example.myapplication.webkit.AayamWebView
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class WebTabViewModel : ViewModel() {
    val webViewTabs = mutableStateListOf<AayamWebView>()
    var activeIndex = mutableIntStateOf(0)
    var title = mutableStateListOf<String>()

    fun createWebView(context: Context): AayamWebView {
        val index = webViewTabs.size
        val webView = AayamWebView(context) {
            if (index == title.size) {
                title.add(it)
            } else if (index < title.size) {
                title[index] = it
            }
        }
        webView.loadUrl("https://www.duckduckgo.com")
        webViewTabs.add(index, webView)
        return webView
    }

    fun updateWebView(webView: AayamWebView) {
        if (activeIndex.value in webViewTabs.indices) {
            webViewTabs[activeIndex.value] = webView
        }
    }
}

val LocalWebTabViewModel = compositionLocalOf { WebTabViewModel() }