package com.example.myapplication.composables


import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.myapplication.composables.webLIst.WebViewModal
import com.example.myapplication.viewModel.LocalWebTabViewModel

@Composable
fun WebViewComposable() {

    val webTabViewModel = LocalWebTabViewModel.current
    val activeIndex = webTabViewModel.activeIndex
    val context = LocalContext.current
    val tabInfo = webTabViewModel.tabInfo.collectAsState()
    if (!webTabViewModel.restored) webTabViewModel.restore(context)
    else {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            AndroidView(
                factory = {
                    FrameLayout(it)
                },
                update = {
                    if (activeIndex.intValue == webTabViewModel.webViewTabs.size) {
                        if (webTabViewModel.restored) {
                            val webview = webTabViewModel.createWebView(context)
                            it.removeAllViews()
                            it.addView(webview)
                        }
                    } else {
                        val webview = webTabViewModel.webViewTabs[activeIndex.intValue]
                        it.removeAllViews()
                        it.addView(webview)
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
            )
            if (tabInfo.value.size > activeIndex.intValue && tabInfo.value.getOrNull(
                    activeIndex.intValue
                )?.progress != 100
            ) {
                Row {
                    if (tabInfo.value[activeIndex.intValue].progress > 0f) {
                        Box(
                            modifier = Modifier
                                .weight(tabInfo.value[activeIndex.intValue].progress.toFloat())
                                .height(2.dp)
                                .background(MaterialTheme.colorScheme.primary)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(100 - tabInfo.value[activeIndex.intValue].progress.toFloat())
                            .height(2.dp)
                            .background(MaterialTheme.colorScheme.tertiary)
                    )
                }
            }
            WebViewModal()
        }

        BackHandler {
            webTabViewModel.webViewTabs.elementAtOrNull(activeIndex.intValue)?.goBack()
        }
    }
}



