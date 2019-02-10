package com.scottquach.today

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotifyReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null) {
            val notificationService = NotificationService(context)
            if (intent?.action.equals("ENTRY_REMINDER")) {
                notificationService.showNotification("What's today's highlight?", "Click to add an entry", 1)
            } else if (intent?.action.equals("COMPLETED_REMINDER")) {
                notificationService.showNotification("Did you complete today's highlight?", "Click to confirm", 2)
            }
        } else {

        }
    }
}