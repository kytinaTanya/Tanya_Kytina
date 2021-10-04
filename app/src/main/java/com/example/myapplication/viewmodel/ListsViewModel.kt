package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.lists.*
import com.example.myapplication.repository.ListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import hilt_aggregated_deps._com_example_myapplication_di_RepositoryModule
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListsViewModel @Inject constructor(private val mRepository: ListRepository): ViewModel() {

    private var _allLists: MutableLiveData<List<MovieList>> = MutableLiveData()
    val allLists: LiveData<List<MovieList>>
    get() = _allLists

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

    fun loadLists(sessionId: String) {
        viewModelScope.launch {
            _allLists.value = mRepository.getAllLists(sessionId)
        }
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
}