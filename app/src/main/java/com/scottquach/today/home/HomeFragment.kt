package com.scottquach.today.home

import android.app.Activity
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.scottquach.today.R
import com.scottquach.today.databinding.HomeFragmentBinding
import kotlinx.android.synthetic.main.home_fragment.*
import timber.log.Timber


class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this)[HomeViewModel::class.java]
        val binding = DataBindingUtil.inflate<HomeFragmentBinding>(inflater,R.layout.home_fragment, container, false).apply {
            setLifecycleOwner(this@HomeFragment)
            this.viewmodel = viewModel
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        button_nav_entry?.setOnClickListener {
            Timber.d("button create clicked")
            view!!.findNavController().navigate(R.id.action_homeFragment_to_entryFragment)
        }

        viewModel.todaysHighlight.observe(this, Observer {
            Timber.d("Todays highlight was ${it}")
        })

        viewModel.allHighlights.observe(this, Observer {
            Timber.d("All highlights werer ${it
            }")
        })

    }
}
