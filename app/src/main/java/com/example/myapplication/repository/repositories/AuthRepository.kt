package com.example.myapplication.repository.repositories

interface AuthRepository {
    suspend fun getRequestToken() : StringResult
    suspend fun createSessionId(requestToken: String) : StringResult
    suspend fun getAccountInfo(sessionId: String) : IntResult

    sealed class StringResult {
        data class Success(val str: String) : StringResult()
        object Error : StringResult()
    }

    sealed class IntResult {
        data class Success(val num: Int) : IntResult()
        object Error : IntResult()
    }
}