package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.repositories.Repository
import com.example.myapplication.states.MainViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: Repository
    ) : ViewModel() {

    private val tag: String = MainViewModel::class.java.simpleName

    private var _moviesInTrend: MutableLiveData<MainViewState> = MutableLiveData()
    val moviesInTrend: LiveData<MainViewState>
        get() {
            return _moviesInTrend
        }

    private var _moviesNowPlaying: MutableLiveData<MainViewState> = MutableLiveData()
    val moviesNowPlaying: LiveData<MainViewState>
        get() {
            return _moviesNowPlaying
        }

    private var _moviesUpcoming: MutableLiveData<MainViewState> = MutableLiveData()
    val moviesUpcoming: LiveData<MainViewState>
        get() {
            return _moviesUpcoming
        }

    private var _moviesTopRated: MutableLiveData<MainViewState> = MutableLiveData()
    val moviesTopRated: LiveData<MainViewState>
        get() {
            return _moviesTopRated
        }

    private var _popularTV: MutableLiveData<MainViewState> = MutableLiveData()
    val popularTV: LiveData<MainViewState>
        get() {
            return _popularTV
        }

    private var _onAirTodayTV: MutableLiveData<MainViewState> = MutableLiveData()
    val onAirTodayTV: LiveData<MainViewState>
        get() {
            return _onAirTodayTV
        }

    private var _nowOnAirTV: MutableLiveData<MainViewState> = MutableLiveData()
    val nowOnAirTV: LiveData<MainViewState>
        get() {
            return _nowOnAirTV
        }

    private var _topRatedTV: MutableLiveData<MainViewState> = MutableLiveData()
    val topRatedTV: LiveData<MainViewState>
        get() {
            return _topRatedTV
        }

    private var _popularPersons: MutableLiveData<MainViewState> = MutableLiveData()
    val popularPersons: LiveData<MainViewState>
        get() {
            return _popularPersons
        }

    fun loadMoviesInTrend() {
        _moviesInTrend.value = MainViewState.Loading
        viewModelScope.launch {
            _moviesInTrend.value = when (val result = mainRepository.getListOfPopularMovies()) {
                is Repository.Result.Success.FilmSuccess ->
                    MainViewState.Success.FilmSuccess(result.list)
                else -> MainViewState.Error
            }
        }
    }

    fun loadNowPlayingMovies() {
        _moviesNowPlaying.value = MainViewState.Loading
        viewModelScope.launch {
            _moviesNowPlaying.value = when (val result = mainRepository.getListOfNowPlayingMovies()) {
                is Repository.Result.Success.FilmSuccess ->
                    MainViewState.Success.FilmSuccess(result.list)
                else -> MainViewState.Error
            }
        }
    }

    fun loadUpcomingMovies() {
        _moviesUpcoming.value = MainViewState.Loading
        viewModelScope.launch {
            _moviesUpcoming.value = when (val result = mainRepository.getListOfUpcomingMovies()) {
                is Repository.Result.Success.FilmSuccess ->
                    MainViewState.Success.FilmSuccess(result.list)
                else -> MainViewState.Error
            }
        }
    }

    fun loadTopRatedMovies() {
        _moviesTopRated.value = MainViewState.Loading
        viewModelScope.launch {
            _moviesTopRated.value = when (val result = mainRepository.getListOfTopRatedMovies()) {
                is Repository.Result.Success.FilmSuccess ->
                    MainViewState.Success.FilmSuccess(result.list)
                else -> MainViewState.Error
            }
        }
    }

    fun loadPopularTV() {
        _popularTV.value = MainViewState.Loading
        viewModelScope.launch {
            _popularTV.value = when (val result = mainRepository.getListOfPopularTv()) {
                is Repository.Result.Success.TvSuccess ->
                    MainViewState.Success.TvSuccess(result.list)
                else -> MainViewState.Error
            }
        }
    }

    fun loadOnAirTodayTV() {
        _onAirTodayTV.value = MainViewState.Loading
        viewModelScope.launch {
            _onAirTodayTV.value = when (val result = mainRepository.getListOfOnAirTodayTV()) {
                is Repository.Result.Success.TvSuccess ->
                    MainViewState.Success.TvSuccess(result.list)
                else -> MainViewState.Error
            }
        }
    }

    fun loadNowOnAirTV() {
        _nowOnAirTV.value = MainViewState.Loading
        viewModelScope.launch {
            _nowOnAirTV.value = when (val result = mainRepository.getListOfNowOnAirTV()) {
                is Repository.Result.Success.TvSuccess ->
                    MainViewState.Success.TvSuccess(result.list)
                else -> MainViewState.Error
            }
        }
    }

    fun loadTopRatedTV() {
        _topRatedTV.value = MainViewState.Loading
        viewModelScope.launch {
            _topRatedTV.value = when (val result = mainRepository.getListOfTopRatedTV()) {
                is Repository.Result.Success.TvSuccess ->
                    MainViewState.Success.TvSuccess(result.list)
                else -> MainViewState.Error
            }
        }
    }

    fun loadPopularPersons() {
        _popularPersons.value = MainViewState.Loading
        viewModelScope.launch {
            _popularPersons.value = when (val result = mainRepository.getListOfPopularPersons()) {
                is Repository.Result.Success.PersonSuccess ->
                    MainViewState.Success.PersonSuccess(result.list)
                else -> MainViewState.Error
            }
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