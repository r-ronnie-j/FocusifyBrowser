package com.example.myapplication.webkit

import android.content.Context
import android.content.Intent
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
    private val shouldBlock: (blocksi: List<BlocksiCategory>, spin: List<SpinWebCategory>) -> Boolean,
    private val onUrlChange: (a: String?) -> Unit,
) : WebViewClient() {

    private val assetLoader = WebViewAssetLoader.Builder()
        .addPathHandler("/assets/", WebViewAssetLoader.AssetsPathHandler(context))
        .build()

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        val url = request?.url
        url?.let { uri ->
            val u = uri.toString()
            if (u.startsWith("intent")) {
                val intent = Intent.parseUri(u, Intent.URI_INTENT_SCHEME)
                if (intent != null) {
                    val packageManager = context.packageManager
                    if (intent.resolveActivity(packageManager) != null) {
                        context.startActivity(intent)
                    }
                }
                return true
            }
        }
        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun shouldInterceptRequest(
        view: WebView,
        request: WebResourceRequest
    ): WebResourceResponse? {
        return assetLoader.shouldInterceptRequest(request.url)
    }

    override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
        super.doUpdateVisitedHistory(view, url, isReload)
        onUrlChange(url)
        if (url != null && !url.startsWith("https://appassets.androidplatform.net/assets")) {
            val testUrl = giveNonAmpUrl(url)
            if (!url.startsWith("intent://")) {
                CoroutineScope(Dispatchers.Main).launch {
                    try {
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
                            ) { }
                        }
                        view?.onResume()
                    } catch (e: Exception) {
                        view?.onResume()
                    }
                }
            }
        }
    }
}

