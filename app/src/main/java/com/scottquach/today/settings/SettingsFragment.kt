package com.scottquach.today.settings

import android.app.TimePickerDialog
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.scottquach.today.R
import kotlinx.android.synthetic.main.settings_fragment.*
import org.joda.time.DateTime


class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)

        switch_entry_reminder.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.entryReminderChecked(isChecked)
        }

        switch_completed_reminder.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.compeleteReminderChecked(isChecked)
        }

        viewModel.events.observe(viewLifecycleOwner, Observer {
            when (it.getContentIfNotHandled()) {
                SettingsViewModel.SettingsEvents.ShowEntryPicker -> showTimePicker(it.peekContent())
                SettingsViewModel.SettingsEvents.ShowCompletedPicker -> showTimePicker(it.peekContent())
            }
        })
    }

    fun showTimePicker(type: SettingsViewModel.SettingsEvents) {
        val currentTime = DateTime()
        val timePicker = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            viewModel.timeSet(hourOfDay, minute, type)
        }, currentTime.hourOfDay, currentTime.minuteOfHour, false)
        timePicker.setTitle("Select Time")
        timePicker.show()
    }

}
