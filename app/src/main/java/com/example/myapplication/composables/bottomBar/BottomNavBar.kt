package com.example.myapplication.composables.bottomBar

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Square
import com.example.myapplication.composables.widgets.BouncingBox
import com.example.myapplication.utilities.Home_Url
import com.example.myapplication.viewModel.LocalTitleBar
import com.example.myapplication.viewModel.LocalWebTabViewModel
import compose.icons.Octicons
import compose.icons.octicons.ChevronLeft24
import compose.icons.octicons.ChevronRight24
import compose.icons.octicons.Home24
import compose.icons.octicons.ThreeBars16

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    showSystemUi = true,
    showBackground = true,
)
@Composable
fun BottomNavBar() {
    val webviewModel = LocalWebTabViewModel.current
    val titleBarViewModel = LocalTitleBar.current
    val activeIndex = webviewModel.activeIndex

    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp),
        containerColor = Color.Transparent
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                val wv = webviewModel.webViewTabs.getOrNull(activeIndex.intValue)
                wv?.let {
                    if (it.canGoBack()) {
                        wv.goBack()
                    } else {
                        wv.clearHistory()
                        wv.loadUrl(Home_Url)
                    }
                }

            }) {
                Icon(
                    imageVector = Octicons.ChevronLeft24,
                    contentDescription = "Backward",
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            IconButton(onClick = {
                webviewModel.webViewTabs.getOrNull(activeIndex.intValue)?.goForward()
            }) {
                Icon(
                    imageVector = Octicons.ChevronRight24,
                    contentDescription = "Forward",
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            IconButton(onClick = {
                webviewModel.webViewTabs.getOrNull(activeIndex.intValue)
                    ?.loadUrl(Home_Url)
            }) {
                Icon(
                    imageVector = Octicons.Home24,
                    contentDescription = "Home",
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            BouncingBox(
                onClick = {
                    titleBarViewModel.showTabs = !titleBarViewModel.showTabs
                    titleBarViewModel.showMenu = false
                }
            ) {
                Icon(
                    imageVector = Lucide.Square,
                    contentDescription = "Tabs",
                    modifier = Modifier.size(25.dp),
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = webviewModel.webViewTabs.size.toString(),
                    fontSize = if (webviewModel.webViewTabs.size < 100) 10.sp else 8.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            IconButton(onClick = {
                titleBarViewModel.showMenu = !titleBarViewModel.showMenu
                titleBarViewModel.showTabs = false
            }) {
                Icon(
                    imageVector = Octicons.ThreeBars16,
                    contentDescription = "Menu",
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}


