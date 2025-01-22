package com.example.myapplication.routes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapplication.composables.TopBottomDrawer
import com.example.myapplication.composables.bottomBar.BottomNavBar
import com.example.myapplication.composables.titleWidget.SearchBar

@Composable
fun HomePage(){
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