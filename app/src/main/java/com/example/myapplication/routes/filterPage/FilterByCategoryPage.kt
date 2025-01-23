package com.example.myapplication.routes.filterPage

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.ChevronDown
import com.composables.icons.lucide.ChevronUp
import com.composables.icons.lucide.Lucide
import com.example.myapplication.composables.widgets.TopBar
import com.example.myapplication.dataClass.WebCategoryStatus
import com.example.myapplication.navigation.LocalFilterPageNavigationProvider
import com.example.myapplication.viewModel.LocalWebTabViewModel

@Composable
fun FilterByCategory() {
    val filterNavigator = LocalFilterPageNavigationProvider.current
    val filterViewModel = LocalWebTabViewModel.current
    val filterCategoryList = filterViewModel.webCategoryStatusList
    Column {
        TopBar(onClick = {
            filterNavigator.popBackStack()
        }, text = "Filter Websites")
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            filterCategoryList.mapIndexed() { index, item ->
                WebCategoryItem(webCategoryStatus = item) {
                    filterViewModel.changeStatus(index)
                }
            }
        }
    }
}


@Composable
fun WebCategoryItem(
    webCategoryStatus: WebCategoryStatus,
    onBlockedChange: (Boolean) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .animateContentSize(),
        elevation = CardDefaults.cardElevation(),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 7.dp)
                .clickable { expanded = !expanded },
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = if (expanded) Lucide.ChevronUp else Lucide.ChevronDown,
                    contentDescription = "Expand/Collapse",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { expanded = !expanded }
                )
                Text(
                    text = webCategoryStatus.category.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                )
                Switch(
                    checked = webCategoryStatus.blocked,
                    onCheckedChange = { onBlockedChange(it) }
                )
            }

            if (expanded) {
                Text(
                    text = webCategoryStatus.category.description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(all = 8.dp)
                )
            }
        }
    }
}

