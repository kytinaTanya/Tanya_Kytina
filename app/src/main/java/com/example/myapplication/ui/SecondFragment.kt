package com.example.myapplication.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.BuildConfig
import com.example.myapplication.adapters.MovieRecyclerAdapter
import com.example.myapplication.databinding.FragmentSecondBinding
import com.example.myapplication.movies.Movie
import com.example.myapplication.movies.TmdbService
import com.example.myapplication.repository.RepositoryImpl


class SecondFragment : Fragment() {
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    lateinit var mAdapter: MovieRecyclerAdapter
    lateinit var repository: RepositoryImpl

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter = MovieRecyclerAdapter()
        repository = RepositoryImpl(TmdbService.createApiService())

        binding.movieList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
        }

        getTopRatedMovies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getTopRatedMovies() {
        // вот здесь нужно обращаться к слою model в котором реализован паттерн Repository
        repository.getData(::onTopRatedMoviesFetched, ::onError)
    }

    private fun onError() {
        Log.d("GetError", "Что-то пошло не так")
    }

    private fun onTopRatedMoviesFetched(list: List<Movie>) {
        mAdapter.appendMovies(list)
        loggingImages(list)
    }

    private fun loggingImages(list: List<Movie>) {
        val task = Thread {
            list.forEach {
                Log.d("Image", BuildConfig.BASE_IMAGE_URL + it.posterPath)
            }
        }
        task.start()
        task.join()
    }
}