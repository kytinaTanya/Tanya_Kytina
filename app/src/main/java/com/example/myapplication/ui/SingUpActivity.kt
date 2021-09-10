package com.example.myapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySingUpBinding
import com.example.myapplication.utils.getStringText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.lang.ref.PhantomReference

class SingUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var reference: DatabaseReference
    private lateinit var sessionKey: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        reference = Firebase.database.reference
        sessionKey = "kjfkjfkjfkjkjfkjf"

        binding.exit.setOnClickListener {
            val i = Intent(this, SingInActivity::class.java)
            startActivity(i)
        }

        binding.enter.setOnClickListener {
            if(checkData()) {
                createAccount(binding.name.getStringText(), binding.surname.getStringText(),
                    binding.email.getStringText(), binding.pass.getStringText(), sessionKey)
            }
        }
    }

    private fun createAccount(name: String,
                              surname: String,
                              email: String,
                              password: String,
                              sessionKey: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful) {
                    val uid = auth.currentUser?.uid.toString()
                    val dataMap = mutableMapOf<String, Any>()

                    dataMap["id"] = uid
                    dataMap["username"] = "$name $surname"
                    dataMap["email"] = email
                    dataMap["password"] = password
                    dataMap["sessionKey"] = sessionKey

                    reference.child("users").child(uid).updateChildren(dataMap)
                        .addOnCompleteListener { userTask ->
                            if(userTask.isSuccessful) {
                                Toast.makeText(this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show()
                                val i = Intent(this, MainActivity::class.java)
                                startActivity(i)
                            } else {
                                Toast.makeText(this, "Не получилось зарегистрироваться", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(this, "Не получилось зарегистрироваться", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun checkData(): Boolean {
        if(isAnyFieldEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            return false
        } else if (isNotPasswordsEquals()){
            Toast.makeText(this, "Введите пароль повторно", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun isNotPasswordsEquals(): Boolean {
        return binding.pass.getStringText() != binding.repass.getStringText()
    }

    private fun isAnyFieldEmpty(): Boolean {
        return binding.name.text.isEmpty() || binding.surname.text.isEmpty() ||
                binding.email.text.isEmpty() || binding.pass.text.isEmpty() ||
                binding.repass.text.isEmpty()
    }
}