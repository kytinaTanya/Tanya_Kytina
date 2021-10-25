package com.example.myapplication.repository.repositories

import com.example.myapplication.models.RequestToken

interface AuthRepository {
    suspend fun getRequestToken() : String
    suspend fun createSessionId(requestToken: String) : String
}