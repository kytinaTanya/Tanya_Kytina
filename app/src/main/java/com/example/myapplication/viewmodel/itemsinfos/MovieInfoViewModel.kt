package com.example.myapplication.viewmodel.itemsinfos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.repositories.itemsinfos.MarkItemRepository
import com.example.myapplication.repository.repositories.itemsinfos.MovieInfoRepository
import com.example.myapplication.repository.repositories.itemsinfos.RateItemResult
import com.example.myapplication.repository.repositories.itemsinfos.RateMovieRepository
import com.example.myapplication.states.FilmInfoStates
import com.example.myapplication.states.MarkedState
import com.example.myapplication.states.RatedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieInfoViewModel @Inject constructor(
    private val movieInfoRepository: MovieInfoRepository,
    private val rateMovieRepository: RateMovieRepository,
    private val markItemRepository: MarkItemRepository,
) : ViewModel() {

    private val tag = MovieInfoViewModel::class.java.simpleName

    private var _movieDetails: MutableLiveData<FilmInfoStates> = MutableLiveData()
    val movieDetails: LiveData<FilmInfoStates>
        get() = _movieDetails

    private var _ratedState: MutableLiveData<RatedState> = MutableLiveData()
    val ratedState: LiveData<RatedState>
        get() = _ratedState

    private var _markAsFavoriteState: MutableLiveData<MarkedState> = MutableLiveData()
    val markAsFavoriteState: LiveData<MarkedState>
        get() = _markAsFavoriteState

    private var _addToWatchlistState: MutableLiveData<MarkedState> = MutableLiveData()
    val addToWatchlistState: LiveData<MarkedState>
        get() = _addToWatchlistState

    fun loadFilmDetails(id: Long, sessionId: String) {
        _movieDetails.value = FilmInfoStates.Loading
        viewModelScope.launch {
            _movieDetails.value =
                when (val result = movieInfoRepository.execute(id, sessionId)) {
                    MovieInfoRepository.Result.Error -> FilmInfoStates.Error
                    is MovieInfoRepository.Result.Success ->
                        FilmInfoStates.Success(result.data)
                }
        }
    }

    fun rateMovie(id: Long, sessionId: String, rating: Float) {
        _ratedState.value = RatedState.Loading
        viewModelScope.launch {
            _ratedState.value =
                when (val result = rateMovieRepository.execute(id, sessionId, rating)) {
                    RateItemResult.Error -> RatedState.Error
                    is RateItemResult.Success -> RatedState.Success(result.data)
                    else -> RatedState.Error
                }
        }
    }

    fun markAsFavorite(id: Long, mark: Boolean, sessionId: String) {
        _markAsFavoriteState.value = MarkedState.Loading
        viewModelScope.launch {
            _markAsFavoriteState.value = when (val result =
                markItemRepository.markAsFavorite(id, "movie", mark, sessionId)) {
                MarkItemRepository.Result.Error -> MarkedState.Error
                is MarkItemRepository.Result.Success -> MarkedState.Success(result.data)
            }
        }
    }

    fun addToWatchlist(id: Long, add: Boolean, sessionId: String) {
        _addToWatchlistState.value = MarkedState.Loading
        viewModelScope.launch {
            _addToWatchlistState.value =
                when (val result = markItemRepository.addToWatchlist(id, "movie", add, sessionId)) {
                    MarkItemRepository.Result.Error -> MarkedState.Error
                    is MarkItemRepository.Result.Success -> MarkedState.Success(result.data)
                }
        }
    }
}