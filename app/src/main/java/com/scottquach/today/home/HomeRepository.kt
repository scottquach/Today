package com.scottquach.today.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.scottquach.today.Event
import com.scottquach.today.TodayApp
import com.scottquach.today.entry.EntryRepository
import com.scottquach.today.room.AppDatabase
import com.scottquach.today.room.Highlight
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

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

    fun completeHighlight(highlight: Highlight) {
        Completable.fromCallable {
            db.highlightDao().completeHighlight(highlight.id!!)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: CompletableObserver {
                override fun onSubscribe(d: Disposable) {}

                override fun onComplete() {
                    Timber.d("Completed completing highlight")
                }
                override fun onError(e: Throwable) {
                    Timber.e(e, "Error completing highlight")
                }
            })
    }
}