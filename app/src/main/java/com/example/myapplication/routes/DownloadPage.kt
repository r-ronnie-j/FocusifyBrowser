package com.example.myapplication.routes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.composables.webLIst.DownloadItem
import com.example.myapplication.composables.webLIst.SearchFilter
import com.example.myapplication.composables.widgets.TopBar
import com.example.myapplication.database.downloads.DownloadEntity
import com.example.myapplication.navigation.LocalMainNavigationProvider
import com.example.myapplication.viewModel.LocalDownloadModel
import java.util.Calendar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DownloadPage() {
    var downloadSearch by remember {
        mutableStateOf("")
    }

    val download = LocalDownloadModel.current
    val mainNavController = LocalMainNavigationProvider.current

    val groupedDownloads = groupDownloadByDate(download.downloads)



    Column {
        TopBar(onClick = { mainNavController.popBackStack() }, text = "Downloads")

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            item {
                SearchFilter(
                    value = downloadSearch,
                    onChange = { downloadSearch = it }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
            groupedDownloads.forEach { (groupTitle, items) ->
                val filteredItems = items.filter {
                    it.name.contains(downloadSearch, ignoreCase = true)
                }

                if (filteredItems.isNotEmpty()) {
                    stickyHeader {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.background)
                                .padding(vertical = 4.dp, horizontal = 16.dp)
                        ) {
                            Text(
                                text = groupTitle,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    items(filteredItems.size) { index ->
                        val filterItem = filteredItems[index]
                        DownloadItem(item = filterItem)
                    }
                }
            }
        }
    }

}

private fun groupDownloadByDate(downloads: List<DownloadEntity>): Map<String, List<DownloadEntity>> {
    val today = Calendar.getInstance()
    val yesterday = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }
    val thisWeek = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -7) }
    val thisMonth = Calendar.getInstance().apply { add(Calendar.MONTH, -1) }
    val thisYear = Calendar.getInstance().apply { add(Calendar.YEAR, -1) }

    return downloads.groupBy { item ->
        val itemDate = Calendar.getInstance().apply { time = item.created }
        when {
            isSameDay(itemDate, today) -> "Today"
            isSameDay(itemDate, yesterday) -> "Yesterday"
            itemDate.after(thisWeek) -> "This Week"
            itemDate.after(thisMonth) -> "This Month"
            itemDate.after(thisYear) -> "This Year"
            else -> "Older"
        }
    }
}