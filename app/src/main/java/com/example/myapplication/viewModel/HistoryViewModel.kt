package com.example.myapplication.viewModel

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class HistoryViewModel : ViewModel() {
    var search by mutableStateOf("")
    var isEdit by mutableStateOf(false)
    var deleteHistory = mutableStateListOf<Int>()

}

val LocalHistoryViewModel = compositionLocalOf { HistoryViewModel() }