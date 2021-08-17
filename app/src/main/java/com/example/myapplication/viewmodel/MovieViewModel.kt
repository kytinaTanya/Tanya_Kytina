package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.movies.Movie
import com.example.myapplication.repository.Repository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieViewModel @Inject constructor(private val mRepository: Repository) : ViewModel() {

    private val tag: String = MovieViewModel::class.java.simpleName

    private var _movies: MutableLiveData<List<Movie>> = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() {
            return _movies
        }

    private var _movieDetails: MutableLiveData<Movie> = MutableLiveData<Movie>()
    val movieDetails: LiveData<Movie>
        get() {
            return _movieDetails
        }

    fun loadMovies() {
        viewModelScope.launch {
            _movies.value = mRepository.getListOfMovies()
        }
    }

    fun getMovieDetails(id: Long) {
        viewModelScope.launch {
            _movieDetails.value = mRepository.getMovieDetails(id)
        }
    }
}