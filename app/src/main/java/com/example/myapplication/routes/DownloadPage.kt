package com.example.myapplication.routes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapplication.composables.webLIst.DownloadItem
import com.example.myapplication.viewModel.LocalDownloadModel

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