package com.example.myapplication.composables

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.myapplication.viewModel.LocalWebTabViewModel

@Composable
fun WebViewComposable() {

    val webTabViewModel = LocalWebTabViewModel.current
    val activeIndex = webTabViewModel.activeIndex
    val context = LocalContext.current
    val currentWebView = remember {
        mutableStateOf(
            if (activeIndex.intValue == webTabViewModel.webViewTabs.size) {
                webTabViewModel.createWebView(context)
            } else {
                webTabViewModel.webViewTabs[activeIndex.intValue]
            }
        )
    }

    DisposableEffect(activeIndex.intValue) {
        currentWebView.value = if (activeIndex.intValue == webTabViewModel.webViewTabs.size) {
            webTabViewModel.createWebView(context)
        } else {
            webTabViewModel.webViewTabs[activeIndex.intValue]
        }
        onDispose {}
    }

    AndroidView(
        factory = { currentWebView.value },
        update = { webView ->
            webTabViewModel.updateWebView(webView)
        },
        modifier = Modifier.fillMaxSize()
    )

    BackHandler {
        webTabViewModel
            .webViewTabs
            .elementAtOrNull(activeIndex.intValue)
            ?.goBack()
    }
}
