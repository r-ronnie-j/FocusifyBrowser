package com.example.myapplication.composables

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.myapplication.viewModel.LocalWebTabViewModel
import com.example.myapplication.webkit.AayamWebView

@Composable
fun WebViewComposable() {

    val webTabViewModel = LocalWebTabViewModel.current

    AndroidView(
        factory = { context ->
            if (webTabViewModel.activeIndex == webTabViewModel.webViewTabs.size) {
                val webView = AayamWebView(context)
                webTabViewModel.addWebView(webView)
                webView
            } else {
                webTabViewModel.webViewTabs[webTabViewModel.activeIndex]
            }
        },
        update = {
            webTabViewModel.updateWebView(it)
        },
        modifier = Modifier.fillMaxSize()
    )

    BackHandler {
        webTabViewModel
            .webViewTabs
            .elementAtOrNull(webTabViewModel.activeIndex)
            ?.let {
                it.goBack()
            }
    }
}