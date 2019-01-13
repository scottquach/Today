package com.scottquach.today.entry

import androidx.lifecycle.ViewModel;
import com.scottquach.today.models.Highlight

class EntryViewModel : ViewModel() {

    fun createHighlight(value: String, goal: String) {
        val highlight = Highlight(value, goal)

    }

}
