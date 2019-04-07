package com.scottquach.today.home

import androidx.lifecycle.*
import com.scottquach.today.model.Event
import com.scottquach.today.model.HighlightStatus
import com.scottquach.today.model.TodayModel
import com.scottquach.today.room.Highlight
import org.joda.time.DateTime
import org.joda.time.LocalDate

class HomeViewModel : ViewModel() {

    enum class Events {
        DisableCreateEntry
    }

    private val repository: HomeRepository = HomeRepository()
    private val _events = MutableLiveData<Event<Events>>()

    private val _allHighlights = repository.allHighlights
    val allHighlights = MediatorLiveData<List<Highlight>>()


    val events = _events as LiveData<Event<Events>>
    val todaysHighlight = Transformations.map(repository.todaysHighlight) {
        if (it != null) {
            val created = DateTime(it.created)
            val isToday = created.toLocalDate() == LocalDate()
            if (isToday) {
                when {
                    it.status == HighlightStatus.COMPLETED -> return@map TodayModel(
                        TodayModel.Status.COMPLETE,
                        it
                    )
                    else -> return@map TodayModel(
                        TodayModel.Status.PENDING,
                        it
                    )
                }
            } else {
                return@map TodayModel(TodayModel.Status.NONE, it)
            }
        } else return@map TodayModel(TodayModel.Status.NONE, it)
    }

    init {
        allHighlights.addSource(_allHighlights) {
            if (todaysHighlight.value?.status == TodayModel.Status.PENDING ||
                todaysHighlight.value?.status == TodayModel.Status.COMPLETE
            ) {
                allHighlights.value = it.drop(1)
            } else {
                allHighlights.value = it
            }
        }
    }

    fun completeHighlight() {
        repository.completeHighlight(todaysHighlight.value!!.highlight!!)
    }

}