package com.example.myapplication.repository.repositories

import okhttp3.MultipartBody

interface AccountRepository {
    suspend fun uploadImage(file: MultipartBody.Part)
    suspend fun getImage(id: String)
}