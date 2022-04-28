package com.example.myapplication.repository.repositories.search_screen

import com.example.myapplication.models.pojo.Episode
import com.example.myapplication.models.pojo.Film
import com.example.myapplication.models.pojo.TV

interface RatedItemsRepository {
    suspend fun execute(sessionId: String): RatedItemsRepository.Result

    sealed class Result {
        data class Success(
            val movies: List<Film>,
            val tvs: List<TV>,
            val episodes: List<Episode>
        ) : RatedItemsRepository.Result()
        object ServerError : RatedItemsRepository.Result()
    }
}