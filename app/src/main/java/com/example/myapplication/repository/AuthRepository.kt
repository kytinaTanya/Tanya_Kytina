package com.example.myapplication.repository

interface AuthRepository {
    suspend fun getRequestToken() : String
    suspend fun createSessionId() : String
}