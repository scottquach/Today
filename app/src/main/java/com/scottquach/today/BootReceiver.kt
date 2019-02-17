package com.scottquach.today

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.scottquach.today.notifications.CompletedReminderReceiver
import com.scottquach.today.notifications.EntryReminderReceiver

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (prefUtil.entryReminderActive) {
                alarmManager.setInexactRepeating(
                    AlarmManager.RTC,
                    prefUtil.entryReminderTime,
                    AlarmManager.INTERVAL_DAY,
                    Intent(context, EntryReminderReceiver::class.java).let {
                        PendingIntent.getBroadcast(context, 0, it, 0)
                    }
                )
            }
            if (prefUtil.completedReminderActive) {
                alarmManager.setInexactRepeating(
                    AlarmManager.RTC,
                    prefUtil.completedReminderTime,
                    AlarmManager.INTERVAL_DAY,
                    Intent(context, CompletedReminderReceiver::class.java).let {
                        PendingIntent.getBroadcast(context, 0, it, 0)
                    }
                )
            }
        }
    }
}