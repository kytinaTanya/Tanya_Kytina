package com.example.myapplication.viewmodel.itemsinfos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.repositories.itemsinfos.MarkItemRepository
import com.example.myapplication.repository.repositories.itemsinfos.RateItemResult
import com.example.myapplication.repository.repositories.itemsinfos.TvInfoRepository
import com.example.myapplication.repository.repositories.itemsinfos.rate.RateTvRepository
import com.example.myapplication.states.MarkedState
import com.example.myapplication.states.RatedState
import com.example.myapplication.states.TvInfoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvInfoViewModel @Inject constructor(
    private val tvInfoRepository: TvInfoRepository,
    private val rateTvRepository: RateTvRepository,
    private val markItemRepository: MarkItemRepository,
) : ViewModel() {

    private var _tvInfo: MutableLiveData<TvInfoState> = MutableLiveData()
    val tvInfo: LiveData<TvInfoState>
        get() = _tvInfo

    private var _ratedState: MutableLiveData<RatedState> = MutableLiveData()
    val ratedState: LiveData<RatedState>
        get() = _ratedState

    private var _markAsFavoriteState: MutableLiveData<MarkedState> = MutableLiveData()
    val markAsFavoriteState: LiveData<MarkedState>
        get() = _markAsFavoriteState

    private var _addToWatchlistState: MutableLiveData<MarkedState> = MutableLiveData()
    val addToWatchlistState: LiveData<MarkedState>
        get() = _addToWatchlistState

    fun loadData(id: Long, sessionId: String) {
        _tvInfo.value = TvInfoState.Loading
        viewModelScope.launch {
            _tvInfo.value =
                when (val result = tvInfoRepository.execute(id, sessionId)) {
                    TvInfoRepository.Result.Error -> TvInfoState.Error
                    is TvInfoRepository.Result.Success -> TvInfoState.Success(result.data)
                }
        }
    }

    fun rateTv(id: Long, sessionId: String, rating: Float) {
        _ratedState.value = RatedState.Loading
        viewModelScope.launch {
            _ratedState.value =
                when (val result = rateTvRepository.execute(id, sessionId, rating)) {
                    RateItemResult.Error -> RatedState.Error
                    is RateItemResult.Success -> RatedState.Success(result.data)
                    else -> RatedState.Error
                }
        }
    }

    fun markAsFavorite(id: Long, mark: Boolean, sessionId: String) {
        _markAsFavoriteState.value = MarkedState.Loading
        viewModelScope.launch {
            _markAsFavoriteState.value =
                when (val result = markItemRepository.markAsFavorite(id, "tv", mark, sessionId)) {
                    MarkItemRepository.Result.Error -> MarkedState.Error
                    is MarkItemRepository.Result.Success -> MarkedState.Success(result.data)
                }
        }
    }

    fun addToWatchlist(id: Long, add: Boolean, sessionId: String) {
        _addToWatchlistState.value = MarkedState.Loading
        viewModelScope.launch {
            _addToWatchlistState.value =
                when (val result = markItemRepository.addToWatchlist(id, "tv", add, sessionId)) {
                    MarkItemRepository.Result.Error -> MarkedState.Error
                    is MarkItemRepository.Result.Success -> MarkedState.Success(result.data)
                }
        }
    }
}