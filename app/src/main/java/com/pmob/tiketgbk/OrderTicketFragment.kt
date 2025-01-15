package com.pmob.tiketgbk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

class OrderTicketFragment : Fragment() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var tribunSpinner: Spinner
    private lateinit var quantityEditText: EditText
    private lateinit var submitButton: Button
    private lateinit var selectedTribun: String
    private var selectedPrice: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_order_ticket, container, false)

        // Bind UI components
        nameEditText = view.findViewById(R.id.nameEditText)
        emailEditText = view.findViewById(R.id.emailEditText)
        phoneEditText = view.findViewById(R.id.phoneEditText)
        tribunSpinner = view.findViewById(R.id.tribunSpinner)
        quantityEditText = view.findViewById(R.id.quantityEditText)
        submitButton = view.findViewById(R.id.submitButton)

        // Tribun options with prices
        val tribunOptions = listOf(
            "VIP" to 1000000,
            "Utara" to 500000,
            "Timur" to 500000,
            "Selatan" to 500000
        )
        val tribunNames = tribunOptions.map { it.first }

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            tribunNames
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        tribunSpinner.adapter = adapter

        // Capture selected tribun
        tribunSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedTribun = tribunOptions[position].first
                selectedPrice = tribunOptions[position].second
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedTribun = ""
                selectedPrice = 0
            }
        }

        // Set button click listener
        submitButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val phone = phoneEditText.text.toString().trim()
            val quantity = quantityEditText.text.toString().toIntOrNull()

            // Regex untuk validasi nama
            val nameRegex = "^[a-zA-Z ]+$".toRegex()
            // Regex untuk validasi email
            val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$".toRegex()
            // Regex untuk validasi nomor telepon (11-13 digit)
            val phoneRegex = "^[0-9]{11,13}$".toRegex()

            // Validasi nama
            if (!name.matches(nameRegex)) {
                Toast.makeText(requireContext(), "Nama hanya boleh berisi huruf dan spasi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validasi email
            if (!email.matches(emailRegex)) {
                Toast.makeText(requireContext(), "Email tidak valid!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validasi nomor telepon
            if (!phone.matches(phoneRegex)) {
                Toast.makeText(requireContext(), "Nomor telepon harus 11-13 digit dan hanya berisi angka!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validasi jumlah tiket
            if (quantity == null || quantity <= 0) {
                Toast.makeText(requireContext(), "Jumlah tiket harus lebih dari 0!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Navigasi ke DetailOrderFragment dengan data
            navigateToDetailOrder(name, email, phone, selectedTribun, selectedPrice, quantity)
        }

        return view
    }

    private fun navigateToDetailOrder(
        name: String,
        email: String,
        phone: String,
        selectedTribun: String,
        selectedPrice: Int,
        quantity: Int
    ) {
        // Ambil data pertandingan
        val matchTitle = arguments?.getString("matchTitle") ?: "Unknown Match"
        val matchDate = arguments?.getString("matchDate") ?: "Unknown Date"
        val matchTime = arguments?.getString("matchTime") ?: "Unknown Time"

        // Siapkan bundle untuk mengirim data
        val bundle = Bundle().apply {
            putString("name", name)
            putString("email", email)
            putString("phone", phone)
            putString("tribun", selectedTribun)
            putInt("price", selectedPrice)
            putInt("quantity", quantity)
            putString("matchTitle", matchTitle)
            putString("matchDate", matchDate)
            putString("matchTime", matchTime)
        }

        // Navigasi ke DetailOrderFragment
        val detailOrderFragment = DetailOrderFragment().apply {
            arguments = bundle
        }

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, detailOrderFragment)
            .addToBackStack(null)
            .commit()
    }
}
