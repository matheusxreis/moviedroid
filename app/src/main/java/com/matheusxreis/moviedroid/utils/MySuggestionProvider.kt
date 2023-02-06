package com.matheusxreis.moviedroid.utils

import android.content.SearchRecentSuggestionsProvider

class MySuggestionProvider: SearchRecentSuggestionsProvider() {

    init {
        setupSuggestions(AUTHORITY, MODE)
    }

    companion object {
        const val AUTHORITY = "com.matheusxreis.moviedroid.utils.MySuggestionProvider"
        const val MODE = SearchRecentSuggestionsProvider.DATABASE_MODE_QUERIES
    }
}