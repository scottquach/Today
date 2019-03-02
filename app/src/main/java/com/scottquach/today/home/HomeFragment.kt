package com.scottquach.today.home

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.transition.ChangeBounds
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnticipateInterpolator
import android.view.animation.LinearInterpolator
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.viewpager.widget.ViewPager
import com.scottquach.today.R
import com.scottquach.today.databinding.HomeFragmentBinding
import com.scottquach.today.notifications.NotificationService
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
        val binding =
            DataBindingUtil.inflate<HomeFragmentBinding>(inflater, R.layout.home_fragment, container, false).apply {
                lifecycleOwner = this@HomeFragment
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

        button_complete_highlight.setOnClickListener {
            //            viewModel.completeHighlight()
            card_today.animate()
                .translationX(card_today.width.toFloat())
                .setInterpolator(AnticipateInterpolator())
                .setDuration(300)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        val constraintSet = ConstraintSet()
                        constraintSet.clone(context, R.layout.home_fragment_complete)
                        val transition = ChangeBounds()
                        transition.interpolator = AccelerateInterpolator()
                        transition.duration = 200
                        TransitionManager.beginDelayedTransition(constraint_home, transition)
                        constraintSet.applyTo(constraint_home)
                    }
                })
        }

        button_settings.setOnClickListener {
            view!!.findNavController().navigate(R.id.action_homeFragment_to_destination_settings)
//            val notificationService = NotificationService(context!!)
//            notificationService.showNotification("test", "test", 1)
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
            Timber.d("Todays highlight was ${it}")
//            button_nav_entry.isEnabled = it == null
        })

        viewModel.allHighlights.observe(viewLifecycleOwner, Observer {
            Timber.d("All highlights were $it")
            adapter.setHighlights(it)
            Handler().post {
                pager_overview.beginFakeDrag()
                pager_overview.fakeDragBy(0f)
                pager_overview.endFakeDrag()
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
