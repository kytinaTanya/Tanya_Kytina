package com.example.myapplication.repository.repositories.itemsinfos

import com.example.myapplication.models.pojo.view.TvView

interface TvInfoRepository {
    suspend fun execute(id: Long, sessionId: String): Result

    sealed class Result {
        data class Success(val data: TvView) : Result()
        object Error : Result()
    }
}