package com.example.myapplication.repository.repositories.itemsinfos.rate

import com.example.myapplication.repository.repositories.itemsinfos.RateItemResult

interface RateTvRepository {
    suspend fun execute(id: Long, sessionId: String, rating: Float): RateItemResult
}