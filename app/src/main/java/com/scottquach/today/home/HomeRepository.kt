package com.scottquach.today.home

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.scottquach.today.TodayApp
import com.scottquach.today.helpers.HighlightDbHelper
import com.scottquach.today.room.Highlight
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeRepository() {
    private val highlightDbHelper by lazy {
        TodayApp.getInstance()?.applicationContext?.let { HighlightDbHelper(it) }
    }

    private val _todaysHighlight = MutableLiveData<Highlight?>()
    fun todaysHighlight() = _todaysHighlight as LiveData<Highlight?>

    private val _allHighlights = MutableLiveData<List<Highlight?>>()
    fun allHighlights() = _allHighlights as LiveData<List<Highlight?>>

    @SuppressLint("CheckResult")
    fun getTodaysHighlight() {
        Observable.fromCallable {
            return@fromCallable highlightDbHelper?.getById(2)
        }
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(Schedulers.io())
            .subscribe {

            }
    }
}