package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.pojo.Film
import com.example.myapplication.models.pojo.TV
import com.example.myapplication.repository.repositories.search_screen.RatedItemsRepository
import com.example.myapplication.repository.repositories.search_screen.SearchRepository
import com.example.myapplication.states.SearchScreenViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val ratedItemsRepository: RatedItemsRepository,
) : ViewModel() {

    private lateinit var viewProxy: SearchView

    private var _ratedFilms: MutableLiveData<SearchScreenViewState> = MutableLiveData()
    val ratedFilms: LiveData<SearchScreenViewState>
        get() = _ratedFilms

    private var _ratedTVs: MutableLiveData<SearchScreenViewState> = MutableLiveData()
    val ratedTVs: LiveData<SearchScreenViewState>
        get() = _ratedTVs

    private var _ratedEpisodes: MutableLiveData<SearchScreenViewState> = MutableLiveData()
    val ratedEpisodes: LiveData<SearchScreenViewState>
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
        _ratedFilms.value = SearchScreenViewState.Loading
        _ratedTVs.value = SearchScreenViewState.Loading
        _ratedEpisodes.value = SearchScreenViewState.Loading
        viewModelScope.launch {
            executeLoadRatedEpisodes(sessionId)
            executeLoadRatedFilms(sessionId)
            executeLoadRatedTvs(sessionId)
        }
    }

    fun loadRatedFilms(sessionId: String) {
        _ratedFilms.value = SearchScreenViewState.Loading
        viewModelScope.launch {
            executeLoadRatedFilms(sessionId)
        }
    }

    fun loadRatedTvs(sessionId: String) {
        _ratedTVs.value = SearchScreenViewState.Loading
        viewModelScope.launch {
            executeLoadRatedTvs(sessionId)
        }
    }

    fun loadRatedEpisodes(sessionId: String) {
        _ratedEpisodes.value = SearchScreenViewState.Loading
        viewModelScope.launch {
            executeLoadRatedEpisodes(sessionId)
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

    private suspend fun executeLoadRatedFilms(sessionId: String) {
        _ratedFilms.value = when (val result = ratedItemsRepository.executeLoadingRatedFilms(sessionId)) {
            is RatedItemsRepository.Result.Success.FilmSuccess ->
                SearchScreenViewState.Success.FilmSuccess(result.films)
            else -> SearchScreenViewState.Error
        }
    }

    private suspend fun executeLoadRatedTvs(sessionId: String) {
        _ratedTVs.value = when (val result = ratedItemsRepository.executeLoadingRatedTvs(sessionId)) {
            is RatedItemsRepository.Result.Success.TvSuccess ->
                SearchScreenViewState.Success.TvSuccess(result.tvs)
            else -> SearchScreenViewState.Error
        }
    }

    private suspend fun executeLoadRatedEpisodes(sessionId: String) {
        _ratedEpisodes.value = when (val result = ratedItemsRepository.executeLoadingRatedEpisodes(sessionId)) {
            is RatedItemsRepository.Result.Success.EpisodesSuccess ->
                SearchScreenViewState.Success.EpisodeSuccess(result.episodes)
            else -> SearchScreenViewState.Error
        }
    }

    interface SearchView {
        var showError: (error: ErrorType) -> Unit
    }

    enum class ErrorType {
        NO_RESULT, SERVER_ERROR
    }
}