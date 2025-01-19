package com.example.myapplication.viewModel

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myapplication.webkit.AayamWebView

class WebTabViewModel : ViewModel() {
    val webViewTabs by mutableStateOf<MutableList<AayamWebView>>(mutableListOf())
    var activeIndex by mutableStateOf(0)

    fun addWebView(webView: AayamWebView) {
        webViewTabs.add(webView)
        webView.loadUrl("https://www.youtube.com/")
    }

    fun updateWebView(webView: AayamWebView) {
        webViewTabs[activeIndex] = webView
    }
}

val LocalWebTabViewModel = compositionLocalOf { WebTabViewModel() }