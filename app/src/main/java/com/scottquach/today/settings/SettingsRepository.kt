package com.scottquach.today.settings

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.scottquach.today.BootReceiver
import com.scottquach.today.notifications.CompletedReminderReceiver
import com.scottquach.today.notifications.EntryReminderReceiver
import timber.log.Timber

class SettingsRepository(val context: Context) {

    private val alarmManager: AlarmManager by lazy {
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    private fun setAlarm(time: Long, alarmIntent: PendingIntent) {
        enableReceiver(BootReceiver::class.java)
        enableReceiver(EntryReminderReceiver::class.java)
        enableReceiver(CompletedReminderReceiver::class.java)
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            time,
            AlarmManager.INTERVAL_DAY,
            alarmIntent
        )
        Timber.d("setting alarm at time ${time}")
    }

    private fun enableReceiver(receiver: Class<out BroadcastReceiver>) {
        with(context.packageManager) {
            setComponentEnabledSetting(
                ComponentName(context, receiver),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
        }
    }

    private fun disableReceiver(receiver: Class<out BroadcastReceiver>) {
        with(context.packageManager) {
            setComponentEnabledSetting(
                ComponentName(context, receiver),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
        }
    }

    private fun cancelAlarm(alarmIntent: PendingIntent) {
        alarmManager.cancel(alarmIntent)
    }

    fun setReminder(time: Long, receiver: Class<out BroadcastReceiver>) {
        enableReceiver(BootReceiver::class.java)
        enableReceiver(receiver)
        val alarmIntent: PendingIntent = Intent(context, receiver).let {
            PendingIntent.getBroadcast(context, 0, it, 0)
        }
        setAlarm(time, alarmIntent)
    }

    fun disableReminder(receiver: Class<out BroadcastReceiver>) {
        disableReceiver(receiver)
        val alarmIntent: PendingIntent = Intent(context, receiver).let {
            PendingIntent.getBroadcast(context, 0, it, 0)
        }
        cancelAlarm(alarmIntent)
    }
}