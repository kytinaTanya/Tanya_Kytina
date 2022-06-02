package com.example.myapplication.repository.repositories.itemsinfos

import com.example.myapplication.models.pojo.BaseItemDetails

interface EpisodeInfoRepository {
    suspend fun execute(tvId: Long, seasonNum: Int, episodeNum: Int, sessionId: String): Result

    sealed class Result {
        data class Success(val data: BaseItemDetails.EpisodeDetails) : Result()
        object Error : Result()
    }
}