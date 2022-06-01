package com.example.myapplication.repository.repositories.itemsinfos

import com.example.myapplication.models.history.PostResponseStatus

interface RateMovieRepository {
    suspend fun execute(id: Long, sessionId: String, rating: Float): RateItemResult
}

sealed class RateItemResult {
    data class Success(val data: PostResponseStatus) : RateItemResult()
    object Error : RateItemResult()
}