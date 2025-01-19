package com.example.myapplication

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.myapplication.composables.BottomNavBar
import com.example.myapplication.composables.titleWidget.SearchBar
import com.example.myapplication.composables.TopBottomDrawer
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val isDarkTheme =
            (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

        setTheme(if (isDarkTheme) R.style.Theme_FocusifyBrowser_Dark else R.style.Theme_FocusifyBrowser_Light)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxHeight()
                    ) {
                        Column {
                            SearchBar()
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                                TopBottomDrawer()
                            }
                            BottomNavBar()
                        }
                    }
                }
            }
        }
    }
}
