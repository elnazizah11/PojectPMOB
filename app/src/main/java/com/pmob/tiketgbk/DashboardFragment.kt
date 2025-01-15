package com.pmob.tiketgbk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        // Tombol Riwayat
        val historyButton = view.findViewById<Button>(R.id.historyButton)
        // Tombol List Match
        val matchListButton = view.findViewById<Button>(R.id.matchListButton)

        // Aksi tombol Riwayat
        historyButton.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_historyFragment)
        }

        // Aksi tombol List Match untuk navigasi ke MatchListFragment
        matchListButton.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_matchListFragment)
        }

        return view
    }
}
