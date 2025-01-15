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

class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inisialisasi Firebase
        FirebaseApp.initializeApp(requireContext())
        auth = FirebaseAuth.getInstance()
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        // Referensi ke UI
        val emailEditText = view.findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = view.findViewById<EditText>(R.id.passwordEditText)
        val loginButton = view.findViewById<Button>(R.id.loginButton)
        val registerTextView = view.findViewById<TextView>(R.id.registerTextView)

        // Aksi tombol login
        loginButton.setOnClickListener {
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

            // Proses login dengan Firebase
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                    } else {
                        Toast.makeText(context, "Login Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        // Aksi untuk navigasi ke fragment registrasi
        registerTextView.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        return view
    }
}
