package com.example.myapplication.navigation

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController

enum class FilterPageNavigation {
    IndexPage,
    FilterByCategory,
    WhiteList,
    BlackList,
    FilterByKeyword,
    FilterDownloads,
}

val LocalFilterPageNavigationProvider = compositionLocalOf<NavController> {
    error("No Filter Page navigation controller provided")
}