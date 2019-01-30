package com.scottquach.today.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.scottquach.today.Event
import com.scottquach.today.TodayApp
import com.scottquach.today.entry.EntryRepository
import com.scottquach.today.room.AppDatabase
import com.scottquach.today.room.Highlight

class HomeRepository() {
    private val db: AppDatabase = AppDatabase.getInstance(TodayApp.getInstance()!!.applicationContext)
    private val _allHighlights: LiveData<List<Highlight>> = db.highlightDao().getAll()
    private val _todaysHighlight: LiveData<Highlight> = db.highlightDao().getToday()
    private val _events = MutableLiveData<Event<EntryRepository.Events>>()

    val allHighlights: LiveData<List<Highlight>>
        get() = _allHighlights
    val todaysHighlight: LiveData<Highlight>
        get() = _todaysHighlight
    val events: LiveData<Event<EntryRepository.Events>>
        get() = _events
}