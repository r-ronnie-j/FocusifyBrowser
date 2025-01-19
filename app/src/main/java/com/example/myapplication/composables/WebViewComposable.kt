package com.example.myapplication.composables

import android.webkit.WebView
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.myapplication.webkit.AayamWebView

@Composable
fun WebViewComposable() {
    var webview by remember {
        mutableStateOf<WebView?>(null)
    }

    AndroidView(
        factory = { context ->
            AayamWebView(context).apply {
                webview = this
                loadUrl("https://www.google.com")
            }
        },
        update = {
            webview = it
        },
        modifier = Modifier.fillMaxSize()

    )

    BackHandler {
        webview?.let {
            it.goBack()
        }
    }
}