package com.scottquach.today.home

import androidx.lifecycle.LiveData
import com.scottquach.today.TodayApp
import com.scottquach.today.room.AppDatabase
import com.scottquach.today.room.Highlight
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class HomeRepository {
    private val db: AppDatabase = AppDatabase.getInstance(TodayApp.getInstance()!!.applicationContext)

    val allHighlights: LiveData<List<Highlight>> = db.highlightDao().getAll()
    val todaysHighlight: LiveData<Highlight> = db.highlightDao().getToday()

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