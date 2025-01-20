package com.example.myapplication.viewModel

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.utilData.SearchSuggestion
import com.example.myapplication.utilData.SuggestionType
import com.example.myapplication.utilities.extractSearchSuggestion
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class TitleBarViewModel : ViewModel() {
    val searchText = MutableStateFlow(TextFieldValue())
    var isFocused by mutableStateOf(false)
    var isTitleBar by mutableStateOf(true)
    val titleBarRequester = FocusRequester()
    var suggestions = MutableStateFlow<List<SearchSuggestion>>(emptyList())
    var showTabs by mutableStateOf(false)
    var showMenu by mutableStateOf(false)

    init {
        viewModelScope.launch {
            searchText
                .debounce(500)
                .distinctUntilChanged()
                .collect {
                    if (it.text.isBlank()) {
                        suggestions.value = emptyList()
                    } else {
                        val searchSuggestion = extractSearchSuggestion(it.text)
                        suggestions.value = searchSuggestion.map { suggestion ->
                            SearchSuggestion(
                                text = suggestion,
                                type = SuggestionType.Google
                            )
                        }
                    }
                }
        }
    }
}

val LocalTitleBar = compositionLocalOf { TitleBarViewModel() }