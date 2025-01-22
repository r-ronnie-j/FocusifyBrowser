package com.example.myapplication.routes

import FilterIndexPage
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.composables.widgets.TopBar
import com.example.myapplication.navigation.FilterPageNavigation
import com.example.myapplication.navigation.LocalFilterPageNavigationProvider
import com.example.myapplication.navigation.LocalMainNavigationProvider
import com.example.myapplication.routes.filterPage.FilterByCategory

@Composable
fun FilterPage() {
    val mainNavController = LocalMainNavigationProvider.current
    val filterNavController = rememberNavController()
    CompositionLocalProvider(LocalFilterPageNavigationProvider provides filterNavController) {
        Column {
            TopBar(onClick = {
                mainNavController.popBackStack()
            }, text = "Filter Web Content")
            NavHost(
                navController = filterNavController,
                startDestination = FilterPageNavigation.IndexPage.name,
                modifier = Modifier.weight(1f)
            ) {
                composable(route = FilterPageNavigation.IndexPage.name) {
                    FilterIndexPage()
                }
                composable(route = FilterPageNavigation.FilterByCategory.name) {
                    FilterByCategory()
                }
                composable(route = FilterPageNavigation.BlackList.name) {

                }
                composable(route = FilterPageNavigation.WhiteList.name) {

                }
                composable(route = FilterPageNavigation.FilterDownloads.name) {

                }
            }
        }
    }
}

