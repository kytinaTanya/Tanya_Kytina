package com.example.myapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.BuildConfig
import com.example.myapplication.appl.MyApplicationClass
import com.example.myapplication.databinding.FragmentMovieBinding
import com.example.myapplication.room.entity.Movie
import com.example.myapplication.utils.setImage
import com.example.myapplication.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieFragment : Fragment() {
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.movieDetails.observe(this, Observer<Movie>{
            binding.movieImage.setImage(BuildConfig.BASE_IMAGE_URL + it.posterPath)
            binding.movieTitle.text = it.title
            binding.yearOfMovie.text = it.releaseYear()
            binding.movieRating.text = it.rating.toString()
            binding.movieAnnotation.text = it.overview
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