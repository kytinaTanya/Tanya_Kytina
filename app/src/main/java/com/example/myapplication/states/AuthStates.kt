package com.example.myapplication.states

sealed class StringAuthState : ViewState {
    data class Success(val data: String) : StringAuthState()
    object Error : StringAuthState()
    object Loading : StringAuthState()
}

sealed class IntAuthState : ViewState {
    data class Success(val data: Int) : IntAuthState()
    object Error : IntAuthState()
    object Loading : IntAuthState()
}