package com.scottquach.today.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.scottquach.today.R

class EntryReminderReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationService = NotificationService(context)
        notificationService.showNotification(context.getString(R.string.notif_entry_title), context.getString(R.string.notif_entry_body), 54)
    }
}