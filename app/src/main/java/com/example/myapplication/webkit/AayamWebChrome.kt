package com.example.myapplication.webkit

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.FrameLayout

class AayamWebChrome(
    private val context: Context,
    private val onTitleReceive: (a: String?) -> Unit,
    private val onIconReceive: (a: Bitmap) -> Unit,
    private val onProgress: (progress: Int) -> Unit
) : WebChromeClient() {
    private var view: View? = null
    override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
        (context as Activity).runOnUiThread {
            val decorView = context.window.decorView as FrameLayout
            this.view = view
            decorView.addView(
                view, FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )
            )
            context.requestedOrientation =
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
    }


    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        onProgress(newProgress)
        super.onProgressChanged(view, newProgress)
    }

    override fun onReceivedTitle(view: WebView?, title: String?) {
        super.onReceivedTitle(view, title)
        onTitleReceive(title)
    }

    override fun onReceivedIcon(view: WebView?, icon: Bitmap?) {
        super.onReceivedIcon(view, icon)
        icon?.let {
            onIconReceive(it)
        }
    }

    override fun onHideCustomView() {
        (context as Activity).runOnUiThread {
            val decorView = context.window.decorView as FrameLayout
            if (this.view != null) {
                decorView.removeView(this.view)
                this.view = null
            }
            context.requestedOrientation =
                ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }
}