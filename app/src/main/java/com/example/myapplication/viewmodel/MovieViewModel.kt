package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.movies.Movie
import com.example.myapplication.movies.TmdbService
import com.example.myapplication.repository.Repository
import com.example.myapplication.repository.RepositoryImpl
import kotlinx.coroutines.launch

class MovieViewModel(
    private val mRepository: Repository = RepositoryImpl(TmdbService.createApiService()))
    : ViewModel() {

    private val tag: String = MovieViewModel::class.java.simpleName

    val movies: MutableLiveData<List<Movie>> by lazy {
        MutableLiveData<List<Movie>>().also {
            loadMovies()
        }
    }

    fun getMovies(): LiveData<List<Movie>>{
        return movies
    }

    private fun loadMovies() {
        viewModelScope.launch {
            mRepository.getData(::onTopRatedMoviesFetched, ::onError)
        }
    }

    private fun onTopRatedMoviesFetched(list: List<Movie>) {
        movies.value = list
    }

    private fun onError() {
        Log.d(tag, "Something don't work")
    }

}