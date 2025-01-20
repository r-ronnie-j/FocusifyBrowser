package com.example.myapplication.composables

import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.myapplication.viewModel.LocalWebTabViewModel

@Composable
fun WebViewComposable() {

    val webTabViewModel = LocalWebTabViewModel.current
    val activeIndex = webTabViewModel.activeIndex
    val context = LocalContext.current

    AndroidView(
        factory = {
            FrameLayout(it)
        },
        update = {
            if (activeIndex.intValue == webTabViewModel.webViewTabs.size) {
                val webview = webTabViewModel.createWebView(context)
                it.removeAllViews()
                it.addView(webview)
            } else {
                val webview = webTabViewModel.webViewTabs[activeIndex.intValue]
                it.removeAllViews()
                it.addView(webview)
            }
        },
        modifier = Modifier.fillMaxSize(),
    )



    BackHandler {
        webTabViewModel.webViewTabs.elementAtOrNull(activeIndex.intValue)?.goBack()
    }

}
