package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.history.PostResponseStatus
import com.example.myapplication.models.marks.AccountStates
import com.example.myapplication.models.movies.BaseItem
import com.example.myapplication.models.movies.Cast
import com.example.myapplication.models.movies.ImageUrlPath
import com.example.myapplication.models.movies.VideoResult
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

    private var _baseItemDetails: MutableLiveData<BaseItem> = MutableLiveData()
    val baseItemDetails: LiveData<BaseItem>
    get() = _baseItemDetails

    private var _movieVideos: MutableLiveData<List<VideoResult>> = MutableLiveData()
    val movieVideos: LiveData<List<VideoResult>>
        get() = _movieVideos

    private var _posterPaths: MutableLiveData<List<ImageUrlPath>> = MutableLiveData()
    val posterPaths: LiveData<List<ImageUrlPath>>
        get() = _posterPaths

    private var _backdropPaths: MutableLiveData<List<ImageUrlPath>> = MutableLiveData()
    val backdropPaths: LiveData<List<ImageUrlPath>>
        get() = _backdropPaths

    private var _profilePaths: MutableLiveData<List<ImageUrlPath>> = MutableLiveData()
    val profilePaths: LiveData<List<ImageUrlPath>>
        get() = _profilePaths

    private var _cast: MutableLiveData<List<Cast>> = MutableLiveData()
    val cast: LiveData<List<Cast>>
        get() = _cast

    private var _recommendations: MutableLiveData<List<BaseItem>> = MutableLiveData()
    val recommendations: LiveData<List<BaseItem>>
        get() = _recommendations

    private var _similar: MutableLiveData<List<BaseItem>> = MutableLiveData()
    val similar: LiveData<List<BaseItem>>
        get() = _similar

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
        _baseItemDetails = MutableLiveData()
        viewModelScope.launch {
            _baseItemDetails.value = detailsRepository.getMovieDetails(id)
        }
    }

    fun loadMovieImages(id: Long) {
        _posterPaths = MutableLiveData()
        _backdropPaths = MutableLiveData()
        viewModelScope.launch {
            _backdropPaths.value = detailsRepository.getMoviesImages(id).backdrops
            _posterPaths.value = detailsRepository.getMoviesImages(id).posters
        }
    }

    fun loadMovieCast(id: Long) {
        _cast = MutableLiveData()
        viewModelScope.launch {
            _cast.value = detailsRepository.getMoviesCredits(id).cast
        }
    }

    fun loadMovieVideos(id: Long) {
        _movieVideos = MutableLiveData()
        viewModelScope.launch {
            _movieVideos.value = detailsRepository.getMovieVideos(id)
        }
    }

    fun loadMovieRecommendations(id: Long) {
        _recommendations = MutableLiveData()
        viewModelScope.launch {
            _recommendations.value = detailsRepository.getRecommendationMovies(id)
        }
    }

    fun loadMovieSimilar(id: Long) {
        _similar = MutableLiveData()
        viewModelScope.launch {
            _similar.value = detailsRepository.getSimilarMovies(id)
        }
    }

    fun loadTVDetails(id: Long) {
        _baseItemDetails = MutableLiveData()
        viewModelScope.launch {
            _baseItemDetails.value = detailsRepository.getTVDetails(id)
        }
    }

    fun loadTvImages(id: Long) {
        _posterPaths = MutableLiveData()
        _backdropPaths = MutableLiveData()
        viewModelScope.launch {
            _backdropPaths.value = detailsRepository.getTvsImages(id).backdrops
            _posterPaths.value = detailsRepository.getTvsImages(id).posters
        }
    }

    fun loadTvCast(id: Long) {
        _cast = MutableLiveData()
        viewModelScope.launch {
            _cast.value = detailsRepository.getTvsCredits(id).cast
        }
    }

    fun loadTvVideos(id: Long) {
        _movieVideos = MutableLiveData()
        viewModelScope.launch {
            _movieVideos.value = detailsRepository.getTvVideos(id)
        }
    }

    fun loadTvRecommendations(id: Long) {
        _recommendations = MutableLiveData()
        viewModelScope.launch {
            _recommendations.value = detailsRepository.getRecommendationTvs(id)
        }
    }

    fun loadTvSimilar(id: Long) {
        _similar = MutableLiveData()
        viewModelScope.launch {
            _similar.value = detailsRepository.getSimilarTvs(id)
        }
    }

    fun loadPersonDetails(id: Long) {
        _baseItemDetails = MutableLiveData()
        viewModelScope.launch {
            _baseItemDetails.value = detailsRepository.getPersonDetails(id)
        }
    }

    fun loadPersonImages(id: Long) {
        _profilePaths = MutableLiveData()
        viewModelScope.launch {
            _profilePaths.value = detailsRepository.getPersonsImages(id)
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