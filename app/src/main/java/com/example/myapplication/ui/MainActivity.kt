package com.example.myapplication.ui

import android.R.attr
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.models.User
import com.example.myapplication.utils.replaceFragment
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
import androidx.core.app.ActivityCompat.startActivityForResult
import android.R.attr.data







@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var reference: DatabaseReference
    private var currentUser: FirebaseUser? = null
    private val mainFragment = MainFragment()
    private val favFragment = FavoriteFragment()
    private val hisFragment = HistoryFragment()
    private val accFragment = AccountFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        reference = Firebase.database.reference
        currentUser = auth.currentUser

        if (currentUser == null) {
            startActivity(Intent(this, SingInActivity::class.java))
            finish()
        } else {
            if (savedInstanceState == null) {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    add(R.id.fragment_container_view, mainFragment)
                }
            }
        }

        binding.navigationBar.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.main -> {
                    replaceFragment(mainFragment, "main")
                    true
                }
                R.id.favorite -> {
                    replaceFragment(favFragment, "favorite")
                    true
                }
                R.id.history -> {
                    replaceFragment(hisFragment, "history")
                    true
                }
                R.id.account -> {
                    replaceFragment(accFragment, "account")
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
                        USER = snapshot.getValue(User::class.java) ?: User()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.d("INITUSER", "initialization is failed")
                    }
                }
            )
    }

    companion object {
        var USER = User()
        const val MEDIA_ID = "media_id"
        const val ITEM_TYPE = "item_type"
        const val SEASON = "season_number"
        const val EPISODE = "episode_number"
        const val MOVIE_TYPE = 1
        const val TV_TYPE = 2
        const val PERSON_TYPE = 3
        const val EPISODE_TYPE = 4
        const val GALLERY_REQUEST = 1

        fun openMovie(id: Long, itemType: Int, context: Context) {
            val intent = Intent(context, ItemInfoActivity::class.java).apply {
                putExtra(MEDIA_ID, id)
                putExtra(ITEM_TYPE, itemType)
            }
            context.startActivity(intent)
        }

        fun openMovie(tvId: Long, season: Int, episode: Int, context: Context) {
            val intent = Intent(context, ItemInfoActivity::class.java).apply {
                putExtra(MEDIA_ID, tvId)
                putExtra(ITEM_TYPE, EPISODE_TYPE)
                putExtra(SEASON, season)
                putExtra(EPISODE, episode)
            }
            context.startActivity(intent)
        }
    }
}