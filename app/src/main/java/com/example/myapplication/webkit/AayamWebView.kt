package com.example.myapplication.webkit

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.webkit.URLUtil
import android.webkit.WebSettings
import android.webkit.WebView
import com.example.myapplication.dataClass.BlocksiCategory
import com.example.myapplication.dataClass.SpinWebCategory
import com.example.myapplication.fetch
import com.example.myapplication.viewModel.ClickedUrl
import com.tonyodev.fetch2.Request
import com.tonyodev.fetch2core.Extras
import java.io.IOException

@SuppressLint("SetJavaScriptEnabled")
class AayamWebView(
    context: Context,
    onTitleReceive: (a: String?) -> Unit,
    onIconReceive: (a: Bitmap?) -> Unit,
    shouldBlock: (blocksi: List<BlocksiCategory>, spin: List<SpinWebCategory>) -> Boolean,
    onProgress: (x: Int) -> Unit,
    onUrlChange: (a: String?) -> Unit,
    onLongPress: (m: ClickedUrl) -> Unit,
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

        setDownloadListener { url, _, contentDisposition, mimeType, _ ->
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
            request.extras = Extras(
                mapOf(
                    Pair("name", fileName), Pair("mime", mimeType), Pair(
                        "folder",
                        "${MediaStore.Downloads.RELATIVE_PATH}/${Environment.DIRECTORY_DOWNLOADS}"
                    )
                )
            )
            fetch?.enqueue(request)
        }

        webViewClient = AayamWebClient(context, shouldBlock, onUrlChange)
        webChromeClient = AayamWebChrome(
            context,
            onTitleReceive = onTitleReceive,
            onIconReceive = onIconReceive,
            onProgress = onProgress
        )

        this.setOnCreateContextMenuListener { _, v, _ ->
            v as WebView
            val hitTestResult = v.hitTestResult
            var imageUrl: String? = null
            var linkUrl: String? = null
            when (hitTestResult.type) {
                HitTestResult.SRC_ANCHOR_TYPE -> {
                    linkUrl = hitTestResult.extra!!
                    onLongPress(
                        ClickedUrl(
                            imageUrl = null,
                            linkUrl = linkUrl,
                            type = HitTestResult.SRC_ANCHOR_TYPE
                        )
                    )
                }

                HitTestResult.IMAGE_TYPE -> {
                    imageUrl = hitTestResult.extra!!
                    onLongPress(
                        ClickedUrl(
                            imageUrl = imageUrl,
                            linkUrl = null,
                            type = HitTestResult.IMAGE_TYPE
                        )
                    )
                }

                HitTestResult.SRC_IMAGE_ANCHOR_TYPE -> {
                    imageUrl = hitTestResult.extra!!
                    val handler = Handler(Looper.getMainLooper())
                    val message = handler.obtainMessage()
                    v.requestFocusNodeHref(message)
                    linkUrl = message.data.getString("url")!!
                    onLongPress(
                        ClickedUrl(
                            imageUrl = imageUrl,
                            linkUrl = linkUrl,
                            type = HitTestResult.SRC_IMAGE_ANCHOR_TYPE

                        )
                    )
                }

                HitTestResult.EMAIL_TYPE -> {
                    linkUrl = hitTestResult.extra
                    onLongPress(
                        ClickedUrl(
                            imageUrl = imageUrl,
                            linkUrl = linkUrl,
                            type = HitTestResult.SRC_IMAGE_ANCHOR_TYPE

                        )
                    )
                }
            }
        }

    }

}