package com.example.myapplication.routes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import com.example.myapplication.composables.webLIst.BookmarkOptions
import com.example.myapplication.composables.webLIst.SearchFilter
import com.example.myapplication.composables.webLIst.WebInfo
import com.example.myapplication.composables.webLIst.WebInfoType
import com.example.myapplication.composables.widgets.TopBar
import com.example.myapplication.navigation.LocalMainNavigationProvider
import com.example.myapplication.viewModel.LocalHistoryBookmark
import com.example.myapplication.viewModel.LocalWebTabViewModel

@Composable
fun BookmarkPage() {
    val webviewModel = LocalWebTabViewModel.current
    val mainNavController = LocalMainNavigationProvider.current
    val historyViewModel = LocalHistoryBookmark.current

    Column {
        TopBar(onClick = { mainNavController.popBackStack() }, text = "Bookmarks")
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            item {
                SearchFilter(value = historyViewModel.search,
                    onChange = { historyViewModel.search = it })
                Spacer(modifier = Modifier.height(10.dp))
            }
            val filteredItems = webviewModel.bookmarks.filter {
                it.url?.toLowerCase(Locale.current)
                    ?.contains(historyViewModel.search.toLowerCase(Locale.current)) == true || it.title?.toLowerCase(
                    Locale.current
                )?.contains(historyViewModel.search.toLowerCase(Locale.current)) == true
            }
            items(filteredItems.size) { index ->
                val filterItem = filteredItems[index]
                if (filterItem.id != null) {
                    WebInfo(
                        favIcon = filterItem.favIcon,
                        title = filterItem.title,
                        url = filterItem.url,
                        type = WebInfoType.Bookmark,
                        id = filterItem.id
                    )
                }
            }
        }
        BookmarkOptions()
    }


    LaunchedEffect(Unit) {
        historyViewModel.isEdit = false
    }

}

