package com.scottquach.today.entry

import com.scottquach.today.TodayApp
import com.scottquach.today.helpers.HighlightDbHelper
import com.scottquach.today.room.Highlight

class EntryRepository {

    private val highlightDbHelper by lazy {
        TodayApp.getInstance()?.applicationContext?.let { HighlightDbHelper(it) }
    }

    fun insertNewHighlight(highlight: Highlight) {
        highlightDbHelper?.insertHighlight(highlight)
    }
}