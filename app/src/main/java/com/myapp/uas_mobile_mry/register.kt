package com.myapp.uas_mobile_mry

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import com.myapp.uas_mobile_mry.databinding.ActivityLoginBinding
import com.myapp.uas_mobile_mry.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth


class register : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding
    lateinit var  auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth= FirebaseAuth.getInstance()
        binding.loginlink.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }

        binding.registerButton.setOnClickListener {
            val email = binding.emailRegister.text.toString()
            val password = binding.passwordRegister.text.toString()

            //Validasi email
            if (email.isEmpty()) {
                binding.emailRegister.error = "Email Harus Diisi"
                binding.passwordRegister.requestFocus()
                return@setOnClickListener
            }

            //Validasi email tidak sesuai
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailRegister.error = "Email Tidak Valid"
                binding.emailRegister.requestFocus()
                return@setOnClickListener
            }

            //Validasi password
            if (password.isEmpty()) {
                binding.passwordRegister.error = "Password Harus Diisi"
                binding.passwordRegister.requestFocus()
                return@setOnClickListener
            }

            //Validasi panjang password
            if (password.length < 6) {
                binding.passwordRegister.error = "Password Minimal 6 Karakter"
                binding.passwordRegister.requestFocus()
                return@setOnClickListener
            }

            RegisterFirebase(email, password)
        }

        binding.emailRegister.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                binding.passwordRegister.requestFocus()
                return@setOnKeyListener true
            }
            false
        }

        binding.passwordRegister.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                // Perform login action
                val email = binding.emailRegister.text.toString()
                val password = binding.passwordRegister.text.toString()
                RegisterFirebase(email, password)
                return@setOnKeyListener true
            }
            false
        }
    }
    private fun RegisterFirebase(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Register Berhasil", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, login::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}