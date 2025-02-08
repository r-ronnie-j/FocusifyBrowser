package com.example.myapplication.webkit

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.webkit.WebViewAssetLoader
import com.example.myapplication.adblocker.CheckFilterList
import com.example.myapplication.adblocker.sanitizeUrl
import com.example.myapplication.adblocker.shouldBlockAd
import com.example.myapplication.dataClass.BlocksiCategory
import com.example.myapplication.dataClass.SpinWebCategory
import com.example.myapplication.utilities.Block_Url
import com.example.myapplication.utilities.category.getBlocksiCategory
import com.example.myapplication.utilities.category.getSpinCategory
import com.example.myapplication.utilities.giveNonAmpUrl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import java.net.URI

val checkFilterList = CheckFilterList()

class AayamWebClient(
    private val context: Context,
    private val shouldBlock: (blocksi: List<BlocksiCategory>, spin: List<SpinWebCategory>) -> Boolean,
    private val onUrlChange: (a: String?) -> Unit,
) : WebViewClient() {

    var currentUrl = ""

    private val assetLoader = WebViewAssetLoader.Builder()
        .addPathHandler("/assets/", WebViewAssetLoader.AssetsPathHandler(context))
        .build()

//    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
//        val url = request?.url
//        url?.let { uri ->
//            val u = uri.toString()
//            if (u.startsWith("intent")) {
//                val intent = Intent.parseUri(u, Intent.URI_INTENT_SCHEME)
//                if (intent != null) {
//                    val packageManager = context.packageManager
//                    if (intent.resolveActivity(packageManager) != null) {
//                        context.startActivity(intent)
//                    }
//                }
//                return true
//            }
//        }
//        return super.shouldOverrideUrlLoading(view, request)
//    }
//
//    override fun shouldInterceptRequest(
//        view: WebView,
//        request: WebResourceRequest
//    ): WebResourceResponse? {
//        return assetLoader.shouldInterceptRequest(request.url)
//    }


    override fun shouldOverrideUrlLoading(
        view: WebView,
        webResourceRequest: WebResourceRequest
    ): Boolean {
        var urlString = webResourceRequest.url.toString()
        urlString = sanitizeUrl(urlString)

        var isThirdPartyRequest = false

        var currentBaseDomain = URI(currentUrl.split("?")[0]).host
        val currentDomain = currentBaseDomain

        var requestBaseDomain = webResourceRequest.url.host

        val requestUrlString = webResourceRequest.url.toString()

        if (currentBaseDomain.isNotEmpty() && (requestBaseDomain != null)) {
            while (currentBaseDomain.indexOf(
                    ".",
                    currentBaseDomain.indexOf(".") + 1
                ) > 0
            ) {
                currentBaseDomain = currentBaseDomain.substring(currentBaseDomain.indexOf(".") + 1)
            }

            while (requestBaseDomain!!.indexOf(
                    ".",
                    requestBaseDomain.indexOf(".") + 1
                ) > 0
            ) {
                requestBaseDomain = requestBaseDomain.substring(requestBaseDomain.indexOf(".") + 1)
            }

            isThirdPartyRequest = currentBaseDomain != requestBaseDomain
        }
        val shouldBlock = shouldBlockAd(
            currentDomain = currentDomain,
            requestUrlString = requestUrlString,
            isThirdPartyRequest = isThirdPartyRequest
        )
        Log.d(
            "adblock",
            "$currentDomain $requestUrlString $isThirdPartyRequest $shouldBlock is block"
        )
        if (shouldBlock) {
            return true
        }

        return when {
            urlString.startsWith("http") -> {
                false
            }

            urlString.startsWith("mailto:") -> {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse(urlString)
                }
                try {
                    context.startActivity(intent, null)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(context, "Mail client not found", Toast.LENGTH_SHORT).show()
                }
                true
            }

            urlString.startsWith("tel:") -> {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse(urlString)
                }

                try {
                    context.startActivity(intent, null)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(context, "Dial client not found", Toast.LENGTH_SHORT).show()
                }
                true
            }
            else -> true
        }
    }


    override fun shouldInterceptRequest(
        view: WebView,
        webResourceRequest: WebResourceRequest
    ): WebResourceResponse? {
        val asset = assetLoader.shouldInterceptRequest(webResourceRequest.url)
        if (asset != null) return asset

        val requestUrlString = webResourceRequest.url.toString()

        val emptyWebResourceResponse =
            WebResourceResponse("text/plain", "utf8", ByteArrayInputStream("".toByteArray()))

        var isThirdPartyRequest = false

        var currentBaseDomain = URI(currentUrl.split("?")[0]).host
        if (currentBaseDomain == null) currentBaseDomain = ""
        val currentDomain = currentBaseDomain

        var requestBaseDomain = webResourceRequest.url.host

        if (currentBaseDomain.isNotEmpty() && (requestBaseDomain != null)) {
            while (currentBaseDomain.indexOf(
                    ".",
                    currentBaseDomain.indexOf(".") + 1
                ) > 0
            ) {
                currentBaseDomain = currentBaseDomain.substring(currentBaseDomain.indexOf(".") + 1)
            }

            while (requestBaseDomain!!.indexOf(
                    ".",
                    requestBaseDomain.indexOf(".") + 1
                ) > 0
            ) {
                requestBaseDomain = requestBaseDomain.substring(requestBaseDomain.indexOf(".") + 1)
            }

            isThirdPartyRequest = currentBaseDomain != requestBaseDomain
        }

        if (
            shouldBlockAd(
                currentDomain = currentDomain,
                requestUrlString = requestUrlString,
                isThirdPartyRequest = isThirdPartyRequest
            )
        ) {
            Log.d("adblock", "should block was there")
            return emptyWebResourceResponse
        }

        return null
    }

    @Deprecated("Deprecated in Java")
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        Log.d("adblock", "not called properly for $url")
        return super.shouldOverrideUrlLoading(view, url)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        currentUrl = view?.url ?: ""
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
                            view?.evaluateJavascript(
                                """
                            (function() { 
                                console.log('Replacing URL...');
                                window.location.replace('$Block_Url'); 
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

