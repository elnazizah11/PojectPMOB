package com.pmob.tiketgbk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter(private var historyList: List<HistoryItem>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val historyItem = historyList[position]
        holder.nameTextView.text = "Nama: ${historyItem.name}"
        holder.matchTitleTextView.text = "Pertandingan: ${historyItem.matchTitle}"
        holder.matchDateTextView.text = "Tanggal: ${historyItem.matchDate}"
        holder.totalPriceTextView.text = "Total Harga: Rp. ${historyItem.totalPrice}"
    }

    override fun getItemCount(): Int = historyList.size

    fun updateData(newHistoryList: List<HistoryItem>) {
        historyList = newHistoryList
        notifyDataSetChanged()
    }

    class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        val matchTitleTextView: TextView = view.findViewById(R.id.matchTitleTextView)
        val matchDateTextView: TextView = view.findViewById(R.id.matchDateTextView)
        val totalPriceTextView: TextView = view.findViewById(R.id.totalPriceTextView)
    }
}

data class HistoryItem(
    val name: String,
    val matchTitle: String,
    val matchDate: String,
    val totalPrice: Int
)
