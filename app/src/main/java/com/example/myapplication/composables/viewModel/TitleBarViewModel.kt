package com.example.myapplication.composables.viewModel

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.ViewModel

class TitleBarViewModel : ViewModel() {
    var searchText by mutableStateOf("")
    var isFocused by mutableStateOf(false)
    var isTitleBar by mutableStateOf(true)
    val titleBarRequester = FocusRequester()

}

val LocalTitleBar = compositionLocalOf { TitleBarViewModel() }