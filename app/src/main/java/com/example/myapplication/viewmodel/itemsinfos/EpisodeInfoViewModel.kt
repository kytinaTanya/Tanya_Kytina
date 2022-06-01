package com.example.myapplication.viewmodel.itemsinfos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.repositories.itemsinfos.EpisodeInfoRepository
import com.example.myapplication.repository.repositories.itemsinfos.RateItemResult
import com.example.myapplication.repository.repositories.itemsinfos.rate.RateEpisodeRepository
import com.example.myapplication.states.EpisodeInfoState
import com.example.myapplication.states.RatedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeInfoViewModel @Inject constructor(
    private val episodeInfoRepository: EpisodeInfoRepository,
    private val rateEpisodeRepository: RateEpisodeRepository,
) : ViewModel() {

    private var _episodeInfo: MutableLiveData<EpisodeInfoState> = MutableLiveData()
    val episodeInfo: LiveData<EpisodeInfoState>
        get() = _episodeInfo

    private var _ratedState: MutableLiveData<RatedState> = MutableLiveData()
    val ratedState: LiveData<RatedState>
        get() = _ratedState

    fun loadInfo(tvId: Long, seasonNum: Int, episodeNum: Int) {
        _episodeInfo.value = EpisodeInfoState.Loading
        viewModelScope.launch {
            _episodeInfo.value =
                when (val result = episodeInfoRepository.execute(tvId, seasonNum, episodeNum)) {
                    EpisodeInfoRepository.Result.Error -> EpisodeInfoState.Error
                    is EpisodeInfoRepository.Result.Success -> EpisodeInfoState.Success(result.data)
                }
        }
    }

    fun rate(tvId: Long, season: Int, episode: Int, sessionId: String, rating: Float) {
        _ratedState.value = RatedState.Loading
        viewModelScope.launch {
            _ratedState.value = when (val result = rateEpisodeRepository.execute(tvId, season, episode, sessionId, rating)) {
                RateItemResult.Error -> RatedState.Error
                is RateItemResult.Success -> RatedState.Success(result.data)
                else -> RatedState.Error
            }
        }
    }
}