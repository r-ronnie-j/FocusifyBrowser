package com.example.myapplication.utilData

enum class SuggestionType {
    History,
    Internet
}


data class SearchSuggestion(
    val text: String,
    val type: SuggestionType
)
