package com.scottquach.today.home

import androidx.lifecycle.ViewModel;

class HomeViewModel : ViewModel() {

    private val repository: HomeRepository = HomeRepository()

    fun getTodaysHighlight() = repository.todaysHighlight()
    fun getAllHighlights() = repository.allHighlights()

}
