package com.scottquach.today.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.scottquach.today.model.Event
import com.scottquach.today.notifications.CompletedReminderReceiver
import com.scottquach.today.notifications.EntryReminderReceiver
import com.scottquach.today.prefUtil
import org.joda.time.MutableDateTime
import timber.log.Timber

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    enum class SettingsEvents {
        ShowEntryPicker,
        ShowCompletedPicker,
        TimeSet
    }

    private val repository: SettingsRepository = SettingsRepository(application)
    private val _events = MutableLiveData<Event<SettingsEvents>>()
    val events = _events as LiveData<Event<SettingsEvents>>

    fun entryReminderChecked(isChecked: Boolean) {
        if (isChecked) {
            _events.value = Event(SettingsEvents.ShowEntryPicker)
        } else {
            repository.disableReminder(EntryReminderReceiver::class.java)
        }
        prefUtil.entryReminderActive = isChecked
    }

    fun completeReminderChecked(isChecked: Boolean) {
        if (isChecked) {
            _events.value = Event(SettingsEvents.ShowCompletedPicker)
        } else {
            repository.disableReminder(CompletedReminderReceiver::class.java)
        }
        prefUtil.completedReminderActive = isChecked
    }

    fun timeSet(hourOfDay: Int, minute: Int, type: SettingsEvents) {
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
            SettingsEvents.ShowEntryPicker -> {
                repository.setReminder(time.millis, EntryReminderReceiver::class.java)
                prefUtil.entryReminderTime = time.millis
                _events.value = Event(SettingsEvents.TimeSet)
            }
            SettingsEvents.ShowCompletedPicker -> {
                repository.setReminder(time.millis, CompletedReminderReceiver::class.java)
                prefUtil.completedReminderTime = time.millis
                _events.value = Event(SettingsEvents.TimeSet)
            }
        }
    }

}
