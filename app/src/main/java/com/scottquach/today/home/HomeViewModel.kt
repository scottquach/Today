package com.scottquach.today.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.scottquach.today.Event
import com.scottquach.today.room.Highlight

class HomeViewModel : ViewModel() {

    enum class Events {
        DisableCreateEntry
    }

    private val repository: HomeRepository = HomeRepository()
    private val _events = MutableLiveData<Event<Events>>()

    val events = _events as LiveData<Event<Events>>
    val todaysHighlight = repository.todaysHighlight
    val allHighlights = repository.allHighlights

    fun completeHighlight() {
        repository.completeHighlight(todaysHighlight.value!!)
    }

}