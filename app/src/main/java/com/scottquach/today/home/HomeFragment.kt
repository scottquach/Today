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
import com.scottquach.today.model.HighlightStatus
import com.scottquach.today.model.TodayModel
import kotlinx.android.synthetic.main.home_fragment.*
import org.joda.time.DateTime
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

        Timber.d("time is ${DateTime().toString()}")

        text_date.text = DateFormatterUtil.getDayOfWeekHumanFriendly(DateTime())
        button_nav_entry?.setOnClickListener {
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

        viewModel.todaysHighlight.observe(viewLifecycleOwner, Observer {
            Timber.d("Todays highlight was $it")
            when (it.status) {
                HighlightStatus.COMPLETED -> {
                    text_today_title.text = getString(R.string.home_highlight_completed)
                    button_complete_highlight.visibility = View.GONE
                    card_today.visibility = View.GONE
                    button_nav_entry.isEnabled = false
                }
                HighlightStatus.PENDING -> {
                    button_nav_entry.isEnabled = false
                    text_cards_highlight.text = it.highlight?.value
                }
                HighlightStatus.NONE -> {
                    button_nav_entry.isEnabled = true
                    button_complete_highlight.visibility = View.GONE
                    card_today.visibility = View.GONE
                    text_today_title.text = getString(R.string.home_create_highlight)
                }
                HighlightStatus.INCOMPLETE -> TODO()
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
                page.scaleY = 1f - 0.1f * position
                page.translationX = (-page.width * position) + (75 * position)
            }
        }
    }
}
