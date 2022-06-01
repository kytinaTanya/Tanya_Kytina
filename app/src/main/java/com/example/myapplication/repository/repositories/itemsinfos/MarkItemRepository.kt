package com.example.myapplication.repository.repositories.itemsinfos

import com.example.myapplication.models.history.PostResponseStatus

interface MarkItemRepository {
    suspend fun markAsFavorite(id: Long, type: String, mark: Boolean, sessionId: String): Result
    suspend fun addToWatchlist(id: Long, type: String, add: Boolean, sessionId: String): Result

    sealed class Result {
        data class Success(val data: PostResponseStatus) : Result()
        object Error : Result()
    }
}