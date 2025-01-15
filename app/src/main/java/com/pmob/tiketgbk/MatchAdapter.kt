package com.pmob.tiketgbk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView

data class Match(val title: String, val date: String, val time: String)

class MatchAdapter(private val matches: List<Match>, private val onClick: (Match) -> Unit) :
    RecyclerView.Adapter<MatchAdapter.MatchViewHolder>() {

    class MatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.matchTitleTextView)
        val dateTextView: TextView = view.findViewById(R.id.matchDateTextView)
        val timeTextView: TextView = view.findViewById(R.id.matchTimeTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_match_card, parent, false)
        return MatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val match = matches[position]
        holder.titleTextView.text = match.title
        holder.dateTextView.text = match.date
        holder.timeTextView.text = match.time
        holder.itemView.setOnClickListener {
            val fragment = OrderTicketFragment()
            val bundle = Bundle()
            bundle.putString("matchTitle", match.title)
            bundle.putString("matchDate", match.date)
            bundle.putString("matchTime", match.time)
            fragment.arguments = bundle

            (holder.itemView.context as AppCompatActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
    override fun getItemCount() = matches.size
}
