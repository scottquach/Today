package com.scottquach.today.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.PagerAdapter
import com.scottquach.today.util.DateFormatterUtil
import com.scottquach.today.model.HighlightStatus
import com.scottquach.today.R
import com.scottquach.today.room.Highlight
import timber.log.Timber

class OverviewPagerAdapter(val context: Context) : PagerAdapter() {

    private var highlights: List<Highlight> = emptyList()

    fun setHighlights(highlights: List<Highlight>) {
        this.highlights = highlights
        notifyDataSetChanged()
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount() = highlights.size

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = LayoutInflater.from(context).inflate(R.layout.highlight_overview_item, container, false)

        itemView.findViewById<TextView>(R.id.text_created).text = DateFormatterUtil.getYearHumanFriendly(highlights[position].created)
        itemView.findViewById<TextView>(R.id.text_highlight).text = highlights[position].value
        if (highlights[position].status == HighlightStatus.COMPLETED) {
            itemView.findViewById<CardView>(R.id.card_overview).setCardBackgroundColor(context.resources.getColor(R.color.green))
            itemView.findViewById<TextView>(R.id.text_status).text = context.getString(R.string.overview_item_status_complete)
        } else {
            itemView.findViewById<CardView>(R.id.card_overview).setCardBackgroundColor(context.resources.getColor(R.color.red))
            itemView.findViewById<TextView>(R.id.text_status).text = context.getString(R.string.overview_item_incomplete)
        }
        container.addView(itemView)

        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as FrameLayout)
    }
}