package com.example.myapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.myapplication.BuildConfig
import com.example.myapplication.databinding.FragmentMovieBinding
import com.example.myapplication.models.Film
import com.example.myapplication.models.Movie
import com.example.myapplication.utils.setImage
import com.example.myapplication.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment() {
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.movieDetails.observe(this, Observer<Film>{ film ->
            binding.movieImage.setImage(BuildConfig.BASE_IMAGE_URL + film.posterPath)
            binding.movieTitle.text = film.title
            binding.yearOfMovie.text = film.releaseYear()
            binding.movieRating.text = film.rating.toString()
            binding.movieAnnotation.text = film.overview
        })
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