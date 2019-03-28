package com.scottquach.today.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel;
import com.scottquach.today.Event
import com.scottquach.today.HighlightStatus
import com.scottquach.today.room.Highlight

class HomeViewModel : ViewModel() {

    enum class Events {
        DisableCreateEntry
    }

    private val repository: HomeRepository = HomeRepository()
    private val _events = MutableLiveData<Event<Events>>()

    val events = _events as LiveData<Event<Events>>
    val todaysHighlight = Transformations.map(repository.todaysHighlight) {
        when {
            it == null -> return@map TodayModel(TodayModel.Status.NONE, it)
            it.status == HighlightStatus.COMPLETED -> return@map TodayModel(TodayModel.Status.COMPLETE, it)
            else -> return@map TodayModel(TodayModel.Status.PENDING, it)
        }
    }

    val allHighlights = repository.allHighlights

    fun completeHighlight() {
        repository.completeHighlight(todaysHighlight.value!!.highlight!!)
    }

}