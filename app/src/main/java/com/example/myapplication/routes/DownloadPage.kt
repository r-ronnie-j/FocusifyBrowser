package com.example.myapplication.routes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.viewModel.LocalDownloadModel

@Composable
fun DownloadPage() {
    val download = LocalDownloadModel.current
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        download.downloads.forEach {
            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                Text(text = it.file ?: "")
                Text(text = it.fileUri ?: "")
                Text(text = it.bps.toString())
                Text(text = it.created.toString())
                Text(text = it.eta.toString())
                Text(text = it.bps.toString())
                Text(text = it.progress.toString())
                Text(text = it.total.toString())
                Text(text = it.paused.toString())
                HorizontalDivider()
            }
        }
    }
}