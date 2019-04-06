package com.scottquach.today.util

import android.content.Context
import java.util.*

class PrefUtil(context: Context) {
    val PREFS_FILENAME = "TODAY_SHARED_PREFS"
    val prefs = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

    var entryReminderTime: Long
    get() = prefs.getLong("entry_reminder_time", Date().time)
    set(value) = prefs.edit().putLong("entry_reminder_time", value).apply()

    var entryReminderActive: Boolean
    get() = prefs.getBoolean("entry_reminder_active", false)
    set(value: Boolean) = prefs.edit().putBoolean("entry_reminder_active", value).apply()

    var completedReminderTime: Long
    get() = prefs.getLong("completed_reminder_time", Date().time)
    set(value) = prefs.edit().putLong("completed_reminder_time", value).apply()

    var completedReminderActive: Boolean
    get() = prefs.getBoolean("completed_reminder_active", false)
    set(value: Boolean) = prefs.edit().putBoolean("completed_reminder_active", value).apply()

    var isFirstBoot: Boolean
    get() = prefs.getBoolean("is_first_boot", true)
    set(value: Boolean) = prefs.edit().putBoolean("is_first_boot", value).apply()

}