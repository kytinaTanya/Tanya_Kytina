package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.pojo.Episode
import com.example.myapplication.models.pojo.Film
import com.example.myapplication.models.pojo.TV
import com.example.myapplication.repository.repositories.search_screen.RatedItemsRepository
import com.example.myapplication.repository.repositories.search_screen.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val ratedItemsRepository: RatedItemsRepository,
) : ViewModel() {

    private lateinit var viewProxy: SearchView

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

    fun init(viewProxy: SearchView) {
        this.viewProxy = viewProxy
    }

    fun loadRatedItems(sessionId: String) {
        viewModelScope.launch {
            executeGetRatedItems(sessionId)
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            executeSearch(query)
        }
    }

    private suspend fun executeSearch(query: String) {
        when (val result = searchRepository.execute(query)) {
            is SearchRepository.Result.Success -> {
                _searchTvs.value = result.tvs
                _searchMovies.value = result.movies
            }
            SearchRepository.Result.ResultError -> viewProxy.showError(ErrorType.NO_RESULT)
            SearchRepository.Result.ServerError -> viewProxy.showError(ErrorType.SERVER_ERROR)
        }
    }

    private suspend fun executeGetRatedItems(sessionId: String) {
        when (val result = ratedItemsRepository.execute(sessionId)) {
            is RatedItemsRepository.Result.Success -> {
                _ratedFilms.value = result.movies
                _ratedTVs.value = result.tvs
                _ratedEpisodes.value = result.episodes
            }
            RatedItemsRepository.Result.ServerError -> viewProxy.showError(ErrorType.SERVER_ERROR)
        }
    }

    interface SearchView {
        var showError: (error: ErrorType) -> Unit
    }

    enum class ErrorType {
        NO_RESULT, SERVER_ERROR
    }
}