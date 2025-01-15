package com.pmob.tiketgbk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MatchListFragment : Fragment() {

    private val matches = listOf(
        Match("Persijap vs Persela", "10 Jan 2025", "15:30"),
        Match("Persijap vs Persebaya", "12 Jan 2025", "19:00"),
        Match("Persijap vs Persipura", "14 Jan 2025", "20:00")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_match_list, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.matchRecyclerView)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = MatchAdapter(matches) { match ->
            // Navigate to OrderTicketFragment with match details
            val fragment = OrderTicketFragment()
            val bundle = Bundle()
            bundle.putString("matchTitle", match.title)
            bundle.putString("matchDate", match.date)
            bundle.putString("matchTime", match.time)
            fragment.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .addToBackStack(null)
                .commit()
        }
        return view
    }
}
