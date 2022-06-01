package com.example.myapplication.repository.repositories.itemsinfos

import com.example.myapplication.models.pojo.BaseItemDetails

interface SeasonInfoRepository {
    suspend fun execute(tvId: Long, seasonNum: Int): Result

    sealed class Result {
        data class Success(val data: BaseItemDetails.SeasonDetails) : Result()
        object Error : Result()
    }
}