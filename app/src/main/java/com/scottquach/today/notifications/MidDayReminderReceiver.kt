package com.scottquach.today.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.scottquach.today.R
import com.scottquach.today.model.HighlightStatus
import com.scottquach.today.room.AppDatabase
import com.scottquach.today.room.Highlight
import io.reactivex.MaybeObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MidDayReminderReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val db: AppDatabase = AppDatabase.getInstance(context)
        db.highlightDao().getTodayHard()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : MaybeObserver<Highlight?> {
                override fun onSuccess(t: Highlight) {
                    if (t.status != HighlightStatus.COMPLETED) {
                        val notificationService = NotificationService(context)
                        notificationService.showNotification(
                            "Focus on ${t.value}",
                            context.getString(R.string.notif_mid_day_reminder),
                            3
                        )
                    }
                }

                override fun onComplete() {
                    // Means that no highlight was set
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    Timber.d(e)
                }
            })
    }
}