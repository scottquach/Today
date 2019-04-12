package com.scottquach.today

import android.app.Application
import com.facebook.stetho.Stetho
import com.scottquach.today.notifications.CompletedReminderReceiver
import com.scottquach.today.notifications.EntryReminderReceiver
import com.scottquach.today.settings.SettingsRepository
import com.scottquach.today.util.PrefUtil
import net.danlew.android.joda.JodaTimeAndroid
import org.joda.time.MutableDateTime
import timber.log.Timber

val prefUtil: PrefUtil by lazy {
    TodayApp.prefs!!
}

class TodayApp: Application() {

    companion object {
        private var instance: Application? = null
        var prefs: PrefUtil? = null

        fun getInstance() = instance
    }

    override fun onCreate() {
        super.onCreate()
        if (instance == null) {
            instance = this
        }
        JodaTimeAndroid.init(this)
        prefs = PrefUtil(this)
        Timber.plant(DebugTree())
//        Timber.d(DebugDB.getAddressLog())
        Stetho.initializeWithDefaults(this)

        firstBootCheck()
    }

    private fun firstBootCheck() {
        if (prefUtil.isFirstBoot) {
            val entryReminderTime = MutableDateTime().apply {
                hourOfDay = 8
                minuteOfHour = 0
                if (!this.isAfterNow) {
                    this.addDays(1)
                }
            }
            val completedReminderTime = MutableDateTime().apply {
                hourOfDay = 20
                minuteOfHour = 0
                if (!this.isAfterNow) {
                    this.addDays(1)
                }
            }
            prefUtil.entryReminderTime = entryReminderTime.millis
            prefUtil.completedReminderTime = completedReminderTime.millis
            prefUtil.entryReminderActive = true
            prefUtil.completedReminderActive = true

            val settingsRepo = SettingsRepository(this)
            settingsRepo.setReminder(entryReminderTime.millis, EntryReminderReceiver::class.java)
            settingsRepo.setReminder(completedReminderTime.millis, CompletedReminderReceiver::class.java)

            prefUtil.isFirstBoot = false
        }
    }
    
    inner class DebugTree : Timber.DebugTree() {
        override fun createStackElementTag(element: StackTraceElement): String? {
            return String.format(
                "[L:%s] [M:%s] [C:%s]",
                element.lineNumber,
                element.methodName,
                super.createStackElementTag(element)
            )
        }
    }
}