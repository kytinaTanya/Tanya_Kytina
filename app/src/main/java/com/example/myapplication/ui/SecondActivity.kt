package com.example.myapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapters.MovieRecyclerAdapter
import com.example.myapplication.databinding.ActivitySecondBinding
import com.example.myapplication.movies.Movie
import com.example.myapplication.movies.MoviesData

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private val mAdapter = MovieRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.movieList.layoutManager = LinearLayoutManager(this)
        binding.movieList.adapter = mAdapter

        getTopRatedMovies()
    }

    private fun getTopRatedMovies() {
        MoviesData.getTopRatedMovies(
            1,
            "ru-RU",
            ::onTopRatedMoviesFetched,
            ::onError
        )
    }

    private fun onError() {
        Log.d("GetError", "Что-то пошло не так")
    }

    private fun onTopRatedMoviesFetched(list: List<Movie>) {
        mAdapter.appendMovies(list)
    }
}