package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.SessionId
import com.example.myapplication.repository.AuthRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(private val mRepository: AuthRepository) : ViewModel() {
    private val tag = AuthViewModel::class.java.simpleName

    private var _sessionId: MutableLiveData<String> = MutableLiveData()
    val sessionId: LiveData<String>
    get() {
        return _sessionId
    }

    fun createSessionId() {
        viewModelScope.launch {
            _sessionId.value = mRepository.createSessionId()
        }
    }
}