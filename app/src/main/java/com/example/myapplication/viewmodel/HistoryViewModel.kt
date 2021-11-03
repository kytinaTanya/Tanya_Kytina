package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.movies.Episode
import com.example.myapplication.models.movies.Film
import com.example.myapplication.models.movies.TV
import com.example.myapplication.repository.repositories.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val historyRepository: HistoryRepository) : ViewModel() {
    private var _filmsHistory: MutableLiveData<List<Film>> = MutableLiveData()
    val filmsHistory: LiveData<List<Film>>
        get() = _filmsHistory

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

    fun loadHistory(id: Int, sessionId: String) {
        viewModelScope.launch {
            _filmsHistory.value = historyRepository.getDetailsAboutHistoryList(id, sessionId)
        }
    }

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
}