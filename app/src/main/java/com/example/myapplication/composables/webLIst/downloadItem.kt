package com.example.myapplication.composables.webLIst

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.FileAudio
import com.composables.icons.lucide.FileImage
import com.composables.icons.lucide.FileText
import com.composables.icons.lucide.FileVideo
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Pause
import com.composables.icons.lucide.Play
import com.example.myapplication.composables.widgets.ProgressIndicator
import com.example.myapplication.database.downloads.DownloadEntity
import com.example.myapplication.utilities.download.DownloadSizeConverter
import com.example.myapplication.viewModel.LocalDownloadModel
import compose.icons.LineAwesomeIcons
import compose.icons.Octicons
import compose.icons.lineawesomeicons.Android
import compose.icons.octicons.File24

fun getImageVector(x: String): ImageVector {
    return if (x.contains("application")) {
        LineAwesomeIcons.Android
    } else if (x.contains("text")) {
        Lucide.FileText
    } else if (x.contains("image")) {
        Lucide.FileImage
    } else if (x.contains("audio")) {
        Lucide.FileAudio
    } else if (x.contains("video")) {
        Lucide.FileVideo
    } else {
        Octicons.File24
    }
}

@Composable
fun DownloadItem(item: DownloadEntity) {
    val downloadViewModel = LocalDownloadModel.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 5.dp)
            .fillMaxWidth()
    ) {
        Icon(imageVector = getImageVector(item.name), contentDescription = "Image download")
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.mime,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelMedium,
            )
            Spacer(modifier = Modifier.height(4.dp))
            if (item.progress != 100) {
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