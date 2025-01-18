package com.example.myapplication.composables.viewModel

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.utilities.extractSearchSuggestion
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class TitleBarViewModel : ViewModel() {
    val searchText = MutableStateFlow("")
    var isFocused by mutableStateOf(false)
    var isTitleBar by mutableStateOf(true)
    val titleBarRequester = FocusRequester()
    var suggestions = MutableStateFlow<List<String>>(emptyList())

    init {
        viewModelScope.launch {
            searchText
                .debounce(500)
                .filter { it.isNotBlank() }
                .distinctUntilChanged()
                .collect {
                    val searchSuggestion = extractSearchSuggestion()
                    suggestions.value = searchSuggestion
                }
        }
    }
}

val LocalTitleBar = compositionLocalOf { TitleBarViewModel() }