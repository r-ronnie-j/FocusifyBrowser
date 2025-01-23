package com.example.myapplication.composables.titleWidget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.ShieldCheck
import com.example.myapplication.viewModel.LocalTitleBar
import com.example.myapplication.viewModel.LocalWebTabViewModel

@Composable
fun TitleBar() {
    val titleBarViewModel = LocalTitleBar.current
    val webviewModel = LocalWebTabViewModel.current
    val activeIndex = webviewModel.activeIndex
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {

            }
            .padding(horizontal = 8.dp)
            .height(36.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {},
        ) {
            Icon(
                imageVector = Lucide.ShieldCheck,
                contentDescription = "Clear Text",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.height(20.dp)
            )
        }
        Text(
            text = webviewModel.tabInfo.getOrNull(activeIndex.intValue)?.title ?: "Homepage",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(1f)
                .clickable {
                    titleBarViewModel.isTitleBar = false
                }
        )

        if (webviewModel.tabInfo.size > activeIndex.intValue && webviewModel.tabInfo[activeIndex.intValue].progress != 100) {
            IconButton(
                onClick = {
                    webviewModel.webViewTabs.getOrNull(activeIndex.intValue)?.reload()
                },
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Stop",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.height(24.dp)
                )
            }
        } else {
            IconButton(
                onClick = {
                    webviewModel.webViewTabs.getOrNull(activeIndex.intValue)?.stopLoading()
                },
            ) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = "Clear Text",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.height(24.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE)
@Composable
fun TitleBarPreview() {
    TitleBar()
}