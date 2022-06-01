package com.example.myapplication.viewmodel.itemsinfos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.repositories.itemsinfos.SeasonInfoRepository
import com.example.myapplication.states.SeasonInfoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeasonInfoViewModel @Inject constructor(
    private val seasonInfoRepository: SeasonInfoRepository
) : ViewModel() {

    private var _seasonInfo: MutableLiveData<SeasonInfoState> = MutableLiveData()
    val seasonInfo: LiveData<SeasonInfoState>
        get() = _seasonInfo

    fun execute(tvId: Long, seasonNum: Int) {
        _seasonInfo.value = SeasonInfoState.Loading
        viewModelScope.launch {
            _seasonInfo.value = when (val result = seasonInfoRepository.execute(tvId, seasonNum)) {
                SeasonInfoRepository.Result.Error -> SeasonInfoState.Error
                is SeasonInfoRepository.Result.Success -> SeasonInfoState.Success(result.data)
            }
        }
    }
}