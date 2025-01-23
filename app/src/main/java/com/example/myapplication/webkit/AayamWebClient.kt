package com.example.myapplication.webkit

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.myapplication.dataClass.BlocksiCategory
import com.example.myapplication.dataClass.SpinWebCategory
import com.example.myapplication.utilities.category.getBlocksiCategory
import com.example.myapplication.utilities.category.getSpinCategory
import com.example.myapplication.utilities.giveNonAmpUrl
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
        val testUrl = giveNonAmpUrl(url)
        Log.d("host", "non amp $testUrl")
        if (url.startsWith("intent://")) {
            Log.d("host", "url: $url")
            val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
            if (intent != null) {
                val packageManager = context.packageManager
                if (intent.resolveActivity(packageManager) != null) {
                    context.startActivity(intent)
                }
            }
        } else {
            CoroutineScope(Dispatchers.Main).launch {
                val blocksi = getBlocksiCategory(testUrl)
                val spin = getSpinCategory(testUrl)
                if (shouldBlock(blocksi, spin)) {
                    val replaceUrl = "file:///android_asset/block/index.html"
                    view.loadUrl(replaceUrl)
                } else {
                    view.loadUrl(url)
                }
            }
        }
        return true
    }
}

