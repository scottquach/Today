package com.scottquach.today.entry

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.scottquach.today.R
import com.scottquach.today.databinding.EntryFragmentBinding
import kotlinx.android.synthetic.main.entry_fragment.*
import timber.log.Timber

/**
 * Provides UI for creating a new highlight and assigning that highlight to a goal
 */
class EntryFragment : Fragment() {

    companion object {
        fun newInstance() = EntryFragment()
    }

    private lateinit var viewModel: EntryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(EntryViewModel::class.java)
        val binding = DataBindingUtil.inflate<EntryFragmentBinding>(inflater,R.layout.entry_fragment, container, false).apply {
            lifecycleOwner = this@EntryFragment
            this.viewmodel = viewmodel
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val newEntryPlaceholders: Array<String> = resources.getStringArray(R.array.new_entry_placeholders)
        edit_entry.hint = newEntryPlaceholders.random()
        
        button_back.setOnClickListener {
            findNavController().navigate(R.id.action_entryFragment_to_homeFragment)
        }

        viewModel.events.observe(this, Observer {
            Timber.d("observer called ${it.peekContent()}")
            if (it.getContentIfNotHandled() == EntryRepository.Events.INSERTED) {
                findNavController().navigate(R.id.action_entryFragment_to_homeFragment)
            }
        })

        button_create.setOnClickListener {
            viewModel.createHighlight(edit_entry.text.toString())
        }
    }
}
