package com.pmob.tiketgbk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HistoryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var historyAdapter: HistoryAdapter
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        // Inisialisasi komponen UI
        recyclerView = view.findViewById(R.id.historyRecyclerView)
        emptyTextView = view.findViewById(R.id.emptyTextView)
        progressBar = view.findViewById(R.id.progressBar)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        historyAdapter = HistoryAdapter(listOf()) // Mulai dengan data kosong
        recyclerView.adapter = historyAdapter

        // Ambil data berdasarkan email pengguna
        fetchHistoryData()

        return view
    }

    private fun fetchHistoryData() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            // Jika pengguna belum login, tampilkan pesan error
            emptyTextView.text = "Pengguna belum login."
            emptyTextView.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            return
        }

        val userEmail = currentUser.email
        if (userEmail == null) {
            // Jika email pengguna tidak tersedia, tampilkan pesan error
            emptyTextView.text = "Email pengguna tidak ditemukan."
            emptyTextView.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            return
        }

        val db = Firebase.firestore
        db.collection("historyTicket")
            .whereEqualTo("email", userEmail) // Filter berdasarkan email
            .get()
            .addOnSuccessListener { result ->
                val historyList = mutableListOf<HistoryItem>()
                for (document in result) {
                    val data = document.data
                    val name = data["name"] as? String ?: ""
                    val matchTitle = data["matchTitle"] as? String ?: ""
                    val matchDate = data["matchDate"] as? String ?: ""
                    val totalPrice = (data["totalPrice"] as? Long)?.toInt() ?: 0
                    historyList.add(HistoryItem(name, matchTitle, matchDate, totalPrice))
                }
                updateRecyclerView(historyList)
            }
            .addOnFailureListener { exception ->
                // Tangani error jika pengambilan data gagal
                emptyTextView.text = "Gagal mengambil data: ${exception.message}"
                emptyTextView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
    }

    private fun updateRecyclerView(historyList: List<HistoryItem>) {
        progressBar.visibility = View.GONE

        if (historyList.isEmpty()) {
            emptyTextView.text = "Tidak ada riwayat pesanan."
            emptyTextView.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            emptyTextView.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            historyAdapter.updateData(historyList) // Update data adapter
        }
    }
}
