package com.scottquach.today

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.scottquach.today.notifications.CompletedReminderReceiver
import com.scottquach.today.notifications.EntryReminderReceiver
import com.scottquach.today.notifications.MidDayReminderReceiver

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (prefUtil.entryReminderActive) {
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    prefUtil.entryReminderTime,
                    AlarmManager.INTERVAL_DAY,
                    Intent(context, EntryReminderReceiver::class.java).let {
                        PendingIntent.getBroadcast(context, 0, it, 0)
                    }
                )
            }
            if (prefUtil.completedReminderActive) {
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    prefUtil.completedReminderTime,
                    AlarmManager.INTERVAL_DAY,
                    Intent(context, CompletedReminderReceiver::class.java).let {
                        PendingIntent.getBroadcast(context, 0, it, 0)
                    }
                )
            }
            if (prefUtil.midDayReminderActive) {
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    prefUtil.midDayreminderTime,
                    AlarmManager.INTERVAL_DAY,
                    Intent(context, MidDayReminderReceiver::class.java).let {
                        PendingIntent.getBroadcast(context, 0, it, 0)
                    }
                )
            }
        }
    }
}