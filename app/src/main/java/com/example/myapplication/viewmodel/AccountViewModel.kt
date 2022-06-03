package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.repositories.AccountRepository
import com.example.myapplication.states.PhotoUploadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {
    private var _profileImageUrl: MutableLiveData<PhotoUploadState> = MutableLiveData()
    val profileImageUrl: LiveData<PhotoUploadState>
        get() = _profileImageUrl

    fun uploadImage(inputStream: InputStream) {
        _profileImageUrl.value = PhotoUploadState.Loading
        viewModelScope.launch {
            _profileImageUrl.value = when (val result = accountRepository.uploadImage(inputStream)) {
                AccountRepository.Result.Error -> PhotoUploadState.Error
                is AccountRepository.Result.Success -> PhotoUploadState.Success(result.url)
            }
        }

    }
}