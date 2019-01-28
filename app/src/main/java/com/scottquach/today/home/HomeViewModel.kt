package com.scottquach.today.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;

class HomeViewModel : ViewModel() {

    private val repository: HomeRepository = HomeRepository()

    val todaysHighlight = repository.todaysHighlight
    val allHighlights = repository.allHighlights

    val test = MutableLiveData<String>("testing")


}
