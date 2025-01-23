package com.example.myapplication.webkit

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.myapplication.dataClass.BlocksiCategory
import com.example.myapplication.dataClass.SpinWebCategory
import com.example.myapplication.utilities.category.getBlocksiCategory
import com.example.myapplication.utilities.category.getSpinCategory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AayamWebClient(
    private val context: Context,
    private val shouldBlock: (blocksi: List<BlocksiCategory>, spin: List<SpinWebCategory>) -> Boolean
) : WebViewClient() {

    @SuppressLint("QueryPermissionsNeeded")
    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        val url = request.url.toString()
        if (url.startsWith("intent://")) {
            val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
            if (intent != null) {
                val packageManager = context.packageManager
                if (intent.resolveActivity(packageManager) != null) {
                    context.startActivity(intent)
                }
            }
        } else {
            view.loadUrl(url)
            CoroutineScope(Dispatchers.IO).launch {
                val blocksi = getBlocksiCategory(url)
                val spin = getSpinCategory(url)
                if (shouldBlock(blocksi, spin)) {
                    val replaceUrl = "file:///android_asset/block/index.html"
                    view.loadUrl(
                        "javascript:(function() { " +
                                "window.location.replace('$replaceUrl'); " +
                                "})()"
                    )
                }
            }
        }
        return true
    }

//    @SuppressLint("QueryPermissionsNeeded")
//    @Deprecated("Deprecated in Java")
//    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
//        if (url != null && url.startsWith("intent://")) {
//            val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
//            if (intent != null) {
//                val packageManager = context.packageManager
//                if (intent.resolveActivity(packageManager) != null) {
//                    context.startActivity(intent)
//                    return true
//                }
//            }
//        } else {
//            url?.let {
//                view?.loadUrl(url)
//            }
//        }
//        return true
//    }

}