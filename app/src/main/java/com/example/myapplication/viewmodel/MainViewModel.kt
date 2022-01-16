package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.pojo.Film
import com.example.myapplication.models.pojo.Person
import com.example.myapplication.models.pojo.TV
import com.example.myapplication.repository.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: Repository
    ) : ViewModel() {

    private val tag: String = MainViewModel::class.java.simpleName

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

    fun loadMoviesInTrend() {
        viewModelScope.launch {
            _moviesInTrend.value = mainRepository.getListOfPopularMovies()
        }
    }

    fun loadNowPlayingMovies() {
        viewModelScope.launch {
            _moviesNowPlaying.value = mainRepository.getListOfNowPlayingMovies()
        }
    }

    fun loadUpcomingMovies() {
        viewModelScope.launch {
            _moviesUpcoming.value = mainRepository.getListOfUpcomingMovies()
        }
    }

    fun loadTopRatedMovies() {
        viewModelScope.launch {
            _moviesTopRated.value = mainRepository.getListOfTopRatedMovies()
        }
    }

    fun loadPopularTV() {
        viewModelScope.launch {
            _popularTV.value = mainRepository.getListOfPopularTv()
        }
    }

    fun loadOnAirTodayTV() {
        viewModelScope.launch {
            _onAirTodayTV.value = mainRepository.getListOfOnAirTodayTV()
        }
    }

    fun loadNowOnAirTV() {
        viewModelScope.launch {
            _nowOnAirTV.value = mainRepository.getListOfNowOnAirTV()
        }
    }

    fun loadTopRatedTV() {
        viewModelScope.launch {
            _topRatedTV.value = mainRepository.getListOfTopRatedTV()
        }
    }

    fun loadPopularPersons() {
        viewModelScope.launch {
            _popularPersons.value = mainRepository.getListOfPopularPersons()
        }
    }
}

enum class MainScreenRequest {
    TOP_RATED_MOVIES,
    POPULAR_MOVIES,
    NOW_PLAYING_MOVIES,
    UPCOMING_MOVIES,
    TOP_RATED_TVS,
    AIRING_TODAY_TVS,
    ON_THE_AIR_TVS,
    POPULAR_TVS,
    POPULAR_PERSONS
}