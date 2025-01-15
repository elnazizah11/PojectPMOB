package com.pmob.tiketgbk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inisialisasi Firebase
        FirebaseApp.initializeApp(requireContext())
        auth = FirebaseAuth.getInstance()
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        // Referensi ke UI
        val emailEditText = view.findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = view.findViewById<EditText>(R.id.passwordEditText)
        val registerButton = view.findViewById<Button>(R.id.registerButton)
        val loginTextView = view.findViewById<TextView>(R.id.loginTextView)

        // Aksi tombol register
        registerButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // Regex untuk validasi email
            val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$".toRegex()
            // Regex untuk validasi password
            val passwordRegex = "^[a-zA-Z0-9@#$%^&+=]{8,12}$".toRegex()

            // Validasi email
            if (!email.matches(emailRegex)) {
                Toast.makeText(context, "Email tidak valid!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validasi password
            if (!password.matches(passwordRegex)) {
                Toast.makeText(context, "Password harus 8-12 karakter dan hanya mengandung huruf, angka, atau simbol @#$%^&+=", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Proses registrasi dengan Firebase
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Registration Successful!", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    } else {
                        Toast.makeText(context, "Registration Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        // Aksi untuk navigasi ke fragment login
        loginTextView.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        return view
    }
}
