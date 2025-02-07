package com.example.myapplication.composables.webLIst

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.webkit.WebView.HitTestResult
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Clipboard
import com.composables.icons.lucide.ExternalLink
import com.composables.icons.lucide.EyeOff
import com.composables.icons.lucide.Lucide
import com.example.myapplication.utilities.download.downloadFile
import com.example.myapplication.viewModel.LocalWebTabViewModel
import compose.icons.Octicons
import compose.icons.octicons.Browser16
import compose.icons.octicons.Download16
import compose.icons.octicons.EyeClosed24
import compose.icons.octicons.Link24
import compose.icons.octicons.ShareAndroid24


@Preview(showSystemUi = true, showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebViewModal() {
    val webViewModel = LocalWebTabViewModel.current
    val tabInfo = webViewModel.tabInfo.collectAsState()
    val a = tabInfo.value.getOrNull(webViewModel.activeIndex.intValue)?.favIcon
    val clickedUrl = webViewModel.clickedUrl

    val context = LocalContext.current
    if (webViewModel.showModel) {
        ModalBottomSheet(
            onDismissRequest = { webViewModel.showModel = false },
            tonalElevation = 20.dp,

            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            dragHandle = { BottomSheetDefaults.DragHandle() },
            modifier = Modifier
                .wrapContentHeight()

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                if ((clickedUrl.type == HitTestResult.SRC_IMAGE_ANCHOR_TYPE ||
                            clickedUrl.type == HitTestResult.SRC_ANCHOR_TYPE) && clickedUrl.linkUrl != null
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (a != null) {
                            Image(
                                bitmap = a.asImageBitmap(),
                                contentDescription = tabInfo.value
                                    .getOrNull(webViewModel.activeIndex.intValue)?.title
                                    ?: "",
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = webViewModel.clickedUrl.linkUrl!!,
                            style = MaterialTheme.typography.titleMedium, // More prominent title
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                BottomSheetRow(
                    icon = Lucide.Clipboard,
                    text = "Copy link address",
                    onClick = {
                        webViewModel.showModel = false
                        val clipboard = context.getSystemService(ClipboardManager::class.java)
                        if (clipboard != null) {
                            val clipData =
                                ClipData.newPlainText("Link Copied", clickedUrl.linkUrl)
                            clipboard.setPrimaryClip(clipData)
                            Toast.makeText(context, "Text copied to clipboard", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                )
                if (clickedUrl.linkUrl != null) {
                    BottomSheetRow(
                        icon = Octicons.Link24,
                        text = "Open in new tab",
                        onClick = {
                            webViewModel.showModel = false
                            webViewModel.createWebView(context, clickedUrl.linkUrl)
                            webViewModel.activeIndex.intValue = webViewModel.webViewTabs.size - 1
                        }
                    )
                    BottomSheetRow(
                        icon = Octicons.Browser16,
                        text = "Open in background tab",
                        onClick = {
                            webViewModel.showModel = false
                            webViewModel.createWebView(context, clickedUrl.linkUrl)
                        }
                    )
                    BottomSheetRow(
                        icon = Octicons.EyeClosed24,
                        text = "Open in incognito tab",
                        onClick = {
                            webViewModel.showModel = false
                            webViewModel.createWebView(
                                context,
                                clickedUrl.linkUrl,
                                incognitoSetting = true
                            )
                        }
                    )
                }

                if (clickedUrl.imageUrl != null) {
                    BottomSheetRow(
                        icon = Lucide.ExternalLink,
                        text = "Open image in new tab",
                        onClick = {
                            webViewModel.showModel = false
                            webViewModel.createWebView(context, clickedUrl.imageUrl)
                            webViewModel.activeIndex.intValue = webViewModel.webViewTabs.size - 1
                        }
                    )
                    BottomSheetRow(
                        icon = Octicons.Browser16,
                        text = "Open image in background tab",
                        onClick = {
                            webViewModel.showModel = false
                            webViewModel.createWebView(context, clickedUrl.imageUrl)
                        }
                    )
                    BottomSheetRow(
                        icon = Lucide.EyeOff,
                        text = "Open image in incognito tab",
                        onClick = {
                            webViewModel.showModel = false
                            webViewModel.createWebView(
                                context,
                                clickedUrl.imageUrl,
                                incognitoSetting = true
                            )
                        }
                    )
                    BottomSheetRow(icon = Octicons.Download16, text = "Download image") {
                        downloadFile(context, url = clickedUrl.imageUrl)
                    }
                }

                BottomSheetRow(
                    icon = Octicons.ShareAndroid24,
                    text = "Share link",
                    onClick = {
                        webViewModel.showModel = false
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(
                                Intent.EXTRA_TEXT,
                                webViewModel.webViewTabs[webViewModel.activeIndex.intValue].url
                            )
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Share via"))
                    }
                )
            }
        }
    }
}


@Composable
private fun BottomSheetRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(20.dp) // Consistent icon size
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
    HorizontalDivider(
        thickness = 0.5.dp,
        color = MaterialTheme.colorScheme.surfaceVariant
    )
}