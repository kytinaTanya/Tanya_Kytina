package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.pojo.Episode
import com.example.myapplication.models.pojo.Film
import com.example.myapplication.models.pojo.TV
import com.example.myapplication.repository.repositories.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val historyRepository: HistoryRepository) : ViewModel() {

    private var _ratedFilms: MutableLiveData<List<Film>> = MutableLiveData()
    val ratedFilms: LiveData<List<Film>>
        get() = _ratedFilms

    private var _ratedTVs: MutableLiveData<List<TV>> = MutableLiveData()
    val ratedTVs: LiveData<List<TV>>
        get() = _ratedTVs

    private var _ratedEpisodes: MutableLiveData<List<Episode>> = MutableLiveData()
    val ratedEpisodes: LiveData<List<Episode>>
        get() = _ratedEpisodes

    private var _searchMovies: MutableLiveData<List<Film>> = MutableLiveData()
    val searchMovies: LiveData<List<Film>>
        get() = _searchMovies

    private var _searchTvs: MutableLiveData<List<TV>> = MutableLiveData()
    val searchTvs: LiveData<List<TV>>
        get() = _searchTvs

    fun loadRatedFilms(sessionId: String) {
        viewModelScope.launch {
            _ratedFilms.value = historyRepository.getRatedMovies(sessionId)
        }
    }

    fun loadRatedTVs(sessionId: String) {
        viewModelScope.launch {
            _ratedTVs.value = historyRepository.getRatedTvs(sessionId)
        }
    }

    fun loadRatedEpisodes(sessionId: String) {
        viewModelScope.launch {
            _ratedEpisodes.value = historyRepository.getRatedEpisodes(sessionId)
        }
    }

    fun searchMovie(query: String) {
        viewModelScope.launch {
            _searchMovies.value = historyRepository.searchMovie(query)
        }
    }

    fun searchTV(query: String) {
        viewModelScope.launch {
            _searchTvs.value = historyRepository.searchTV(query)
        }
    }
}