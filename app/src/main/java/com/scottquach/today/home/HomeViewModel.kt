package com.scottquach.today.home

import androidx.lifecycle.*
import com.scottquach.today.model.Event
import com.scottquach.today.model.HighlightStatus
import com.scottquach.today.model.TodayModel
import com.scottquach.today.room.Highlight
import org.joda.time.DateTime
import org.joda.time.LocalDate
import timber.log.Timber

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
        Timber.d("$it")
        if (it != null) {
            val created = DateTime(it.created)
            val isToday = created.toLocalDate() == LocalDate()
            if (isToday) {
                when {
                    it.status == HighlightStatus.COMPLETED -> return@map TodayModel(
                        HighlightStatus.COMPLETED,
                        it
                    )
                    else -> return@map TodayModel(
                        HighlightStatus.PENDING,
                        it
                    )
                }
            } else {
                return@map TodayModel(HighlightStatus.NONE, it)
            }
        } else return@map TodayModel(HighlightStatus.NONE, it)
    }

    init {
        allHighlights.addSource(_allHighlights) {

            if (todaysHighlight.value?.status == HighlightStatus.PENDING) {
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