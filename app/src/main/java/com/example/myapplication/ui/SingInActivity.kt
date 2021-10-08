package com.example.myapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySingInBinding
import com.example.myapplication.utils.getStringText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingInBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        reference = Firebase.database.reference

        binding.exit.setOnClickListener {
            val i = Intent(this, SingUpActivity::class.java)
            startActivity(i)
        }

        binding.enter.setOnClickListener {
            if(checkData()) {
                signIn(binding.login.getStringText(), binding.password.getStringText())
            }
        }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful) {
                    Toast.makeText(this, "Добро пожаловать!", Toast.LENGTH_SHORT).show()
                    val i = Intent(this, MainActivity::class.java)
                    startActivity(i)
                    finish()
                } else {
                    Toast.makeText(this, "Попробуйте еще раз", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun checkData(): Boolean {
        if(isAnyFieldEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            return false
        } else {
            return true
        }
    }

    private fun isAnyFieldEmpty(): Boolean {
        return binding.login.text.isEmpty() || binding.password.text.isEmpty()
    }
}