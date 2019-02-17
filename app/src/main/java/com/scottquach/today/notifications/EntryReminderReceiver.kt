package com.scottquach.today.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class EntryReminderReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationService = NotificationService(context)
        notificationService.showNotification("Enter highlight", "Enter your highlight for the day", 54)
    }

}