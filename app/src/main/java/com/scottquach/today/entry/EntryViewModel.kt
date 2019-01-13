package com.scottquach.today.entry

import androidx.lifecycle.ViewModel;
import com.scottquach.today.room.Highlight

class EntryViewModel : ViewModel() {

    private val repository = EntryRepository()

    fun createHighlight(value: String, goalId: Int) {
        repository.insertNewHighlight(Highlight(value, goalId))
    }
}
