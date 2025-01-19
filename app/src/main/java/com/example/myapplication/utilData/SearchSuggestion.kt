package com.example.myapplication.utilData

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
