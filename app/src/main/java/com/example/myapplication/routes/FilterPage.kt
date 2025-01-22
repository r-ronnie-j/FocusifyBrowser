package com.example.myapplication.routes

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.navigation.FilterPageNavigation
import com.example.myapplication.navigation.LocalFilterPageNavigationProvider
import com.example.myapplication.routes.filterPage.FilterByCategory
import com.example.myapplication.routes.filterPage.FilterIndexPage

@Composable
fun FilterPage() {
    val filterNavController = rememberNavController()
    CompositionLocalProvider(LocalFilterPageNavigationProvider provides filterNavController) {
        NavHost(
            navController = filterNavController,
            startDestination = FilterPageNavigation.IndexPage.name,
            enterTransition = {
                fadeIn(animationSpec = tween(300)) +
                        slideInHorizontally(initialOffsetX = { -it })
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300)) + slideOutHorizontally(
                    targetOffsetX = { -it }
                )
            }
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

