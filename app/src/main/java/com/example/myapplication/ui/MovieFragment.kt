package com.example.myapplication.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.myapplication.BuildConfig
import com.example.myapplication.databinding.FragmentMovieBinding
import com.example.myapplication.models.movies.Film
import com.example.myapplication.models.movies.Person
import com.example.myapplication.models.movies.TV
import com.example.myapplication.ui.MainActivity.Companion.USER
import com.example.myapplication.utils.setImage
import com.example.myapplication.viewmodel.MovieViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment() {
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieViewModel by activityViewModels()
    private var film: Film? = null
    private var listId = USER.historyListID/*7110423*/
    private lateinit var auth: FirebaseAuth
    private lateinit var reference: DatabaseReference
    private var currentUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        reference = Firebase.database.reference
        currentUser = auth.currentUser



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
                viewModel.createList(USER.sessionKey)

                viewModel.listId.observe(this) { id ->
                    listId = id
                    reference.child("users").child(currentUser?.uid.toString()).child("historyListID").setValue(listId).addOnCompleteListener {
                        if(it.isSuccessful) {
                            Toast.makeText(requireContext(), "Создание списка истории прошла прошло успешно", Toast.LENGTH_SHORT).show()
                        } else {
                            Log.d("AUTH", "${it.exception}")
                            Toast.makeText(requireContext(), "Не получилось: ${it.exception}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            //viewModel.removeMovie(USER.historyListID, USER.sessionKey, film!!.id)
            viewModel.addMovie(USER.historyListID, USER.sessionKey, film!!.id)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}