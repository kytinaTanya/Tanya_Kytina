package com.example.myapplication.repository.repositories.itemsinfos.rate

import com.example.myapplication.repository.repositories.itemsinfos.RateItemResult

interface RateEpisodeRepository {
    suspend fun execute(
        tvId: Long,
        season: Int,
        episode: Int,
        sessionId: String,
        rating: Float
    ): RateItemResult
}