package com.example.myapplication.composables


import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.composables.icons.lucide.Clipboard
import com.composables.icons.lucide.Lucide
import com.example.myapplication.R
import com.example.myapplication.viewModel.LocalWebTabViewModel
import compose.icons.Octicons
import compose.icons.octicons.Copy24

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
            WebViewWithContextMenu()
        }

        BackHandler {
            webTabViewModel.webViewTabs.elementAtOrNull(activeIndex.intValue)?.goBack()
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebViewWithContextMenu() {
    val webViewModel = LocalWebTabViewModel.current

    if (webViewModel.showModel) {
        ModalBottomSheet(
            onDismissRequest = { webViewModel.showModel = false },
            tonalElevation = 8.dp,
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 5.dp)
            ) {
                HorizontalDivider(
                    thickness = 0.5.dp,
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            webViewModel.showModel = false
                        }
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Lucide.Clipboard,
                        contentDescription = "Copy",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Copy link address",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                HorizontalDivider(
                    thickness = 0.5.dp,
                    color = MaterialTheme.colorScheme.surfaceVariant
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            webViewModel.showModel = false
                        }
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Octicons.Copy24,
                        contentDescription = "Copy",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Open in new tab",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                HorizontalDivider(
                    thickness = 0.5.dp,
                    color = MaterialTheme.colorScheme.surfaceVariant
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            webViewModel.showModel = false
                        }
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Lucide.Clipboard,
                        contentDescription = "Copy",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Open in background tab",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                HorizontalDivider(
                    thickness = 0.5.dp,
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            webViewModel.showModel = false
                        }
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.incognito),
                        contentDescription = "Copy",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Open in incognito tab",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                HorizontalDivider(
                    thickness = 0.5.dp,
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            webViewModel.showModel = false
                        }
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Lucide.Clipboard,
                        contentDescription = "Copy",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Copy link text",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

