package com.example.myapplication.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.myapplication.BuildConfig
import com.example.myapplication.databinding.FragmentMovieBinding
import com.example.myapplication.models.movies.Film
import com.example.myapplication.models.movies.Person
import com.example.myapplication.models.movies.TV
import com.example.myapplication.utils.setImage
import com.example.myapplication.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment() {
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.movieDetails.observe(this) { movie ->
            when(movie) {
                is Film -> {
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