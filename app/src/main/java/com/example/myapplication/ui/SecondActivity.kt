package com.example.myapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapters.MovieRecyclerAdapter
import com.example.myapplication.databinding.ActivitySecondBinding
import com.example.myapplication.movies.Movie
import com.example.myapplication.movies.TmdbServise
import com.example.myapplication.repository.RepositoryImpl

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    lateinit var mAdapter: MovieRecyclerAdapter
    lateinit var repository: RepositoryImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAdapter = MovieRecyclerAdapter()
        repository = RepositoryImpl(TmdbServise.createApiService())

        binding.movieList.apply {
            layoutManager = LinearLayoutManager(this@SecondActivity)
            adapter = mAdapter
        }

        getTopRatedMovies()
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
    }
}