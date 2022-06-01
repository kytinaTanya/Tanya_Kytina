package com.example.myapplication.viewmodel.itemsinfos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.repositories.itemsinfos.PersonInfoRepository
import com.example.myapplication.states.PersonInfoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonInfoViewModel @Inject constructor(
    private val personInfoRepository: PersonInfoRepository
) : ViewModel() {

    private var _personInfo: MutableLiveData<PersonInfoState> = MutableLiveData()
    val personInfo: LiveData<PersonInfoState>
        get() = _personInfo

    fun execute(id: Long) {
        _personInfo.value = PersonInfoState.Loading
        viewModelScope.launch {
            _personInfo.value =
                when (val result = personInfoRepository.execute(id)) {
                    PersonInfoRepository.Result.Error -> PersonInfoState.Error
                    is PersonInfoRepository.Result.Success -> PersonInfoState.Success(result.data)
                }
        }
    }
}