package com.example.myapplication.repository.repositories.itemsinfos

import com.example.myapplication.models.pojo.view.MovieView

interface MovieInfoRepository {
    suspend fun execute(id: Long, sessionId: String): Result

    sealed class Result {
        data class Success(val data: MovieView) : Result()
        object Error : Result()
    }
}