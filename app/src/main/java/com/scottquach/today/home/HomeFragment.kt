package com.scottquach.today.home

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.viewpager.widget.ViewPager
import com.scottquach.today.util.DateFormatterUtil
import com.scottquach.today.R
import com.scottquach.today.databinding.HomeFragmentBinding
import com.scottquach.today.model.TodayModel
import kotlinx.android.synthetic.main.home_fragment.*
import timber.log.Timber
import java.util.*


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
        val binding =
            DataBindingUtil.inflate<HomeFragmentBinding>(inflater, R.layout.home_fragment, container, false).apply {
                lifecycleOwner = this@HomeFragment
                this.viewmodel = viewModel
            }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        text_date.text = DateFormatterUtil.getDayOfWeekHumanFriendly(Date().time)
        button_nav_entry?.setOnClickListener {
            Timber.d("button create clicked")
            view!!.findNavController().navigate(R.id.action_homeFragment_to_entryFragment)
        }

        button_complete_highlight.setOnClickListener {
            viewModel.completeHighlight()
            text_today_title.text = getString(R.string.home_highlight_completed)
            button_complete_highlight.visibility = View.GONE
            card_today.visibility = View.GONE
        }

        button_settings.setOnClickListener {
            view!!.findNavController().navigate(R.id.action_homeFragment_to_destination_settings)
        }

        val adapter = OverviewPagerAdapter(context!!)
        pager_overview.apply {
            this.adapter = adapter
            setPageTransformer(true, ViewPagerStack())
            offscreenPageLimit = 4
        }

        viewModel.events.observe(viewLifecycleOwner, Observer {

        })

        viewModel.todaysHighlight.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                TodayModel.Status.COMPLETE -> {
                    text_today_title.text = getString(R.string.home_highlight_completed)
                    text_cards_highlight.text = it.highlight?.value
                    button_nav_entry.isEnabled = false
                }
                TodayModel.Status.PENDING -> {
                    button_nav_entry.isEnabled = false
                    text_cards_highlight.text = it.highlight?.value

                }
                TodayModel.Status.NONE -> {
                    button_nav_entry.isEnabled = true
                    button_complete_highlight.visibility = View.GONE
                    card_today.visibility = View.GONE
                    text_today_title.text = getString(R.string.home_create_highlight)
                }
            }
        })

        viewModel.allHighlights.observe(viewLifecycleOwner, Observer {
            Timber.d("All highlights were $it")
            adapter.setHighlights(it)
            if (it.isNotEmpty()) {
                Handler().post {
                    pager_overview.beginFakeDrag()
                    pager_overview.fakeDragBy(0f)
                    pager_overview.endFakeDrag()
                }
            }
        })
    }

    private inner class ViewPagerStack : ViewPager.PageTransformer {
        override fun transformPage(page: View, position: Float) {
            if (position >= 0) {
                page.scaleX = 1f - 0.05f * position
                page.scaleY = 1f - 0.05f * position
                page.translationX = (-page.width * position) + (45 * position)
            }
        }
    }
}
