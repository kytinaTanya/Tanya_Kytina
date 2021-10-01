package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.movies.Film
import com.example.myapplication.models.movies.Person
import com.example.myapplication.models.movies.PersonResponse
import com.example.myapplication.models.movies.TV
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

    private var _moviesNowPlaying: MutableLiveData<List<Film>> = MutableLiveData<List<Film>>()
    val moviesNowPlaying: LiveData<List<Film>>
        get() {
            return _moviesNowPlaying
        }

    private var _moviesUpcoming: MutableLiveData<List<Film>> = MutableLiveData<List<Film>>()
    val moviesUpcoming: LiveData<List<Film>>
        get() {
            return _moviesUpcoming
        }

    private var _moviesTopRated: MutableLiveData<List<Film>> = MutableLiveData<List<Film>>()
    val moviesTopRated: LiveData<List<Film>>
        get() {
            return _moviesTopRated
        }

    private var _popularTV: MutableLiveData<List<TV>> = MutableLiveData<List<TV>>()
    val popularTV: LiveData<List<TV>>
        get() {
            return _popularTV
        }

    private var _onAirTodayTV: MutableLiveData<List<TV>> = MutableLiveData<List<TV>>()
    val onAirTodayTV: LiveData<List<TV>>
        get() {
            return _onAirTodayTV
        }

    private var _nowOnAirTV: MutableLiveData<List<TV>> = MutableLiveData<List<TV>>()
    val nowOnAirTV: LiveData<List<TV>>
        get() {
            return _nowOnAirTV
        }

    private var _topRatedTV: MutableLiveData<List<TV>> = MutableLiveData<List<TV>>()
    val topRatedTV: LiveData<List<TV>>
        get() {
            return _topRatedTV
        }

    private var _popularPersons: MutableLiveData<List<Person>> = MutableLiveData()
    val popularPersons: LiveData<List<Person>>
        get() {
            return _popularPersons
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

    fun loadNowPlayingMovies() {
        viewModelScope.launch {
            _moviesNowPlaying.value = mRepository.getListOfNowPlayingMovies()
        }
    }

    fun loadUpcomingMovies() {
        viewModelScope.launch {
            _moviesUpcoming.value = mRepository.getListOfUpcomingMovies()
        }
    }

    fun loadTopRatedMovies() {
        viewModelScope.launch {
            _moviesTopRated.value = mRepository.getListOfTopRatedMovies()
        }
    }

    fun loadPopularTV() {
        viewModelScope.launch {
            _popularTV.value = mRepository.getListOfPopularTv()
        }
    }

    fun loadOnAirTodayTV() {
        viewModelScope.launch {
            _onAirTodayTV.value = mRepository.getListOfOnAirTodayTV()
        }
    }

    fun loadNowOnAirTV() {
        viewModelScope.launch {
            _nowOnAirTV.value = mRepository.getListOfNowOnAirTV()
        }
    }

    fun loadTopRatedTV() {
        viewModelScope.launch {
            _topRatedTV.value = mRepository.getListOfTopRatedTV()
        }
    }

    fun loadPopularPersons() {
        viewModelScope.launch {
            _popularPersons.value = mRepository.getListOfPopularPersons()
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