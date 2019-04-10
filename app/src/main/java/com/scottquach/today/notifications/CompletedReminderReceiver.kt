package com.scottquach.today.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.scottquach.today.R

class CompletedReminderReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationService = NotificationService(context)
        notificationService.showNotification(context.getString(R.string.notif_completed_title), context.getString(R.string.notif_completed_body), 3)
    }
}