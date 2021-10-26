package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.history.PostResponseStatus
import com.example.myapplication.models.marks.AccountStates
import com.example.myapplication.models.movies.Movie
import com.example.myapplication.repository.repositories.DetailsRepository
import com.example.myapplication.repository.repositories.HistoryRepository
import com.example.myapplication.ui.activities.ItemInfoActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemInfoViewModel @Inject constructor(
    private val detailsRepository: DetailsRepository,
    private val historyRepository: HistoryRepository
) : ViewModel() {

    private val tag = ItemInfoActivity::class.java.simpleName

    private var _movieDetails: MutableLiveData<Movie> = MutableLiveData()
    val movieDetails: LiveData<Movie>
    get() = _movieDetails

    private var _listId: MutableLiveData<Int> = MutableLiveData()
    val listId: LiveData<Int>
        get() = _listId

    private var _movieStates: MutableLiveData<AccountStates> = MutableLiveData()
    val movieStates: LiveData<AccountStates>
        get() = _movieStates

    private var _addToWatchlistState: MutableLiveData<PostResponseStatus> = MutableLiveData()
    val addToWatchlistState: LiveData<PostResponseStatus>
        get() = _addToWatchlistState

    private var _markAsFavState: MutableLiveData<PostResponseStatus> = MutableLiveData()
    val markAsFavState: LiveData<PostResponseStatus>
        get() = _markAsFavState

    private var _ratedState: MutableLiveData<PostResponseStatus> = MutableLiveData()
    val ratedState: LiveData<PostResponseStatus>
        get() = _ratedState

    fun loadFilmDetails(id: Long) {
        _movieDetails = MutableLiveData()
        viewModelScope.launch {
            _movieDetails.value = detailsRepository.getMovieDetails(id)
        }
    }

    fun loadTVDetails(id: Long) {
        _movieDetails = MutableLiveData()
        viewModelScope.launch {
            _movieDetails.value = detailsRepository.getTVDetails(id)
        }
    }

    fun loadPersonDetails(id: Long) {
        _movieDetails = MutableLiveData()
        viewModelScope.launch {
            _movieDetails.value = detailsRepository.getPersonDetails(id)
        }
    }

    fun loadEpisodeDetails(showId: Long, season: Int, episode: Int) {
        _movieDetails = MutableLiveData()
        viewModelScope.launch {
            _movieDetails.value = detailsRepository.getEpisodeDetails(showId, season, episode)
        }
    }

    fun createList(sessionId: String) {
        viewModelScope.launch {
            _listId.value = historyRepository.createList(sessionId)
            Log.d(tag, "${_listId.value}")
        }
    }

    fun addMovie(id: Int, sessionId: String, mediaId: Long) {
        viewModelScope.launch {
            historyRepository.addMovie(id, sessionId, mediaId)
        }
    }

    fun removeMovie(id: Int, sessionId: String, mediaId: Long) {
        viewModelScope.launch {
            historyRepository.removeMovie(id, sessionId, mediaId)
        }
    }

    fun markAsFavorite(id: Long, type: String, mark: Boolean, sessionId: String) {
        viewModelScope.launch {
            _markAsFavState.value = detailsRepository.markAsFavorite(id, type, mark, sessionId)
        }
    }

    fun addToWatchlist(id: Long, type: String, add: Boolean, sessionId: String) {
        viewModelScope.launch {
            _addToWatchlistState.value = detailsRepository.addToWatchlist(id, type, add, sessionId)
        }
    }

    fun rateMovie(id: Long, sessionId: String, rating: Float) {
        viewModelScope.launch {
            _ratedState.value = detailsRepository.rateMovie(id, sessionId, rating)
        }
    }

    fun rateTv(id: Long, sessionId: String, rating: Float) {
        viewModelScope.launch {
            _ratedState.value = detailsRepository.rateTv(id, sessionId, rating)
        }
    }

    fun loadMovieStates(id: Long, sessionId: String) {
        viewModelScope.launch {
            _movieStates.value = detailsRepository.getMovieAccountStates(id, sessionId)
        }
    }

    fun loadTvStates(id: Long, sessionId: String) {
        viewModelScope.launch {
            _movieStates.value = detailsRepository.getTvAccountStates(id, sessionId)
        }
    }
}