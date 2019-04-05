package com.scottquach.today.settings

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.scottquach.today.BootReceiver
import com.scottquach.today.notifications.EntryReminderReceiver
import com.scottquach.today.prefUtil
import timber.log.Timber
import kotlin.reflect.KClass


class SettingsRepository(val context: Context) {

    private val alarmManager: AlarmManager by lazy {
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    private fun setAlarm(time: Long, alarmIntent: PendingIntent) {
        with(context.packageManager) {
            setComponentEnabledSetting(
                ComponentName(context, BootReceiver::class.java),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
        }
        alarmManager.setRepeating(
            AlarmManager.RTC,
            time,
            AlarmManager.INTERVAL_DAY,
            alarmIntent
        )
        Timber.d("setting alarm at time ${time}")
    }

    private fun cancelAlarm(alarmIntent: PendingIntent) {
        alarmManager.cancel(alarmIntent)
        with(context.packageManager) {
            setComponentEnabledSetting(
                ComponentName(context, BootReceiver::class.java),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
        }
    }

    fun setReminder(time: Long, receiver: Class<out BroadcastReceiver>) {
        val componentName = ComponentName(context, receiver)
        with(context.packageManager) {
            setComponentEnabledSetting(
                componentName,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
        }
        val alarmIntent: PendingIntent = Intent(context, receiver).let {
            PendingIntent.getBroadcast(context, 0, it, 0)
        }
        setAlarm(time, alarmIntent)
    }

    fun disableReminder(receiver: Class<out BroadcastReceiver>) {
        val componentName = ComponentName(context, receiver)
        with(context.packageManager) {
            setComponentEnabledSetting(
                componentName,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
        }
        val alarmIntent: PendingIntent = Intent(context, receiver).let {
            PendingIntent.getBroadcast(context, 0, it, 0)
        }
        cancelAlarm(alarmIntent)
    }
}