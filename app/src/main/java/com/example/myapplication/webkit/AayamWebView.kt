package com.example.myapplication.webkit

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.webkit.URLUtil
import android.webkit.WebSettings
import android.webkit.WebView
import com.example.myapplication.dataClass.BlocksiCategory
import com.example.myapplication.dataClass.SpinWebCategory

@SuppressLint("SetJavaScriptEnabled")
class AayamWebView(
    context: Context,
    onTitleReceive: (a: String?) -> Unit,
    onIconReceive: (a: Bitmap?) -> Unit,
    shouldBlock: (blocksi: List<BlocksiCategory>, spin: List<SpinWebCategory>) -> Boolean,
    onProgress: (x: Int) -> Unit,
    onUrlChange: (a: String?) -> Unit,
) : WebView(context) {

    init {

        layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
        )

        settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            allowFileAccess = true
            loadsImagesAutomatically = true
            loadWithOverviewMode = true
            useWideViewPort = true
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            mediaPlaybackRequiresUserGesture = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                isForceDarkAllowed = true
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                isAlgorithmicDarkeningAllowed = true
            }
        }

        setDownloadListener { url, userAgent, contentDisposition, mimeType, _ ->
            val request = DownloadManager.Request(Uri.parse(url)).apply {
                setMimeType(mimeType)
                addRequestHeader("User-Agent", userAgent)
                setDescription("Downloading file...")
                setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType))
                setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    URLUtil.guessFileName(url, contentDisposition, mimeType)
                )
            }

            val downloadManager =
                context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            downloadManager.enqueue(request)
        }

        webViewClient = AayamWebClient(context, shouldBlock, onUrlChange)
        webChromeClient = AayamWebChrome(
            context,
            onTitleReceive = onTitleReceive,
            onIconReceive = onIconReceive,
            onProgress = onProgress
        )
    }
}