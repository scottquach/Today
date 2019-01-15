package com.scottquach.today.entry

import android.app.Activity
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.scottquach.today.R
import com.scottquach.today.databinding.EntryFragmentBinding

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
        return inflater.inflate(R.layout.entry_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(EntryViewModel::class.java)
        DataBindingUtil.setContentView<EntryFragmentBinding>(activity as Activity, R.layout.entry_fragment).apply {
            this.setLifecycleOwner(this@EntryFragment)
            this.viewmodel = viewModel
        }
    }
}
