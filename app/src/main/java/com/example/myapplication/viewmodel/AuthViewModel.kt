package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.repositories.AuthRepository
import com.example.myapplication.states.IntAuthState
import com.example.myapplication.states.StringAuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val mRepository: AuthRepository) : ViewModel() {
    private val tag = AuthViewModel::class.java.simpleName

    private var _requestToken = MutableLiveData<StringAuthState>()
    val requestToken: LiveData<StringAuthState>
        get() {
            return _requestToken
        }

    fun getRequestToken() {
        _requestToken.value = StringAuthState.Loading
        viewModelScope.launch {
            _requestToken.value = when (val result = mRepository.getRequestToken()) {
                AuthRepository.StringResult.Error -> StringAuthState.Error
                is AuthRepository.StringResult.Success -> StringAuthState.Success(result.str)
            }
        }
    }

    private var _sessionId = MutableLiveData<StringAuthState>()
    val sessionId: LiveData<StringAuthState>
        get() {
            return _sessionId
        }

    fun getSessionId(token: String) {
        _sessionId.value = StringAuthState.Loading
        viewModelScope.launch {
            _sessionId.value = when (val result = mRepository.createSessionId(token)) {
                AuthRepository.StringResult.Error -> StringAuthState.Error
                is AuthRepository.StringResult.Success -> StringAuthState.Success(result.str)
            }
        }
    }

    private var _accountId = MutableLiveData<IntAuthState>()
    val accountId: LiveData<IntAuthState>
        get() {
            return _accountId
        }

    fun getAccountInfo(sessionId: String) {
        _accountId.value = IntAuthState.Loading
        viewModelScope.launch {
            _accountId.value = when (val result = mRepository.getAccountInfo(sessionId)) {
                AuthRepository.IntResult.Error -> IntAuthState.Error
                is AuthRepository.IntResult.Success -> IntAuthState.Success(result.num)
            }
        }
    }
}