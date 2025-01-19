package com.example.myapplication.webkit

import android.content.Context
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class AayamWebClient(private val context: Context) : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        request?.let {
            return !(it.url != null && it.url.toString().startsWith("intent://"))
        }
        return true
    }
}