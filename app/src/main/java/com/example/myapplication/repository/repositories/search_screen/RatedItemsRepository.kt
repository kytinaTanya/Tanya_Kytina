package com.example.myapplication.repository.repositories.search_screen

import com.example.myapplication.models.pojo.Episode
import com.example.myapplication.models.pojo.Film
import com.example.myapplication.models.pojo.TV

interface RatedItemsRepository {
    suspend fun executeLoadingRatedFilms(sessionId: String): Result
    suspend fun executeLoadingRatedTvs(sessionId: String): Result
    suspend fun executeLoadingRatedEpisodes(sessionId: String): Result

    sealed class Result {
        sealed class Success : RatedItemsRepository.Result() {
            data class FilmSuccess(val films: List<Film>) : Success()
            data class TvSuccess(val tvs: List<TV>) : Success()
            data class EpisodesSuccess(val episodes: List<Episode>) : Success()
        }
        object ServerError : RatedItemsRepository.Result()
    }
}