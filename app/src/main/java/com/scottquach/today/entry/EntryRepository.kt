package com.scottquach.today.entry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.scottquach.today.model.Event
import com.scottquach.today.TodayApp
import com.scottquach.today.room.AppDatabase
import com.scottquach.today.room.Highlight
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Repository for the EntryViewModel
 */
class EntryRepository {

    enum class EntryRepoEvent {
        INSERTED
    }

    private val db: AppDatabase = AppDatabase.getInstance(TodayApp.getInstance()!!.applicationContext)
    private val events = MutableLiveData<Event<EntryRepoEvent>>()

    fun getEvents() = events as LiveData<Event<EntryRepoEvent>>

    /**
     * Calls the db helper to insert a new highlight into the highlights table. Completed asynchronously with a
     * completable
     */
    fun insertNewHighlight(highlight: Highlight) {
        Completable.fromCallable {
            db.highlightDao().insert(highlight)
        }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object: CompletableObserver {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onComplete() {
                    Timber.d("inserting new highlight completed")
                    events.value = Event(EntryRepoEvent.INSERTED)
                }
                override fun onError(e: Throwable) {
                    Timber.e(e, "Error inserting new highlight")
                }
            })
    }
}