package com.example.myapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.example.myapplication.BuildConfig
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityItemInfoBinding
import com.example.myapplication.models.movies.Film
import com.example.myapplication.models.movies.Person
import com.example.myapplication.models.movies.TV
import com.example.myapplication.ui.MainActivity.Companion.ITEM_TYPE
import com.example.myapplication.ui.MainActivity.Companion.MEDIA_ID
import com.example.myapplication.utils.setImage
import com.example.myapplication.viewmodel.ItemInfoViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemInfoBinding
    private val viewModel: ItemInfoViewModel by viewModels()
    private var film: Film? = null
    private var listId = MainActivity.USER.historyListID
    private lateinit var auth: FirebaseAuth
    private lateinit var reference: DatabaseReference
    private var currentUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        reference = Firebase.database.reference
        currentUser = auth.currentUser

        val id: Long = (intent.extras?.get(MEDIA_ID) ?: 1L) as Long
        when(intent.extras?.get(ITEM_TYPE)) {
            1 -> viewModel.loadFilmDetails(id)
            2 -> viewModel.loadTVDetails(id)
            3 -> viewModel.loadPersonDetails(id)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.movieDetails.observe(this) { movie ->
            when(movie) {
                is Film -> {
                    film = movie
                    addToHistory()
                    binding.movieImage.setImage(BuildConfig.BASE_IMAGE_URL + movie.posterPath)
                    binding.movieTitle.text = movie.title
                    binding.yearOfMovie.text = movie.releaseYear()
                    binding.movieRating.text = movie.rating.toString()
                    binding.movieAnnotation.text = movie.overview
                }
                is TV -> {
                    binding.movieImage.setImage(BuildConfig.BASE_IMAGE_URL + movie.posterPath)
                    binding.movieTitle.text = movie.name
                    binding.movieAnnotation.text = movie.overview
                    binding.movieRating.text = movie.rating.toString()
                }
                is Person -> {
                    binding.movieImage.setImage(BuildConfig.BASE_IMAGE_URL + movie.profilePath)
                    binding.movieTitle.text = movie.name
                    binding.movieAnnotation.text = movie.biography
                    binding.movieRating.text = movie.popularity.toString()
                }
            }
        }
    }

    private fun addToHistory() {
        if(film != null) {
            if(listId == 0) {
                viewModel.createList(MainActivity.USER.sessionKey)

                viewModel.listId.observe(this) { id ->
                    listId = id
                    reference.child("users").child(currentUser?.uid.toString()).child("historyListID").setValue(listId).addOnCompleteListener {
                        if(it.isSuccessful) {
                            Toast.makeText(this, "Создание списка истории прошла прошло успешно", Toast.LENGTH_SHORT).show()
                        } else {
                            Log.d("AUTH", "${it.exception}")
                            Toast.makeText(this, "Не получилось: ${it.exception}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            //viewModel.removeMovie(USER.historyListID, USER.sessionKey, film!!.id)
            viewModel.addMovie(MainActivity.USER.historyListID, MainActivity.USER.sessionKey, film!!.id)
        }
    }
}