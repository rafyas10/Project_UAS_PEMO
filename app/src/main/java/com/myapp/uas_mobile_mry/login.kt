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

class login : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var  auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth= FirebaseAuth.getInstance()

        binding.registerlink.setOnClickListener {
            val intent = Intent(this, register::class.java)
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailLogin.text.toString()
            val password = binding.passwordLogin.text.toString()

            //Validasi email
            if (email.isEmpty()){
                binding.emailLogin.error = "Email Harus Diisi"
                binding.emailLogin.requestFocus()
                return@setOnClickListener
            }

            //Validasi email tidak sesuai
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.passwordLogin.error = "Email Tidak Valid"
                binding.passwordLogin.requestFocus()
                return@setOnClickListener
            }

            //Validasi password
            if (password.isEmpty()){
                binding.passwordLogin.error = "Password Harus Diisi"
                binding.passwordLogin.requestFocus()
                return@setOnClickListener
            }

            LoginFirebase(email,password)
        }

        binding.emailLogin.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                binding.passwordLogin.requestFocus()
                return@setOnKeyListener true
            }
            false
        }

        binding.passwordLogin.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                // Perform login action
                val email = binding.emailLogin.text.toString()
                val password = binding.passwordLogin.text.toString()
                LoginFirebase(email, password)
                return@setOnKeyListener true
            }
            false
        }
    }

    private fun LoginFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Selamat datang $email", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Email atau Password Salah", Toast.LENGTH_SHORT).show()
                }
            }
    }

}

