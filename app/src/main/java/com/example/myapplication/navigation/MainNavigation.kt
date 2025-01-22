package com.example.myapplication.navigation

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController

enum class MainNavigation {
    HomePage,
    FilterPage
}

val LocalMainNavigationProvider = compositionLocalOf<NavController> {
    error("No main navigation controller provided")
}