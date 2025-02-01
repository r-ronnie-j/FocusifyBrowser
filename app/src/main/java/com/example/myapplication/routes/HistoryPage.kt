package com.example.myapplication.routes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.composables.webLIst.SearchFilter
import com.example.myapplication.composables.webLIst.WebInfo
import com.example.myapplication.composables.widgets.TopBar
import com.example.myapplication.database.history.HistoryEntity
import com.example.myapplication.navigation.LocalMainNavigationProvider
import com.example.myapplication.viewModel.LocalWebTabViewModel
import java.util.Calendar

@Composable
fun HistoryPage() {
    val webviewModel = LocalWebTabViewModel.current
    val mainNavController = LocalMainNavigationProvider.current
    var search by remember { mutableStateOf("") }

    val historyItems = webviewModel.history

    val groupedHistory = remember(historyItems) { groupHistoryByDate(historyItems) }

    Column {
        TopBar(onClick = { mainNavController.popBackStack() }, text = "History")
        SearchFilter(
            value = search,
            onChange = { search = it }
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
        ) {
            groupedHistory.forEach { (groupTitle, items) ->
                if (items.any {
                        it.title.contains(search, ignoreCase = true) ||
                                it.url.contains(search, ignoreCase = true)
                    }
                ) {
                    Text(
                        text = groupTitle, modifier = Modifier.padding(8.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    items.forEach { it ->
                        if (it.title.contains(search, ignoreCase = true) ||
                            it.url.contains(search, ignoreCase = true)
                        ) {
                            WebInfo(favIcon = it.favIcon, title = it.title, url = it.url)
                        }
                    }
                }
            }
        }

        Box(modifier = Modifier.height(30.dp)) {
            Text("Option box")
        }
    }
}

fun groupHistoryByDate(history: List<HistoryEntity>): Map<String, List<HistoryEntity>> {
    val today = Calendar.getInstance()
    val yesterday = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }
    val thisWeek = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -7) }
    val thisMonth = Calendar.getInstance().apply { add(Calendar.MONTH, -1) }
    val thisYear = Calendar.getInstance().apply { add(Calendar.YEAR, -1) }

    return history.reversed().groupBy { item ->
        val itemDate = Calendar.getInstance().apply { time = item.time }
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

fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
}
