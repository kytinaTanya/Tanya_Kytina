package com.example.myapplication.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.commit
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.models.User
import com.example.myapplication.utils.replaceFragment
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import com.example.myapplication.firebase.*
import com.example.myapplication.ui.fragments.AccountFragment
import com.example.myapplication.ui.fragments.FavoriteFragment
import com.example.myapplication.ui.fragments.HistoryFragment
import com.example.myapplication.ui.fragments.MainFragment


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentUser: FirebaseUser? = null
    private val mainFragment = MainFragment()
    private val favFragment = FavoriteFragment()
    private val hisFragment = HistoryFragment()
    private val accFragment = AccountFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFirebase()
        currentUser = AUTH.currentUser

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
        REF_DATABASE_ROOT.child("users").child(UID)
            .addValueEventListener(
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
        const val MEDIA_ID = "media_id"
        const val ITEM_TYPE = "item_type"
        const val SEASON = "season_number"
        const val EPISODE = "episode_number"
        const val MOVIE_TYPE = 1
        const val TV_TYPE = 2
        const val PERSON_TYPE = 3
        const val SEASON_TYPE = 4
        const val EPISODE_TYPE = 5

        fun openMovie(id: Long, itemType: Int, context: Context) {
            val intent = Intent(context, ItemInfoActivity::class.java).apply {
                putExtra(MEDIA_ID, id)
                putExtra(ITEM_TYPE, itemType)
            }
            context.startActivity(intent)
        }

        fun openMovie(tvId: Long, season: Int, itemType: Int, context: Context) {
            val intent = Intent(context, ItemInfoActivity::class.java).apply {
                putExtra(MEDIA_ID, tvId)
                putExtra(ITEM_TYPE, itemType)
                putExtra(SEASON, season)
            }
            context.startActivity(intent)
        }

        fun openMovie(tvId: Long, season: Int, episode: Int, itemType: Int, context: Context) {
            val intent = Intent(context, ItemInfoActivity::class.java).apply {
                putExtra(MEDIA_ID, tvId)
                putExtra(ITEM_TYPE, itemType)
                putExtra(SEASON, season)
                putExtra(EPISODE, episode)
            }
            context.startActivity(intent)
        }

        fun setProfileImage(url: String) {
            REF_DATABASE_ROOT.child("users").child(UID).child("profileUrl").setValue("https://$url").addOnCompleteListener {
                if(it.isSuccessful) {
                    Log.d("PROFILE_IMAGE", "Successful")
                } else {
                    Log.d("PROFILE_IMAGE", "${it.exception}")
                }
            }
        }
    }
}