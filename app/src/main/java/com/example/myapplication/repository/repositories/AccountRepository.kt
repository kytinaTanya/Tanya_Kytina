package com.example.myapplication.repository.repositories

import okhttp3.MultipartBody

interface AccountRepository {
    fun uploadImage(file: MultipartBody.Part, onSuccess: (String) -> Unit)
}