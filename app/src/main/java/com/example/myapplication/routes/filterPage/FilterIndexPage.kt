package com.example.myapplication.routes.filterPage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.composables.widgets.TopBar
import com.example.myapplication.navigation.FilterPageNavigation
import com.example.myapplication.navigation.LocalFilterPageNavigationProvider
import com.example.myapplication.navigation.LocalMainNavigationProvider

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun FilterIndexPage() {
    val mainNavController = LocalMainNavigationProvider.current
    val filterPageNavController = LocalFilterPageNavigationProvider.current
    Column {
        TopBar(onClick = {
            mainNavController.popBackStack()
        }, text = "Filter Websites")
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
        ) {
            FilterItems(text = "Filter by Category") {
                filterPageNavController.navigate(FilterPageNavigation.FilterByCategory.name)
            }
            FilterItems(text = "Whitelist Websites") {
                filterPageNavController.navigate(FilterPageNavigation.WhiteList.name)
            }
            FilterItems(text = "Blacklist Websites") {
                filterPageNavController.navigate(FilterPageNavigation.BlackList.name)
            }
            FilterItems(text = "Filter by Keyword") {
                filterPageNavController.navigate(FilterPageNavigation.FilterByKeyword.name)
            }
            FilterItems(text = "Filter what you Download") {
                filterPageNavController.navigate(FilterPageNavigation.FilterDownloads.name)
            }
        }
    }
}


@Composable
fun FilterItems(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 15.dp)
            .clickable { onClick() }
    )
}