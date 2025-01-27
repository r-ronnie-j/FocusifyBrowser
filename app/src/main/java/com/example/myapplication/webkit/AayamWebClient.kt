package com.example.myapplication.webkit

import android.content.Context
import android.content.Intent
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.webkit.WebViewAssetLoader
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

    private val assetLoader = WebViewAssetLoader.Builder()
        .addPathHandler("/assets/", WebViewAssetLoader.AssetsPathHandler(context))
        .build()

    override fun shouldInterceptRequest(
        view: WebView,
        request: WebResourceRequest
    ): WebResourceResponse? {
        return assetLoader.shouldInterceptRequest(request.url)
    }

    override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
        super.doUpdateVisitedHistory(view, url, isReload)
        if (url != null && !url.startsWith("https://appassets.androidplatform.net/assets")) {
            val testUrl = giveNonAmpUrl(url)
            if (url.startsWith("intent://")) {
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
                    view?.onPause()
                    if (shouldBlock(blocksi, spin)) {
                        view?.stopLoading()
                        val replaceUrl =
                            "https://appassets.androidplatform.net/assets/block/index.html"
                        view?.evaluateJavascript(
                            """
                            (function() { 
                                console.log('Replacing URL...');
                                window.location.replace('$replaceUrl'); 
                                return "done"
                            })();
                            """
                        ) { result ->
                            Log.d("WebClient", "Result from JavaScript: $result")
                        }
                        view?.onResume()
                    } else {
                        view?.onResume()
                    }
                }
            }
        }
    }
}

