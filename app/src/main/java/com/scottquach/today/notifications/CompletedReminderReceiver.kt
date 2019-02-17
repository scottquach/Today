package com.scottquach.today.notifications

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class CompletedReminderReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationService = NotificationService(context)
        notificationService.showNotification("Completed highlight?", "Did you complete your highlight?", 3)
    }
}