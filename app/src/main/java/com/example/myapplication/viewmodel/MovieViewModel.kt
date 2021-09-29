package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.Film
import com.example.myapplication.models.Movie
import com.example.myapplication.models.TV
import com.example.myapplication.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val mRepository: Repository) : ViewModel() {

    private val tag: String = MovieViewModel::class.java.simpleName

    private var _moviesInTrend: MutableLiveData<List<Film>> = MutableLiveData<List<Film>>()
    val moviesInTrend: LiveData<List<Film>>
        get() {
            return _moviesInTrend
        }

    private var _moviesNew: MutableLiveData<List<Film>> = MutableLiveData<List<Film>>()
    val moviesNew: LiveData<List<Film>>
        get() {
            return _moviesNew
        }

    private var _moviesRecommend: MutableLiveData<List<Film>> = MutableLiveData<List<Film>>()
    val moviesRecommend: LiveData<List<Film>>
        get() {
            return _moviesRecommend
        }

    private var _popularTV: MutableLiveData<List<TV>> = MutableLiveData<List<TV>>()
    val popularTV: LiveData<List<TV>>
        get() {
            return _popularTV
        }

    private var _movieDetails: MutableLiveData<Film> = MutableLiveData<Film>()
    val movieDetails: LiveData<Film>
        get() {
            return _movieDetails
        }

    fun loadMoviesInTrend() {
        viewModelScope.launch {
            _moviesInTrend.value = mRepository.getListOfPopularMovies()
        }
    }

    fun loadNewMovies() {
        viewModelScope.launch {
            _moviesNew.value = mRepository.getListOfRecommendations()
        }
    }

    fun loadRecommendations() {
        viewModelScope.launch {
            _moviesRecommend.value = mRepository.getListOfLatestMovies()
        }
    }

    fun loadPopularTV() {
        viewModelScope.launch {
            _popularTV.value = mRepository.getPopularTv()
        }
    }

    fun getMovieDetails(id: Long) {
        _movieDetails = MutableLiveData<Film>()
        viewModelScope.launch {
            _movieDetails.value = mRepository.getMovieDetails(id)
            Log.d(tag, "${_movieDetails.value}")
        }
    }
}