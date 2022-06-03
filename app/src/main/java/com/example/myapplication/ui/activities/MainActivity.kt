package com.example.myapplication.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.firebase.*
import com.example.myapplication.models.User
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MovieDatabase_NoActionBar)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFirebase()
        currentUser = AUTH.currentUser

        if (currentUser == null) {
            startActivity(Intent(this, SingInActivity::class.java))
            finish()
        }
        initUser()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.navigationBar.setupWithNavController(navController)
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
        const val MEDIA_ID = "mediaId"
        const val SEASON = "seasonId"
        const val EPISODE = "episodeId"

        fun setProfileImage(
            uri: String,
            onSuccess: (url: String) -> Unit,
            onFaluire: () -> Unit
        ) {
            val url = if (uri.contains("https://")) uri else "https://$uri"
            REF_DATABASE_ROOT.child("users").child(UID).child("profileUrl").setValue(url)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        onSuccess(url)
                        Log.d("PROFILE_IMAGE", "$url")
                    } else {
                        onFaluire()
                        Log.e("PROFILE_IMAGE", "${it.exception}")
                    }
                }
        }
    }
}