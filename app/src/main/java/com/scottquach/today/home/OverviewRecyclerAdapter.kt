package com.scottquach.today.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.scottquach.today.R
import com.scottquach.today.room.Highlight

class OverviewRecyclerAdapter: RecyclerView.Adapter<OverviewRecyclerAdapter.HighlightHolder>() {

    private var highlights: List<Highlight> = emptyList()

    fun setHighlights(highlights: List<Highlight>) {
        this.highlights = highlights
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HighlightHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.highlight_overview_item, parent, false)
        return HighlightHolder(itemView)
    }

    override fun getItemCount() = highlights.size

    override fun onBindViewHolder(holder: HighlightHolder, position: Int) {
        val currentHighlight = highlights[position]
        holder.bind(currentHighlight)
    }

    inner class HighlightHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val textDate = itemView.findViewById<TextView>(R.id.text_date)
        private val textHighlight = itemView.findViewById<TextView>(R.id.text_highlight)

        fun bind(highlight: Highlight) {
            textDate.text = highlight.created.toString()
            textHighlight.text = highlight.value
        }
    }
}