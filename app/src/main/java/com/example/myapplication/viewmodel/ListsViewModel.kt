package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.lists.*
import com.example.myapplication.models.movies.Movie
import com.example.myapplication.repository.DetailsRepository
import com.example.myapplication.repository.ListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import hilt_aggregated_deps._com_example_myapplication_di_RepositoryModule
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListsViewModel @Inject constructor(
    private val mRepository: ListRepository,
    private val detailsRepository: DetailsRepository
): ViewModel() {

    val tag: String = ListsViewModel::class.java.simpleName

    private var _favoriteMoviesList: MutableLiveData<FavouriteMovieList> = MutableLiveData()
    val favoriteMoviesList: LiveData<FavouriteMovieList>
        get() {
            return _favoriteMoviesList
        }

    private var _favoriteTVsList: MutableLiveData<FavouriteTVList> = MutableLiveData()
    val favoriteTVsList: LiveData<FavouriteTVList>
        get() {
            return _favoriteTVsList
        }

    private var _movieWatchlist: MutableLiveData<MovieWatchList> = MutableLiveData()
    val movieWatchlist: LiveData<MovieWatchList>
        get() {
            return _movieWatchlist
        }

    private var _tvWatchlist: MutableLiveData<TVWatchList> = MutableLiveData()
    val tvWatchlist: LiveData<TVWatchList>
        get() {
            return _tvWatchlist
        }

    private var _movieDetails: MutableLiveData<Movie> = MutableLiveData<Movie>()
    val movieDetails: LiveData<Movie>
        get() {
            return _movieDetails
        }

    fun loadFavoriteMoviesList(sessionId: String) {
        viewModelScope.launch {
            _favoriteMoviesList.value = mRepository.getFavoriteMoviesList(sessionId = sessionId)
        }
    }

    fun loadFavoriteTVsList(sessionId: String) {
        viewModelScope.launch {
            _favoriteTVsList.value = mRepository.getFavoriteTVsList(sessionId = sessionId)
        }
    }

    fun loadMovieWatchlist(sessionId: String) {
        viewModelScope.launch {
            _movieWatchlist.value = mRepository.getMovieWatchlist(sessionId = sessionId)
        }
    }

    fun loadTVWatchlist(sessionId: String) {
        viewModelScope.launch {
            _tvWatchlist.value = mRepository.getTVWatchlist(sessionId = sessionId)
        }
    }

    fun getMovieDetails(id: Long) {
        _movieDetails = MutableLiveData<Movie>()
        viewModelScope.launch {
            _movieDetails.value = detailsRepository.getMovieDetails(id)
            Log.d(tag, "${_movieDetails.value}")
        }
    }

    fun getTVDetails(id: Long) {
        _movieDetails = MutableLiveData<Movie>()
        viewModelScope.launch {
            _movieDetails.value = detailsRepository.getTVDetails(id)
            Log.d(tag, "${_movieDetails.value}")
        }
    }
}