package com.pmob.tiketgbk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DetailOrderFragment : Fragment() {
    private lateinit var paymentMethodRadioGroup: RadioGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_order, container, false)

        // Bind UI components
        val matchTitleText: TextView = view.findViewById(R.id.matchTitleText)
        val matchDateText: TextView = view.findViewById(R.id.matchDateText)
        val matchTimeText: TextView = view.findViewById(R.id.matchTimeText)
        val userNameText: TextView = view.findViewById(R.id.userNameText)
        val userEmailText: TextView = view.findViewById(R.id.userEmailText)
        val userPhoneText: TextView = view.findViewById(R.id.userPhoneText)
        val tribunText: TextView = view.findViewById(R.id.tribunText)
        val quantityText: TextView = view.findViewById(R.id.quantityText)
        val totalPriceText: TextView = view.findViewById(R.id.totalPriceText)
        val confirmOrderButton: Button = view.findViewById(R.id.confirmOrderButton)
        paymentMethodRadioGroup = view.findViewById(R.id.paymentOptionsGroup)

        // Get data from arguments
        val name = arguments?.getString("name") ?: "Unknown"
        val email = arguments?.getString("email") ?: "Unknown"
        val phone = arguments?.getString("phone") ?: "Unknown"
        val tribun = arguments?.getString("tribun") ?: "Unknown"
        val price = arguments?.getInt("price") ?: 0
        val quantity = arguments?.getInt("quantity") ?: 0
        val matchTitle = arguments?.getString("matchTitle") ?: "Unknown Match"
        val matchDate = arguments?.getString("matchDate") ?: "Unknown Date"
        val matchTime = arguments?.getString("matchTime") ?: "Unknown Time"

        // Populate data to UI
        matchTitleText.text = "Pertandingan: $matchTitle"
        matchDateText.text = "Tanggal: $matchDate"
        matchTimeText.text = "Waktu: $matchTime"
        userNameText.text = "Nama: $name"
        userEmailText.text = "Email: $email"
        userPhoneText.text = "Telepon: $phone"
        tribunText.text = "Tribun: $tribun"
        quantityText.text = "Jumlah Tiket: $quantity"

        // Calculate and display total price
        val totalPrice = price * quantity
        totalPriceText.text = "Total Harga: Rp.${totalPrice}"

        confirmOrderButton.setOnClickListener {
            val selectedPaymentMethod = getSelectedPaymentMethod()

            if (selectedPaymentMethod.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Silakan pilih metode pembayaran", Toast.LENGTH_SHORT).show()
            } else {
                saveDataToFirebase(
                    name, email, phone, tribun, quantity,
                    matchTitle, matchDate, matchTime, totalPrice, selectedPaymentMethod
                )
            }
        }

        return view
    }

    private fun getSelectedPaymentMethod(): String? {
        val selectedId = paymentMethodRadioGroup.checkedRadioButtonId
        if (selectedId == -1) {
            return null // No selection
        }
        val selectedRadioButton = view?.findViewById<RadioButton>(selectedId)
        return selectedRadioButton?.text.toString()
    }

    private fun saveDataToFirebase(
        name: String,
        email: String,
        phone: String,
        tribun: String,
        quantity: Int,
        matchTitle: String,
        matchDate: String,
        matchTime: String,
        totalPrice: Int,
        paymentMethod: String
    ) {
        val db = Firebase.firestore

        val ticketData = mapOf(
            "name" to name,
            "email" to email,
            "phone" to phone,
            "tribun" to tribun,
            "quantity" to quantity,
            "matchTitle" to matchTitle,
            "matchDate" to matchDate,
            "matchTime" to matchTime,
            "totalPrice" to totalPrice,
            "paymentMethod" to paymentMethod
        )

        db.collection("historyTicket")
            .add(ticketData)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Pesanan berhasil disimpan!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Gagal menyimpan pesanan, coba lagi.", Toast.LENGTH_SHORT).show()
            }
    }
}
