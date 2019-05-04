package com.scottquach.today.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.scottquach.today.model.Event
import com.scottquach.today.notifications.CompletedReminderReceiver
import com.scottquach.today.notifications.EntryReminderReceiver
import com.scottquach.today.notifications.MidDayReminderReceiver
import com.scottquach.today.prefUtil
import org.joda.time.MutableDateTime
import timber.log.Timber

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    enum class SettingsEvent {
        ShowEntryPicker,
        ShowCompletedPicker,
        ShowMidDayPicker,
        TimeSet
    }

    private val repository: SettingsRepository = SettingsRepository(application)
    private val events = MutableLiveData<Event<SettingsEvent>>()
    fun getEvents() = events as LiveData<Event<SettingsEvent>>

    fun entryReminderChecked(isChecked: Boolean) {
        if (isChecked) {
            events.value = Event(SettingsEvent.ShowEntryPicker)
        } else {
            repository.disableReminder(EntryReminderReceiver::class.java)
        }
        prefUtil.entryReminderActive = isChecked
    }

    fun completeReminderChecked(isChecked: Boolean) {
        if (isChecked) {
            events.value = Event(SettingsEvent.ShowCompletedPicker)
        } else {
            repository.disableReminder(CompletedReminderReceiver::class.java)
        }
        prefUtil.completedReminderActive = isChecked
    }

    fun midDayReminderChecked(isChecked: Boolean) {
        if (isChecked) {
            events.value = Event(SettingsEvent.ShowMidDayPicker)
        } else {
            repository.disableReminder(MidDayReminderReceiver::class.java)
        }
        prefUtil.midDayReminderActive = isChecked
    }

    fun timeSet(hourOfDay: Int, minute: Int, type: SettingsEvent) {
        val time = MutableDateTime().apply {
            this.hourOfDay = hourOfDay
            this.minuteOfHour = minute
        }
        if (!time.isAfterNow) {
            time.addDays(1)
        }

        Timber.d("hourOfday ${hourOfDay} minute ${minute}")
        Timber.d("Time was $time")
        when(type) {
            SettingsEvent.ShowEntryPicker -> {
                repository.setReminder(time.millis, EntryReminderReceiver::class.java)
                prefUtil.entryReminderTime = time.millis
                events.value = Event(SettingsEvent.TimeSet)
            }
            SettingsEvent.ShowCompletedPicker -> {
                repository.setReminder(time.millis, CompletedReminderReceiver::class.java)
                prefUtil.completedReminderTime = time.millis
                events.value = Event(SettingsEvent.TimeSet)
            }
            SettingsEvent.ShowMidDayPicker -> {
                repository.setReminder(time.millis, MidDayReminderReceiver::class.java)
                prefUtil.midDayreminderTime = time.millis
                events.value = Event(SettingsEvent.TimeSet)
            }
        }
    }

}
