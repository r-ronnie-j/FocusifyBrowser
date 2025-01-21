package com.example.myapplication.dataClass

enum class SuggestionType {
    History,
    Google,
    Duckduckgo
}


data class SearchSuggestion(
    val text: String,
    val type: SuggestionType,
    val source: String? = null
)
