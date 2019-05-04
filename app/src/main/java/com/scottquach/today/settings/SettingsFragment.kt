package com.scottquach.today.settings

import android.app.TimePickerDialog
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.scottquach.today.R
import com.scottquach.today.prefUtil
import com.scottquach.today.util.DateFormatterUtil
import kotlinx.android.synthetic.main.settings_fragment.*
import org.joda.time.DateTime
import timber.log.Timber


class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        switch_entry_reminder.isChecked = prefUtil.entryReminderActive
        switch_completed_reminder.isChecked = prefUtil.completedReminderActive
        switch_mid_day_switch.isChecked = prefUtil.midDayReminderActive
        text_entry_time.text = DateFormatterUtil.getTimeHumanFriendly(prefUtil.entryReminderTime)
        text_completed_time.text = DateFormatterUtil.getTimeHumanFriendly(prefUtil.completedReminderTime)
        text_mid_day_time.text = DateFormatterUtil.getTimeHumanFriendly(prefUtil.midDayreminderTime)

        switch_entry_reminder.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.entryReminderChecked(isChecked)
        }

        switch_completed_reminder.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.completeReminderChecked(isChecked)
        }

        switch_mid_day_switch.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.midDayReminderChecked(isChecked)
        }

        button_back.setOnClickListener {
            view!!.findNavController().navigate(R.id.action_destination_settings_to_homeFragment)
        }

        viewModel.getEvents().observe(viewLifecycleOwner, Observer {
            when (it.getContentIfNotHandled()) {
                SettingsViewModel.SettingsEvent.ShowEntryPicker -> showTimePicker(it.peekContent())
                SettingsViewModel.SettingsEvent.ShowCompletedPicker -> showTimePicker(it.peekContent())
                SettingsViewModel.SettingsEvent.ShowMidDayPicker -> showTimePicker(it.peekContent())
                SettingsViewModel.SettingsEvent.TimeSet -> {
                    text_entry_time.text = DateFormatterUtil.getTimeHumanFriendly(prefUtil.entryReminderTime)
                    text_completed_time.text = DateFormatterUtil.getTimeHumanFriendly(prefUtil.completedReminderTime)
                    text_mid_day_time.text = DateFormatterUtil.getTimeHumanFriendly(prefUtil.midDayreminderTime)
                    Timber.d("time set")
                }
            }
        })
    }

    private fun showTimePicker(type: SettingsViewModel.SettingsEvent) {
        val currentTime = DateTime()
        val timePicker = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            viewModel.timeSet(hourOfDay, minute, type)
        }, currentTime.hourOfDay, currentTime.minuteOfHour, false)
        timePicker.setTitle("Select Time")
        timePicker.show()
    }

}
