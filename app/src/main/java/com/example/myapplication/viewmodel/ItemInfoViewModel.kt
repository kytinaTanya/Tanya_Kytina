package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.history.PostResponseStatus
import com.example.myapplication.models.pojo.*
import com.example.myapplication.models.pojo.view.MovieView
import com.example.myapplication.models.pojo.view.PersonView
import com.example.myapplication.models.pojo.view.TvView
import com.example.myapplication.repository.repositories.DetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemInfoViewModel @Inject constructor(
    private val detailsRepository: DetailsRepository
) : ViewModel() {

    private val tag = ItemInfoViewModel::class.java.simpleName

    private var _movieDetails: MutableLiveData<MovieView> = MutableLiveData()
    val movieDetails: LiveData<MovieView>
        get() = _movieDetails

    private var _tvDetails: MutableLiveData<TvView> = MutableLiveData()
    val tvDetails: LiveData<TvView>
        get() = _tvDetails

    private var _personDetails: MutableLiveData<PersonView> = MutableLiveData()
    val personDetails: LiveData<PersonView>
        get() = _personDetails

    private var _baseItemDetails: MutableLiveData<BaseItem> = MutableLiveData()
    val baseItemDetails: LiveData<BaseItem>
    get() = _baseItemDetails

    private var _profilePaths: MutableLiveData<List<ImageUrlPath>> = MutableLiveData()
    val profilePaths: LiveData<List<ImageUrlPath>>
        get() = _profilePaths

    private var _addToWatchlistState: MutableLiveData<PostResponseStatus> = MutableLiveData()
    val addToWatchlistState: LiveData<PostResponseStatus>
        get() = _addToWatchlistState

    private var _markAsFavState: MutableLiveData<PostResponseStatus> = MutableLiveData()
    val markAsFavState: LiveData<PostResponseStatus>
        get() = _markAsFavState

    private var _ratedState: MutableLiveData<PostResponseStatus> = MutableLiveData()
    val ratedState: LiveData<PostResponseStatus>
        get() = _ratedState

    fun loadFilmDetails(id: Long, sessionId: String) {
        _movieDetails = MutableLiveData()
        viewModelScope.launch {
            _movieDetails.value = detailsRepository.getMovieView(id, sessionId)
        }
    }

    fun loadTVDetails(id: Long, sessionId: String) {
        _tvDetails = MutableLiveData()
        viewModelScope.launch {
            _tvDetails.value = detailsRepository.getTVView(id, sessionId)
        }
    }

    fun loadPersonDetails(id: Long) {
        _personDetails = MutableLiveData()
        viewModelScope.launch {
            _personDetails.value = detailsRepository.getPersonView(id)
        }
    }

    fun loadSeasonDetails(showId: Long, season: Int) {
        _baseItemDetails = MutableLiveData()
        viewModelScope.launch {
            _baseItemDetails.value = detailsRepository.getSeasonDetails(showId, season)
        }
    }

    fun loadEpisodeDetails(showId: Long, season: Int, episode: Int) {
        _baseItemDetails = MutableLiveData()
        viewModelScope.launch {
            _baseItemDetails.value = detailsRepository.getEpisodeDetails(showId, season, episode)
        }
    }

    fun loadCollectionDetails(id: Int) {
        _baseItemDetails = MutableLiveData()
        viewModelScope.launch {
            _baseItemDetails.value = detailsRepository.getCollectionsDetails(id)
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
}