package com.scottquach.today.entry

import com.scottquach.today.TodayApp
import com.scottquach.today.helpers.HighlightDbHelper
import com.scottquach.today.room.Highlight
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Repository for the EntryViewModel
 */
class EntryRepository {

    private val highlightDbHelper by lazy {
        TodayApp.getInstance()?.applicationContext?.let { HighlightDbHelper(it) }
    }

    /**
     * Calls the db helper to insert a new highlight into the highlights table. Completed asynchronously with a
     * completable
     */
    fun insertNewHighlight(highlight: Highlight) {
        Completable.fromCallable {
            highlightDbHelper?.insertHighlight(highlight)
        }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object: CompletableObserver {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onComplete() {
                    Timber.d("inserting new highlight completed")
                }
                override fun onError(e: Throwable) {
                    Timber.e(e, "Error inserting new highlight")
                }
            })
    }
}