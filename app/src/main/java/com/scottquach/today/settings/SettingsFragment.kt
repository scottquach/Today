package com.scottquach.today.settings

import android.app.TimePickerDialog
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.scottquach.today.R
import com.scottquach.today.prefUtil
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
//        val binding = DataBindingUtil.inflate<SettingsFragmentBinding>(inflater, R.layout.settings_fragment, container, false).apply {
//            this.lifecycleOwner = this@SettingsFragment
//        }
//        binding.viewModel = viewModel
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        switch_entry_reminder.isChecked = prefUtil.entryReminderActive
        switch_completed_reminder.isChecked = prefUtil.completedReminderActive

        switch_entry_reminder.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.entryReminderChecked(isChecked)
        }

        switch_completed_reminder.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.completeReminderChecked(isChecked)
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
