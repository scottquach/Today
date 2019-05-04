package com.scottquach.today.notifications

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.scottquach.today.R
import com.scottquach.today.TodayApp
import com.scottquach.today.room.AppDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class EntryReminderReceiver : BroadcastReceiver() {
    @SuppressLint("CheckResult")
    override fun onReceive(context: Context, intent: Intent) {
        val db: AppDatabase = AppDatabase.getInstance(TodayApp.getInstance()!!.applicationContext)
        db.highlightDao().getTodayHard()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    //Means highlight was set
                },
                { error -> Timber.d(error)},
                {
                    val notificationService = NotificationService(context)
                    notificationService.showNotification(
                        context.getString(R.string.notif_entry_title),
                        context.getString(R.string.notif_entry_body),
                        54
                    )
                }
            )
    }
}