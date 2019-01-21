package com.scottquach.today.home

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.scottquach.today.room.Highlight

class HomeRepository() {
    private val highlightDbHelper by lazy {
    }

    private val _todaysHighlight = MutableLiveData<Highlight?>()
    fun todaysHighlight() = _todaysHighlight as LiveData<Highlight?>

    private val _allHighlights = MutableLiveData<List<Highlight?>>()
    fun allHighlights() = _allHighlights as LiveData<List<Highlight?>>

    @SuppressLint("CheckResult")
    fun getTodaysHighlight() {

    }
}