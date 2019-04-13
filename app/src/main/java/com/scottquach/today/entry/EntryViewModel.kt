package com.scottquach.today.entry

import androidx.lifecycle.ViewModel
import com.scottquach.today.room.Highlight
import timber.log.Timber

class EntryViewModel : ViewModel() {

    private val repository: EntryRepository = EntryRepository()

    val events = repository.getEvents()

    fun createHighlight(entryValue: String) {
        if (entryIsValid(entryValue)) {
            repository.insertNewHighlight(Highlight(entryValue, null))
        } else {
            Timber.e("entry wasn't valid: $entryValue")
        }
    }

    /**
     * Returns true if the user entry is valid for creating a highlight
     */
    private fun entryIsValid(entryValue: String): Boolean {
        return !entryValue.isNullOrBlank()
    }
}
