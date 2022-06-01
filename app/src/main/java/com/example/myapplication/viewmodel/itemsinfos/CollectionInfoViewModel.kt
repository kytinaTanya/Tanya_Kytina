package com.example.myapplication.viewmodel.itemsinfos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.repositories.itemsinfos.CollectionInfoRepository
import com.example.myapplication.states.CollectionInfoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionInfoViewModel @Inject constructor(
    private val collectionInfoRepository: CollectionInfoRepository
) : ViewModel() {

    private var _collectionInfo: MutableLiveData<CollectionInfoState> = MutableLiveData()
    val collectionInfo: LiveData<CollectionInfoState>
        get() = _collectionInfo

    fun execute(id: Int) {
        _collectionInfo.value = CollectionInfoState.Loading
        viewModelScope.launch {
            _collectionInfo.value = when (val result = collectionInfoRepository.execute(id)) {
                CollectionInfoRepository.Result.Error -> CollectionInfoState.Error
                is CollectionInfoRepository.Result.Success -> CollectionInfoState.Success(result.data)
            }
        }
    }
}