package com.example.myapplication.repository.repositories

import java.io.InputStream

interface AccountRepository {
    suspend fun uploadImage(inputStream: InputStream) : Result

    sealed class Result {
        data class Success(val url : String) : Result()
        object Error : Result()
    }
}