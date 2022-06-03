package com.example.myapplication.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySingUpBinding
import com.example.myapplication.firebase.AUTH
import com.example.myapplication.firebase.REF_DATABASE_ROOT
import com.example.myapplication.firebase.initFirebase
import com.example.myapplication.models.User
import com.example.myapplication.states.IntAuthState
import com.example.myapplication.states.StringAuthState
import com.example.myapplication.utils.getStringText
import com.example.myapplication.utils.hideAnimated
import com.example.myapplication.utils.showAnimated
import com.example.myapplication.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class SingUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingUpBinding
    private lateinit var sessionKey: String
    private var accountId by Delegates.notNull<Int>()
    private var requestToken: String = ""

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MovieDatabase_NoActionBar)
        binding = ActivitySingUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFirebase()

        viewModel.requestToken.observe(this) { state ->
            when (state) {
                StringAuthState.Error -> {
                    binding.ratingProgress.hideAnimated()
                    Toast.makeText(this,
                        "Что-то пошло не так, попробуйте снова!",
                        Toast.LENGTH_SHORT).show()
                }
                StringAuthState.Loading -> binding.ratingProgress.showAnimated()
                is StringAuthState.Success -> {
                    binding.ratingProgress.hideAnimated()
                    requestToken = state.data
                    binding.proof.visibility = View.GONE
                    binding.signUp.visibility = View.VISIBLE
                    val intent = Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.themoviedb.org/authenticate/$requestToken"))
                    startActivity(intent)
                }
            }
        }

        viewModel.sessionId.observe(this) { state ->
            when (state) {
                StringAuthState.Error -> {
                    binding.ratingProgress.hideAnimated()
                    Toast.makeText(this,
                        "Что-то пошло не так, попробуйте снова!",
                        Toast.LENGTH_SHORT).show()
                }
                StringAuthState.Loading -> binding.ratingProgress.showAnimated()
                is StringAuthState.Success -> {
                    sessionKey = state.data
                    viewModel.getAccountInfo(sessionKey)
                }
            }
        }

        viewModel.accountId.observe(this) { state ->
            when (state) {
                IntAuthState.Error -> {
                    binding.ratingProgress.hideAnimated()
                    Toast.makeText(this,
                        "Что-то пошло не так, попробуйте снова!",
                        Toast.LENGTH_SHORT).show()
                }
                is IntAuthState.Success -> {
                    accountId = state.data
                    if (checkData()) {
                        createAccount(binding.name.getStringText(),
                            binding.surname.getStringText(),
                            binding.email.getStringText(),
                            binding.pass.getStringText(),
                            sessionKey,
                            accountId)
                    }
                }
            }
        }

        binding.iAlredyHaveAnAccount.setOnClickListener {
            val i = Intent(this, SingInActivity::class.java)
            startActivity(i)
        }

        binding.proof.setOnClickListener {
            viewModel.getRequestToken()
        }

        binding.signUp.setOnClickListener {
            viewModel.getSessionId(requestToken)
        }
    }

    private fun createAccount(
        name: String,
        surname: String,
        email: String,
        password: String,
        sessionKey: String,
        tmdbAccountId: Int,
    ) {
        AUTH.createUserWithEmailAndPassword(email, password)
            .addOnCanceledListener {
                Toast.makeText(this, "Отмена операции создания пользователя", Toast.LENGTH_SHORT)
                    .show()
            }
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    changeData(name, surname, email, password, sessionKey, tmdbAccountId)
                } else {
                    binding.ratingProgress.hideAnimated()
                    Log.d("AUTH", "${task.exception}")
                    Toast.makeText(this,
                        "Не получилось зарегистрироваться (создание пользователя): ${task.exception}",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun changeData(
        name: String,
        surname: String,
        email: String,
        password: String,
        sessionKey: String,
        tmdbAccountId: Int,
    ) {
        val uid = AUTH.currentUser?.uid.toString()

        val user = User(id = uid,
            username = "$name $surname",
            email = email,
            password = password,
            sessionKey = sessionKey,
            tmdbAccountId = tmdbAccountId)

        REF_DATABASE_ROOT.child("users").child(uid).setValue(user)
            .addOnCanceledListener {
                Toast.makeText(this,
                    "Отмена операции изменения данных пользователя",
                    Toast.LENGTH_SHORT).show()
            }
            .addOnCompleteListener { userTask ->
                binding.ratingProgress.hideAnimated()
                if (userTask.isSuccessful) {
                    Toast.makeText(this,
                        "Регистрация прошла успешно!",
                        Toast.LENGTH_SHORT).show()
                    val i = Intent(this, MainActivity::class.java)
                    startActivity(i)
                    finish()
                } else {
                    Log.d("AUTH", "${userTask.exception}")
                    Toast.makeText(this,
                        "Не получилось зарегистрироваться (изменение данных): ${userTask.exception}",
                        Toast.LENGTH_SHORT).show()
                    binding.signUpFirebase.visibility = View.VISIBLE
                    binding.signUpFirebase.setOnClickListener {
                        changeData(name,
                            surname,
                            email,
                            password,
                            sessionKey,
                            tmdbAccountId)
                    }
                    binding.signUp.visibility = View.GONE
                }
            }
    }

    private fun checkData(): Boolean {
        if (isAnyFieldEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            return false
        } else if (isNotPasswordsEquals()) {
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