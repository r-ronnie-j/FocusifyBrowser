package com.example.myapplication.webkit

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.webkit.URLUtil
import android.webkit.WebSettings
import android.webkit.WebView
import com.example.myapplication.dataClass.BlocksiCategory
import com.example.myapplication.dataClass.SpinWebCategory
import com.example.myapplication.fetch
import com.tonyodev.fetch2.Request
import java.io.IOException

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
//            val request = DownloadManager.Request(Uri.parse(url)).apply {
//                setMimeType(mimeType)
//                addRequestHeader("User-Agent", userAgent)
//                setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType))
//                setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//                setDestinationInExternalPublicDir(
//                    Environment.DIRECTORY_DOWNLOADS,
//                    URLUtil.guessFileName(url, contentDisposition, mimeType)
//                )
//            }

//            val downloadManager =
//                context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
//            val downloadId = downloadManager.enqueue(request)

            val fileName = URLUtil.guessFileName(url, contentDisposition, mimeType)
            val contentValues = ContentValues().apply {
                put(MediaStore.Downloads.DISPLAY_NAME, fileName)
                put(MediaStore.Downloads.MIME_TYPE, mimeType)
                put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }
            val resolver = context.contentResolver
            val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
                    ?: throw IOException("Failed to create file entry")
            } else {
                TODO("VERSION.SDK_INT < Q")
            }
            val request = Request(url, uri.toString())
            fetch?.let { fetch ->
                fetch.enqueue(request, { updateRequest ->

                }) { error ->
                    Log.d("download", "There is error download the file using fetch $error")
                }
            }

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