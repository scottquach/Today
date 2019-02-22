package com.scottquach.today.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.scottquach.today.Event
import com.scottquach.today.prefUtil
import org.joda.time.DateTime
import timber.log.Timber

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    enum class SettingsEvents {
        ShowEntryPicker,
        ShowCompletedPicker
    }

    private val repository: SettingsRepository = SettingsRepository(application)
    private val _events = MutableLiveData<Event<SettingsEvents>>()

    val events = _events as LiveData<Event<SettingsEvents>>


    fun entryReminderChecked(isChecked: Boolean) {
        if (isChecked) {
            _events.value = Event(SettingsEvents.ShowEntryPicker)
        } else {
            repository.disableEntryReminder()
        }
        prefUtil.entryReminderActive = isChecked
    }

    fun completeReminderChecked(isChecked: Boolean) {
        if (isChecked) {
            _events.value = Event(SettingsEvents.ShowCompletedPicker)
        } else {
            repository.disableCompletedReminder()
        }
        prefUtil.completedReminderActive = isChecked
    }

    fun timeSet(hourOfDay: Int, minute: Int, type: SettingsEvents) {
        val time = DateTime().apply {
            this.withHourOfDay(hourOfDay)
            this.withMinuteOfHour(minute)
        }

        Timber.d("Time was $time")
        when(type) {
            SettingsEvents.ShowEntryPicker -> repository.setEntryReminder(time.millis)
            SettingsEvents.ShowCompletedPicker -> repository.setCompletedReminder(time.millis)
        }
    }

}
