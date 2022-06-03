package com.example.myapplication.states

sealed class PhotoUploadState {
    data class Success(val url: String) : PhotoUploadState()
    object Loading : PhotoUploadState()
    object Error : PhotoUploadState()
}