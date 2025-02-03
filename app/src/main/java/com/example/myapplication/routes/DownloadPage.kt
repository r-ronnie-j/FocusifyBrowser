package com.example.myapplication.routes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Image
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Pause
import com.composables.icons.lucide.Play
import com.example.myapplication.composables.widgets.ProgressIndicator
import com.example.myapplication.database.downloads.DownloadEntity
import com.example.myapplication.utilities.download.DownloadSizeConverter
import com.example.myapplication.viewModel.LocalDownloadModel

@Composable
fun DownloadItem(item: DownloadEntity) {
    val downloadViewModel = LocalDownloadModel.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 5.dp)
            .fillMaxWidth()
    ) {
        Icon(imageVector = Lucide.Image, contentDescription = "Image download")
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(4.dp))
            if (item.progress != 100 && item.eta >= 0) {
                ProgressIndicator(
                    progress = item.progress.toFloat() / 100,
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
            ) {
                if (item.progress != 100) {
                    Text(
                        text = "${DownloadSizeConverter.formatSpeed(item.bps)} - ${
                            DownloadSizeConverter.formatTime(
                                item.eta
                            )
                        }",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    text = "${
                        if (item.progress != 100) DownloadSizeConverter.formatSize(item.downloaded) + " / " else ""
                    }${
                        DownloadSizeConverter.formatSize(
                            item.total
                        )
                    }",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        if (item.progress < 100) {
            if (item.error) {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Restart",
                        modifier = Modifier.width(24.dp)
                    )
                }
            } else {
                if (item.paused) {
                    IconButton(onClick = {
                        downloadViewModel.resume(item.id.toInt())
                    }) {
                        Icon(
                            imageVector = Lucide.Play,
                            contentDescription = "Play or pause",
                            modifier = Modifier.width(24.dp)
                        )
                    }
                } else {
                    IconButton(onClick = {
                        downloadViewModel.pause(item.id.toInt())
                    }) {
                        Icon(
                            imageVector = Lucide.Pause,
                            contentDescription = "Play or pause",
                            modifier = Modifier.width(24.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DownloadPage() {
    val download = LocalDownloadModel.current
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        download.downloads.forEach {
            DownloadItem(item = it)
        }
    }
}