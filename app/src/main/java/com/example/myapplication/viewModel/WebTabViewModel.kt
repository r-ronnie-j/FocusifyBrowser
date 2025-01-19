package com.example.myapplication.viewModel

import android.content.Context
import android.os.Build
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.myapplication.webkit.AayamWebView
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class WebTabViewModel : ViewModel() {
    val webViewTabs = mutableStateListOf<AayamWebView>()
    var activeIndex by mutableStateOf(0)
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
        if (activeIndex in webViewTabs.indices) {
            webViewTabs[activeIndex] = webView
        }
    }
}

val LocalWebTabViewModel = compositionLocalOf { WebTabViewModel() }