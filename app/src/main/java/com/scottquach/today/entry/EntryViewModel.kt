package com.scottquach.today.entry

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.scottquach.today.room.Highlight
import timber.log.Timber

class EntryViewModel : ViewModel() {

    private val repository: EntryRepository = EntryRepository()
    val highlightTextValue = MutableLiveData<String>("")

    val events = repository.events

    fun createHighlightClick() {
        Timber.d("Crate highlight clicked %s", highlightTextValue.value)
        if (entryIsValid()) {
            repository.insertNewHighlight(Highlight(highlightTextValue.value!!, null))
        }
    }


    /**
     * Returns true if the user entry is valid for creating a highlight
     */
    private fun entryIsValid(): Boolean {
        return !highlightTextValue.value.isNullOrBlank()
    }
}
