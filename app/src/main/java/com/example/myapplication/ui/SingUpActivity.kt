package com.example.myapplication.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySingUpBinding
import com.example.myapplication.utils.getStringText
import com.example.myapplication.viewmodel.AuthViewModel
import com.example.myapplication.viewmodel.MovieViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.PhantomReference
import javax.inject.Inject

@AndroidEntryPoint
class SingUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var reference: DatabaseReference
    private lateinit var sessionKey: String
    private var requestToken: String = ""

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        reference = Firebase.database.reference

        viewModel.getRequestToken()
        viewModel.requestToken.observe(this) {
            requestToken = it
        }

        viewModel.sessionId.observe(this) {
            sessionKey = it
        }

        binding.exit.setOnClickListener {
            val i = Intent(this, SingInActivity::class.java)
            startActivity(i)
        }

        binding.proof.setOnClickListener {
            binding.proof.visibility = View.GONE
            binding.enter.visibility = View.VISIBLE
            val intent = Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.themoviedb.org/authenticate/$requestToken"))
            startActivity(intent)
        }

        binding.enter.setOnClickListener {
            if(checkData()) {

                createAccount(binding.name.getStringText(), binding.surname.getStringText(),
                    binding.email.getStringText(), binding.pass.getStringText(), sessionKey)
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        viewModel.getSessionId(requestToken)
    }

    private fun createAccount(name: String,
                              surname: String,
                              email: String,
                              password: String,
                              sessionKey: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCanceledListener {
                Toast.makeText(this, "Отмена операции1", Toast.LENGTH_SHORT).show()
            }
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful) {
                    val uid = auth.currentUser?.uid.toString()
                    val dataMap = mutableMapOf<String, Any>()

                    dataMap["id"] = uid
                    dataMap["username"] = "$name $surname"
                    dataMap["email"] = email
                    dataMap["password"] = password
                    dataMap["sessionKey"] = sessionKey
                    dataMap["accessToken"] = ""
                    dataMap["historyListID"] = 0

                    reference.child("users").child(uid).updateChildren(dataMap)
                        .addOnCanceledListener {
                            Toast.makeText(this, "Отмена операции2", Toast.LENGTH_SHORT).show()
                        }
                        .addOnCompleteListener { userTask ->
                            if(userTask.isSuccessful) {
                                Toast.makeText(this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show()
                                val i = Intent(this, MainActivity::class.java)
                                startActivity(i)
                                finish()
                            } else {
                                Log.d("AUTH", "${userTask.exception}")
                                Toast.makeText(this, "Не получилось зарегистрироваться: ${userTask.exception}", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Log.d("AUTH", "${task.exception}")
                    Toast.makeText(this, "Не получилось зарегистрироваться: ${task.exception}", Toast.LENGTH_SHORT).show()
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