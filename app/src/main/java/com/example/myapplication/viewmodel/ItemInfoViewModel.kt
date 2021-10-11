package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.movies.Movie
import com.example.myapplication.repository.DetailsRepository
import com.example.myapplication.repository.HistoryRepository
import com.example.myapplication.ui.ItemInfoActivity
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
}