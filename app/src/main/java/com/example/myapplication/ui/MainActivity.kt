package com.example.myapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var reference: DatabaseReference
    private lateinit var user: User
    private var currentUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        reference = Firebase.database.reference
        currentUser = auth.currentUser

        if (currentUser == null) {
            startActivity(Intent(this, SingInActivity::class.java))
        } else {
            if (savedInstanceState == null) {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    add<MainFragment>(R.id.fragment_container_view)
                }
            }
        }

        binding.navigationBar.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.main -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<MainFragment>(R.id.fragment_container_view)
                        addToBackStack("first")
                    }
                    true
                }
                R.id.favorite -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<FavoriteFragment>(R.id.fragment_container_view)
                        addToBackStack("first")
                    }
                    true
                }
                R.id.history -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<HistoryFragment>(R.id.fragment_container_view)
                        addToBackStack("first")
                    }
                    true
                }
                R.id.account -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<AccountFragment>(R.id.fragment_container_view)
                        addToBackStack("first")
                    }
                    true
                }
                else -> false
            }
        }
    }

    override fun onStart() {
        super.onStart()
        initUser()
    }

    private fun initUser() {
        reference.child("users").child(currentUser?.uid.toString())
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        user = snapshot.getValue(User::class.java) ?: User()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.d("INITUSER", "initialization is failed")
                    }
                }
            )
    }
}