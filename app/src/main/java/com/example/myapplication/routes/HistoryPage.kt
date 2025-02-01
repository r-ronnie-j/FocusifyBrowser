package com.example.myapplication.routes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachReversed
import com.example.myapplication.composables.webLIst.WebInfo
import com.example.myapplication.composables.widgets.TopBar
import com.example.myapplication.navigation.LocalMainNavigationProvider
import com.example.myapplication.viewModel.LocalWebTabViewModel

@Composable
fun HistoryPage() {
    val webviewModel = LocalWebTabViewModel.current
    val mainNavController = LocalMainNavigationProvider.current
    Column {
        TopBar(onClick = {
            mainNavController.popBackStack()
        }, text = "History")
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
        ) {
            webviewModel.history.fastForEachReversed {
                WebInfo(favIcon = it.favIcon, title = it.title, url = it.url)
            }
        }
        Box(modifier = Modifier.height(30.dp)) {
            Text("Option box")
        }
    }
}