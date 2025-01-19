package com.example.myapplication.webkit

import android.content.Context
import android.content.Intent
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient

class AayamWebClient(private val context: Context) : WebViewClient() {

    @Deprecated("Deprecated in Java")
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        if (url != null && url.startsWith("intent://")) {
            Log.d("intent", "$url")
            val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
            if (intent != null) {
                val packageManager = context.packageManager
                if (intent.resolveActivity(packageManager) != null) {
                    context.startActivity(intent)
                    return true
                }
            }
        } else {
            url?.let {
                view?.loadUrl(url)
            }
        }
        return true
    }
}