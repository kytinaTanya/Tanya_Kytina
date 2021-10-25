package com.example.myapplication.repository.impl

import android.util.Log
import com.example.myapplication.repository.repositories.AccountRepository
import com.example.myapplication.repository.services.ImageService
import okhttp3.MultipartBody

class AccountRepositoryImpl(private val service: ImageService): AccountRepository {
    override suspend fun uploadImage(file: MultipartBody.Part) {
        val response = service.uploadImage(file = file)

    }

    override suspend fun getImage(id: String) {
        TODO("Not yet implemented")
    }
}