package com.example.myapplication

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.navigation.LocalMainNavigationProvider
import com.example.myapplication.navigation.MainNavigation
import com.example.myapplication.routes.FilterPage
import com.example.myapplication.routes.HomePage
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewModel.LocalWebTabViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val isDarkTheme =
            (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES


        setTheme(if (isDarkTheme) R.style.Theme_FocusifyBrowser_Dark else R.style.Theme_FocusifyBrowser_Light)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val intent = rememberUpdatedState(this.intent) // Ensure latest intent is used
            val webviewModel = LocalWebTabViewModel.current
            val context = LocalContext.current

            LaunchedEffect(intent.value) {
                intent.value.data?.let { uri ->
                    webviewModel.createWebView(context, uri.toString())
                    webviewModel.activeIndex.intValue = webviewModel.webViewTabs.size - 1
                }
            }

            MyApplicationTheme {
                StartComposable()
            }
        }

    }
}

@Composable
fun StartComposable() {
    val navController = rememberNavController()
    CompositionLocalProvider(LocalMainNavigationProvider provides navController) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxHeight()
            ) {

                NavHost(navController = navController,
                    startDestination = MainNavigation.HomePage.name,
                    enterTransition = {
                        fadeIn(animationSpec = tween(300)) + slideInHorizontally(initialOffsetX = { -it })
                    },
                    exitTransition = {
                        fadeOut(animationSpec = tween(300)) + slideOutHorizontally(targetOffsetX = { -it })
                    }) {
                    composable(route = MainNavigation.HomePage.name) {
                        HomePage()
                    }
                    composable(route = MainNavigation.FilterPage.name) {
                        FilterPage()
                    }
                }
            }
        }
    }
}
